package ast;

import java.util.LinkedList;
import java.util.List;

import compilers.ASTVisitor;
import data_structures.Pair;

public class Identifier extends AST{

	private List<Pair<IdName,IntegerLiteral>> listIdentifier;
	
	public Identifier(Pair<IdName,IntegerLiteral> pair){
		listIdentifier = new LinkedList<>();
		this.listIdentifier.add(pair);
	}
	
	public void addIdentifierToList(Pair<IdName,IntegerLiteral> pair){
		this.listIdentifier.add(pair);
	}
	
	public List<Pair<IdName,IntegerLiteral>> getIdentifierList(){
		return this.listIdentifier;
	}
	
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		// TODO Auto-generated method stub
		return null;
	}

}
