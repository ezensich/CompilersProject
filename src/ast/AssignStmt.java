package ast;

import ast.enumerated_types.AssignOpType;
import compilers.ASTVisitor;

public class AssignStmt extends Statement{
	
	private Expression expr;
	private LocationExpr location;
	private AssignOpType operator;

	public AssignStmt(Expression e, LocationExpr l, AssignOpType op){
		this.expr = e;
		this.location = l;
		this.operator = op;
	}
	public LocationExpr getLocation(){
		return this.location;
	}
	
	public Expression getExpression(){
		return this.expr;
	}
	
	public AssignOpType getAssignOpType(){
		return this.operator;
	}
	
	//method toString
	public String toString() {
		return location.toString()  + " " + operator.toString() + " " + expr.toString() + ";";
	}
	
	@Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }

}
