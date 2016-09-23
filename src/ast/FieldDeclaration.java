package ast;


import java.util.LinkedList;
import java.util.List;

import ast.enumerated_types.Type;
import compilers.ASTVisitor;
import data_structures.Pair;


public class FieldDeclaration extends AST {

	private Pair<GenericType, Identifier> pairTypeAndId;
	
	public FieldDeclaration(Pair<GenericType, Identifier> pair){
		this.pairTypeAndId = pair;
	}
	
	public Pair<GenericType,Identifier> getFieldDec(){
		return this.pairTypeAndId;
	}
	
	@Override
	public String toString(){
		return pairTypeAndId.getFirst().toString() + " "+ pairTypeAndId.getSecond().toString()+";";
	}

	@Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }
	
}
