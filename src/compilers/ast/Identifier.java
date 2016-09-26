package compilers.ast;


import compilers.ASTVisitor;

public class Identifier extends AST {

	private IdName id;
	private IntegerLiteral size;

	public Identifier(IdName id, IntegerLiteral size, int line, int col){
		this.setColumnNumber(col);
		this.setLineNumber(line);
		this.id = id;
		this.size = size;
	}

	public Identifier(IdName id, int line, int col){
		this.id = id;
		this.size = null;
		this.setColumnNumber(col);
		this.setLineNumber(line);
	}
	
	public IdName getId() {
		return id;
	}

	public IntegerLiteral getSize() {
		return size;
	}
	

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}
