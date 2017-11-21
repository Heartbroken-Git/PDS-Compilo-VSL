package TP2;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ASD {
  static public class Program {
    Expression e; // What a program contains. TODO : change when you extend the language

    public Program(Statement s) {
      this.e = e;
    }

    // Pretty-printer
    public String pp() {
      return e.pp();
    }

    // IR generation
    public Llvm.IR toIR() throws TypeException {
      // TODO : change when you extend the language

      // computes the IR of the expression
      Expression.RetExpression retExpr = e.toIR();
      // add a return instruction
      Llvm.Instruction ret = new Llvm.Return(retExpr.type.toLlvmType(), retExpr.result);
      retExpr.ir.appendCode(ret);


      return retExpr.ir;
    }
  }

  // All toIR methods returns the IR, plus extra information (synthesized attributes)
  // They can take extra arguments (inherited attributes)

  static public abstract class Statement {
    public abstract String pp();
    public abstract RetStatement toIR() throws TypeException;

    static public class RetStatement{
      // The LLVM IR:
      public Llvm.IR ir;

      public RetStatement(Llvm.IR ir) {
        this.ir = ir;
      }

    }
  }

  static public abstract class Assignable{
    public abstract String pp();
    public abstract RetAssignable toIR() throws TypeException;

    // Object returned by toIR on expressions, with IR + synthesized attributes
    static public class RetAssignable{
      // The LLVM IR:
      public Llvm.IR ir;
      // And additional stuff:
      public Type type;
      public String result; // The name containing the expression's result
// (either an identifier, or an immediate value)

      public RetAssignable(Llvm.IR ir, Type type, String result) {
        this.ir = ir;
        this.type = type;
        this.result = result;
      }
    }
  }

  static public abstract class Expression{
    public abstract String pp();
    public abstract RetExpression toIR() throws TypeException;

    // Object returned by toIR on expressions, with IR + synthesized attributes
    static public class RetExpression{
      // The LLVM IR:
      public Llvm.IR ir;
      // And additional stuff:
      public Type type; // The type of the expression
      public String result; // The name containing the expression's result
      // (either an identifier, or an immediate value)

      public RetExpression(Llvm.IR ir, Type type, String result) {
        this.ir = ir;
        this.type = type;
        this.result = result;
      }
    }
  }


  // Concrete class for Expression: add case
  static public class AddExpression extends Expression {
    Expression left;
    Expression right;

    public AddExpression(Expression left, Expression right) {
      this.left = left;
      this.right = right;
    }

    // Pretty-printer
    public String pp() {
      return "(" + left.pp() + " + " + right.pp() + ")";
    }

    // IR generation
    public RetExpression toIR() throws TypeException {
      RetExpression leftRet = left.toIR();
      RetExpression rightRet = right.toIR();

      // We check if the types mismatches
      if(!leftRet.type.equals(rightRet.type)) {
        throw new TypeException("type mismatch: have " + leftRet.type + " and " + rightRet.type);
      }

      // We base our build on the left generated IR:
      // append right code
      leftRet.ir.append(rightRet.ir);

      // allocate a new identifier for the result
      String result = Utils.newtmp();

      // new add instruction result = left + right
      Llvm.Instruction add = new Llvm.Add(leftRet.type.toLlvmType(), leftRet.result, rightRet.result, result);

      // append this instruction
      leftRet.ir.appendCode(add);

      // return the generated IR, plus the type of this expression
      // and where to find its result
      return new RetExpression(leftRet.ir, leftRet.type, result);
    }
  }

  // Concrete class for Expression: mul case
  static public class TimesExpression extends Expression {
    Expression left;
    Expression right;

    public TimesExpression(Expression left, Expression right) {
      this.left = left;
      this.right = right;
    }

    // Pretty-printer
    public String pp() {
      return "(" + left.pp() + " * " + right.pp() + ")";
    }

    // IR generation
    public RetExpression toIR() throws TypeException {
      RetExpression leftRet = left.toIR();
      RetExpression rightRet = right.toIR();

      // We check if the types mismatches
      if(!leftRet.type.equals(rightRet.type)) {
        throw new TypeException("type mismatch: have " + leftRet.type + " and " + rightRet.type);
      }

      // We base our build on the left generated IR:
      // append right code
      leftRet.ir.append(rightRet.ir);

      // allocate a new identifier for the result
      String result = Utils.newtmp();

      // new mul instruction result = left * right
      Llvm.Instruction mul = new Llvm.Mul(leftRet.type.toLlvmType(), leftRet.result, rightRet.result, result);

      // append this instruction
      leftRet.ir.appendCode(mul);

      // return the generated IR, plus the type of this expression
      // and where to find its result
      return new RetExpression(leftRet.ir, leftRet.type, result);
    }
  }

  // Concrete class for Expression: sub case
  static public class SubExpression extends Expression {
    Expression left;
    Expression right;

    public SubExpression(Expression left, Expression right) {
      this.left = left;
      this.right = right;
    }

    // Pretty-printer
    public String pp() {
      return "(" + left.pp() + " - " + right.pp() + ")";
    }

    // IR generation
    public RetExpression toIR() throws TypeException {
      RetExpression leftRet = left.toIR();
      RetExpression rightRet = right.toIR();

      // We check if the types mismatches
      if(!leftRet.type.equals(rightRet.type)) {
        throw new TypeException("type mismatch: have " + leftRet.type + " and " + rightRet.type);
      }

      // We base our build on the left generated IR:
      // append right code
      leftRet.ir.append(rightRet.ir);

      // allocate a new identifier for the result
      String result = Utils.newtmp();

      // new sub instruction result = left - right
      Llvm.Instruction sub = new Llvm.Sub(leftRet.type.toLlvmType(), leftRet.result, rightRet.result, result);

      // append this instruction
      leftRet.ir.appendCode(sub);

      // return the generated IR, plus the type of this expression
      // and where to find its result
      return new RetExpression(leftRet.ir, leftRet.type, result);
    }
  }

  // Concrete class for Expression: sdiv case
  static public class SDivExpression extends Expression {
    Expression left;
    Expression right;

    public SDivExpression(Expression left, Expression right) {
      this.left = left;
      this.right = right;
    }

    // Pretty-printer
    public String pp() {
      return "(" + left.pp() + " / " + right.pp() + ")";
    }

    // IR generation
    public RetExpression toIR() throws TypeException {
      RetExpression leftRet = left.toIR();
      RetExpression rightRet = right.toIR();

      // We check if the types mismatches
      if(!leftRet.type.equals(rightRet.type)) {
        throw new TypeException("type mismatch: have " + leftRet.type + " and " + rightRet.type);
      }

      // We base our build on the left generated IR:
      // append right code
      leftRet.ir.append(rightRet.ir);

      // allocate a new identifier for the result
      String result = Utils.newtmp();

      // new mul instruction result = left / right
      Llvm.Instruction sdiv = new Llvm.SDiv(leftRet.type.toLlvmType(), leftRet.result, rightRet.result, result);

      // append this instruction
      leftRet.ir.appendCode(sdiv);

      // return the generated IR, plus the type of this expression
      // and where to find its result
      return new RetExpression(leftRet.ir, leftRet.type, result);
    }
  }

  // Concrete class for Expression: constant (integer) case
  static public class IntegerExpression extends Expression {
    int value;
    public IntegerExpression(int value) {
      this.value = value;
    }

    public String pp() {
      return "" + value;
    }

    public RetExpression toIR() {
      // Here we simply return an empty IR
      // the `result' of this expression is the integer itself (as string)
      return new RetExpression(new Llvm.IR(Llvm.empty(), Llvm.empty()), new IntType(), "" + value);
    }
  }

  // Concrete class for Statement: assignement case
  static public class Assignement extends Statement {
    Assignable left;
    Expression right;

    public Assignement (Assignable a, Expression e){
      left = a;
      right = e;

    }

    public String pp() {
      return "(" + left.pp() + " := " + right.pp() + ")";
    }

    public RetStatement toIR() throws TypeException {
      Assignable.RetAssignable leftRet = left.toIR();
      Expression.RetExpression rightRet = right.toIR();

      // We check if the types mismatches
      if(!leftRet.type.equals(rightRet.type)) {
        throw new TypeException("type mismatch: have " + leftRet.type + " and " + rightRet.type);
      }

      // We base our build on the left generated IR:
      // append right code
      leftRet.ir.append(rightRet.ir);

      // new mul instruction result = left := right
      Llvm.Instruction assign = new Llvm.Assign(leftRet.result, rightRet.result);
      
      leftRet.ir.appendCode(assign);

      return new RetStatement(leftRet.ir);
    }
  }

  // Warning: this is the type from VSL+, not the LLVM types!
  static public abstract class Type {
    public abstract String pp();
    public abstract Llvm.Type toLlvmType();
  }

  static class IntType extends Type {
    public String pp() {
      return "INT";
    }

    @Override public boolean equals(Object obj) {
      return obj instanceof IntType;
    }

    public Llvm.Type toLlvmType() {
      return new Llvm.IntType();
    }
  }
}
