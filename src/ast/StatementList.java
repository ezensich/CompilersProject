package ast;

import compilers.ASTVisitor;

public class StatementList extends Statement {

	public StatementList(Statement stmt) {
		addStatementToList(stmt);
	}
	
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		// TODO Auto-generated method stub
		return null;
	}

}
