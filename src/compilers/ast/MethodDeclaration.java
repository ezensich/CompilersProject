package compilers.ast;

import compilers.ast.enumerated_types.GenericType;
import compilers.ASTVisitor;

public class MethodDeclaration extends AST {
	
	
	private IdName id;
	private GenericType type;
	private ArgumentDeclaration arguments;
	private Body body;
	private int offset = 0;
	
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
	
	public IdName getId() {
		return id;
	}

	public GenericType getType() {
		return type;
	}

	public ArgumentDeclaration getArguments() {
		return arguments;
	}

	public Body getBody() {
		return body;
	}
	
	public int getOffset(){
		return this.offset;
	}
	
	public void setOffset(int o){
		this.offset = o;
	}

	@Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }

}
