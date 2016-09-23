package compilers.semcheck;

import ast.ArgumentDeclaration;
import ast.AssignStmt;
import ast.BinOpExpr;
import ast.Block;
import ast.Body;
import ast.BoolLiteral;
import ast.BreakStmt;
import ast.ContinueStmt;
import ast.DeclarationClass;
import ast.ExpressionList;
import ast.FieldDeclaration;
import ast.FieldDeclarationList;
import ast.FloatLiteral;
import ast.ForStmt;
import ast.Identifier;
import ast.IfStmt;
import ast.IntegerLiteral;
import ast.LocationExpr;
import ast.MethodCallExpr;
import ast.MethodCallStmt;
import ast.MethodDeclaration;
import ast.MethodDeclarationList;
import ast.Program;
import ast.ReturnStmt;
import ast.SemicolonStmt;
import ast.StatementList;
import ast.UnaryOpExpr;
import ast.WhileStmt;
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


}
