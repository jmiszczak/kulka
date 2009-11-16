grammar Kulka;

options {
  language = Java;
  output = AST;
  ASTLabelType = CommonTree;
}

tokens { 
PROGRAM; // root
NOP; // not an operations
VARDEF; // variable definition
VARREF; // variable reference
NOT; // logical negation 
NEG; // artithmetic negation
SAY; // print statement
ASK; // read statement
IF; // if construction
VEC; // ket vector
NUM; // number
CPLX; // complex number
STR; // string
}

@header {
package kulka.lang;


}

@lexer::header {
package kulka.lang;
}

program
  : stmtOrDefList EOF 
  -> ^(PROGRAM  stmtOrDefList)
  ;

stmtOrDefList
  : stmtOrDef*
  ;

stmtOrDef
  : def
  | stmt
  ;

def
  : varDef ';'!
  ;

stmt
  // basic statemets
  : ';' -> NOP
  | expr ';'!
  | ifStmt
  | sayStmt
  ;
  
ifStmt
options{
backtrack=true;
}
  : 'if' '(' expr ')' caseTrue=stmt 'else' caseFalse=stmt 
  -> ^(IF expr $caseTrue $caseFalse)
  | 'if' '(' expr ')' stmt 
  -> ^(IF expr stmt NOP)
  ;
  
sayStmt
  : 'say' expr ';' -> ^(SAY expr)
  ;
  
varDef
options{
backtrack=true;
}
  :
  t=TYPE i=ID ('=' v=expr)?  
  -> {v != null}? ^(VARDEF $t $i $v)
  -> ^(VARDEF $t $i)
  ;
  
expr
options{
backtrack=true;
}
  : orExpr
  ;

orExpr
  : andExpr (('or'|'||')^ andExpr)*
  ;

andExpr 
  : eqExpr (('and'|'&&')^ eqExpr)*
  ;
  
eqExpr
  : compExpr (('=='|'!='|'<>')^ compExpr)*
  ;

compExpr
  : addExpr (('>'|'<'|'<='|'>=')^ addExpr)*
  ;

addExpr
  : mulExpr (('+'|'-')^ mulExpr)*
  ;

mulExpr
  : notExpr (('*'|'/'| '&')^ notExpr)*
  ;

notExpr
  : (op='!'|op='not')? negExpr
  -> {op != null}? ^(NOT[$op,"NOT"] negExpr)
  -> negExpr
  ;

negExpr
  : (op='-')? primary 
  -> {op != null}? ^(NEG[$op,"NEG"] primary)
  -> primary
  ;

primary 
  : atom
  | '(' expr ')' -> expr  
  ;

atom
  : s=ID -> ^(VARREF $s) 
  | s=KET -> ^(VEC $s) 
  | s=SUPERPOSE -> ^(SUPERPOSE[$s,"SUPERPOSE"] $s) 
  | '[' rp=expr ',' ip=expr ']' -> ^(CPLX  $rp $ip) 
  | s=STRING -> ^(STR $s) 
  | s=INT -> ^(NUM[$s,"INT"] INT)
  | s=REAL -> ^(NUM[$s,"REAL"] REAL)
  ;
  
/************************* Lexer *************************/

TYPE
  : 'int' | 'real' | 'complex' | 'string' | 'qreg' | 'qint' 
  ;
 
ID
  : LETTER ('_' | LETTER | DIGIT)*
  ;

KET
	: '|' DIGIT* '>'
	{ 
		setText(getText().substring(1, getText().length()-1)); 
	}
	; 

SUPERPOSE
  : '(' INT ('|' INT)+ ')'
  {
    setText(getText().substring(1, getText().length()-1).replace('|' , ' ' ));
  }
  ;

WS  
  : ('\t'|' '|NEWLINE)+ { $channel=HIDDEN; }
  ;
  
INT 
  : ('-'|'+')? DIGIT*
  ;

REAL
  : ('-'|'+')? DIGIT* '.' DIGIT+
  ;
  
  
STRING
  : DQ_STRING | SQ_STRING
  ;
  
ML_COMMENT  
  : '/*' (options {greedy=false;}:.)* '*/' {$channel=HIDDEN;}
  ;
    
SL_COMMENT
  :( '#'|'//') ~('\r'|'\n')* NEWLINE {$channel=HIDDEN;}
  ;

 
fragment LETTER
  : 'a'..'z' | 'A'..'Z'
  ;

fragment DIGIT
  : '0'..'9'
  ;

fragment NEWLINE
  : '\r' | '\n'
  ;
  
fragment ESC_SEQ
  : '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
  ;
  
fragment DQ_STRING
  : '"' ( ESC_SEQ | ~('\\'|'"') )* '"'
  {
    String txt = getText(); 
    txt = txt.substring(1, txt.length() -1);
    txt = txt.replaceAll("\\\\n","\n");
    txt = txt.replaceAll("\\\\t","\t");
    txt = txt.replaceAll("\\\\b","\b");
    txt = txt.replaceAll("\\\\r","\r");
    txt = txt.replaceAll("\\\\f","\f");
    txt = txt.replaceAll("\\\\'","'");
    txt = txt.replaceAll("\\\\\"","\"");
    setText(txt);
  }
  ;

fragment SQ_STRING
  : '\'' ( ESC_SEQ | ~('\''|'\\') )* '\'' 
  { 
    setText(getText().substring(1, getText().length()-1)); 
  }
  ;