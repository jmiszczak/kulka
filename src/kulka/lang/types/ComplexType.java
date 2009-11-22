package kulka.lang.types;

import kulka.lang.errors.VariableInitializationError;

import org.jscience.mathematics.number.Complex;

public class ComplexType extends DataType implements NumericDataType {

	private static final DataTypeClass dataTypeClass = DataTypeClass.ComplexType;
	private static final String kulkaTypeName = "complex";

	private Complex value;

	public ComplexType(Object re, Object im) throws VariableInitializationError {
		try {
			value = Complex.valueOf(Double.parseDouble(re.toString()), Double
					.parseDouble(im.toString()));
		} catch (Exception e) {
			throw new VariableInitializationError("[" + re.toString() + " ,"
					+ im.toString() + "]");
		}
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	public Double getReValue() {
		// TODO Auto-generated method stub
		return value.getReal();
	}

	public Double getImValue() {
		// TODO Auto-generated method stub
		return value.getImaginary();
	}

	@Override
	public void setValue(DataType dt) {
		// TODO Auto-generated method stub

	}

	public String toString() {
		return "[" + value.getReal() + ", " + value.getImaginary() + "]";
	}

	@Override
	public String getKulkaTypeName() {
		return kulkaTypeName;
	}

	@Override
	public DataType add(DataType dt) throws VariableInitializationError {
		DataType ret = null;
		switch (dt.getDataTypeClass()) {
		case ComplexType:
			ret = new ComplexType(this.getReValue()
					+ ((ComplexType) dt).getReValue(), this.getImValue()
					+ ((ComplexType) dt).getImValue());
			break;
		case RealType:
			ret = new ComplexType(value.getReal()
					+ ((RealType) dt).doubleValue(), ((RealType) dt)
					.doubleValue());
			break;
		case IntType:
			ret = new ComplexType(this.getReValue()
					+ ((IntType) dt).doubleValue(), this.getImValue());
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
	public DataTypeClass getDataTypeClass() {
		return dataTypeClass;
	}

	public Complex complexValue() {
		return value;
	}

	public Double doubleValue() {
		return value.doubleValue();
	}

	public Integer integerValue() {
		return value.intValue();
	}

}
