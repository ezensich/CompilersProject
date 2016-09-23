package ast;

import java.util.LinkedList;
import java.util.List;

public abstract class Statement extends AST {

	private boolean isReturnStmt = false;
	private boolean isBreakStmt = false;
	private boolean isContinueStmt = false;

	public void setReturnStmtTrue() {
		this.isReturnStmt = true;
	}

	public void setBreakStmtTrue() {
		this.isBreakStmt = true;
	}

	public void setContinueStmtTrue() {
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
