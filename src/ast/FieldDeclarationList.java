package ast;

import java.util.LinkedList;
import java.util.List;

import compilers.ASTVisitor;

public class FieldDeclarationList extends AST{
	
	private List<FieldDeclaration> listFieldDec = new LinkedList<>();
	
	public FieldDeclarationList(FieldDeclaration fd){
		this.listFieldDec.add(fd);
	}
	
	public void addFieldDecToList(FieldDeclaration fd){
		this.listFieldDec.add(fd);
	}
	
	public List<FieldDeclaration> getFieldDecList(){
		return this.getFieldDecList();
	}
	
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		// TODO Auto-generated method stub
		return null;
	}

}
