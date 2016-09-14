package ast;

public class FloatLiteral extends Literal {

	private String stringValue;
	private Float floatValue;
	

	public FloatLiteral(Float val){
		stringValue = val.toString(); 
		floatValue = val;
	}

	@Override
	public Type getType() {
		return Type.FLOAT;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public Float getValue() {
		return floatValue;
	}

	public void setValue(Float value) {
		this.floatValue = value;
	}

	@Override
	public String toString() {
		return stringValue;
	}

}
