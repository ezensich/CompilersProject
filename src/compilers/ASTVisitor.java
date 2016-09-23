package compilers;

import ast.*;

public interface ASTVisitor<T> {

	// program
	T visit(Program prog);
	T visit(DeclarationClass decClass);

	// visit statements
	T visit(StatementList stmtList);
	
	T visit(AssignStmt stmt);

	T visit(ReturnStmt stmt);

	T visit(IfStmt stmt);

	T visit(WhileStmt stmt);

	T visit(ForStmt stmt);

	T visit(BreakStmt stmt);

	T visit(ContinueStmt stmt);

	T visit(SemicolonStmt stmt);

	T visit(MethodCallStmt methStmt);
	// T visit(ExternInvkStmt extInvStmt);
	// T visit(ExternInvkStmt extStmt);

	// visit arguments declaration
	T visit(ArgumentDeclaration argDec);
	
	// visit body - block
	T visit(Body body);
	T visit(Block block);

	// visit expressions
	T visit(ExpressionList exprList);
	
	T visit(LocationExpr locExpr);
	
	T visit(BinOpExpr expr);

	T visit(UnaryOpExpr unaryExpr);

	T visit(MethodCallExpr methExpr);

	// visit literals
	T visit(IntegerLiteral lit);

	T visit(BoolLiteral lit);

	T visit(FloatLiteral lit);

	// visit identifier
	T visit(Identifier id);
	
	// visit locations
	// T visit(VarLocation loc);
	// T visit(ArrayLocation arrLoc);

	// visit fields declaration
	T visit(FieldDeclaration fd);
	T visit(FieldDeclarationList fdl);
	
	// visit methodDecl
	T visit(MethodDeclaration methD);
	T visit(MethodDeclarationList methDecList);
}
