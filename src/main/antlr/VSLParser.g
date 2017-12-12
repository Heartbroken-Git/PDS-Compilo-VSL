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
    : b=bloc { $out = new ASD.Program($b.out); }
    ;

bloc returns [ASD.Bloc out]
    : { List<ASD.Statement> ls = new ArrayList(); List<ASD.Declaration> ld = new ArrayList(); }
    (d=declaration {ld.add($d.out);} )*
    (s=statement {ls.add($s.out);} )+
    { $out = new ASD.Bloc(ld, ls); }
    ;

declaration returns [ASD.Declaration out]
    : { List<ASD.Assignable> la = new ArrayList();}
    TYPEINT (i=ident {la.add($i.out);} )
    (VIRGULE i=ident {la.add($i.out);} )*
    { $out = new ASD.Declaration(la); }
    ;

statement returns [ASD.Statement out]
    : e=expression { $out = $e.out; }
    | a=assignment { $out = $a.out; }
    ;

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
    | IDENT { $out = new ASD.IdentExpression($IDENT.text); }
    // TODO : that's all?
    ;
