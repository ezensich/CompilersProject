package ast;

import compilers.ASTVisitor;

public class ExpressionList extends Expression {

	public ExpressionList(Expression e) {
		addExpressionToList(e);
	}
	
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		// TODO Auto-generated method stub
		return null;
	}

}
