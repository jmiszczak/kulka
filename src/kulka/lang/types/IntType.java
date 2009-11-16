package kulka.lang.types;

public class IntType extends DataType {

	private Integer value;
	private String kulkaTypeName = "int";

	public IntType(String initVal) {
		value = Integer.parseInt(initVal);
	}

	public IntType(Integer initVal) {
		value = initVal;
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
		value = value + (Integer) dt.getValue();

	}

	@Override
	public void div(DataType dt) {
		// TODO Auto-generated method stub
		value = value / (Integer) dt.getValue();
	}

	@Override
	public void mul(DataType dt) {
		value = value * (Integer) dt.getValue();
	}

	@Override
	public void sub(DataType dt) {
		value = value - (Integer) dt.getValue();
	}

	@Override
	public DataType add(DataType dt1, DataType dt2) {
		return new IntType((Integer) dt1.getValue()
				+ (Integer) dt2.getValue());
	}

	@Override
	public DataType div(DataType dt1, DataType dt2) {
		return new IntType((Integer) dt1.getValue()
				- (Integer) dt2.getValue());
	}

	@Override
	public DataType mul(DataType dt1, DataType dt2) {
		return new IntType((Integer) dt1.getValue()
				* (Integer) dt2.getValue());
	}

	@Override
	public DataType sub(DataType dt1, DataType dt2) {
		return new IntType((Integer) dt1.getValue()
				- (Integer) dt2.getValue());
	}
	@Override
	public String getKulkaTypeName() {
		return kulkaTypeName;
	}
}
