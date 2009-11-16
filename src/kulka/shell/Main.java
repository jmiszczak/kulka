package kulka.shell;

import static java.lang.System.err;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import jline.ConsoleReader;
import jline.ConsoleReaderInputStream;
import jline.History;
import kulka.lang.Environment;
import kulka.lang.KulkaEvaluator;
import kulka.lang.KulkaLexer;
import kulka.lang.KulkaParser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;

/**
 * 
 * @author Jarek Miszczak <miszczak@iitis.pl>
 * @version 0.2.0
 * @link http://www.iitis.pl/~miszczak/
 */

@SuppressWarnings("unused")
public class Main {

	private final static String release = "20091115";
	private final static String historyFileName = ".kulka_history";
	private final static String userHome = System.getProperty("user.home");
	private final static String separator = System
			.getProperty("file.separator");
	private static Environment globalEnv;
	private static CommonTreeNodeStream nodes;

	public static void main(String[] args) throws IOException {

		PrintStream outStream;
		ConsoleReader consoleReader;
		ConsoleReaderInputStream consoleInStream;

		History history;
		String line;
		ANTLRStringStream ss;

		KulkaLexer lexer;
		KulkaParser parser;

		outStream = new PrintStream(System.out);
		consoleReader = new ConsoleReader(System.in, new PrintWriter(outStream));
		consoleInStream = new ConsoleReaderInputStream(consoleReader);

		printInfo(outStream);
		history = initHistory(userHome + separator + historyFileName);
		consoleReader.setHistory(history);
		// create execution environment
		globalEnv = new Environment();

		while ((line = consoleReader.readLine("|kulka> ")) != null) {

			ss = new ANTLRStringStream(line);

			// lexer
			lexer = new KulkaLexer(ss);
			CommonTokenStream tokens = new CommonTokenStream(lexer);

			// parser and AST construction
			parser = new KulkaParser(tokens);
			KulkaParser.program_return result;
			CommonTree ast = null;
			try {
				result = parser.program();
				ast = (CommonTree) result.getTree();
			} catch (RecognitionException e) {
				reportParserError(e);
			}

			// gather function information during the first walk
			nodes = new CommonTreeNodeStream(ast);

			// showInfo(globalEnv);
			nodes = new CommonTreeNodeStream(ast);
			KulkaEvaluator eval = new KulkaEvaluator(nodes, globalEnv);
			eval.setOutputStream(outStream);
			try {
				eval.program();
			} catch (RecognitionException e) {
				reportInterpreterError(e);
			}

			// append last line to history
			history.addToHistory(line);
		}
	}

	private static void printInfo(PrintStream out) {
		out.println("--------------------------------------------------------");
		out.println("This is TIK - tiny interpreter for kulka language. ");
		out.println("(c) 2007-2009 Jarek Miszczak <miszczak@iitis.pl>. ");
		out.println("Note: This is an experimental software! ");
		out.println("Interpreter release: " + release);
		out.println("--------------------------------------------------------");
		out.flush();
	}

	private static History initHistory(String fileName) {
		try {
			History history = new History(new File(fileName));
			return history;
		} catch (IOException e) {
			reportError(e);
			return null;
		}
	}

	private static void reportParserError(Exception ex) {
		err.println("Parser error: " + ex.getCause());
		err.println("Parser error: " + ex.getMessage());
	}

	private static void reportInterpreterError(Exception ex) {
		err.println("Interpreter error: " + ex.getCause());
		err.println("Interpreter error: " + ex.getMessage());
	}

	private static void reportIOError(IOException ex) {
		err.println("I/O Error: " + ex.getCause());
		err.println("I/O Error: " + ex.getMessage());
	}

	private static void reportError(Exception ex) {
		err.println("Error type:    " + ex.getClass());
		err.println("Error cause:   " + ex.getCause());
		err.println("Error message: " + ex.getMessage());
	}

}