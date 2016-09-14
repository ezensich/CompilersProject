package ast;

import compilers.ProgramVisitor;

public class ReturnStmt extends Statement {
	private Expression expression;
	
	public ReturnStmt(Expression e) {
		this.expression = e;
	}
	
	public ReturnStmt() {
		this.expression = null;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
	public String toString() {
		if (expression == null) {
			return "return";
		}
		else {
			return "return " + expression;
		}
	}
	
	public <T> T accept(ProgramVisitor<T> v) {
		return v.visit(this);
	}
}
