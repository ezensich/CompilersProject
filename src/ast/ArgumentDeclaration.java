package ast;

import data_structures.*;

import java.util.LinkedList;
import java.util.List;

import ast.enumerated_types.Type;
import compilers.ASTVisitor;

public class ArgumentDeclaration extends AST {
	
	private List<Pair<Type,IdName>> listArgumentsDec;
	
	public ArgumentDeclaration(Pair<Type,IdName> pair){
		this.listArgumentsDec = new LinkedList<Pair<Type,IdName>>();
		this.listArgumentsDec.add(pair);
	}
	
	public void addArgumentDec(Pair<Type,IdName> pair){
		this.listArgumentsDec.add(pair);
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		// TODO Auto-generated method stub
		return null;
	}

}
