package ast;

import compilers.ASTVisitor;

public class WhileStmt extends Statement{
	private Expression condition;
	private Block whileBlock;

	public WhileStmt(Expression e, Block whileB){
		this.condition = e;
		this.whileBlock = whileB;
	}
	
	//methods set and gets
	//sets
	public void setCondition(Expression cond){
		this.condition = cond;
	}
	
	public void setWhileBlock (Block b){
		this.whileBlock = b;
	}
	
	//gets
	public Expression getCondition(){
		return this.condition;
	}
	
	public Block getBlockCondition(){
		return this.whileBlock;
	}
	
	public String toString(){
		return "while" + condition + '\n' + whileBlock.toString();
		
	}
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
	
	
}
