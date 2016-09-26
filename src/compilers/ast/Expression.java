package compilers.ast;


import compilers.ast.enumerated_types.GenericType;

public abstract class Expression extends AST{

	protected GenericType type;
	
	public GenericType getType() {
		return this.type;
	}
	
	public void setType(GenericType t) {
		this.type = t;
	}
	
}
