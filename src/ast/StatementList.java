package ast;

import java.util.LinkedList;
import java.util.List;

import compilers.ASTVisitor;

public class StatementList extends Statement {

	private static List<Statement> listStmt = new LinkedList<>();
	
	public StatementList(Statement stmt){
		this.listStmt.add(stmt);
	}
	
	public void addStatementToList(Statement stmt) {
		listStmt.add(stmt);
	}

	public List<Statement> getStatementList() {
		return listStmt;
	}

	
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		// TODO Auto-generated method stub
		return null;
	}

}
