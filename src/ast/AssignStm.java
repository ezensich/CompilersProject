package ast;

import compilers.ProgramVisitor;

public class AssignStm extends Statement{
	
	private Expression expr;
	private Location location;
	private AssignOpType operator;

	
	public <T> T accept(ProgramVisitor<T> v) {
		return v.visit(this);
	}

}
