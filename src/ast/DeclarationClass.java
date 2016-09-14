package ast;

import java.util.LinkedList;
import java.util.List;

public abstract class DeclarationClass extends AST {

	private List<FieldDeclaration> listFieldDec;
	private List<MethodDeclaration> listMetDec;
	
	public DeclarationClass(){
		this.listFieldDec = new LinkedList<FieldDeclaration>();
		this.listMetDec = new LinkedList<MethodDeclaration>();
	}
}
