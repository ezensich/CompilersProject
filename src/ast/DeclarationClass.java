package ast;

import compilers.ASTVisitor;

public class DeclarationClass extends AST {

	private IdName idName;
	private FieldDeclarationList listFieldDec;
	private MethodDeclarationList listMethodDec;
	
	public DeclarationClass(IdName id, FieldDeclarationList fdl, MethodDeclarationList mdl){
		this.idName = id;
		this.listFieldDec = fdl;
		this.listMethodDec = mdl;
	}
	
	public MethodDeclarationList getMethodDecList(){
		return this.listMethodDec;
	}
	
	public FieldDeclarationList getFieldDecList(){
		return this.listFieldDec;
	}
	
	public IdName getIdName(){
		return this.idName;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		// TODO Auto-generated method stub
		return null;
	}
}
