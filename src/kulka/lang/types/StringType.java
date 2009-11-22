package kulka.lang.types;

public class StringType extends DataType {

	private static final String kulkaTypeName = "string";
	private static final DataTypeClass dataTypeClass = DataTypeClass.StringType;

	private String value;

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
	public String getKulkaTypeName() {
		return kulkaTypeName;
	}

	@Override
	public DataType add(DataType dt) {
		switch (dt.getDataTypeClass()) {
		case ComplexType:
			System.out.println("ComplexType");
			break;
		case IntType:
			System.out.println("IntType");
			break;
		default:
			break;
		}

		return null;
	}

	@Override
	public DataTypeClass getDataTypeClass() {
		return dataTypeClass;
	}

}
