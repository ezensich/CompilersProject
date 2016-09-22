package ast;

import java.util.LinkedList;
import java.util.List;

import compilers.ASTVisitor;

public class ExpressionList extends Expression {

	private static List<Expression> listExpression = new LinkedList<>();
	
	public ExpressionList(Expression e){
		this.addExpressionToList(e);
	}
	
	public void addExpressionToList(Expression expr){
		listExpression.add(expr);
	}
	
	public List<Expression> getExpressionList(){
		return listExpression;
	}
	
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		// TODO Auto-generated method stub
		return null;
	}

}
