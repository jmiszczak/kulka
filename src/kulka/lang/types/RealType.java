package kulka.lang.types;

import kulka.lang.errors.VariableInitializationError;

public class RealType extends DataType {

	private Double value;
	private String kulkaTypeName = "real";

	public RealType(String initVal) {
		value = Double.parseDouble(initVal);
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public void setValue(DataType dt) {

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
		// TODO Auto-generated method stub
		return null;
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
	public DataType sub(DataType dt1, DataType dt2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getKulkaTypeName() {
		return kulkaTypeName;
	}

}
