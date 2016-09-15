package ast;

import java.util.List;

import compilers.ASTVisitor;

public class MethodCallExpr extends Expression{

	private ExpressionList listExpr;
    private IdName methodId;

   
    public MethodCallExpr(IdName id, ExpressionList list){
        methodId = id;
        listExpr=list;
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
