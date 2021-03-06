package compilers.ast;

import compilers.ASTVisitor;

public class ContinueStmt extends Statement {

	public ContinueStmt(){
		this.setContinueStmtTrue();
	}
	
	@Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }

}
