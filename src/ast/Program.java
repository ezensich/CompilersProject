package ast;

import compilers.ProgramVisitor;

public abstract class Program {
	protected int lineNumber;
	protected int colNumber;
	
	public Program(){
		
	}
	
	public int getLineNumber() {
		return lineNumber;
	}
	
	public void setLineNumber(int ln) {
		lineNumber = ln;
	}
	
	public int getColumnNumber() {
		return colNumber;
	}
	
	public void setColumnNumber(int cn) {
		colNumber = cn;
	}
	
	public abstract <T> T accept(ProgramVisitor<T> v);
}


