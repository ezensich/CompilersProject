package compilers;

import ast.*;

public interface ProgramVisitor<T> {

	//visit statements
		T visit(AssignStmt stmt);
		T visit(IfStmt stmt);
	
		
		
	//  T visit(ReturnStmt stmt);
		
		
	/* visit expressions
		T visit(BinOpExpr expr);;
		
	// visit literals	
		T visit(IntLiteral lit);

	// visit locations	
		T visit(VarLocation loc);
		
	*/	
}
