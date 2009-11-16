/**
 * 
 */
package kulka.gui;

import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JTextArea;

public class TextAreaPrintStream extends PrintStream {

	private JTextArea textArea;

	public TextAreaPrintStream(JTextArea area, OutputStream out) {
		super(out);
		textArea = area;
	}

	public TextAreaPrintStream(JTextArea area) {
		super(System.out);
		textArea = area;
	}

	/**
	 * Method println
	 * 
	 * @param str
	 *            String to be output in the JTextArea textArea (private
	 *            attribute of the class). After having printed such a String,
	 *            prints a new line.
	 */
	public void println(String str) {
		textArea.append(str + "\n");
	}

	/**
	 * Method print
	 * 
	 * @param str
	 *            String to be output in the JTextArea textArea (private
	 *            attribute of the class).
	 */
	public void print(String str) {
		textArea.append(str);
	}
}
