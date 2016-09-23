package ast;

import data_structures.*;

import java.util.LinkedList;
import java.util.List;

import ast.enumerated_types.Type;
import compilers.ASTVisitor;

public class ArgumentDeclaration extends AST {
	
	private List<Pair<GenericType,IdName>> listArgumentsDec;
	
	public ArgumentDeclaration(Pair<GenericType,IdName> pair){
		this.listArgumentsDec = new LinkedList<Pair<GenericType,IdName>>();
		this.listArgumentsDec.add(pair);
	}
	
	public void addArgumentDec(Pair<GenericType,IdName> pair){
		this.listArgumentsDec.add(pair);
	}
	
	@Override
	public String toString(){
		String result = "";
		if(listArgumentsDec != null && !listArgumentsDec.isEmpty()){
			result += listArgumentsDec.get(0).getFirst().toString() + " "+listArgumentsDec.get(0).getSecond().toString();
			for (int i = 1; i<listArgumentsDec.size();i++){
				result += ", "+listArgumentsDec.get(0).getFirst().toString() + " "+listArgumentsDec.get(0).getSecond().toString();
			}
		}
		return result;
	}

	@Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }

}
