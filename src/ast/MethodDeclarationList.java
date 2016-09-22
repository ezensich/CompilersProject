package ast;

import java.util.LinkedList;
import java.util.List;

import compilers.ASTVisitor;

public class MethodDeclarationList extends AST{

	private List<MethodDeclaration> listMethodDec;
	
	public MethodDeclarationList(MethodDeclaration md){
		this.listMethodDec = new LinkedList<MethodDeclaration>();
		this.listMethodDec.add(md);
	}
	
	public void addMethodDecToList(MethodDeclaration md){
		this.listMethodDec.add(md);
	}
	
	public List<MethodDeclaration> getMethodDecList(){
		return this.listMethodDec;
	}
	
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		// TODO Auto-generated method stub
		return null;
	}

}
