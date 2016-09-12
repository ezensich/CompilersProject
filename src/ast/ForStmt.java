package ast;

import compilers.ProgramVisitor;

public class ForStmt extends Statement {
	
	private Expression init;
	private Expression cota;
	private Block ForBlock;

	
	public <T> T accept(ProgramVisitor<T> v) {
		return v.visit(this);
	}

}
