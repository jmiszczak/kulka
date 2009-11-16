package kulka.lang.errors;

@SuppressWarnings("serial")
public class VariableRedefinitionError extends ExecutionError {
	private String msg;

	public VariableRedefinitionError(String name) {
		this.msg = errPrompt + "Variable '" + name + "' already defined!";
	}

	@Override
	public String getMessage() {
		return msg;
	}
}
