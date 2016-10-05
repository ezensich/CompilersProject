package  compilers.ast;

import compilers.ASTVisitor;

public class GenericType extends AST{

	private String type;
	
	public GenericType(String t){
		this.type=t;
	}
	
	public String getType(){
		return this.type;
	}
	
	@Override
	public String toString(){
		return type;
	}
	
	public boolean isBool(){
		return this.type == "bool";
	}
	
	public boolean isFloat(){
		return this.type == "float";
	}
	
	public boolean isInteger(){
		return this.type == "integer";
	}
	
	public boolean isVoid(){
		return this.type == "void";
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
