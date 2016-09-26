package compilers.ast;

import compilers.ast.enumerated_types.UnaryOpType;
import compilers.ASTVisitor;

public class UnaryOpExpr extends Expression{

	private UnaryOpType operator; //operator in the expr
    private Expression expr; //expression
    
    public UnaryOpExpr(Expression e, UnaryOpType op, int line, int col){
		this.setColumnNumber(col);
		this.setLineNumber(line);
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
    public String toString(){
    	return operator.toString()+expr.toString();
    }
	
    @Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }

}
