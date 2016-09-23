package ast;

import java.util.LinkedList;
import java.util.List;

import compilers.ASTVisitor;

public class Program extends AST {

	private List<DeclarationClass> listDecClass; 
	
	public Program(DeclarationClass decClass) {
		this.listDecClass = new LinkedList<>();
		this.listDecClass.add(decClass);
	}
	
	public void addDeclarationClass(DeclarationClass decClass){
		this.listDecClass.add(decClass);
	}
	
	public List<DeclarationClass> getListDeclarationClass(){
		return this.listDecClass;
	}
	
	@Override
    public String toString(){
		String result = "";
		if (listDecClass != null && !listDecClass.isEmpty()){
    		for(DeclarationClass decClass : listDecClass){
    			result += decClass.toString()+'\n';
    		}
    	}
    	return result;
    }
	
	@Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }

}
