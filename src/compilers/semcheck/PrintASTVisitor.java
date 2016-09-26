package compilers.semcheck;

import compilers.ast.*;
import compilers.ASTVisitor;

public class PrintASTVisitor implements ASTVisitor<String>{

	@Override
	public String visit(Program prog) {
		return prog.toString();
	}

	@Override
	public String visit(DeclarationClass decClass) {
		return decClass.toString();
	}

	@Override
	public String visit(StatementList stmtList) {
		return stmtList.toString();
	}

	@Override
	public String visit(AssignStmt stmt) {
		return stmt.toString();
	}

	@Override
	public String visit(ReturnStmt stmt) {
		return stmt.toString();
	}

	@Override
	public String visit(IfStmt stmt) {
		return stmt.toString();
	}

	@Override
	public String visit(WhileStmt stmt) {
		return stmt.toString();
	}

	@Override
	public String visit(ForStmt stmt) {
		return stmt.toString();
	}

	@Override
	public String visit(BreakStmt stmt) {
		return stmt.toString();
	}

	@Override
	public String visit(ContinueStmt stmt) {
		return stmt.toString();
	}

	@Override
	public String visit(SemicolonStmt stmt) {
		return stmt.toString();
	}

	@Override
	public String visit(MethodCallStmt methStmt) {
		return methStmt.toString();
	}

	@Override
	public String visit(ArgumentDeclaration argDec) {
		return argDec.toString();
	}

	@Override
	public String visit(Block block) {
		return block.toString();
	}

	@Override
	public String visit(ExpressionList exprList) {
		return exprList.toString();
	}

	@Override
	public String visit(LocationExpr locExpr) {
		return locExpr.toString();
	}

	@Override
	public String visit(BinOpExpr expr) {
		return expr.toString();
	}

	@Override
	public String visit(UnaryOpExpr unaryExpr) {
		return unaryExpr.toString();
	}

	@Override
	public String visit(MethodCallExpr methExpr) {
		return methExpr.toString();
	}

	@Override
	public String visit(IntegerLiteral lit) {
		return lit.toString();
	}

	@Override
	public String visit(BoolLiteral lit) {
		return lit.toString();
	}

	@Override
	public String visit(FloatLiteral lit) {
		return lit.toString();
	}

	@Override
	public String visit(Identifier id) {
		return id.toString();
	}

	@Override
	public String visit(FieldDeclaration fd) {
		return fd.toString();
	}

	@Override
	public String visit(FieldDeclarationList fdl) {
		return fdl.toString();
	}

	@Override
	public String visit(MethodDeclaration methD) {
		return methD.toString();
	}

	@Override
	public String visit(MethodDeclarationList methDecList) {
		return methDecList.toString();
	}

	@Override
	public String visit(Body body) {
		return body.toString();
	}

	@Override
	public String visit(Parameter parameter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visit(IdentifiersList identifiersList) {
		// TODO Auto-generated method stub
		return null;
	}


}
