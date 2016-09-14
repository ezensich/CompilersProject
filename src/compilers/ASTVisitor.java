package compilers;

import ast.*;

public interface ASTVisitor<T> {

	//visit statements
		T visit(AssignStmt stmt);
		T visit(IfStmt stmt);
		T visit(WhileStmt stmt);
		T visit(ForStmt stmt);
		T visit(ReturnStmt stmt);
		
		// visit literals
		T visit(BoolLiteral lit);
		
		
	/* visit expressions
		T visit(BinOpExpr expr);

	// visit locations	
		T visit(VarLocation loc);
		
	*/	
}
