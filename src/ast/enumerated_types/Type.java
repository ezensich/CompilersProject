package ast.enumerated_types;

public enum Type {
	INTEGER,
	FLOAT,
	VOID,
	BOOL;
	
	public String toString() {
		switch(this) {
			case INTEGER:
				return "int";
			case VOID:
				return "void";
			case BOOL:
				return "bool";
			case FLOAT:
				return "float";
		}
		
		return null;
	}
}
