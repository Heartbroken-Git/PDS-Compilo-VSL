parser grammar VSLParser;

options {
  language = Java;
  tokenVocab = VSLLexer;
}

@header {
  package TP2;

  import java.util.stream.Collectors;
  import java.util.Arrays;
}


// TODO : other rules

program returns [ASD.Program out]
    : e=expression { $out = new ASD.Program($e.out); }
    | a=assignment { $out = new ASD.Program($a.out); }
    ;

//statement returns [ASD.Statement out]
//    : e=expression { $out = new ASD.Statement($e.out); }
//    | a=assignment { $out = new ASD.Statement($a.out); }
//    ;

assignment returns [ASD.Assignment out]
    : l=ident ASSIGN r=expression { $out = new ASD.Assignment($l.out, $r.out); }
    ;

expression returns [ASD.Expression out]
    : l=expression2 PLUS r=expression  { $out = new ASD.AddExpression($l.out, $r.out); }
    | l=expression2 MINUS r=expression { $out = new ASD.SubExpression($l.out, $r.out); }
    | f=expression2 { $out = $f.out; }
    // TODO : that's all?
    ;

expression2 returns [ASD.Expression out]
    : l=factor TIMES r=expression2 { $out = new ASD.TimesExpression($l.out, $r.out); }
    | l=factor SDIV r=expression2 { $out = new ASD.SDivExpression($l.out, $r.out); }
    | f=factor { $out = $f.out; }
    ;

factor returns [ASD.Expression out]
    : p=primary { $out = $p.out; }
    | LP e=expression RP { $out = $e.out; }
    // TODO : that's all?
    ;

ident returns [ASD.Assignable out]
    : IDENT { $out = new ASD.IdentAssignable($IDENT.text); }
    ;

primary returns [ASD.Expression out]
    : INTEGER { $out = new ASD.IntegerExpression($INTEGER.int); }
    // TODO : that's all?
    ;
