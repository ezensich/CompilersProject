package  compilers.ast.enumerated_types;

public enum Type {
	INTEGER,
	FLOAT,
	VOID,
	BOOL;
	
	@Override
	public String toString() {
		switch(this) {
			case INTEGER:
				return "integer";
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
