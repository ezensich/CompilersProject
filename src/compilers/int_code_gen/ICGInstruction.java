package compilers.int_code_gen;

import compilers.ast.AST;
import compilers.ast.Expression;
import compilers.semcheck.PrintASTVisitor;

public class ICGInstruction {

	private ICGOperation op;
	private AST p1;
	private AST p2;
	private AST p3;

	public ICGInstruction(ICGOperation op, AST p1, AST p2, AST p3) {
		this.op = op;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}

	public ICGOperation getOp() {
		return this.op;
	}

	public AST getP1() {
		return this.p1;
	}

	public AST getP2() {
		return this.p2;
	}

	public AST getP3() {
		return this.p3;
	}

	@Override
	public String toString() {
		String res = op.toString();
		if (p1 != null) {
			res += " " + (p1.accept(new PrintASTVisitor()));
		} else {
			res += " -";
		}
		if (p2 != null) {
			res += " " + (p2.accept(new PrintASTVisitor()));
		} else {
			res += " -";
		}
		if (p3 != null) {
			res += " " + (p3.accept(new PrintASTVisitor()));
		} else {
			res += " -";
		}
		return "[ " + res + " ]";
	}

}
