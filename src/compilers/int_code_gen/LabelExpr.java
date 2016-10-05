package compilers.int_code_gen;

import compilers.ASTVisitor;
import compilers.ast.Expression;

public class LabelExpr extends Expression{
	
	private Integer id;
	private String label;
	
	public LabelExpr(Integer i, String l){
		this.id = i;
		this.label = l;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	@Override
	public String toString(){
		return id.toString()+label;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		// TODO Auto-generated method stub
		return v.visit(this);
	}

}
