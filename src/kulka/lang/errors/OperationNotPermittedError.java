package kulka.lang.errors;

@SuppressWarnings("serial")
public class OperationNotPermittedError extends ExecutionError {

	private String msg;

	public OperationNotPermittedError(String name, String dt1Name,
			String dt2Name) {
		this.msg = errPrompt + "Operation '" + name + "' is not defined for "
				+ dt1Name + " and " + dt1Name;
	}

	@Override
	public String getMessage() {
		return msg;
	}

}
