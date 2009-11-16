package kulka.lang.types;

import kulka.lang.errors.OperationNotPermittedError;
import kulka.lang.errors.VariableInitializationError;

public class StringType extends DataType {

	private String value;

	private String kulkaTypeName = "string";

	public StringType(String initVal) {
		this.value = initVal;
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public void setValue(DataType dt) {
		// TODO Auto-generated method stub

	}

	public String toString() {
		return value;
	}

	@Override
	public void add(DataType dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void div(DataType dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mul(DataType dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sub(DataType dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public DataType add(DataType dt1, DataType dt2)
			throws VariableInitializationError {
		return new StringType(dt1.toString() + dt2.toString());
	}

	@Override
	public DataType div(DataType dt1, DataType dt2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataType mul(DataType dt1, DataType dt2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataType sub(DataType dt1, DataType dt2) throws OperationNotPermittedError {
		throw new OperationNotPermittedError("sub", dt1.getKulkaTypeName(), dt1
				.getKulkaTypeName());
	}

	@Override
	public String getKulkaTypeName() {
		return kulkaTypeName;
	}
}
