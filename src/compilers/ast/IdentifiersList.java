package compilers.ast;

import java.util.List;
import java.util.LinkedList;

import compilers.ASTVisitor;

public class IdentifiersList extends AST {

	private List<Identifier> listIdentifier;
	
	public IdentifiersList(Identifier id){
		this.listIdentifier = new LinkedList<>();
		this.listIdentifier.add(id);
	}
	
	public List<Identifier> getListIdentifier() {
		return listIdentifier;
	}
	
	public void addIdentifierToList(Identifier id){
		this.listIdentifier.add(id);
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}
