package compilers.ast;

import compilers.ast.enumerated_types.BinOpType;
import compilers.ast.enumerated_types.GenericType;
import compilers.ASTVisitor;

public class BinOpExpr extends Expression {

	private BinOpType operator; // operator in the expr
	private Expression lExpr; // left expression
	private Expression rExpr; // right expression

	public BinOpExpr(Expression l, BinOpType op, Expression r, int line, int col){
		this.setColumnNumber(col);
		this.setLineNumber(line);
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
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}
