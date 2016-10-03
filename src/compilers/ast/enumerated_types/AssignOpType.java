package compilers.ast.enumerated_types;

public enum AssignOpType {
	INC, DEC, ASSIGN;

	@Override
	public String toString() {
		switch (this) {
		case INC:
			return "+=";
		case DEC:
			return "-=";
		case ASSIGN:
			return "=";
		}

		return null;
	}

	public boolean isAssign() {
		if (this == AssignOpType.ASSIGN) {
			return true;
		}

		return false;
	}

	public boolean isIncrement() {
		if (this == AssignOpType.INC) {
			return true;
		}

		return false;
	}

	public boolean isDecrement() {
		if (this == AssignOpType.DEC) {
			return true;
		}

		return false;
	}
}
