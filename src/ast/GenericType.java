package ast;

public class GenericType {

	private String type;
	
	public GenericType(String t){
		this.type=t;
	}
	
	public String getType(){
		return this.type;
	}
	
	@Override
	public String toString(){
		return type;
	}
}
