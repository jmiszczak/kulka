package kulka.lang.types;

import java.util.Vector;

import org.jscience.mathematics.vector.ComplexVector;

import kulka.lang.errors.VariableInitializationError;

public class QintType extends DataType implements QuantumDataType {

	private static final String kulkaTypeName = "qint";
	private static final DataTypeClass dataTypeClass = DataTypeClass.QintType;

	private Vector<Integer> initList;
	private ComplexVector value;

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
	public String getKulkaTypeName() {
		return kulkaTypeName;
	}

	@Override
	public DataType add(DataType dt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataTypeClass getDataTypeClass() {
		return dataTypeClass;
	}
}
