package compilers.ast;

import java.util.LinkedList;
import java.util.List;

import compilers.ASTVisitor;

public class FieldDeclarationList extends AST{
	
	private List<FieldDeclaration> listFieldDec = new LinkedList<FieldDeclaration>();
	
	public FieldDeclarationList(FieldDeclaration fd){
		this.listFieldDec.add(fd);
	}
	
	public void addFieldDecToList(FieldDeclaration fd){
		this.listFieldDec.add(fd);
	}
	
	public List<FieldDeclaration> getFieldDecList(){
		return this.listFieldDec;
	}
	
	@Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }

}
