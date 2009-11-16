package kulka.lang.types;

import java.util.Vector;

import org.jscience.mathematics.vector.ComplexVector;

import kulka.lang.errors.VariableInitializationError;

public class QintType extends DataType {

	private Vector<Integer> initList;
	private ComplexVector value;
	private String kulkaTypeName = "qint";

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public void setValue(DataType dt) {

	}

	/**
	 * Initialize quantum register using list of integers.
	 * 
	 * @param s
	 *            list of integers as a String
	 * @throws VariableInitializationError
	 */
	public QintType(String s) throws VariableInitializationError {
		try {
			initList = new Vector<Integer>();
			String l[] = s.split(" ");
			for (int i = 0; i < l.length; i++) {
				initList.add(Integer.parseInt(l[i]));
			}
		} catch (Exception e) {
			throw new VariableInitializationError(s);
		}
	}

	public String toString() {
		return initList.toString();
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
	public DataType add(DataType dt1, DataType dt2) {
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
