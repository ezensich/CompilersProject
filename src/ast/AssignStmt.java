package ast;

import ast.enumerated_types.AssignOpType;
import compilers.ASTVisitor;

public class AssignStmt extends Statement{
	
	private Expression expr;
	private LocationExpr location;
	private AssignOpType operator;

	// declared gets and sets
	//sets
	public void setExpression(Expression e){
		this.expr = e;
	}
	
	public void setLocation (LocationExpr l){
		this.location = l;
	}
	
	public void setAssignOpType (AssignOpType o){
		this.operator = o;
	}
	
	//gets 
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
		return location + " " + operator + " " + expr;
	}
	
	
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}
