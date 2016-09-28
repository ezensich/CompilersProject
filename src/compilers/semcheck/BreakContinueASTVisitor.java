package compilers.semcheck;


import java.util.LinkedList;
import java.util.List;

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
import compilers.ast.Statement;
import compilers.ast.StatementList;
import compilers.ast.UnaryOpExpr;
import compilers.ast.WhileStmt;

/*
 *   Esta clase es la encargada de hacer el chequeo semantico diferentes reglas provistas por
 * la descripcion del lenguaje. Las reglas que valida son las siguientes:
 *   Regla 18: Las sentencias 'break' y 'continue' solo pueden encontrarse en el cuerpo de un
 * ciclo.
 */
public class BreakContinueASTVisitor implements ASTVisitor<String> {

	private List<String> errorList;
	
	public BreakContinueASTVisitor() {
		errorList = new LinkedList<>();
	}
	
	public List<String> getErrorList(){
		return this.errorList;
	}
	
	@Override
	public String visit(Program prog) {
		String result = "";
		for (DeclarationClass c : prog.getListDeclarationClass()) {
			result += c.accept(this);
		}
		return result;
	}

	@Override
	public String visit(DeclarationClass decClass) {
		return (decClass.getMethodDecList()).accept(this);
	}

	@Override
	public String visit(StatementList stmtList) {
		String result = "";
		if (stmtList.getStatementList() != null) {
			for (Statement s : stmtList.getStatementList()) {
				String g = s.accept(this);
				if (g.equals("break") || g.equals("continue")) {
					result += " "+g;
				}
			}
		}
		return result;
	}

	@Override
	public String visit(AssignStmt stmt) {
		return "";
	}

	@Override
	public String visit(ReturnStmt stmt) {
		return "";
	}

	@Override
	public String visit(IfStmt stmt) {
		String g = stmt.getIfStatement().accept(this);
		if (stmt.getElseStatement() != null) {
			String g2 = stmt.getElseStatement().accept(this);
			if (!g2.equals("")) {
				g += " "+g2;
			}
		}
		return g;
	}

	@Override
	public String visit(WhileStmt stmt) {
		return stmt.getStatementCondition().accept(this);
	}

	@Override
	public String visit(ForStmt stmt) {
		return stmt.getForStatement().accept(this);
	}

	@Override
	public String visit(BreakStmt stmt) {
		return "break";
	}

	@Override
	public String visit(ContinueStmt stmt) {
		return "continue";
	}

	@Override
	public String visit(SemicolonStmt stmt) {
		return "";
	}

	@Override
	public String visit(MethodCallStmt methStmt) {
		return "";
	}

	@Override
	public String visit(ArgumentDeclaration argDec) {
		return "";
	}

	@Override
	public String visit(Body body) {
		String result = "";
		if (body.getBlock() != null) {
			result = body.getBlock().accept(this);
		}
		return result;
	}

	@Override
	public String visit(Block block) {
		return block.getStmtList().accept(this);
	}

	@Override
	public String visit(ExpressionList exprList) {
		return "";
	}

	@Override
	public String visit(LocationExpr locExpr) {
		return "";
	}

	@Override
	public String visit(BinOpExpr expr) {
		return "";
	}

	@Override
	public String visit(UnaryOpExpr unaryExpr) {
		return "";
	}

	@Override
	public String visit(MethodCallExpr methExpr) {
		return "";
	}

	@Override
	public String visit(IntegerLiteral lit) {
		return "";
	}

	@Override
	public String visit(BoolLiteral lit) {
		return "";
	}

	@Override
	public String visit(FloatLiteral lit) {
		return "";
	}

	@Override
	public String visit(Identifier id) {
		return "";
	}

	@Override
	public String visit(IdentifiersList identifiersList) {
		return "";
	}

	@Override
	public String visit(FieldDeclaration fd) {
		return "";
	}

	@Override
	public String visit(FieldDeclarationList fdl) {
		return "";
	}

	@Override
	public String visit(MethodDeclaration methD) {
		return methD.getBody().accept(this);
		
	}

	@Override
	public String visit(MethodDeclarationList methDecList) {
		String result = "";
		if (methDecList.getMethodDecList() != null) {
			for (MethodDeclaration m : methDecList.getMethodDecList()) {
				String g = m.accept(this);
				if (g.contains("break")) {
					errorList.add("Error en el metodo " + m.getId() + ": La sentencia break"
							+ " debe estar dentro de un ciclo.");
				}
				if(g.contains("continue")){
					errorList.add("Error en el metodo " + m.getId() + ": La sentencia continue"
							+ " debe estar dentro de un ciclo.");
				}
				result += g + " ";
			}
		}
		return result;
	}

	@Override
	public String visit(Parameter parameter) {
		return "";
	}

}
