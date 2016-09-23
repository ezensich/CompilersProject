package ast;

import compilers.ASTVisitor;

public class ReturnStmt extends Statement {
	private Expression expression;
	
	public ReturnStmt(Expression e) {
		this.expression = e;
	}
	
	public ReturnStmt() {
		this.expression = null;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
	@Override
    public String toString(){
    	String result = "return";
    	if(expression!= null){
    		result += " "+expression.toString();
    	}
    	return result;
    }
	
	@Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }
}
