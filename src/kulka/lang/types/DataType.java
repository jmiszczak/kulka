package kulka.lang.types;

import kulka.lang.errors.OperationNotPermittedError;
import kulka.lang.errors.UnknowTypeError;
import kulka.lang.errors.VariableInitializationError;

/**
 * 
 * @author Jarek Miszczak <miszczak@iitis.pl>
 * @version 0.2.0
 * 
 */
public abstract class DataType {

	public abstract Object getValue();

	public abstract void setValue(DataType dt);
	
	public abstract String getKulkaTypeName();

	public abstract void add(DataType dt);

	public abstract void sub(DataType dt);

	public abstract void mul(DataType dt);

	public abstract void div(DataType dt);

	public abstract DataType add(DataType dt1, DataType dt2) throws VariableInitializationError;

	public abstract DataType div(DataType dt1, DataType dt2);

	public abstract DataType mul(DataType dt1, DataType dt2);

	public abstract DataType sub(DataType dt1, DataType dt2) throws OperationNotPermittedError;

	/**
	 * Provides an instance of class implementing data type <code>name</name>.
	 * 
	 * @param name
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
	 * Returns qualified name of the class implementing data type
	 * <code>typeName</code>.
	 * 
	 * @param typeName
	 * @return
	 */
	public static String getImplementingClassName(String typeName) {
		return "kulka.lang.types." + typeName + "Type";

	}
}
