package ast;

import java.util.LinkedList;
import java.util.List;

import compilers.ProgramVisitor;

public abstract class Program {
	private int lineNumber;
	private int colNumber;
	private List<DeclarationClass> listDecClass; 
	
	public Program(){
		this.listDecClass = new LinkedList<DeclarationClass>();
	}
	
	public void addDecClass(DeclarationClass dc){
		this.listDecClass.add(dc);
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


