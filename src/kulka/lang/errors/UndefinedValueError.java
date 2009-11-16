/**
 * 
 */
package kulka.lang.errors;

/**
 * @author jam
 * 
 */
@SuppressWarnings("serial")
public class UndefinedValueError extends ExecutionError {

	private String msg;

	public UndefinedValueError(String name) {
		this.msg = errPrompt + "Variable '" + name + "' not declared!";
	}

	@Override
	public String getMessage() {
		return msg;
	}

}
