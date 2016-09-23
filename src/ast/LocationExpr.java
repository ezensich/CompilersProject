package ast;

import compilers.ASTVisitor;

public class LocationExpr extends Expression{

	private IdName id;
	private Expression expr;
	
	public LocationExpr(IdName i, Expression e){
		this.id = i;
		this.expr = e;
	}
	
	public LocationExpr(IdName i){
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
	public String toString(){
		String result = id.toString();
		if(expr != null){
			result += "["+expr.toString()+"]";
		}
		return  result;
	}
	
	@Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }
	
	
	
}
