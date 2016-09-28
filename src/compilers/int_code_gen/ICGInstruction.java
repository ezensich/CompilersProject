package compilers.int_code_gen;

import compilers.ast.Expression;

public class ICGInstruction {

	private ICGOperation op;
	private Expression p1;
	private Expression p2;
	private Expression p3;

	public ICGInstruction(ICGOperation op, Expression p1, Expression p2, Expression p3) {
		this.op = op;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}

	public ICGOperation getOp() {
		return this.op;
	}

	public Object getP1() {
		return this.p1;
	}

	public Object getP2() {
		return this.p2;
	}

	public Object getP3() {
		return this.p3;
	}

	@Override
	public String toString() {
		String res = op.toString();
		if (p1 != null) {
			res = res.concat(" " + p1.toString());
			if (p2 != null) {
				res = res.concat(" " + p2.toString());
				if (p3 != null) {
					res = res.concat(" " + p3.toString());
				}
			}
		}
		return res;
	}

}
