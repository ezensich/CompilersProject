package ast;

import compilers.ASTVisitor;

public class WhileStmt extends Statement{
	private Expression condition;
	private Statement whileStatement;

	public WhileStmt(Expression e, Statement whileStmt){
		this.condition = e;
		this.whileStatement = whileStmt;
	}
	
	//methods set and gets
	//sets
	public void setCondition(Expression cond){
		this.condition = cond;
	}
	
	public void setWhileStatement (Statement whileStmt){
		this.whileStatement = whileStmt;
	}
	
	//gets
	public Expression getCondition(){
		return this.condition;
	}
	
	public Statement getStatementCondition(){
		return this.whileStatement;
	}
	
	public String toString(){
		return "while" + condition + '\n' + whileStatement.toString();
		
	}
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
	
	
}
