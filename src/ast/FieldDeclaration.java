package ast;


import java.util.LinkedList;
import java.util.List;

import ast.enumerated_types.Type;
import compilers.ASTVisitor;
import data_structures.Pair;


public class FieldDeclaration extends AST {

	private Pair<Type, Identifier> pairTypeAndId;
	
	public FieldDeclaration(Pair pair){
		this.pairTypeAndId = pair;
	}
	
	public Pair<Type,Identifier> getFieldDec(){
		return this.pairTypeAndId;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
