package ast;

import compilers.ASTVisitor;

public class BoolLiteral extends Literal {
	private String stringValue;
	private Boolean booleanValue;
	

	public BoolLiteral(String val){
		stringValue = val; 
		booleanValue = Boolean.parseBoolean(val);
	}

	@Override
	public Type getType() {
		return Type.BOOL;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public Boolean getValue() {
		return booleanValue;
	}

	public void setValue(boolean value) {
		this.booleanValue = value;
	}

	@Override
	public String toString() {
		return stringValue;
	}

}
