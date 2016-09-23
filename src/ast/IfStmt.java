package ast;

import compilers.*;

public class IfStmt extends Statement {
	private Expression condition;
	private Statement ifStatement;
	private Statement elseStatement;

	public IfStmt(Expression cond, Statement ifStmt) {
		this.condition = cond;
		this.ifStatement = ifStmt;
		this.elseStatement = null;
	}

	public IfStmt(Expression cond, Statement ifBl, Statement elseStmt) {
		this.condition = cond;
		this.ifStatement = ifBl;
		this.elseStatement = elseStmt;
	}

	public Expression getCondition() {
		return condition;
	}

	public void setCondition(Expression condition) {
		this.condition = condition;
	}

	public Statement getIfStatement() {
		return ifStatement;
	}

	public void setIfStatement(Statement ifStatement) {
		this.ifStatement = ifStatement;
	}

	public Statement getElseStatement() {
		return elseStatement;
	}

	public void setElseStatement(Statement elseStatement) {
		this.elseStatement = elseStatement;
	}

	@Override
	public String toString() {
		String result = "if (" + condition.toString() + ") \n" + ifStatement.toString() + "\n";

		if (elseStatement != null) {
			result += "else \n" + elseStatement.toString() + "\n";
		}

		return result;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
