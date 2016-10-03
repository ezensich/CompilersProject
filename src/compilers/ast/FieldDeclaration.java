package compilers.ast;


import compilers.ast.enumerated_types.GenericType;
import compilers.ASTVisitor;


public class FieldDeclaration extends AST {

	private GenericType type;
	private IdentifiersList listIdentifiers;
	
	public FieldDeclaration(GenericType t, IdentifiersList list){
		this.type = t;
		this.listIdentifiers = list;
	}
	
	public IdentifiersList getIdentifiersList(){
		return this.listIdentifiers;
	}
	
	public GenericType getType(){
		return this.type;
	}

	@Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }
	
}
