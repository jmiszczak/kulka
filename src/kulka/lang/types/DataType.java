package kulka.lang.types;

import kulka.lang.errors.UnknowTypeError;
import kulka.lang.errors.VariableInitializationError;

/**
 * 
 * @author Jarek Miszczak <miszczak@iitis.pl>
 * @version 0.2.0
 * 
 */
public abstract class DataType {

	/*
	 * Public abstract methods used to operate on internal properties.
	 */
	protected enum DataTypeClass {
		ComplexType, IntType, QintType, QregType, RealType, StringType
	}

	public abstract Object getValue();

	public abstract void setValue(DataType dt);

	public abstract String getKulkaTypeName();

	public abstract DataTypeClass getDataTypeClass();

	/*
	 * Public abstract methods for implementing various operators.
	 */

	public abstract DataType add(DataType dt) throws VariableInitializationError;

	/*
	 * Public static methods.
	 */

	/**
	 * Provides an instance of class implementing data type <code>name</name>.
	 * 
	 * @param name
	 *            String
	 * @return
	 * @throws UnknowTypeError
	 */
	public static DataType fromName(String name) throws UnknowTypeError {
		DataType ret = null;
		name = name.substring(0, 1).toUpperCase()
				+ name.substring(1).toLowerCase();
		name = DataType.getImplementingClassName(name);
		try {
			ret = (DataType) Class.forName(name).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new UnknowTypeError(name);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new UnknowTypeError(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new UnknowTypeError(name);
		}

		return ret;
	}

	/**
	 * Returns qualified name of the class implementing <i>kulka</i> data type
	 * <code>typeName</code>.
	 * 
	 * @param typeName
	 *            {@link java.lang.String}
	 * @return Object of one of the {@link kulka.lang.types.DataType} subclasses
	 */
	public static String getImplementingClassName(String typeName) {
		return "kulka.lang.types." + typeName + "Type";

	}

}
