package ast.enumerated_types;

public enum BinOpType {
	PLUS, // Arithmetic
	MINUS,
	PRODUCT,
	DIVIDE,
	MOD,
	LESS, // Relational
	HIGH,
	LESS_EQ,
	HIGH_EQ,
	EQUAL, // Equal
	DISTINCT, 
	AND, // Conditional
	OR;
	
	@Override
	public String toString() {
		switch(this) {
			case PLUS:
				return "+";
			case MINUS:
				return "-";
			case PRODUCT:
				return "*";
			case DIVIDE:
				return "/";
			case MOD:
				return "%";
			case LESS:
				return "<";
			case LESS_EQ:
				return "<=";
			case HIGH:
				return ">";
			case HIGH_EQ:
				return ">=";
			case EQUAL:
				return "==";
			case DISTINCT:
				return "!=";
			case AND:
				return "&&";
			case OR:
				return "||";
		}
		
		return null;
	}
}
