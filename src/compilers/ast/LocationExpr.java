package compilers.ast;

import compilers.ASTVisitor;

public class LocationExpr extends Expression{

	private IdName id;
	private Expression expr;
	
	public LocationExpr(IdName i){
		this.id = i;
		this.expr = null;
	}
	
	public LocationExpr(IdName i, Expression e, int line, int col){
		this.setColumnNumber(col);
		this.setLineNumber(line);
		this.id = i;
		this.expr = e;
	}
	
	public LocationExpr(IdName i, int line, int col){
		this.setColumnNumber(col);
		this.setLineNumber(line);
		this.id = i;
		this.expr = null;
	}

	public IdName getId() {
		return id;
	}

	public Expression getExpr() {
		return expr;
	}
	
	@Override
	public GenericType getType() {
		return super.getType();
	}
	
	public void setExpr(Expression expr) {
		this.expr = expr;
	}
	
	@Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }
	
	
}
