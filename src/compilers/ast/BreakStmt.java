package compilers.ast;

import compilers.ASTVisitor;

public class BreakStmt extends Statement {

	public BreakStmt(){
		this.setBreakStmtTrue();
	}
	
	@Override
	public String toString(){
		return "break";
	}
	
	@Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }

}
