package kulka.lang.errors;

/**
 * 
 * @author Jarek Miszczak <miszczak@iitis.pl>
 * @version 0.2.0
 * 
 */
@SuppressWarnings("serial")
public class UnknowTypeError extends ExecutionError {
	private String msg;

	public UnknowTypeError(String name) {
		msg = errPrompt + "Could not find type implementation: " + name;
	}

	@Override
	public String getMessage() {
		return msg;
	}

}
