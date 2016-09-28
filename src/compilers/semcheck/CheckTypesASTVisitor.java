package compilers.semcheck;

import java.util.LinkedList;
import java.util.List;

import compilers.ast.*;
import compilers.ast.enumerated_types.BinOpType;
import compilers.ast.enumerated_types.GenericType;
import compilers.ast.enumerated_types.Type;
import compilers.ast.enumerated_types.UnaryOpType;
import compilers.ASTVisitor;
import compilers.symbol_table.*;

public class CheckTypesASTVisitor implements ASTVisitor<GenericType> {

	private SymbolTable symbolTable;
	private List<String> errorList;

	public CheckTypesASTVisitor() {
		this.errorList = new LinkedList<String>();
		symbolTable = new SymbolTable();

	}
	
	public List<String> getErrorList(){
		return this.errorList;
	}

	public SymbolTable getSymbolTable() {
		return symbolTable;
	}

	@Override
	public GenericType visit(Program prog) {
		if (prog.getListDeclarationClass() == null || prog.getListDeclarationClass().isEmpty()) {
			errorList.add("Error, no hay clases definidas en el programa");
		} else {
			for (DeclarationClass dc : prog.getListDeclarationClass()) {
				dc.accept(this);
			}
		}
		// analizo que exista una clase "Main" y que tenga un metodo main
		if (symbolTable.existClassSymbolTable("Main")) {
			MethodSymbolTable meth = symbolTable.getMethodSymbolTable("Main", "main");
			if (meth == null) {
				errorList.add("error. La clase debe contener un metodo main");
			}
			if (!meth.getParameterList().isEmpty()) {
				errorList.add("error. El metodo main no debe contener parametros");
			} else {
				if (!meth.getReturnType().isVoid()) {
					errorList.add("error. el metodo main debe retornar void");
				}
			}
		} else {
			errorList.add("El programa debe contener una clase main");
		}
		return null;

	}

	@Override
	public GenericType visit(DeclarationClass decClass) {

		ClassSymbolTable c = new ClassSymbolTable();
		symbolTable.pushClassSymbolTable(decClass.getIdName().getId(), c);
		symbolTable.pushBlockSymbolTable(new BlockSymbolTable());
		if (decClass.getFieldDecList() != null && decClass.getFieldDecList().getFieldDecList() != null
				&& !decClass.getFieldDecList().getFieldDecList().isEmpty()) {
			for (FieldDeclaration fd : decClass.getFieldDecList().getFieldDecList()) {
				for (Identifier identifier : fd.getIdentifiersList().getListIdentifier()) {
					// creo un atributo
					AttributeSymbolTable attr;
					if (identifier.getSize() == null) {
						attr = new AttributeSymbolTable(null, fd.getType(), identifier.getId().getId());
					} else {
						attr = new AttributeSymbolTable(null, fd.getType(), identifier.getId().getId(),
								identifier.getSize().getValue());
					}
					symbolTable.setVarBlockSymbolTable(attr);
					symbolTable.insertAttrClassSymbolTable(symbolTable.getLastClass(), attr);
				}
			}
		}
		if (decClass.getMethodDecList() != null && decClass.getMethodDecList().getMethodDecList() != null
				&& !decClass.getMethodDecList().getMethodDecList().isEmpty()) {
			// recorro la definicion de metodos
			for (MethodDeclaration m : decClass.getMethodDecList().getMethodDecList()) {
				m.accept(this);
				List<Parameter> listParameter = new LinkedList<>();
				if (m.getArguments() != null && m.getArguments().getArgumentsList() != null
						&& !m.getArguments().getArgumentsList().isEmpty()) {
					listParameter = m.getArguments().getArgumentsList();
				}
				MethodSymbolTable methST = new MethodSymbolTable(m.getId().getId(), m.getType(), listParameter);
				symbolTable.insertMethClassSymbolTable(symbolTable.getLastClass(), methST);
			}
		}
		symbolTable.popBlockSymbolTable();
		return null;

	}

	@Override
	public GenericType visit(StatementList stmtList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericType visit(AssignStmt stmt) {
		AttributeSymbolTable loc = symbolTable.getAttributeSymbolTable(stmt.getLocation().getId().toString());
		stmt.getLocation().accept(this);
		if (loc != null) {
			GenericType typeStmt = stmt.getExpression().accept(this);
			if (loc.getType().toString() != typeStmt.toString()) {
				errorList.add("error de tipos, linea: " + stmt.getExpression().getLineNumber() + " columna: "
						+ stmt.getExpression().getColumnNumber());
			}
			// si es += o -= y la variable no es float o int se rompe
			if (!loc.getType().isFloat() && !loc.getType().isInteger()
					&& (stmt.getAssignOpType().isDecrement() || stmt.getAssignOpType().isIncrement())) {
				errorList.add("No se puede aplicar " + stmt.getAssignOpType().toString()
						+ " a una variable de tipo " + loc.getType().toString() + ". linea: "
						+ stmt.getExpression().getLineNumber() + " columna: " + stmt.getExpression().getColumnNumber());
			}
			// si la variable definida NO es un arreglo pero en la location si
			// lo usa como arreglo se rompe
			if (loc.getSize() == 0 && stmt.getLocation().getExpr() != null) {
				errorList.add("La variable " + loc.getName() + " No es un arreglo. linea: "
						+ stmt.getLocation().getLineNumber() + " columna: " + stmt.getLocation().getColumnNumber());
			}
		} else {
			errorList.add("variable no definida, linea: " + stmt.getLocation().getLineNumber() + " columna: "
					+ stmt.getLocation().getColumnNumber());
		}
		return new GenericType(Type.VOID.toString());
	}

	@Override
	public GenericType visit(ReturnStmt stmt) {
		if (stmt.getExpression() != null) {
			return stmt.getExpression().accept(this);
		}
		return new GenericType(Type.VOID.toString());
	}

	@Override
	public GenericType visit(IfStmt stmt) {
		if (!stmt.getCondition().accept(this).isBool()) {
			errorList.add("Condicion del if no es booleana, linea: " + stmt.getCondition().getLineNumber()
					+ " columna: " + stmt.getCondition().getColumnNumber());
		} else {
			stmt.getIfStatement().accept(this);
			if (stmt.getElseStatement() != null) {
				stmt.getElseStatement().accept(this);
			}
		}
		return new GenericType(Type.VOID.toString());
	}

	@Override
	public GenericType visit(WhileStmt stmt) {
		GenericType type = stmt.getCondition().accept(this);
		if (!type.isBool()) {
			errorList.add("El tipo de la expresion debe ser bool. linea: " + stmt.getCondition().getLineNumber()
					+ " columna: " + stmt.getCondition().getColumnNumber());
		} else {
			stmt.getStatementCondition().accept(this);
		}
		return new GenericType(Type.VOID.toString());
	}

	@Override
	public GenericType visit(ForStmt stmt) {
		GenericType typeExpInit = stmt.getInit().accept(this);
		GenericType typeExpCota = stmt.getCota().accept(this);
		// las expresion de inicio debe ser entero unicamente
		if (!typeExpInit.isInteger()) {
			errorList.add("El tipo de la expresion de inicio debe ser int linea: " + stmt.getInit().getLineNumber()
					+ " columna: " + stmt.getInit().getColumnNumber());
		}
		if (!typeExpCota.isBool()) {
			errorList.add("El tipo de la expresion debe ser bool. linea: " + stmt.getCota().getLineNumber()
					+ " columna: " + stmt.getCota().getColumnNumber());
		} else {
			stmt.getForStatement().accept(this);
		}
		return new GenericType(Type.VOID.toString());
	}

	@Override
	public GenericType visit(BreakStmt stmt) {
		return new GenericType(Type.VOID.toString());
	}

	@Override
	public GenericType visit(ContinueStmt stmt) {
		return new GenericType(Type.VOID.toString());
	}

	@Override
	public GenericType visit(SemicolonStmt stmt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericType visit(MethodCallStmt methStmt) {
		return null;
	}

	@Override
	public GenericType visit(ArgumentDeclaration argDec) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericType visit(Body body) {
		if (body.getBlock() != null) {
			return body.getBlock().accept(this);
		} else {
			return null;
		}
	}

	@Override
	public GenericType visit(Block block) {
		symbolTable.pushBlockSymbolTable(new BlockSymbolTable());
		if (block.getFieldDecList() != null && block.getFieldDecList().getFieldDecList() != null
				&& !block.getFieldDecList().getFieldDecList().isEmpty()) {
			for (FieldDeclaration fd : block.getFieldDecList().getFieldDecList()) {
				if (fd.getIdentifiersList() != null) {
					List<Identifier> listIdentifier = fd.getIdentifiersList().getListIdentifier();
					for (Identifier identifier : listIdentifier) {
						// creo un atributo
						AttributeSymbolTable attr;
						
						if (symbolTable.getAttributeSymbolTableSameBlock(identifier.getId().getId()) == null) {
							if (identifier.getSize() == null) {
								attr = new AttributeSymbolTable(null, fd.getType(), identifier.getId().getId());
							} else {
								attr = new AttributeSymbolTable(null, fd.getType(), identifier.getId().getId(),
										identifier.getSize().getValue());
							}
							symbolTable.setVarBlockSymbolTable(attr);
						} else {
							errorList.add("Error, ya existe una variable en el bloque corriente'"
									+ identifier.getId().getId() + "'" + ", linea: " + identifier.getLineNumber()
									+ " columna: " + identifier.getColumnNumber());
						}
					}
				}
			}
		}
		GenericType lastType = new GenericType(Type.VOID.toString()); /*
										 * utizo esto para retornar el tipo, en
										 * caso de no ser de retorno es void
										 */
		for (Statement s : block.getStmtList().getStatementList()) {
			if (!lastType.getType().equals((Type.VOID.toString()))) {
				errorList.add("No se puede tener sentencias despues de un return");
			}
			lastType = s.accept(this);
		}
		symbolTable.popBlockSymbolTable();
		return lastType;
	}

	@Override
	public GenericType visit(ExpressionList exprList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericType visit(LocationExpr locExpr) {
		AttributeSymbolTable var = symbolTable.getAttributeSymbolTable(locExpr.getId().getId());
		if (var == null) {
			errorList.add("variable '" + locExpr.getId().getId() + "' no definida, linea: "
					+ locExpr.getLineNumber() + " columna: " + locExpr.getColumnNumber());
			return null;
		} else {
			if (var.getSize() > 0) {
				if (locExpr.getExpr() != null) {
					GenericType t = locExpr.getExpr().accept(this);
					if (!t.isInteger()) {
						errorList.add("el tipo de expr debe ser int." + locExpr.getExpr().getLineNumber()
								+ " columna: " + locExpr.getExpr().getColumnNumber());
					}
				} else {
					errorList.add("el arreglo se esta usando como una variable " + locExpr.getLineNumber()
							+ " columna: " + locExpr.getColumnNumber());
				}
			}
			return var.getType();
		}
	}

	@Override
	public GenericType visit(BinOpExpr expr) {
		GenericType typeLeft = expr.getLeftExpr().accept(this);
		GenericType typeRight = expr.getRightExpr().accept(this);
		if (!typeLeft.getType().equals(typeRight.getType())){
			errorList.add("error de tipos, no se puede hacer " + typeLeft.toString()
					+ expr.getOperator().toString() + typeRight.toString() + ", linea: " + expr.getLineNumber()
					+ " columna: " + expr.getColumnNumber());
		}
		BinOpType op = expr.getOperator();
		// CORROBORO QUE SI SON OPERACIONES RELACIONALES O LOGICAS LOS OPERANDOS
		// SEAN LOGICOS Y QUE RETORNE ALGO LOGICO
		if (op.isConditional() || op.isEquational() || op.isRelational()) {
			expr.setType(new GenericType(Type.BOOL.toString()));
			return expr.getType();
		} else {// si es aritmetico retorno el tipo de algun operando
			expr.setType(typeLeft);
			return typeLeft;
		}
	}

	@Override
	public GenericType visit(UnaryOpExpr unaryExpr) {
		GenericType typeExpr = unaryExpr.getExpression().accept(this);
		if (unaryExpr.getOperator() == UnaryOpType.MINUS) {
			// el tipo de la expresión debe ser si o si float o int
			if (!typeExpr.isFloat() && !typeExpr.isInteger()) {
				errorList.add("no se puede aplicar '-' a una expresión de tipo " + typeExpr.toString()
						+ ", linea: " + unaryExpr.getLineNumber() + " columna: " + unaryExpr.getColumnNumber());
			} else {
				return typeExpr;
			}
		} else {
			// el tipo debe ser booleano
			if (!typeExpr.isBool()) {
				errorList.add("no se puede aplicar '!' a una expresión de tipo " + typeExpr.toString()
						+ ", linea: " + unaryExpr.getLineNumber() + " columna: " + unaryExpr.getColumnNumber());
			} else {
				return new GenericType(Type.BOOL.toString());
			}
		}
		return null;
	}

	@Override
	public GenericType visit(MethodCallExpr methExpr) {
		MethodSymbolTable meth = symbolTable.getMethodSymbolTable(symbolTable.getLastClass(),
				methExpr.getIdName().toString());
		if (meth.getParameterList() != null && methExpr.getExpressionList() != null
				&& methExpr.getExpressionList().getExpressionList() != null) {
			if (meth.getParameterList().size() != methExpr.getExpressionList().getExpressionList().size()) {
				errorList.add("Error en la cantidad de parametros en la llamada del metodo "
						+ methExpr.getIdName().toString() + ", linea: " + methExpr.getLineNumber());
			}
		}
		if (methExpr.getExpressionList() != null && methExpr.getExpressionList().getExpressionList() != null) {
			for (int i = 0; i < methExpr.getExpressionList().getExpressionList().size(); i++) {
				if (!methExpr.getExpressionList().getExpressionList().get(i).accept(this).toString().equals((meth.getParameterList()
						.get(i).getType().getType()))) {
					errorList.add("Error de tipo de parametros en la llamada del metodo "
							+ methExpr.getIdName().getId() + ", linea: " + methExpr.getLineNumber());
				}
			}
		}
		return meth.getReturnType();
	}

	@Override
	public GenericType visit(IntegerLiteral lit) {
		return lit.getType();
	}

	@Override
	public GenericType visit(BoolLiteral lit) {
		return lit.getType();
	}

	@Override
	public GenericType visit(FloatLiteral lit) {
		return lit.getType();
	}

	@Override
	public GenericType visit(Identifier id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericType visit(FieldDeclaration fd) {
		return fd.getType();
	}

	@Override
	public GenericType visit(FieldDeclarationList fdl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericType visit(MethodDeclaration methD) {
		BlockSymbolTable block = new BlockSymbolTable();
		symbolTable.pushBlockSymbolTable(block);
		if (methD.getArguments() != null && methD.getArguments().getArgumentsList() != null
				&& !methD.getArguments().getArgumentsList().isEmpty()) {
			for (Parameter par : methD.getArguments().getArgumentsList()) {
				par.accept(this);
			}
		}
		GenericType ret = methD.getBody().accept(this);
		if (ret != null)// si es null es porque es un extern, de esta forma
						// ignoro el analisis de retorno
		{
			if (methD.getType().getType() != ret.getType()) {
				errorList.add("Error de tipo, el tipo de retorno del metodo es " + methD.getType().getType()
						+ " y el tipo retornado es " + ret.getType());
			}
		}
		symbolTable.popBlockSymbolTable();
		return null;
	}

	@Override
	public GenericType visit(MethodDeclarationList methDecList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericType visit(Parameter p) {
		AttributeSymbolTable attr;
		if (symbolTable.getVariableBlockSymbolTable(p.getId().getId()) == null) {
			attr = new AttributeSymbolTable(null, p.getType(), p.getId().getId());
			symbolTable.setVarBlockSymbolTable(attr);
		} else {
			errorList.add("Error, ya existe una parametro en el metodo '" + p.getId().getId() + "'");
		}
		return null;
	}

	@Override
	public GenericType visit(IdentifiersList identifiersList) {
		// TODO Auto-generated method stub
		return null;
	}

}
