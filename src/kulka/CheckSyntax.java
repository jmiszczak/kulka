/**
 * 
 */
package kulka;

import static java.lang.System.err;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import kulka.lang.KulkaLexer;
import kulka.lang.KulkaParser;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;

/**
 * Application for syntax testing.
 * 
 * @author Jaroslaw Miszczak <miszczak@iitis.pl>
 * @version 0.2.0
 * @link http://www.iitis.pl/~miszczak/
 */
public class CheckSyntax {

	static public CommonTreeNodeStream nodes;

	/**
	 * @param args
	 * @throws IOException
	 * @throws RecognitionException
	 */
	public static void main(String[] args) throws IOException,
			RecognitionException {
		if (args.length != 0) {
			File f = new File(args[0]);
			FileInputStream fis = new FileInputStream(f);
			ANTLRInputStream input = new ANTLRInputStream(fis);

			// Lexer
			KulkaLexer lexer = new KulkaLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);

			// Parser and AST construction
			KulkaParser parser = new KulkaParser(tokens);
			err.println("[INFO]\tParsing " + f.getAbsoluteFile());
			KulkaParser.program_return result = parser.program();
			
			err.println("[INFO]\tResulting AST " + f.getAbsoluteFile());
			CommonTree ast = (CommonTree) result.getTree();
			err.println(ast.toStringTree());
			
		} else {
			err.println("Usage: kulka.CheckSytax <file name>");
		}
	}

}