/**
 * 
 */
package kulka.algebra;

import org.jscience.mathematics.number.Complex;
import org.jscience.mathematics.vector.ComplexVector;

/**
 * @author Jaroslaw Miszczak <miszczak@iitis.pl>
 * 
 */
public class SuperpositionOfIntegers {

	private int[] initList;

	/*
	 * TODO: implement the algorithm for generating superposition
	 */

	public SuperpositionOfIntegers(int[] initList) {
		this.initList = initList;
	}

	public static ComplexVector asComplexVector() {
		return ComplexVector.valueOf(Complex.ZERO, Complex.ZERO, Complex.ZERO,
				Complex.ZERO);

	}

	public String toString() {
		return initList.toString();
	}

}
