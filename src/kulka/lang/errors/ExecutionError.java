/**
 * 
 */
package kulka.lang.errors;

/**
 * @author Jarek Miszczak <miszczak@iitis.pl>
 * @version 0.2.0
 * 
 */
@SuppressWarnings("serial")
public abstract class ExecutionError extends Exception {
	protected String errPrompt = "[Error]\t";

	public abstract String getMessage();
}
