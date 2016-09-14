package ast;

import java.util.List;

import compilers.ASTVisitor;

public class Program extends AST {

	private List<DeclarationClass> listDecClass; 
	
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		// TODO Auto-generated method stub
		return null;
	}

}
