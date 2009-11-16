/**
 * 
 */
package kulka.lang.errors;

/**
 * @author jam
 * 
 */
@SuppressWarnings("serial")
public class UndefinedVariableError extends ExecutionError {

	private String msg;

	public UndefinedVariableError(String name) {
		this.msg = errPrompt + "Undefined variable '" + name + "'!";
	}

	@Override
	public String getMessage() {
		return msg;
	}

}
