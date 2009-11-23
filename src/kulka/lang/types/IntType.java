package kulka.lang.types;

import kulka.lang.errors.VariableInitializationError;

import org.jscience.mathematics.number.Complex;

public class IntType extends DataType implements NumericDataType {

	private static final DataTypeClass dataTypeClass = DataTypeClass.IntType;
	private static final String kulkaTypeName = "int";
	private Integer value;

	public IntType() {
		value = 0;
	}
	
	public IntType(String initVal) {
		value = Integer.parseInt(initVal);
	}

	public IntType(Integer initVal) {
		value = initVal;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public void setValue(DataType dt) {
		// TODO not implemented yet
	}

	@Override
	public String getKulkaTypeName() {
		return kulkaTypeName;
	}

	@Override
	public DataType add(DataType dt) throws VariableInitializationError {
		DataType ret = null;
		switch (dt.getDataTypeClass()) {
		case IntType:
			ret = new IntType(value + (Integer) dt.getValue());
			break;
		case RealType:
			ret = new RealType(this.doubleValue()
					+ ((RealType) dt).doubleValue());
			break;
		case ComplexType:
			ret = new ComplexType(this.doubleValue()
					+ ((ComplexType) dt).getReValue(), ((ComplexType) dt)
					.getImValue());
			break;
		case StringType:
			ret = new StringType(this.toString() + dt.toString());
			break;
		default:
			break;
		}
		return ret;
	}

	@Override
	public DataType mul(DataType dt) throws VariableInitializationError {
		DataType ret = null;
		switch (dt.getDataTypeClass()) {
		case ComplexType:
			break;
		case RealType:
			break;
		case IntType:
			break;
		case StringType:
			break;
		default:
			break;
		}
		return ret;
	}

	@Override
	public DataTypeClass getDataTypeClass() {
		return dataTypeClass;
	}

	public Complex complexValue() {
		return Complex.valueOf(value.doubleValue(), 0.0);
	}

	public Double doubleValue() {
		return value.doubleValue();
	}

	public Integer integerValue() {
		return value;
	}

	@Override
	public String toString() {
		return value.toString();
	}

}
