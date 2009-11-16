package kulka.gui;

import static java.lang.System.err;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import kulka.lang.Environment;
import kulka.lang.KulkaEvaluator;
import kulka.lang.KulkaLexer;
import kulka.lang.KulkaParser;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;

@SuppressWarnings("serial")
public class XKulka extends JPanel {

	// elements of the interpreter
	static private Environment globalEnv;

	static private CommonTreeNodeStream nodes;

	// GUI
	private final static String APP_NAME = "Xkulka";

	private final static String newline = "\n";

	// some messages
	private final static String ABORT_MESSAGE = "Action aborted";

	private final static String INFO_MESSAGE = "|info>";

	// user interface
	private static JFrame mainFrame;

	private JTextField inputField;

	private JTextArea outputArea;

	private TextAreaPrintStream outputStream;

	private JButton execButton;

	private KulkaLexer lexer;

	private KulkaParser parser;

	@SuppressWarnings("unused")
	private KulkaEvaluator interpreter;

	/*
	 * Objects implementing actions
	 */

	private UnimplementedAction unimplementedAction;

	// File menu
	private ExecFileAction execFileAction;

	private SaveFileAction saveFileAction;

	private ExitAction exitAction;

	// Run menu
	private ExecuteAction execAction;

	private RestartAction restartAction;

	private ClearAction clearAction;

	// Help menu
	private AboutAction aboutAction;

	private ManualAction manualAction;

	@SuppressWarnings("unused")
	private OptionsAction optionsAction;

	public XKulka() {
		super(new GridBagLayout());

		// build objects for actions
		execAction = new ExecuteAction();
		restartAction = new RestartAction();
		clearAction = new ClearAction();

		aboutAction = new AboutAction();
		manualAction = new ManualAction();
		optionsAction = new OptionsAction();

		execFileAction = new ExecFileAction();
		saveFileAction = new SaveFileAction();
		exitAction = new ExitAction();

		unimplementedAction = new UnimplementedAction();

		// simple menu with basic actions
		JMenuBar mainMenu = createMenuBar();

		// panel with output text
		outputArea = new JTextArea(20, 50);
		outputArea.setEditable(false);

		JScrollPane outputPane = new JScrollPane(outputArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// panel with input text and exec button
		inputField = new JTextField(20);
		inputField.addActionListener(execAction);

		execButton = new JButton("Execute");
		execButton.addActionListener(execAction);
		JPanel controlPanel = new JPanel(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		// full width for textField
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		controlPanel.add(inputField, c);

		// constant width for execButton
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.0;
		controlPanel.add(execButton, c);

		// both panels should occupy full width of the window
		c.gridwidth = GridBagConstraints.REMAINDER;

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 0.0;
		add(mainMenu, c);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(outputPane, c);

		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(controlPanel, c);

		// print results to the outputTexArea
		outputStream = new TextAreaPrintStream(outputArea);

		// biuld global environment
		globalEnv = new Environment();

	}

	/**
	 * Creates the main menu bar
	 * 
	 * @return menuBar
	 */
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		// menu items
		JMenu fileMenu = new JMenu("File");
		JMenu runMenu = new JMenu("Run");
		JMenu helpMenu = new JMenu("Help");

		fileMenu.setToolTipText("Operations on files");
		runMenu.setToolTipText("Session controll");
		helpMenu.setToolTipText("Help on " + APP_NAME);

		fileMenu.setMnemonic('F');
		runMenu.setMnemonic('R');
		helpMenu.setMnemonic('H');

		/*
		 * Elements of the File menu.
		 */
		JMenuItem execFromFileItem = new JMenuItem("Open file");
		JMenuItem saveToFileItem = new JMenuItem("Save to file");
		JMenuItem exitShellItem = new JMenuItem("Exit shell");

		saveToFileItem.setToolTipText("Save output area to file");
		execFromFileItem.setToolTipText("Execute commands from file");
		exitShellItem.setToolTipText("Exit program");

		saveToFileItem.setAccelerator(KeyStroke.getKeyStroke('S',
				InputEvent.CTRL_MASK));
		execFromFileItem.setAccelerator(KeyStroke.getKeyStroke('F',
				InputEvent.CTRL_MASK));
		exitShellItem.setAccelerator(KeyStroke.getKeyStroke('X',
				InputEvent.CTRL_MASK + InputEvent.ALT_MASK));

		saveToFileItem.addActionListener(saveFileAction);
		execFromFileItem.addActionListener(execFileAction);
		exitShellItem.addActionListener(exitAction);

		/*
		 * Elements of the Help menu.
		 */
		JMenuItem aboutItem = new JMenuItem("About");
		JMenuItem manualItem = new JMenuItem("Manual");
		JMenuItem optionsItem = new JMenuItem("Options");

		aboutItem.setToolTipText("Info about this program");
		manualItem.setToolTipText(APP_NAME + " manual");
		optionsItem.setToolTipText("Settings for kulka shell");

		aboutItem.setAccelerator(KeyStroke.getKeyStroke('A',
				InputEvent.CTRL_MASK));
		manualItem.setAccelerator(KeyStroke.getKeyStroke('M',
				InputEvent.CTRL_MASK));
		optionsItem.setAccelerator(KeyStroke.getKeyStroke('O',
				InputEvent.CTRL_MASK));

		aboutItem.addActionListener(aboutAction);
		manualItem.addActionListener(manualAction);
		optionsItem.addActionListener(unimplementedAction);

		/*
		 * Elements of the Run menu.
		 */
		JMenuItem executeItem = new JMenuItem("Execute");
		JMenuItem clearItem = new JMenuItem("Clear");
		JMenuItem restartItem = new JMenuItem("Restart");

		executeItem.setToolTipText("Execute command");
		clearItem.setToolTipText("Clear output area");
		restartItem.setToolTipText("Restart session");

		executeItem.setAccelerator(KeyStroke.getKeyStroke('E',
				InputEvent.CTRL_MASK));
		clearItem.setAccelerator(KeyStroke.getKeyStroke('C',
				InputEvent.CTRL_MASK));
		restartItem.setAccelerator(KeyStroke.getKeyStroke('R',
				InputEvent.CTRL_MASK));

		executeItem.addActionListener(execAction);
		clearItem.addActionListener(clearAction);
		restartItem.addActionListener(restartAction);

		// fill the File menu
		fileMenu.add(execFromFileItem);
		fileMenu.add(saveToFileItem);
		fileMenu.addSeparator();
		fileMenu.add(exitShellItem);

		// fill the Help menu
		helpMenu.add(aboutItem);
		helpMenu.add(manualItem);
		helpMenu.addSeparator();
		helpMenu.add(optionsItem);

		// fill the Run menu
		runMenu.add(executeItem);
		runMenu.add(clearItem);
		runMenu.addSeparator();
		runMenu.add(restartItem);

		menuBar.add(fileMenu);
		menuBar.add(runMenu);
		menuBar.add(helpMenu);

		return menuBar;
	}

	/**
	 * Creates GUI components.
	 * 
	 */
	private static void createAndShowGUI() {
		mainFrame = new JFrame(APP_NAME);
		XKulka shell = new XKulka();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setContentPane(shell);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}

	/**
	 * FunctionDT main for running graphical shell
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				err.println("[Info]\t Starting graphicall shell.");
				createAndShowGUI();
			}
		});
	}

	/**
	 * Internal class for handling executing single command.
	 * 
	 * @author Jarek Miszczak <miszczak@iitis.gliwice.pl>
	 * 
	 */
	class ExecuteAction extends AbstractAction {

		public void actionPerformed(ActionEvent evt) {
			String command = inputField.getText();
			ANTLRStringStream ss = new ANTLRStringStream(command);

			try {
				outputArea.append("kulka> " + command + newline);

				// Lexer
				lexer = new KulkaLexer(ss);
				CommonTokenStream tokens = new CommonTokenStream(lexer);

				// Parser and AST construction
				parser = new KulkaParser(tokens);
				KulkaParser.program_return result = parser.program();
				CommonTree ast = (CommonTree) result.getTree();

				// gather function information during the first walk
				nodes = new CommonTreeNodeStream(ast);
				//FunctionParser funParser = new FunctionParser(nodes, globalEnv);
				//funParser.program();

				// showInfo(globalEnv);

				nodes = new CommonTreeNodeStream(ast);
				KulkaEvaluator eval = new KulkaEvaluator(nodes, globalEnv);
				eval.setOutputStream((PrintStream) outputStream);
				eval.program();

			} catch (Exception e) {
				err.println("Exception: " + e.getLocalizedMessage());
			}

			inputField.selectAll();
			inputField.requestFocus();

			// Make sure the new text is visible, even if there
			// was a selection in the text area.
			outputArea.setCaretPosition(outputArea.getDocument().getLength());
		}
	}

	/**
	 * Action for restarting interpreter. Upon this event new Kulka Interpreter
	 * is created and output area is cleared.
	 * 
	 * @author Jarek Miszczak <miszczak@iitis.gliwice.pl>
	 */
	class RestartAction extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			globalEnv = new Environment();
			// create new interpreter
			interpreter = new KulkaEvaluator(nodes, globalEnv);
			// clear the output area
			outputArea.setText("");
			outputArea.append(INFO_MESSAGE + " Session restarted" + newline);
		}
	}

	/**
	 * Action for clearing output area
	 * 
	 * @author Jarek Miszczak <miszczak@iitis.gliwice.pl>
	 * 
	 */
	class ClearAction extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			// clear the output area
			outputArea.setText("");
		}
	}

	/**
	 * Action for showing about dialog
	 * 
	 * @author Jarek Miszczak <miszczak@iitis.gliwice.pl>
	 * 
	 */
	class AboutAction extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			JDialog aboutDialog = new JDialog(mainFrame);
			JLabel appNameLabel = new JLabel(APP_NAME, JLabel.CENTER);
			JLabel authorLabel = new JLabel("(c) 2009 Jarek Miszczak",
					JLabel.CENTER);

			appNameLabel.setFont(new Font("Sans", Font.ITALIC, 23));
			authorLabel.setFont(new Font("Serif", Font.BOLD, 14));

			aboutDialog.setSize(new Dimension(120, 120));
			aboutDialog.setTitle("About " + APP_NAME);
			aboutDialog.setLayout(new BorderLayout());
			aboutDialog.add(appNameLabel, BorderLayout.NORTH);
			aboutDialog.add(authorLabel, BorderLayout.CENTER);

			aboutDialog.setModal(true);
			aboutDialog.setVisible(true);
		}
	}

	/**
	 * Show options dialog, edit preferences and save them
	 * 
	 * @author Jarek Miszczak <miszczak@iitis.gliwice.pl>
	 */
	class OptionsAction extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			// TODO Not implemented yet
			// TODO Implement options dialog and add file for storing options
			JOptionPane.showMessageDialog(mainFrame,
					"This function is not implemented yet");
		}
	}

	/**
	 * TODO Showing the manual .
	 */
	class ManualAction extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(mainFrame,
					"This function is not implemented yet");
		}
	}

	/**
	 * Implementation of the exit action.
	 */
	class ExitAction extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			err.println("[Info]\t Exiting graphical shell.");
			System.exit(0);
		}

	}

	/**
	 * Action for executing external script. Current session will be restarted
	 * 
	 * TODO If user presses "Cancel" in dialog showing warning message, the
	 * current session should be continued
	 */
	class ExecFileAction extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			File scriptFile;
			File currentDir;
			FileInputStream scriptStream;
			JFileChooser chooser;

			currentDir = new File(System.getProperty("user.dir"));
			chooser = new JFileChooser(currentDir);
			chooser.setFileFilter(new KulkaFileFilter());

			if (chooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
				scriptFile = chooser.getSelectedFile();
				err.println("Executing script: "
						+ chooser.getSelectedFile().getName());
				int confirmRestart = JOptionPane.showConfirmDialog(mainFrame,
						"Current session will be restarted", "Warninng!",
						JOptionPane.WARNING_MESSAGE);

				if (confirmRestart == JOptionPane.CANCEL_OPTION) {
					// abort the execution
					JOptionPane.showMessageDialog(mainFrame, ABORT_MESSAGE);
				} else {
					try {
						scriptStream = new FileInputStream(scriptFile);
						ANTLRInputStream is = new ANTLRInputStream(scriptStream);

						// Lexer
						KulkaLexer lexer = new KulkaLexer(is);
						CommonTokenStream tokens = new CommonTokenStream(lexer);

						// Parser and AST construction
						KulkaParser parser = new KulkaParser(tokens);
						KulkaParser.program_return result = parser.program();
						CommonTree ast = (CommonTree) result.getTree();
						// create execution environment
						globalEnv = new Environment();

						// gather function information during the first walk
						nodes = new CommonTreeNodeStream(ast);
						//FunctionParser funParser = new FunctionParser(nodes,
							//	globalEnv);
						//funParser.program();

						// showInfo(globalEnv);

						nodes = new CommonTreeNodeStream(ast);
						KulkaEvaluator eval = new KulkaEvaluator(nodes,
								globalEnv);
						eval.program();

					} catch (RecognitionException ex) {
						JOptionPane.showMessageDialog(mainFrame, ex
								.getMessage());
					} catch (FileNotFoundException ex) {
						JOptionPane.showMessageDialog(mainFrame, ex
								.getMessage());
					} catch (IOException ex) {
						JOptionPane.showMessageDialog(mainFrame, ex
								.getMessage());
					}

				}
			}
		}

	}

	/**
	 * Dump the output to the text file
	 */
	class SaveFileAction extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			String outputFileName = JOptionPane
					.showInputDialog("Enter file name");

			if (outputFileName != null && !outputFileName.equals("")) {
				err.println(outputFileName);
				try {
					FileWriter outputWriter = new FileWriter(outputFileName);
					outputWriter.write(outputArea.getText());
					outputWriter.flush();
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(mainFrame, ex.getMessage());
				}
			} else {
				err.println(ABORT_MESSAGE);
			}
		}

	}

	/**
	 * 
	 * @author Jarek Miszczak <miszczak@iitis.gliwice.pl>
	 */
	class UnimplementedAction extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			// TODO Implement options dialog and add file for storing options
			JOptionPane.showMessageDialog(mainFrame,
					"This function is not implemented yet");
		}
	}

}
