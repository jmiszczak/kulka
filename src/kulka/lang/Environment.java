/**
 * 
 */
package kulka.lang;

import java.util.ArrayDeque;
import java.util.Iterator;

import kulka.lang.errors.ExecutionError;
import kulka.lang.errors.UndefinedVariableError;
import kulka.lang.errors.VariableRedefinitionError;

/**
 * Management of the symbol tables based on {@link java.util.ArrayDeque}. This
 * class is essential for scoping.
 * 
 * @author Jarek Miszczak <miszczak@iitis.pl>
 * @version 0.2.0
 * 
 */
@SuppressWarnings("serial")
public class Environment extends ArrayDeque<SymbolTable> {

	private SymbolTable globalSymTable;

	public Environment() {
		globalSymTable = new SymbolTable();
		push(globalSymTable);
	}

	/**
	 * 
	 * @param sym
	 * @return
	 * @throws ExecutionError
	 */
	public Symbol lookup(String sym) throws ExecutionError {
		Iterator<SymbolTable> iter = descendingIterator();

		while (iter.hasNext()) {
			SymbolTable st = iter.next();
			if (st.hasSymbol(sym))
				return st.lookup(sym);
		}
		throw new UndefinedVariableError(sym);
	}

	/**
	 * 
	 * @param name
	 * @param sym
	 * @return
	 * @throws ExecutionError
	 */
	public Symbol insert(String name, Symbol sym) throws ExecutionError {
		if (peek().hasSymbol(name)) {
			throw new VariableRedefinitionError(name);
		} else {
			peek().insert(name, sym);
			return sym;
		}
	}

	/**
	 * 
	 * @param sym
	 * @param data
	 * @return
	 * @throws ExecutionError
	 */
	public Symbol update(String sym, Symbol data) throws ExecutionError {
		Iterator<SymbolTable> iter = descendingIterator();
		while (iter.hasNext()) {
			SymbolTable st = iter.next();
			if (st.hasSymbol(sym))
				return st.update(sym, data);
		}
		throw new UndefinedVariableError(sym);
	}

}
