package compilers.ast.enumerated_types;

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
	


	public boolean isArithmetic() {
		if (this == BinOpType.PLUS || this == BinOpType.MINUS || this == BinOpType.PRODUCT
				|| this == BinOpType.DIVIDE || this == BinOpType.MOD) {
			return true;
		}
		return false;
	}

	public boolean isRelational() {
		if (this == BinOpType.LESS || this == BinOpType.LESS_EQ || this == BinOpType.HIGH
				|| this == BinOpType.HIGH_EQ) {
			return true;
		}
		return false;
	}

	public boolean isEquational() {
		if (this == BinOpType.EQUAL || this == BinOpType.DISTINCT) {
			return true;
		}
		return false;
	}

	public boolean isConditional() {
		if (this == BinOpType.AND || this == BinOpType.OR) {
			return true;
		}
		return false;
	}
}
