package ast;

import java.util.LinkedList;
import java.util.List;

import compilers.ASTVisitor;

public class MethodDeclarationList extends AST{

	private List<MethodDeclaration> listMethodDec;
	
	public MethodDeclarationList(MethodDeclaration md){
		this.listMethodDec = new LinkedList<MethodDeclaration>();
		this.listMethodDec.add(md);
	}
	
	public void addMethodDecToList(MethodDeclaration md){
		this.listMethodDec.add(md);
	}
	
	public List<MethodDeclaration> getMethodDecList(){
		return this.listMethodDec;
	}
	
	@Override
    public String toString(){
		String result = "";
		if (listMethodDec != null && !listMethodDec.isEmpty()){
    		for(MethodDeclaration md : listMethodDec){
    			result += md.toString()+'\n'+'\n';
    		}
    	}
    	return result;
    }
	
	@Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }

}
