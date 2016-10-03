package compilers.semcheck;

import compilers.ast.*;
import compilers.ASTVisitor;

public class PrintASTVisitor implements ASTVisitor<String> {

	@Override
	public String visit(Program prog) {
		String result = "";
		for (DeclarationClass dc : prog.getListDeclarationClass()) {
			result += dc.accept(this) + '\n';
		}
		return result;
	}

	@Override
	public String visit(DeclarationClass decClass) {
		String result = "class " + decClass.getIdName().getId() + " { \n";
		if (decClass.getFieldDecList() != null) {
			result += decClass.getFieldDecList().accept(this);
		}
		if (decClass.getMethodDecList() != null) {
			result += decClass.getMethodDecList().accept(this);
		}
		return result += " \n }";
	}

	@Override
	public String visit(StatementList stmtList) {
		String result = "";
		for (Statement stmt : stmtList.getStatementList()) {
			result += stmt.accept(this) + "\n";
		}
		return result;
	}

	@Override
	public String visit(AssignStmt stmt) {
		return stmt.getLocation().accept(this) + " " + stmt.getAssignOpType().toString() + " "
				+ stmt.getExpression().accept(this) + ";";
	}

	@Override
	public String visit(ReturnStmt stmt) {
		String result = "return";
		if (stmt.getExpression() != null) {
			result += " " + stmt.getExpression().accept(this) + ";";
		} else {
			result += ";";
		}
		return result;
	}

	@Override
	public String visit(IfStmt stmt) {
		String result = "if (" + stmt.getCondition().accept(this) + ") \n" + stmt.getIfStatement().accept(this) + "\n ";
		if (stmt.getElseStatement() != null) {
			result += "else \n" + stmt.getElseStatement().accept(this) + "\n";
		}
		return result;
	}

	@Override
	public String visit(WhileStmt stmt) {
		return "while " + stmt.getCondition().accept(this) + '\n' + stmt.getStatementCondition().accept(this);
	}

	@Override
	public String visit(ForStmt stmt) {
		return "for " + stmt.getId().getId() + " = " + stmt.getInit().accept(this) + "," + stmt.getCota().accept(this)
				+ '\n' + stmt.getForStatement().accept(this);
	}

	@Override
	public String visit(BreakStmt stmt) {
		return "break;";
	}

	@Override
	public String visit(ContinueStmt stmt) {
		return "continue;";
	}

	@Override
	public String visit(SemicolonStmt stmt) {
		return ";";
	}

	@Override
	public String visit(MethodCallStmt methStmt) {
		String result = methStmt.getIdName().getId();
		if (methStmt.getExpressionList() != null) {
			result += "(" + methStmt.getExpressionList().accept(this) + ")";
		}
		return result;
	}

	@Override
	public String visit(ArgumentDeclaration argDec) {
		String result = "";
		if (argDec.getArgumentsList() != null && !argDec.getArgumentsList().isEmpty()) {
			result += argDec.getArgumentsList().get(0).accept(this);
			for (int i = 1; i < argDec.getArgumentsList().size(); i++) {
				result += ", " + argDec.getArgumentsList().get(i).accept(this);
			}
		}
		return result;
	}

	@Override
	public String visit(Block block) {
		String listStmt = "";
		String listField = "";
		if (block.getStmtList() != null) {
			listStmt = block.getStmtList().accept(this);
		}
		if (block.getFieldDecList() != null) {
			listField = block.getFieldDecList().accept(this);
		}
		return "{  " + listField + listStmt + " }";
	}

	@Override
	public String visit(ExpressionList exprList) {
		String result = "";
		if (!exprList.getExpressionList().isEmpty()) {
			result += exprList.getExpressionList().get(0).accept(this);

			for (int i = 1; i < exprList.getExpressionList().size(); i++) {
				result += ("," + exprList.getExpressionList().get(i).accept(this));
			}
		}
		return result;
	}

	@Override
	public String visit(LocationExpr locExpr) {
		String result = locExpr.getId().getId();
		if (locExpr.getExpr() != null) {
			result += "[" + locExpr.getExpr().accept(this) + "]";
		}
		return result;
	}

	@Override
	public String visit(BinOpExpr expr) {
		return expr.getLeftExpr().accept(this) + " " + expr.getOperator().toString() + " "
				+ expr.getRightExpr().accept(this);
	}

	@Override
	public String visit(UnaryOpExpr unaryExpr) {
		return unaryExpr.getOperator().toString() + unaryExpr.getExpression().accept(this);
	}

	@Override
	public String visit(MethodCallExpr methExpr) {
		String result = methExpr.getIdName().getId();
		if (methExpr.getExpressionList() != null) {
			result += "(" + methExpr.getExpressionList().accept(this) + ")";
		}
		return result;
	}

	@Override
	public String visit(IntegerLiteral lit) {
		return lit.getStringValue();
	}

	@Override
	public String visit(BoolLiteral lit) {
		return lit.getStringValue();
	}

	@Override
	public String visit(FloatLiteral lit) {
		return lit.getStringValue();
	}

	@Override
	public String visit(Identifier id) {
		if (id.getSize() != null) {
			return id.getId().getId() + "[" + id.getSize().getValue() + "]";
		} else {
			return id.getId().getId();
		}
	}

	@Override
	public String visit(IdentifiersList identifiersList) {
		String result = "";
		if (identifiersList.getListIdentifier() != null) {
			for (int i = 0; i < identifiersList.getListIdentifier().size(); i++) {
				if ((i + 1) == identifiersList.getListIdentifier().size()) {
					result += identifiersList.getListIdentifier().get(i).accept(this);
				} else {
					result += identifiersList.getListIdentifier().get(i).accept(this) + ", ";
				}
			}
		}
		return result;
	}

	@Override
	public String visit(FieldDeclaration fd) {
		return fd.getType().getType() + " " + fd.getIdentifiersList().accept(this) + ";";
	}

	@Override
	public String visit(FieldDeclarationList fdl) {
		String result = "";
		if (fdl.getFieldDecList() != null) {
			for (FieldDeclaration fd : fdl.getFieldDecList()) {
				result += fd.accept(this) + '\n' + '\n';
			}
		}
		return result;
	}

	@Override
	public String visit(MethodDeclaration methD) {
		String result = methD.getType().getType() + " " + methD.getId().getId();
		if (methD.getArguments() != null) {
			result += "(" + methD.getArguments().accept(this) + ") \n " + methD.getBody().accept(this);
		} else {
			result += "() \n " + methD.getBody().accept(this);
		}
		return result;
	}

	@Override
	public String visit(MethodDeclarationList methDecList) {
		String result = "";
		if (methDecList.getMethodDecList() != null) {
			for (MethodDeclaration md : methDecList.getMethodDecList()) {
				result += md.accept(this) + '\n' + '\n';
			}
		}
		return result;
	}

	@Override
	public String visit(Body body) {
		if (body.isExtern()) {
			return "extern;";
		} else {
			return body.getBlock().accept(this);
		}
	}

	@Override
	public String visit(Parameter parameter) {
		return parameter.getType().getType() + " " + parameter.getId().getId();
	}

}
