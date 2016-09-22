package ast;

import ast.enumerated_types.Type;
import compilers.ASTVisitor;

public class MethodDeclaration extends AST {
	
	
	private IdName id;
	private Type type;
	private ArgumentDeclaration arguments;
	private Block block;
	
	public MethodDeclaration(IdName i, Type t, ArgumentDeclaration arg, Block b){
		this.id = i;
		this.type = t;
		this.arguments = arg;
		this.block = b;
	}
	
	public MethodDeclaration(IdName i, Type t, Block b){
		this.id = i;
		this.type = t;
		this.arguments = null;
		this.block = b;
	}
	
	@Override
    public String toString() {
        return "";
    }

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		// TODO Auto-generated method stub
		return null;
	}

}
