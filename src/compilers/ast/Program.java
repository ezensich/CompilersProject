package compilers.ast;

import java.util.LinkedList;
import java.util.List;

import compilers.ASTVisitor;

public class Program extends AST {

	private List<DeclarationClass> listDecClass;

	public Program(DeclarationClass decClass) {
		this.listDecClass = new LinkedList<>();
		this.listDecClass.add(decClass);
	}

	public void addDeclarationClass(DeclarationClass decClass) {
		this.listDecClass.add(decClass);
	}

	public List<DeclarationClass> getListDeclarationClass() {
		return this.listDecClass;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}
