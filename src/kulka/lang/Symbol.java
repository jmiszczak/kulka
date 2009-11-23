/**
 * 
 */
package kulka.lang;

import kulka.lang.errors.UnknowTypeError;
import kulka.lang.types.DataType;

/**
 * 
 * @author Jarek Miszczak <miszczak@iitis.pl>
 * @version 0.2.0
 * 
 */
public class Symbol {

	private String symName;
	private DataType symData;

	public Symbol(String name, String typeName) throws UnknowTypeError {
		symName = name;
		symData = DataType.fromName(typeName);
	}

	public Symbol(String name, DataType initVal) {
		this.symName = name;
		this.symData = initVal;
	}

	public String getName() {
		return symName;
	}

	public DataType getValue() {
		return symData;
	}

	public String toString() {
		return symName + "->" + symData;

	}

}
