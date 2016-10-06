package compilers.ast;

import java.util.LinkedList;
import java.util.List;

import compilers.ASTVisitor;

public class IdName extends AST{

	List<String> idList;
	
	public IdName(String id){
		this.idList = new LinkedList<>();
		this.idList.add(id);
	}
	
	public String getId(){
		return this.toString();
	}
	
	public List<String> getIdList(){
		return this.idList;
	}
	
	public void extendId(String newId){
		this.idList.add(newId);
	}
	
	@Override
	public String toString(){
		String result = this.idList.get(0);
		for(int i = 1; i<idList.size();i++){
			result+= "."+this.idList.get(i); 
		}
		return result;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
	
}
