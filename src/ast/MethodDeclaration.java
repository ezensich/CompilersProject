package ast;

import compilers.ASTVisitor;

public class MethodDeclaration extends AST {
	
	
	private IdName id;
	private GenericType type;
	private ArgumentDeclaration arguments;
	private Body body;
	
	public MethodDeclaration(IdName i, GenericType t, ArgumentDeclaration arg, Body b){
		this.id = i;
		this.type = t;
		this.arguments = arg;
		this.body = b;
	}
	
	public MethodDeclaration(IdName i, GenericType t, Body b){
		this.id = i;
		this.type = t;
		this.arguments = null;
		this.body = b;
	}
	
	@Override
    public String toString(){
		String result = type.toString() + " " + id.toString();
		if (arguments != null){
			result += "("+arguments.toString()+") \n "+body.toString();
		}
		else{
			result += "() \n "+body.toString();
		}
    	return result;
    }

	@Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }

}
