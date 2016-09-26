package compilers.ast;

import java.util.LinkedList;
import java.util.List;

import compilers.ASTVisitor;

public class ExpressionList extends Expression {

	private List<Expression> listExpression = new LinkedList<>();
	
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
	public String toString(){
		String result = "";
		if (!listExpression.isEmpty()){
			result += listExpression.get(0);

			for (int i = 1; i<listExpression.size();i++){
				result +=( "," + listExpression.get(i));
			}
		}
		return result;
	}
	
	
	@Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }

}
