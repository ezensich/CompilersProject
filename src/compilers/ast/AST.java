package compilers.ast;


import compilers.ASTVisitor;

public abstract class AST {
	private int lineNumber;
	private int colNumber;
	private Object reference;
	
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
	
	public Object getReference() {
		return reference;
	}

	public void setReference(Object reference) {
		this.reference = reference;
	}

	public abstract <T> T accept(ASTVisitor<T> v);
}


