package compilers.ast;

import compilers.ast.enumerated_types.GenericType;
import compilers.ast.enumerated_types.Type;
import compilers.ASTVisitor;

public class BoolLiteral extends Literal {
	private String stringValue;
	private Boolean booleanValue;
	

	public BoolLiteral(String val, int line, int col){
		this.setColumnNumber(col);
		this.setLineNumber(line);
		stringValue = val; 
		booleanValue = Boolean.parseBoolean(val);
	}

	@Override
	public GenericType getType() {
		return new GenericType(Type.BOOL.toString());
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

	@Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }

}
