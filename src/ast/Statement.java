package ast;

import java.util.LinkedList;
import java.util.List;

public abstract class Statement extends AST {

	protected static List<Statement> listStmt = new LinkedList<>();
	private boolean isReturnStmt = false;
	private boolean isBreakStmt = false;
	private boolean isContinueStmt = false;

	public static void addStatementToList(Statement stmt) {
		listStmt.add(stmt);
	}

	public static List<Statement> getStatementList() {
		return listStmt;
	}

	public void setReturnStmtTrue() {
		this.isReturnStmt = true;
		this.isBreakStmt = false;
		this.isContinueStmt = false;
	}

	public void setBreakStmtTrue() {
		this.isReturnStmt = false;
		this.isBreakStmt = true;
		this.isContinueStmt = false;
	}

	public void setContinueStmtTrue() {
		this.isReturnStmt = false;
		this.isBreakStmt = false;
		this.isContinueStmt = true;
	}

	public boolean isBreakStmt() {
		return isBreakStmt;
	}

	public boolean isContinueStmt() {
		return isContinueStmt;
	}
	
	public boolean isReturnStmt() {
		return isReturnStmt;
	}

}
