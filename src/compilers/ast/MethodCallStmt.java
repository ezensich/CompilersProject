package compilers.ast;

import compilers.ASTVisitor;

public class MethodCallStmt extends Statement{

	private ExpressionList listExpr;
    private IdName methodId;

	
	public MethodCallStmt(Expression expr){
		this.listExpr = ((MethodCallExpr) expr).getExpressionList();
		this.methodId = ((MethodCallExpr) expr).getIdName();
	}
	
	public IdName getIdName(){
		return this.methodId;
	}
	
	public ExpressionList getExpressionList(){
		return this.listExpr;
	}
	
	@Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }

}
