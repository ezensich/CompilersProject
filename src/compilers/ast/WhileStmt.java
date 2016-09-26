package compilers.ast;

import compilers.ASTVisitor;

public class WhileStmt extends Statement{
	private Expression condition;
	private Statement whileStatement;

	public WhileStmt(Expression cond, Statement whileStmt){
		this.condition = cond;
		this.whileStatement = whileStmt;
	}
	
	public Expression getCondition(){
		return this.condition;
	}
	
	public Statement getStatementCondition(){
		return this.whileStatement;
	}
	
	@Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }
	
	
}
