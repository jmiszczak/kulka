package kulka.lang.types;

import kulka.lang.errors.UnknowTypeError;
import kulka.lang.errors.VariableInitializationError;
import kulka.lang.types.IntType;

/**
 * 
 * @author Jarek Miszczak <miszczak@iitis.pl>
 * @version 0.2.0
 * 
 */

public abstract class DataType {

	/*
	 * Methods used to operate on internal properties.
	 */
	protected enum DataTypeClass {
		ComplexType, IntType, QintType, QregType, RealType, StringType
	}

	public abstract Object getValue();

	public abstract void setValue(DataType dt);

	public abstract String getKulkaTypeName();

	public abstract DataTypeClass getDataTypeClass();

	/*
	 * Methods for implementing arithmetic operators.
	 */

	public abstract DataType add(DataType dt)
			throws VariableInitializationError;

	public abstract DataType mul(DataType dt)
			throws VariableInitializationError;

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
	public static DataType fromName(String kulkaName) throws UnknowTypeError {
		DataType ret = null;
		String name = DataType.getImplementingClassName(kulkaName);
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
	private static String getImplementingClassName(String kulkaName) {
		String className = kulkaName.substring(0, 1).toUpperCase()
				+ kulkaName.substring(1).toLowerCase();
		className = "kulka.lang.types." + className + "Type";
		return className;
	}

}
