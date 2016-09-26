package compilers.ast;

import compilers.ast.enumerated_types.GenericType;
import compilers.ast.enumerated_types.Type;
import compilers.ASTVisitor;

public class IntegerLiteral extends Literal {

	private String stringValue;
	private Integer integerValue;
	

	public IntegerLiteral(String val, int line, int col){
		this.setColumnNumber(col);
		this.setLineNumber(line);
		stringValue = val; 
		integerValue = Integer.parseInt(val);
	}

	@Override
	public GenericType getType() {
		return new GenericType(Type.INTEGER.toString());
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
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }


}
