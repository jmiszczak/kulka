tree grammar KulkaEvaluator;

options {
  language = Java;
  tokenVocab = Kulka;
  ASTLabelType = CommonTree;
}

@header { 
package kulka.lang;

import static java.lang.System.out;
import static java.lang.System.err;

import java.util.Vector;
import java.io.PrintStream;

import kulka.lang.errors.ExecutionError;
import kulka.lang.errors.VariableInitializationError; 
import kulka.lang.errors.VariableRedefinitionError;
import kulka.lang.errors.UndefinedVariableError;
import kulka.lang.errors.UnknowTypeError;

import kulka.lang.types.DataType;
import kulka.lang.types.QregType;
import kulka.lang.types.QintType;
import kulka.lang.types.StringType;
import kulka.lang.types.ComplexType;
import kulka.lang.types.RealType;
import kulka.lang.types.IntType;

}

@members {

private Environment globalEnv;
private PrintStream out;

private CommonTreeNodeStream stream = (CommonTreeNodeStream)input;

public Environment getGlobalEnv() {
  return globalEnv;
}

public void setOutputStream(PrintStream stream) {
  out = stream;
}

// this constructor allows for a global symbol table
public KulkaEvaluator(TreeNodeStream input, Environment env) {
  super(input);
  globalEnv = env;
}

private void complain(ExecutionError e) {
  this.out.println("[Eval]\t Error while executing script!");
  this.out.println(e.getMessage());
}

}

program
  : ^(PROGRAM  ( varDef | stmt | . )* ) 
  ;
  
varDef
  : ^(VARDEF type=TYPE name=ID (initVal=expr)?) 
  { try {
      Symbol s ;
      if (initVal != null){
        s = new Symbol($name.text, initVal);  
      } else {
        s = new Symbol($name.text, $type.text);
      }
      globalEnv.insert(s.getName(), s);
    } catch (VariableRedefinitionError e) {
      complain(e);
    } catch (ExecutionError e) {
      complain(e);
    }
  }
  ;
  
stmt 
  : ^(SAY v=expr) 
  { 
    out.print(v.toString());
  }
  ;

/**
 * 
 * Expressions.
 * 
 **/
 
expr returns [DataType val]
  : v = varRef { val = v; }
  | v = ketVec { val = v; }
  | v = sPose { val = v; }
  | v = string { val = v; }
  | v = complex { val = v; }
  | v = numeric { val = v; }
  | v = mathOper { val = v; }
  ;

varRef returns [DataType val]
  : ^(VARREF s=ID) 
  { try { 
      val = globalEnv.lookup($s.text).getValue();
    } catch (UndefinedVariableError e) {
      complain(e);
    } catch (ExecutionError e) {
      complain(e);
    } 
  }
  ;

ketVec returns [DataType val]
  : ^(VEC v=KET) 
  { try {
      val = new QregType ($v.text);
    } catch (VariableInitializationError e) {
      complain(e);
      e.printStackTrace();
    } 
  }
  ;

sPose returns [DataType val] 
  : ^(SUPERPOSE s=.)
  { try {
      val = new QintType ($s.toString());
    } catch (VariableInitializationError e) {
      complain(e);
      e.printStackTrace();
    }
  }
  ;
  
string returns [DataType val]
  : ^(STR s=.)
  {
    val = new StringType (s.toString());
  }
  ;

complex returns [DataType val]
  : ^(CPLX e1=expr e2=expr) 
  { try {
      val = new ComplexType(e1.getValue(),e2.getValue());
    } catch (VariableInitializationError e) {
      complain(e);
    }
  } 
  ;

numeric returns [DataType val]
  : ^(NUM INT)  { val = new IntType($INT.text); }
  | ^(NUM REAL) { val = new RealType($REAL.text); }
  ;
  
mathOper returns [DataType val]
  : ^('+' e1=expr e2=expr)  { val = e1.add(e2);}
  | ^('-' e1=expr e2=expr)  { }
  | ^('*' e1=expr e2=expr)  { }
  | ^('/' e1=expr e2=expr)  { }
  ;
catch [VariableInitializationError e] {
  complain(e);
}