package compilers;

import ast.*;

public interface ProgramVisitor<T> {

	//visit statements
		T visit(AssignStm stmt);
	/*		T visit(ReturnStmt stmt);
		T visit(IfStmt stmt);
		
	// visit expressions
		T visit(BinOpExpr expr);;
		
	// visit literals	
		T visit(IntLiteral lit);

	// visit locations	
		T visit(VarLocation loc);
		
	*/	
}
