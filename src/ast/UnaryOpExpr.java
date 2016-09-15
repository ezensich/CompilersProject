package ast;

import ast.enumerated_types.UnaryOpType;
import compilers.ASTVisitor;

public class UnaryOpExpr extends Expression{

	private UnaryOpType operator; //operator in the expr
    private Expression expr; //expression
    
    public UnaryOpExpr(Expression e, UnaryOpType op){
        operator = op;
        expr = e;
    }

    public UnaryOpType getOperator() {
        return operator;
    }

    public Expression getExpression() {
        return expr;
    }
	
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		// TODO Auto-generated method stub
		return null;
	}

}
