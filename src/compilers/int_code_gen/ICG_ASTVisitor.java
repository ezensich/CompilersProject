package compilers.int_code_gen;

import compilers.ASTVisitor;
import compilers.ast.ArgumentDeclaration;
import compilers.ast.AssignStmt;
import compilers.ast.BinOpExpr;
import compilers.ast.Block;
import compilers.ast.Body;
import compilers.ast.BoolLiteral;
import compilers.ast.BreakStmt;
import compilers.ast.ContinueStmt;
import compilers.ast.DeclarationClass;
import compilers.ast.Expression;
import compilers.ast.ExpressionList;
import compilers.ast.FieldDeclaration;
import compilers.ast.FieldDeclarationList;
import compilers.ast.FloatLiteral;
import compilers.ast.ForStmt;
import compilers.ast.Identifier;
import compilers.ast.IdentifiersList;
import compilers.ast.IfStmt;
import compilers.ast.IntegerLiteral;
import compilers.ast.LocationExpr;
import compilers.ast.MethodCallExpr;
import compilers.ast.MethodCallStmt;
import compilers.ast.MethodDeclaration;
import compilers.ast.MethodDeclarationList;
import compilers.ast.Parameter;
import compilers.ast.Program;
import compilers.ast.ReturnStmt;
import compilers.ast.SemicolonStmt;
import compilers.ast.StatementList;
import compilers.ast.UnaryOpExpr;
import compilers.ast.WhileStmt;

public class ICG_ASTVisitor implements ASTVisitor<Expression>{

	@Override
	public Expression visit(Program prog) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(DeclarationClass decClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(StatementList stmtList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(AssignStmt stmt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(ReturnStmt stmt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(IfStmt stmt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(WhileStmt stmt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(ForStmt stmt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(BreakStmt stmt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(ContinueStmt stmt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(SemicolonStmt stmt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(MethodCallStmt methStmt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(ArgumentDeclaration argDec) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(Body body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(Block block) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(ExpressionList exprList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(LocationExpr locExpr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(BinOpExpr expr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(UnaryOpExpr unaryExpr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(MethodCallExpr methExpr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(IntegerLiteral lit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(BoolLiteral lit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(FloatLiteral lit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(Identifier id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(IdentifiersList identifiersList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(FieldDeclaration fd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(FieldDeclarationList fdl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(MethodDeclaration methD) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(MethodDeclarationList methDecList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(Parameter parameter) {
		// TODO Auto-generated method stub
		return null;
	}

}
