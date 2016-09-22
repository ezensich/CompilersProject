package ast;

import compilers.ASTVisitor;

public class BreakStmt extends Statement {

	public BreakStmt(){
		this.setBreakStmtTrue();
	}
	
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		// TODO Auto-generated method stub
		return null;
	}

}
