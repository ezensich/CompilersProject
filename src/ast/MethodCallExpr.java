package ast;


import compilers.ASTVisitor;

public class MethodCallExpr extends Expression{

	private ExpressionList listExpr;
    private IdName methodId;

   
    public MethodCallExpr(IdName id, Expression listExpr){
        this.methodId = id;
        this.listExpr=(ExpressionList) listExpr;
    }
    
    public MethodCallExpr(IdName id){
        methodId = id;
        listExpr=null;
    }
    

    public IdName getIdName(){
        return methodId;
    }

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		// TODO Auto-generated method stub
		return null;
	}

}
