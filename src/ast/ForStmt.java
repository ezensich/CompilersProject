package ast;

import compilers.ProgramVisitor;

public class ForStmt extends Statement {
	
	private Identifier id;
	private Expression init;
	private Expression cota;
	private Block forBlock;

	public ForStmt(Identifier id, Expression i, Expression c, Block forB){
		this.id = id;
		this.init = i;
		this.cota = c;
		this.forBlock = forB;
		
	}
	
	//gets and sets
	//sets
	public void setId(Identifier i){
		this.id = i;
	}
	
	public void setInit (Expression i){
		this.init = i;
	}
	
	public void setCota (Expression c){
		this.cota = c;
	}
	
	public void setForBlock (Block b){
		this.forBlock = b;
	}
	
	//gets
	public Identifier getId(){
		return this.id;
	}
	
	public Expression getInit (){
		return this.init;
	}
	
	public Expression getCota(){
		return this.cota;
	}
	
	public Block getForBlock(){
		return this.forBlock;
	}
	
	//method toString
	public String toString(){
		return id + "=" + init + "," + cota + '\n' + forBlock.toString();
	}
	
	public <T> T accept(ProgramVisitor<T> v) {
		return v.visit(this);
	}

}
