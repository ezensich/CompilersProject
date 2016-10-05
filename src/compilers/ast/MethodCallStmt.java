package compilers.ast;

import compilers.ASTVisitor;

public class MethodCallStmt extends Statement{

	private MethodCallExpr meth;

	public MethodCallStmt(Expression expr){
		this.meth = (MethodCallExpr) expr;
	}
	
	public IdName getIdName(){
		return this.meth.getIdName();
	}
	
	public ExpressionList getExpressionList(){
		return this.meth.getExpressionList();
	}

	public MethodCallExpr getMethodCallExpr(){
		return this.meth;
	}
	
	@Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }

}
