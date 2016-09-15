package ast;

import ast.enumerated_types.Type;
import compilers.ASTVisitor;

public class IntegerLiteral extends Literal {

	private String stringValue;
	private Integer integerValue;
	

	public IntegerLiteral(Integer val){
		stringValue = val.toString(); 
		integerValue = val;
	}

	@Override
	public Type getType() {
		return Type.INTEGER;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public Integer getValue() {
		return integerValue;
	}

	public void setValue(Integer value) {
		this.integerValue = value;
	}

	@Override
	public String toString() {
		return stringValue;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		// TODO Auto-generated method stub
		return null;
	}


}
