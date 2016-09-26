package compilers.ast;

import java.util.LinkedList;
import java.util.List;

import compilers.ASTVisitor;

public class ArgumentDeclaration extends AST {
	
	private List<Parameter> listArgumentsDec;
	
	public ArgumentDeclaration(Parameter parameter){
		this.listArgumentsDec = new LinkedList<Parameter>();
		this.listArgumentsDec.add(parameter);
	}
	
	public void addArgumentDec(Parameter par){
		this.listArgumentsDec.add(par);
	}
	
	public List<Parameter> getArgumentsList(){
		return this.listArgumentsDec;
	}
 	
	@Override
	public String toString(){
		String result = "";
		/*if(listArgumentsDec != null && !listArgumentsDec.isEmpty()){
			result += listArgumentsDec.get(0).getFirst().toString() + " "+listArgumentsDec.get(0).getSecond().toString();
			for (int i = 1; i<listArgumentsDec.size();i++){
				result += ", "+listArgumentsDec.get(0).getFirst().toString() + " "+listArgumentsDec.get(0).getSecond().toString();
			}
		} */
		return result;
	}

	@Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }

}
