package compilers.int_code_gen;

import compilers.data_structures.Pair;
import compilers.semcheck.PrintASTVisitor;
import compilers.symbol_table.AttributeSymbolTable;
import compilers.symbol_table.MethodSymbolTable;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import compilers.ASTVisitor;
import compilers.ast.*;
import compilers.ast.enumerated_types.BinOpType;

public class ICG_ASTVisitor implements ASTVisitor<Expression> {
	// lista con los codigos de instruccion(codigo intermedio)
	private List<ICGInstruction> instructionCodeList;
	// identificador para instrucciones
	private int instrId;
	// identificador para labels
	private int labelId;
	// pila de labels de ciclos
	private Stack<Pair<Integer, Integer>> iterLabels;

	// constructor
	public ICG_ASTVisitor() {
		instructionCodeList = new LinkedList<ICGInstruction>();
		iterLabels = new Stack<Pair<Integer, Integer>>();
		instrId = 0;
		labelId = 0;
	}

	// getters and setters
	public List<ICGInstruction> getInstructionCodeList() {
		return this.instructionCodeList;
	}

	@Override
	public Expression visit(Program prog) {
		for (DeclarationClass dc : prog.getListDeclarationClass()) {
			dc.accept(this);
		}
		return null;
	}

	// metodos
	@Override
	public Expression visit(DeclarationClass decClass) {
		if (decClass.getFieldDecList() != null && decClass.getFieldDecList().getFieldDecList() != null) {
			for (FieldDeclaration fd : decClass.getFieldDecList().getFieldDecList()) {
				for (Identifier id : fd.getIdentifiersList().getListIdentifier()) {
					instructionCodeList.add(new ICGInstruction(ICGOperation.GDEF, fd.getType(), id, null));
				}
			}
		}
		if (decClass.getMethodDecList() != null && decClass.getMethodDecList().getMethodDecList() != null) {
			for (MethodDeclaration m : decClass.getMethodDecList().getMethodDecList()) {
				m.accept(this);
			}
		}
		return null;
	}

	@Override
	public Expression visit(StatementList stmtList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(AssignStmt stmt) {
		Expression loc = stmt.getLocation().accept(this);
		Expression expr = stmt.getExpression().accept(this);
		switch (stmt.getAssignOpType()) {
		case INC:
			instructionCodeList.add(new ICGInstruction(ICGOperation.ADD, loc, expr, loc));
			break;
		case DEC:
			instructionCodeList.add(new ICGInstruction(ICGOperation.SUB, loc, expr, loc));
			break;
		case ASSIGN:
			instructionCodeList.add(new ICGInstruction(ICGOperation.ASSIGN, loc, expr, null));
			break;
		}
		return null;
	}

	@Override
	public Expression visit(ReturnStmt stmt) {
		if (stmt.getExpression() != null) {
			instructionCodeList
					.add(new ICGInstruction(ICGOperation.RETURN, stmt.getExpression().accept(this), null, null));
		} else {
			instructionCodeList.add(new ICGInstruction(ICGOperation.RETURN, null, null, null));
		}
		return null;
	}

	@Override
	public Expression visit(IfStmt stmt) {
		Expression cond = stmt.getCondition().accept(this);
		// creo las etiquetas que necesito para el if
		int lblBeginElse = ++labelId;
		int lblEndElse = ++labelId;
		// veo si la cond del if es 'true' (CMP, 1, cond, -)
		instructionCodeList.add(new ICGInstruction(ICGOperation.CMP, new IntegerLiteral(1), cond, null));
		// salto si no es true salto al else (JNE, lbl1, - , - )
		instructionCodeList
				.add(new ICGInstruction(ICGOperation.JNE, new LabelExpr(lblBeginElse, "BeginElse."), null, null));
		if (stmt.getIfStatement() != null) {
			stmt.getIfStatement().accept(this);// ejecuto el bloque del if
			// salto al else (JMP, lbl2, - , - )
			instructionCodeList
					.add(new ICGInstruction(ICGOperation.JMP, new LabelExpr(lblEndElse, "EndElse."), null, null));
		}
		// inserto label (LABEL, lbl1, - , - )
		instructionCodeList
				.add(new ICGInstruction(ICGOperation.LABEL, new LabelExpr(lblBeginElse, "BeginElse."), null, null));
		if (stmt.getElseStatement() != null) {
			stmt.getElseStatement().accept(this);// ejecuto el bloque del else
		}
		// inserto label (LABEL, lblFinElse, - , - )
		instructionCodeList
				.add(new ICGInstruction(ICGOperation.LABEL, new LabelExpr(lblEndElse, "EndElse."), null, null));
		return null;
	}

	@Override
	public Expression visit(WhileStmt stmt) {
		int lbl = ++labelId;
		iterLabels.push(new Pair<>(lbl, ++labelId));
		instructionCodeList.add(new ICGInstruction(ICGOperation.LABEL,
				new LabelExpr(iterLabels.peek().getFirst(), "BeginLoop."), null, null));
		Expression cond = stmt.getCondition().accept(this);
		instructionCodeList.add(new ICGInstruction(ICGOperation.CMP, new IntegerLiteral(1), cond, null));
		instructionCodeList.add(new ICGInstruction(ICGOperation.JNE,
				new LabelExpr(iterLabels.peek().getSecond(), "EndLoop."), null, null));
		stmt.getStatementCondition().accept(this);
		instructionCodeList.add(new ICGInstruction(ICGOperation.JMP,
				new LabelExpr(iterLabels.peek().getFirst(), "BeginLoop."), null, null));
		instructionCodeList.add(new ICGInstruction(ICGOperation.LABEL,
				new LabelExpr(iterLabels.peek().getSecond(), "EndLoop."), null, null));
		iterLabels.pop();
		return null;
	}

	@Override
	public Expression visit(ForStmt stmt) {
		int lbl = ++labelId;
		iterLabels.push(new Pair<>(lbl, ++labelId));
		// asigno la expresion de inicio del for (ASSIGN, idFor, expr, - )
		instructionCodeList
				.add(new ICGInstruction(ICGOperation.ASSIGN, stmt.getId(), stmt.getInit().accept(this), null));
		// creo el label de inicio del for
		instructionCodeList.add(new ICGInstruction(ICGOperation.LABEL,
				new LabelExpr(iterLabels.peek().getFirst(), "BeginLoop."), null, null));
		Expression cota = stmt.getCota().accept(this);
		// comparo si la instruccion de cota se cumple
		instructionCodeList.add(new ICGInstruction(ICGOperation.CMP, stmt.getId(), cota, null));
		// salto si el indice es igual a la cota del for
		instructionCodeList.add(new ICGInstruction(ICGOperation.JE,
				new LabelExpr(iterLabels.peek().getSecond(), "EndLoop."), null, null));
		if (stmt.getForStatement() != null) {
			stmt.getForStatement().accept(this);
		}
		// incremento el indice del for
		instructionCodeList.add(new ICGInstruction(ICGOperation.INC, stmt.getId(), null, null));
		// salto al inicio del for
		instructionCodeList.add(new ICGInstruction(ICGOperation.JMP,
				new LabelExpr(iterLabels.peek().getFirst(), "BeginLoop."), null, null));
		// etiqueta de fin del for
		instructionCodeList.add(new ICGInstruction(ICGOperation.LABEL,
				new LabelExpr(iterLabels.peek().getSecond(), "EndLoop."), null, null));
		iterLabels.pop();
		return null;
	}

	@Override
	public Expression visit(BreakStmt stmt) {
		// si es un 'break' salto al fin del ciclo
		instructionCodeList.add(new ICGInstruction(ICGOperation.JMP,
				new LabelExpr(iterLabels.peek().getSecond(), "EndLoop."), null, null));
		return null;
	}

	@Override
	public Expression visit(ContinueStmt stmt) {
		// si es un 'continue' salto al inicio del ciclo
		instructionCodeList.add(new ICGInstruction(ICGOperation.JMP,
				new LabelExpr(iterLabels.peek().getFirst(), "BeginLoop."), null, null));
		return null;
	}

	@Override
	public Expression visit(SemicolonStmt stmt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(MethodCallStmt methStmt) {
		return methStmt.getMethodCallExpr().accept(this);
	}

	@Override
	public Expression visit(ArgumentDeclaration argDec) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(Body body) {
		if (body.getBlock() != null) {
			body.getBlock().accept(this);
		}
		return null;
	}

	@Override
	public Expression visit(Block block) {
		if (block.getFieldDecList() != null && block.getFieldDecList().getFieldDecList() != null) {
			for (FieldDeclaration fd : block.getFieldDecList().getFieldDecList()) {
				fd.accept(this);
			}
		}
		if (block.getStmtList() != null && block.getStmtList().getStatementList() != null) {
			for (Statement s : block.getStmtList().getStatementList()) {
				s.accept(this);
			}
		}
		return null;
	}

	@Override
	public Expression visit(ExpressionList exprList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(LocationExpr locExpr) {
		if (locExpr.getExpr() != null) {
			locExpr.setExpr(locExpr.getExpr().accept(this));
		}
		return locExpr;
	}

	@Override
	public Expression visit(BinOpExpr expr) {
		BinOpType op = expr.getOperator();// operador
		// expresion del primer operando
		Expression left = expr.getLeftExpr().accept(this);
		// expresion del segundo operando
		Expression right = expr.getRightExpr().accept(this);
		++instrId;
		int id = instrId;
		LocationExpr var = new LocationExpr(new IdName("temp." + id));
		switch (op) {
		case MINUS:
			instructionCodeList.add(new ICGInstruction(ICGOperation.SUB, left, right, var));
			break;
		case PLUS:
			instructionCodeList.add(new ICGInstruction(ICGOperation.ADD, left, right, var));
			break;
		case PRODUCT:
			instructionCodeList.add(new ICGInstruction(ICGOperation.MUL, left, right, var));
			break;
		case DIVIDE:
			instructionCodeList.add(new ICGInstruction(ICGOperation.DIV, left, right, var));
			break;
		case MOD:
			instructionCodeList.add(new ICGInstruction(ICGOperation.MOD, left, right, var));
			break;
		case AND:
			instructionCodeList.add(new ICGInstruction(ICGOperation.AND, left, right, var));
			break;
		case OR:
			instructionCodeList.add(new ICGInstruction(ICGOperation.OR, left, right, var));
			break;
		case LESS:
			instructionCodeList.add(new ICGInstruction(ICGOperation.LESS, left, right, var));
			break;
		case LESS_EQ:
			instructionCodeList.add(new ICGInstruction(ICGOperation.LESS_EQ, left, right, var));
			break;
		case HIGH:
			instructionCodeList.add(new ICGInstruction(ICGOperation.HIGH, left, right, var));
			break;
		case HIGH_EQ:
			instructionCodeList.add(new ICGInstruction(ICGOperation.HIGH_EQ, left, right, var));
			break;
		case EQUAL:
			instructionCodeList.add(new ICGInstruction(ICGOperation.EQUAL, left, right, var));
			break;
		case DISTINCT:
			instructionCodeList.add(new ICGInstruction(ICGOperation.DISTINCT, left, right, var));
			break;
		}
		return var;
	}

	@Override
	public Expression visit(UnaryOpExpr unaryExpr) {
		++instrId;
		int id = instrId;
		Expression e = unaryExpr.getExpression().accept(this);
		LocationExpr var = new LocationExpr(new IdName("temp." + id));
		AttributeSymbolTable a = new AttributeSymbolTable(null, unaryExpr.getType(), "temp." + id);
		var.setReference(a);
		switch (unaryExpr.getOperator()) {
		case NOT:
			instructionCodeList.add(new ICGInstruction(ICGOperation.NOT, e, var, null));
		case MINUS:
			instructionCodeList.add(new ICGInstruction(ICGOperation.MIN, e, var, null));
			break;
		}
		return var;
	}

	@Override
	public Expression visit(MethodCallExpr methExpr) {
		List<Expression> list = new LinkedList<>();
		if (methExpr.getExpressionList() != null && methExpr.getExpressionList().getExpressionList() != null) {
			list = methExpr.getExpressionList().getExpressionList();
			for (int i = 0; i < list.size(); i++) {
				Expression e = list.get(i).accept(this);
				list.set(i, e);
			}
		}
		methExpr.setExpressionList(new ExpressionList(list));
		++instrId;
		int id = instrId;
		LocationExpr var = new LocationExpr(new IdName("temp." + id));
		AttributeSymbolTable a = new AttributeSymbolTable(null, methExpr.getType(), "temp." + id);
		var.setReference(a);
		instructionCodeList.add(new ICGInstruction(ICGOperation.CALL, methExpr, var, null));
		return var;
	}

	@Override
	public Expression visit(IntegerLiteral lit) {
		return lit;
	}

	@Override
	public Expression visit(BoolLiteral lit) {
		return lit;
	}

	@Override
	public Expression visit(FloatLiteral lit) {
		return lit;
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
		if (fd.getIdentifiersList() != null && fd.getIdentifiersList().getListIdentifier() != null) {
			for (Identifier id : fd.getIdentifiersList().getListIdentifier()) {
				instructionCodeList.add(new ICGInstruction(ICGOperation.DEF, fd.getType(), id, null));
			}
		}
		return null;
	}

	@Override
	public Expression visit(FieldDeclarationList fdl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(MethodDeclaration methD) {
		if (methD.getArguments() != null && methD.getArguments().getArgumentsList() != null) {
			for (Parameter p : methD.getArguments().getArgumentsList()) {
				p.accept(this);
			}
		}
		// si es distinti de null, quiere decir que no es extern el bloque
		if (methD.getBody().getBlock() != null) {
			++labelId;
			methD.getBody().accept(this);
			if (!((LinkedList<ICGInstruction>) instructionCodeList).getLast().getOp().equals(ICGOperation.RETURN)) {
				instructionCodeList.add(new ICGInstruction(ICGOperation.RETURN, null, null, null));
			}
		} else {// si es null es extern el bloque

			// arreglar despues de setear las referencias

			// ((MethodSymbolTable) methD.getReference()).setIsExtern(true);

		}
		return null;
	}

	@Override
	public Expression visit(MethodDeclarationList methDecList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(Parameter parameter) {
		return null;
	}

	@Override
	public Expression visit(IdName idName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(GenericType genericType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(LabelExpr labelExpr) {
		// TODO Auto-generated method stub
		return null;
	}

}
