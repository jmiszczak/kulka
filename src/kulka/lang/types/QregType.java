package kulka.lang.types;

import java.util.Vector;

import kulka.lang.errors.VariableInitializationError;

import org.jscience.mathematics.number.Complex;
import org.jscience.mathematics.vector.ComplexVector;

public class QregType extends DataType implements QuantumDataType {

	private String initList;
	private ComplexVector value;
	private String kulkaTypeName = "qreg";
	private DataTypeClass dataTypeClass = DataTypeClass.QregType;

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public void setValue(DataType dt) {

	}

	public QregType() {
		value = ComplexVector.valueOf(Complex.ONE, Complex.ZERO);
	}

	public QregType(String initList) throws VariableInitializationError {
		this.initList = initList;
		char[] qubitCharVals = initList.toCharArray();
		int[] qubitVals = new int[qubitCharVals.length];
		for (int i = 0; i < qubitCharVals.length; i++) {
			qubitVals[i] = Character.getNumericValue(qubitCharVals[i]);
		}
		ComplexVector[] qubits = new ComplexVector[qubitVals.length];

		// produce a list of qubits as complex vectors
		for (int i = 0; i < qubitVals.length; i++) {
			if (qubitVals[i] == 1) {
				qubits[i] = ComplexVector.valueOf(Complex.ZERO, Complex.ONE);
			} else if (qubitVals[i] == 0) {
				qubits[i] = ComplexVector.valueOf(Complex.ONE, Complex.ZERO);
			} else {
				throw new VariableInitializationError(initList);
			}
		}

		value = kron(qubits);
	}

	public String toString() {
		return "|" + initList + ">";
	}

	/**
	 * Kronecker product of two vectors.
	 * 
	 * @param cv1
	 *            ComplexVector
	 * @param cv2
	 *            ComplexVector
	 * @return Kronecker product of cv1 and cv2.
	 */
	private ComplexVector kron(ComplexVector cv1, ComplexVector cv2) {
		int dim1 = cv1.getDimension();
		int dim2 = cv2.getDimension();
		int resDim = dim1 * dim2;
		Vector<Complex> elements = new Vector<Complex>(resDim);
		for (int i = 0; i < dim1; i++) {
			for (int j = 0; j < dim2; j++) {
				elements.add(cv1.get(i).times(cv2.get(j)));
			}
		}
		return ComplexVector.valueOf(elements);
	}

	/**
	 * Kronecker product for a list of vectors.
	 * 
	 * @param cvl
	 *            List of {@link org.jscience.mathematics.vector.ComplexVector}s
	 * @return Kronecker product of elements in the input list.
	 */
	private ComplexVector kron(ComplexVector[] cvl) {
		ComplexVector res = cvl[0];
		for (int i = 1; i < cvl.length; i++) {
			res = kron(res, cvl[i]);
		}
		return res;
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
