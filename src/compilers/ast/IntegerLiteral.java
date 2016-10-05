package compilers.ast;

import compilers.ast.enumerated_types.Type;
import compilers.ASTVisitor;

public class IntegerLiteral extends Literal {

	private String stringValue;
	private Integer integerValue;
	private GenericType type = new GenericType(Type.INTEGER.toString());
	
	public IntegerLiteral(Integer val){
		this.integerValue = val;
		this.stringValue = val.toString();
	}
	
	public IntegerLiteral(Integer val, int line, int col){
		this.setColumnNumber(col);
		this.setLineNumber(line);
		stringValue = val.toString(); 
		integerValue = val;
	}
	
	@Override
	public GenericType getType() {
		return this.type;
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
