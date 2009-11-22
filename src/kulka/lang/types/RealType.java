package kulka.lang.types;

import kulka.lang.errors.VariableInitializationError;

import org.jscience.mathematics.number.Complex;

public class RealType extends DataType implements NumericDataType {

	private static final String kulkaTypeName = "real";
	private static final DataTypeClass dataTypeClass = DataTypeClass.RealType;

	private Double value;

	public RealType(String initVal) {
		value = Double.parseDouble(initVal);
	}

	public RealType(double d) {
		value = d;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public void setValue(DataType dt) {

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
			ret = new RealType(value + ((Integer) dt.getValue()).doubleValue());
			break;
		case RealType:
			ret = new RealType(this.doubleValue() + ((RealType)dt).doubleValue());
			break;
		case ComplexType:
			ret = new ComplexType(((Complex) dt.getValue()).getReal()
					+ this.doubleValue(), ((Complex) dt.getValue())
					.getImaginary());
		case StringType:
			ret = new StringType(this.toString() + dt.toString());
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

	@Override
	public Complex complexValue() {
		return Complex.valueOf(value, 0);
	}

	@Override
	public Double doubleValue() {
		return value;
	}

	@Override
	public Integer integerValue() {
		return value.intValue();
	}
	
	@Override
	public String toString() {
		return value.toString();
	}

}
