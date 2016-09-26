package compilers.ast;


import compilers.ASTVisitor;

public class MethodCallExpr extends Expression{

	private ExpressionList listExpr;
    private IdName methodId;

   
    public MethodCallExpr(IdName id, Expression listExpr, int line, int col){
		this.setColumnNumber(col);
		this.setLineNumber(line);
        this.methodId = id;
        this.listExpr=(ExpressionList) listExpr;
    }
    
    public MethodCallExpr(IdName id, int line, int col){
		this.setColumnNumber(col);
		this.setLineNumber(line);
        methodId = id;
        listExpr=null;
    }
    
    public ExpressionList getExpressionList(){
    	return this.listExpr;
    }
    

    public IdName getIdName(){
        return methodId;
    }

    @Override
    public String toString(){
    	String result = methodId.toString();
    	if (listExpr!= null ){
    		result += "(" + listExpr.toString() + ")";
    	}
    	return  result;
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }

}
