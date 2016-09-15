package ast;

import java.util.LinkedList;
import java.util.List;

public abstract class Expression extends AST{

	private static List<Expression> listExpression = new LinkedList<>();
	
	public void addExpressionToList(Expression expr){
		listExpression.add(expr);
	}
	
	public List<Expression> getExpressionList(){
		return listExpression;
	}
	
}
