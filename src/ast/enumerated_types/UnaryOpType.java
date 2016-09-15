package ast.enumerated_types;

public enum UnaryOpType {
	MINUS,
    NOT;
    
    @Override
	public String toString() {
		switch(this) {
			case NOT:
				return "!";
			case MINUS:
				return "-";
                }
                return null;
        }
}
