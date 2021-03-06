lexer grammar VSLLexer;

options {
  language = Java;
}

@header {
  package TP2;
}

WS : (' '|'\n'|'\t') -> skip
   ;

COMMENT : '//' (~'\n')* -> skip
        ;

fragment LETTER : 'a'..'z' ;
fragment DIGIT  : '0'..'9' ;
fragment ASCII  : ~('\n'|'"');

// keywords
LP       : '('        ;
RP       : ')'        ;
PLUS     : '+'        ;
MINUS    : '-'        ;
TIMES    : '*'        ;
SDIV     : '/'        ;
ASSIGN   : ':='       ;
TYPEINT  : 'INT'      ;
VIRGULE  : ','        ;


// TODO : other keywords

// other tokens (no conflict with keywords in VSL)
IDENT   : LETTER (LETTER|DIGIT)*;
TEXT    : '"' (ASCII)* '"' { setText(getText().substring(1, getText().length() - 1)); };
INTEGER : (DIGIT)+ ;
