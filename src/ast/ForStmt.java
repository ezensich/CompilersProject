package ast;

import compilers.ProgramVisitor;

public class ForStmt extends Statement {
	
	private Identifier id;
	private Expression init;
	private Expression cota;
	private Statement forStatement;

	public ForStmt(Identifier id, Expression i, Expression c, Statement forStmt){
		this.id = id;
		this.init = i;
		this.cota = c;
		this.forStatement = forStmt;
		
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
	
	public void setForStatement (Statement s){
		this.forStatement = s;
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
	
	public Statement getForStatement(){
		return this.forStatement;
	}
	
	//method toString
	public String toString(){
		return id + "=" + init + "," + cota + '\n' + forStatement.toString();
	}
	
	public <T> T accept(ProgramVisitor<T> v) {
		return v.visit(this);
	}

}
