package  compilers.ast;

import compilers.ASTVisitor;
import compilers.ast.enumerated_types.Type;

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
		return this.type.equals(Type.BOOL.toString());
	}
	
	public boolean isFloat(){
		return this.type.equals(Type.FLOAT.toString());
	}
	
	public boolean isInteger(){
		return this.type.equals(Type.INTEGER.toString());
	}
	
	public boolean isVoid(){
		return this.type.equals(Type.VOID.toString());
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
