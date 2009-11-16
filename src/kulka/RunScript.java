/**
 * 
 */
package kulka;

import static java.lang.System.out;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

import kulka.lang.Environment;
import kulka.lang.KulkaEvaluator;
import kulka.lang.KulkaLexer;
import kulka.lang.KulkaParser;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;

/**
 * Application for testing kulka scripts.
 * 
 * @author Jaroslaw Miszczak <miszczak@iitis.pl>
 * @version 0.2.0
 * @link http://www.iitis.pl/~miszczak/
 */
public class RunScript {

	private static CommonTreeNodeStream nodes;
	private static Environment globalEnv;

	/**
	 * @param args
	 * @throws IOException
	 * @throws RecognitionException
	 */
	public static void main(String[] args) throws IOException,
			RecognitionException {
		if (args.length != 0) {
			File f = new File(args[0]);
			PrintStream outStream;

			ANTLRInputStream input = new ANTLRInputStream(
					new FileInputStream(f));

			// Lexer
			KulkaLexer lexer = new KulkaLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);

			// Parser and AST construction
			KulkaParser parser = new KulkaParser(tokens);
			KulkaParser.program_return result = parser.program();
			CommonTree ast = (CommonTree) result.getTree();

			// create execution environment
			globalEnv = new Environment();

			// evaluate the program
			nodes = new CommonTreeNodeStream(ast);
			outStream = new PrintStream(System.out);
			KulkaEvaluator eval = new KulkaEvaluator(nodes, globalEnv);
			eval.setOutputStream(outStream);
			eval.program();

			//showInfo(globalEnv);

		} else {
			out.println("Usage: kulka.RunScript <file name>");
		}
	}

	/**
	 * @param o
	 */
	protected static void showInfo(Environment o) {
		out.println("[INFO]\t" + o.toString());
	}
}
