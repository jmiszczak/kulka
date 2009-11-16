package kulka.lang.types;

import kulka.lang.errors.VariableInitializationError;

import org.jscience.mathematics.number.Complex;

public class ComplexType extends DataType {
	private Complex value;
	private String kulkaTypeName = "complex";

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

	@Override
	public void setValue(DataType dt) {
		// TODO Auto-generated method stub

	}

	public String toString() {
		return "[" + value.getReal() + ", " + value.getImaginary() + "]";
	}

	@Override
	public void add(DataType dt) {
		value = value.plus((Complex) dt.getValue());

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
		value = value.minus((Complex) dt.getValue());

	}

	@Override
	public DataType add(DataType dt1, DataType dt2)
			throws VariableInitializationError {
		Complex c = ((Complex) dt1.getValue()).plus((Complex) dt2.getValue());
		try {
			return new ComplexType(c.getReal(), c.getImaginary());
		} catch (VariableInitializationError e) {
			throw e;

		}
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
