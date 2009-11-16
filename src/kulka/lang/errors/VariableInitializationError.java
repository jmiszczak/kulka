/**
 * 
 */
package kulka.lang.errors;

/**
 * 
 * @author Jarek Miszczak <miszczak@iitis.pl>
 * @version 0.2.0
 * 
 */
@SuppressWarnings("serial")
public class VariableInitializationError extends ExecutionError {
	private String msg;

	public VariableInitializationError(String init) {
		this.msg = errPrompt
				+ "Error while initializing variable using initial value '"
				+ init + "'!";
	}

	@Override
	public String getMessage() {
		return msg;
	}

}
