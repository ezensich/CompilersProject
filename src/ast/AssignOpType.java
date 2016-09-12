package ast;

public enum AssignOpType {
	INC,
	DEC,
	ASSIGN;
	
	
	public String toString() {
		switch(this) {
			case INC:
				return "+=";
			case DEC:
				return "-=";
			case ASSIGN:
				return "=";
		}
		
		return null;		
		}
}
