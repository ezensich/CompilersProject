package compilers.ast;

import compilers.ASTVisitor;

public class IdName extends AST{

	private String id;
	
	public IdName(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}
	
	public void extendId(String newId){
		this.id = this.id +"."+ newId;
	}
	
	@Override
	public String toString(){
		return this.id;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
	
}
