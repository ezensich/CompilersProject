package ast;

public enum Type {
	INT,
	FLOAT,
	VOID,
	BOOL;
	
	@Override
	public String toString() {
		switch(this) {
			case INT:
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
