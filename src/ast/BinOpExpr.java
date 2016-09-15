package ast;

import ast.enumerated_types.BinOpType;
import compilers.ASTVisitor;

public class BinOpExpr extends Expression {

	private BinOpType operator; //operator in the expr
	private Expression lExpr; //left expression
	private Expression rExpr; //right expression
        
	
	public BinOpExpr(Expression l, BinOpType op, Expression r){
		this.operator = op;
		this.lExpr = l;
		this.rExpr = r;
	}
	
	public BinOpType getOperator() {
		return operator;
	}

	public Expression getLeftExpr() {
		return lExpr;
	}

	public Expression getRightExpr() {
		return rExpr;
	}
        
	@Override
	public String toString() {
		return lExpr.toString() + " " + operator.toString() + " " + rExpr.toString();
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return null;
	}

}
