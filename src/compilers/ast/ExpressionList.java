package compilers.ast;

import java.util.LinkedList;
import java.util.List;

import compilers.ASTVisitor;

public class ExpressionList extends Expression {

	private List<Expression> listExpression = new LinkedList<>();
	
	public ExpressionList(Expression e){
		this.addExpressionToList(e);
	}
	
	public ExpressionList(List<Expression> exprList){
		this.listExpression = exprList;
	}
	
	public void addExpressionToList(Expression expr){
		listExpression.add(expr);
	}
	
	public List<Expression> getExpressionList(){
		return listExpression;
	}

	public void setExpressionList(List<Expression> list) {
		this.listExpression = list;
	}
	
	@Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }

}
