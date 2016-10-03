package compilers.ast;

import compilers.ASTVisitor;

public class ForStmt extends Statement {

	private IdName id;
	private Expression init;
	private Expression cota;
	private Statement forStatement;

	public ForStmt(IdName id, Expression i, Expression c, Statement forStmt) {
		this.id = id;
		this.init = i;
		this.cota = c;
		this.forStatement = forStmt;

	}

	public IdName getId() {
		return this.id;
	}

	public Expression getInit() {
		return this.init;
	}

	public Expression getCota() {
		return this.cota;
	}

	public Statement getForStatement() {
		return this.forStatement;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}
