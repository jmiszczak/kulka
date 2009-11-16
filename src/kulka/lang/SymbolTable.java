/**
 * 
 */
package kulka.lang;

import java.util.Hashtable;
import java.util.Vector;

import kulka.lang.errors.ExecutionError;
import kulka.lang.errors.UndefinedValueError;
import kulka.lang.errors.UndefinedVariableError;
import kulka.lang.errors.VariableRedefinitionError;

/**
 * Implementation of the symbol table for the kulka interpreter.
 * 
 * @author Jarek Miszczak <miszczak@iitis.pl>
 * @version 0.2.0
 * 
 */
public class SymbolTable {
	private Hashtable<String, Symbol> symTable;

	/**
	 * Default constructor - create new hash table for storing symbols.
	 */
	public SymbolTable() {
		symTable = new Hashtable<String, Symbol>();
	}

	/**
	 * Creates new symbol table based on SymbolTable <code>env</code>. This is
	 * useful when dealing with scoping.
	 * 
	 * @param env
	 */
	public SymbolTable(SymbolTable env) throws ExecutionError {
		symTable = new Hashtable<String, Symbol>();
		for (String s : env.getSymbols()) {
			insert(s, env.lookup(s));
		}
	}

	/**
	 * Returns Symbol object which is bind to the name <code>sym</code>.
	 * 
	 * @param name
	 *            String representing the symbol
	 * @return
	 */
	public Symbol lookup(String name) throws ExecutionError {
		if (symTable.containsKey(name)) {
			return symTable.get(name);
		} else {
			throw new UndefinedVariableError(name);
		}
	}

	/**
	 * Check if a given symbol is already defined.
	 * 
	 * @param name
	 * @return
	 */
	public boolean hasSymbol(String name) {
		if (symTable.containsKey(name))
			return true;
		else
			return false;
	}

	/**
	 * Add new symbol to the existing symbol table.
	 * 
	 * @param name
	 * @param sym
	 * @return
	 * @throws ExcutionError
	 */
	public Symbol insert(String name, Symbol sym) throws ExecutionError {
		if (sym == null)
			throw new UndefinedValueError(name);
		if (symTable.containsKey(name)) {
			throw new VariableRedefinitionError(name);
		} else {
			symTable.put(name, sym);
			return sym;
		}
	}

	/**
	 * Update value associated with a given symbol.
	 * 
	 * @param name
	 * @param sym
	 * @return
	 * @throws ExcutionError
	 */
	public Symbol update(String name, Symbol sym) throws ExecutionError {
		if (!symTable.containsKey(name)) {
			throw new UndefinedVariableError(name);
		} else if (sym == null) {
			throw new UndefinedValueError(name);
		} else {
			return symTable.put(name, sym);
		}
	}

	/**
	 * Merge another table of symbols into the exsiting one.
	 * 
	 * @param st
	 * @return
	 * @throws ExcutionError
	 */
	public SymbolTable appendSybolTable(SymbolTable st) throws ExecutionError {
		SymbolTable newSymbolTable = new SymbolTable();
		for (String s : getSymbols()) {
			newSymbolTable.insert(s, lookup(s));
		}
		if (st != null) {
			for (String s : st.getSymbols()) {
				if (this.lookup(s) == null) {
					newSymbolTable.insert(s, st.lookup(s));
				} else {
					newSymbolTable.update(s, st.lookup(s));
				}
			}
		}
		return newSymbolTable;
	}

	/**
	 * Return vector of symbols defined in the current symbol table.
	 * 
	 * @return
	 */
	public Vector<String> getSymbols() {
		Vector<String> symbols = new Vector<String>();
		for (String s : symTable.keySet()) {
			symbols.add(s);
		}
		return symbols;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("{");
			for (String s : symTable.keySet()) {
				sb.append(" ");
				sb.append(s);
				sb.append(" -> ");
				sb.append(lookup(s));
				sb.append(", ");
			}
			if (sb.lastIndexOf(",") > 0)
				sb.deleteCharAt(sb.lastIndexOf(","));
			sb.append("}");
		} catch (ExecutionError er) {
			System.err.println(er.getMessage());
		}
		return sb.toString();
	}

}
