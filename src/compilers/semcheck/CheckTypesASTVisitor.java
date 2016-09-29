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

/*
 *   Esta clase es la encargada de hacer el chequeo semantico de diferentes reglas provistas por
 * la descripcion del lenguaje. Las reglas que valida son las siguientes:
 *   Regla 1: Ningun identificador es declarado dos veces en el mismo bloque. Este metodo no tiene parametros.
 *   Regla 2: Ningun identificador es usado antes de ser declarado.
 *   Regla 3: Toda programa contiene la definicion de una clase y un metodo en la misma clase llamado "main".
 *   Regla 4: El (int_literal) en la declaracion de un arreglo debe ser mayor a 0.
 *   Regla 5: La cantidad y tipo de los argumentos en una invocacion a un metodo, debe ser igual a la cantidad
 * y tipo de argumentos en la definicion del metodo.
 *   Regla 7: Una sentencia return solo tiene asociada una expresion si el metodo retorna un valor distinto de "VOID".
 *   Regla 8: La expresion en una sentencia "return" debe ser igual al tipo de retorno declarado para el 
 * metodo.
 *   Regla 9: un id debe estar declarado como un parametro o como variable local o global.
 *   Regla 10: En la declaracion de arreglos "id[exp]", "id" debe ser una variable de arreglo(array), y el tipo de
 * "exp" debe ser Integer.
 *   Regla 11: La expresion en una sentencia "if" o "while" debe ser Boolean.
 *   Regla 12: Los operandos aritmeticos y relacionales deben ser Float o Integer.
 *   Regla 13: Los operandos de igualdad deben ser del mismo tipo.
 *   Regla 14: Los operandos condicionales y negacion deben ser de tipo Boolean.
 *   Regla 15: La "location" y "expresion" en una asignacion deben ser del mismo tipo.
 *   Regla 16: La "location" y "expresion" en las asignaciones +=, -= deben ser de tipo Float o Integer.
 *   Regla 17: Las expresiones iniciales y finales de un "for" deben ser de tipo Integer.
 *   
 */
public class CheckTypesASTVisitor implements ASTVisitor<GenericType> {

	private SymbolTable symbolTable; // Tabla de simbolos
	private List<String> errorList; // Lista de errores encontrados

	// Constructor
	public CheckTypesASTVisitor() {
		this.errorList = new LinkedList<String>();
		symbolTable = new SymbolTable();

	}

	// Getters
	public List<String> getErrorList() {
		return this.errorList;
	}

	public SymbolTable getSymbolTable() {
		return symbolTable;
	}

	@Override
	public GenericType visit(Program prog) {
		// Si la lista de clases declaradas del programa es NULL ni Vacia
		if (prog.getListDeclarationClass() == null || prog.getListDeclarationClass().isEmpty()) {
			errorList.add("Error, no hay clases definidas en el programa");
		} else {// Si hay clases declaradas en el programa
			for (DeclarationClass dc : prog.getListDeclarationClass()) {
				dc.accept(this);
			}
			// Chequeo que exista una clase "Main"
			if (symbolTable.existClassSymbolTable("Main")) {
				// Busco el metodo "main" dentro de la clase "Main"
				MethodSymbolTable meth = symbolTable.getMethodSymbolTable("Main", "main");
				if (meth == null) {// Si no existe el metodo "main"
					errorList.add("error. La clase debe contener un metodo main");
				}
				// la lista de parametros de "main" debe ser vacia
				if (!meth.getParameterList().isEmpty()) {
					errorList.add("error. El metodo main no debe contener parametros");
				} else {
					// el metodo debe tener retorno de tipo "void"
					if (!meth.getReturnType().isVoid()) {
						errorList.add("error. el metodo main debe retornar void");
					}
				}
			} else {// Si no existe la clase "Main"
				errorList.add("El programa debe contener una clase main");
			}
		}
		return null;
	}// fin visit Program

	@Override
	public GenericType visit(DeclarationClass decClass) {
		// APilo una clase en la tabla de simbolos
		symbolTable.pushClassSymbolTable(decClass.getIdName().getId(), new ClassSymbolTable());
		// Agrego un bloque en la tabla de simbolos si la lista de simbolos
		symbolTable.pushBlockSymbolTable(new BlockSymbolTable());
		// Si la lista de declaraciones de atributos de la clase no es NULL
		if (decClass.getFieldDecList() != null) {
			decClass.getFieldDecList().accept(this);
		}
		// Si la lista de declaraciones de metodos de la clase no es NULL
		if (decClass.getMethodDecList() != null) {
			decClass.getMethodDecList().accept(this);
		}
		// Desapilo el bloque de la tabla de simbolos
		symbolTable.popBlockSymbolTable();
		return null;

	}// fin visit DeclarationClass

	@Override
	public GenericType visit(StatementList stmtList) {
		// Defino este tipo para retornarlo en caso de que no tenga tipo de
		// retorno(debe ser "void")
		GenericType lastType = new GenericType(Type.VOID.toString());
		for (Statement s : stmtList.getStatementList()) {
			if (!lastType.getType().equals((Type.VOID.toString()))) {
				errorList.add("No se puede tener sentencias despues de un return");
			}
			lastType = s.accept(this);
		}
		return lastType;
	}// fin visit StatementList

	@Override
	public GenericType visit(AssignStmt stmt) {
		// saco los atributos de la tabla de simbolos
		AttributeSymbolTable loc = symbolTable.getAttributeSymbolTable(stmt.getLocation().getId().getId());
		stmt.getLocation().accept(this);
		// si hay atributos en la tabla de simbolos
		if (loc != null) {
			GenericType typeStmt = stmt.getExpression().accept(this);
			// si la variable no tiene el mismo tipo que la expresion
			// que se le asigna
			if (loc.getType().toString() != typeStmt.toString()) {
				errorList.add("error de tipos, linea: " + stmt.getExpression().getLineNumber() + " columna: "
						+ stmt.getExpression().getColumnNumber());
			}
			// Si es += o -= la variable declarada debe ser int o float
			if (!loc.getType().isFloat() && !loc.getType().isInteger()
					&& (stmt.getAssignOpType().isDecrement() || stmt.getAssignOpType().isIncrement())) {
				errorList.add("No se puede aplicar " + stmt.getAssignOpType().toString() + " a una variable de tipo "
						+ loc.getType().toString() + ". linea: " + stmt.getExpression().getLineNumber() + " columna: "
						+ stmt.getExpression().getColumnNumber());
			}
			// si la variable definida NO es un arreglo si se esta usando como
			// un arreglo
			if (loc.getSize() == 0 && stmt.getLocation().getExpr() != null) {
				errorList.add("La variable " + loc.getName() + " No es un arreglo. linea: "
						+ stmt.getLocation().getLineNumber() + " columna: " + stmt.getLocation().getColumnNumber());
			}
		} else {
			// si los atributos no estan en la tabla de simbolos, es que no
			// fueron definidos
			errorList.add("variable no definida, linea: " + stmt.getLocation().getLineNumber() + " columna: "
					+ stmt.getLocation().getColumnNumber());
		}
		return new GenericType(Type.VOID.toString());
	}

	@Override
	public GenericType visit(ReturnStmt stmt) {
		if (stmt.getExpression() != null) {
			return stmt.getExpression().accept(this);
		} else {
			return new GenericType(Type.VOID.toString());
		}
	}// fin visit ReturnStmt

	@Override
	public GenericType visit(IfStmt stmt) {
		// chequeo que la condicion del if sea booleana
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
	}// fin visit IfStmt

	@Override
	public GenericType visit(WhileStmt stmt) {
		// chequeo que la condicion del while sea booleana
		GenericType type = stmt.getCondition().accept(this);
		if (!type.isBool()) {
			errorList.add("El tipo de la expresion debe ser bool. linea: " + stmt.getCondition().getLineNumber()
					+ " columna: " + stmt.getCondition().getColumnNumber());
		} else {
			stmt.getStatementCondition().accept(this);
		}
		return new GenericType(Type.VOID.toString());
	}// fin visit WhileStmt

	@Override
	public GenericType visit(ForStmt stmt) {
		GenericType typeExpInit = stmt.getInit().accept(this);
		GenericType typeExpCota = stmt.getCota().accept(this);
		// La exprecion de inicio debe ser integer
		if (!typeExpInit.isInteger()) {
			errorList.add("El tipo de la expresion de inicio debe ser int linea: " + stmt.getInit().getLineNumber()
					+ " columna: " + stmt.getInit().getColumnNumber());
		}
		// la expresion de cota debe ser integer
		if (!typeExpCota.isInteger()) {
			errorList.add("El tipo de la expresion debe ser integer. linea: " + stmt.getCota().getLineNumber()
					+ " columna: " + stmt.getCota().getColumnNumber());
		} else {
			stmt.getForStatement().accept(this);
		}
		return new GenericType(Type.VOID.toString());
	}// fin visit ForStmt

	@Override
	public GenericType visit(BreakStmt stmt) {
		return new GenericType(Type.VOID.toString());
	}// fin visit BreakStmt

	@Override
	public GenericType visit(ContinueStmt stmt) {
		return new GenericType(Type.VOID.toString());
	}// fin visit ContinueStmt

	@Override
	public GenericType visit(SemicolonStmt stmt) {
		// Punto y coma no tiene tipo
		return new GenericType(Type.VOID.toString());
	}// fin visit SemicolonStmt

	@Override
	public GenericType visit(MethodCallStmt methStmt) {
		return new GenericType(Type.VOID.toString());
	}// fin visit MethodCallStmt

	@Override
	public GenericType visit(ArgumentDeclaration argDec) {
		// si la lista de argumentos no es vacia
		if (argDec.getArgumentsList() != null && !argDec.getArgumentsList().isEmpty()) {
			for (Parameter par : argDec.getArgumentsList()) {
				par.accept(this);
			}
		}
		return null;
	}// fin visit ArgumentDeclaration

	@Override
	public GenericType visit(Body body) {
		// Si el bloque es NULL quiere decir que es "extern"
		if (body.getBlock() != null) {
			return body.getBlock().accept(this);
		} else {
			return null;
		}
	}// fin visit Body

	// metodo para tratar la declaracion de atributos dentro de un bloque(Block)
	private GenericType fieldDeclarationListBlock(FieldDeclarationList fdl) {
		// si la lista de atributos no es vacia
		if (fdl.getFieldDecList() != null && !fdl.getFieldDecList().isEmpty()) {
			for (FieldDeclaration fd : fdl.getFieldDecList()) {
				if (fd.getIdentifiersList() != null) {
					// saco la lista de identificadores de los atributos
					List<Identifier> listIdentifier = fd.getIdentifiersList().getListIdentifier();
					// reviso todos los identificadores de los atributos
					for (Identifier identifier : listIdentifier) {
						// creo un atributo de la tabla de simbolos
						AttributeSymbolTable attr;
						// si el atributo no fue declarado ya en algun bloque
						// visible a este
						if (symbolTable.getAttributeSymbolTableSameBlock(identifier.getId().getId()) == null) {
							if (identifier.getSize() == null) {
								attr = new AttributeSymbolTable(null, fd.getType(), identifier.getId().getId());
							} else {
								attr = new AttributeSymbolTable(null, fd.getType(), identifier.getId().getId(),
										identifier.getSize().getValue());
							}
							symbolTable.setVarBlockSymbolTable(attr);

						} // si el atributo ya estaba declarado en el bloque
						else {
							errorList.add("Error, ya existe una variable en el bloque corriente'"
									+ identifier.getId().getId() + "'" + ", linea: " + identifier.getLineNumber()
									+ " columna: " + identifier.getColumnNumber());
						}
					}
				}
			}
		}
		return null;
	}// fin method fieldDeclarationListBlock

	@Override
	public GenericType visit(Block block) {
		// meto un bloque en la tabla de simbolos
		symbolTable.pushBlockSymbolTable(new BlockSymbolTable());
		// si el bloque (Block) tiene atributos declarados en su cuerpo
		if (block.getFieldDecList() != null) {
			fieldDeclarationListBlock(block.getFieldDecList());
		}
		// saco el tipo de retorno del bloque(Block)
		GenericType lastType = block.getStmtList().accept(this);
		// elimino el bloque de la tabla de simbolos
		symbolTable.popBlockSymbolTable();
		return lastType;
	}// fin visit Block

	@Override
	public GenericType visit(ExpressionList exprList) {
		// la lista de expresiones, no tiene un unico tipo
		return null;
	}// fin visit ExpressionList

	@Override
	public GenericType visit(LocationExpr locExpr) {
		// Busco la variable declarada en la tabla de simbolos
		AttributeSymbolTable var = symbolTable.getAttributeSymbolTable(locExpr.getId().getId());
		// si la variable no existe
		if (var == null) {
			errorList.add("variable '" + locExpr.getId().getId() + "' no definida, linea: " + locExpr.getLineNumber()
					+ " columna: " + locExpr.getColumnNumber());
			return null;
		} else {
			// si la variable fue declarada como un arreglo (size > 0)
			if (var.getSize() > 0) {
				// si la expresion es distinta de NULL
				if (locExpr.getExpr() != null) {
					GenericType t = locExpr.getExpr().accept(this);
					// el tipo de la expresion contenida en el arreglo debe ser
					// integer
					if (!t.isInteger()) {
						errorList.add("el tipo de expr debe ser int." + locExpr.getExpr().getLineNumber() + " columna: "
								+ locExpr.getExpr().getColumnNumber());
					}
				}
				// si no tiene una expresion asociada, la variable se esta
				// usando mal
				else {
					errorList.add("el arreglo se esta usando como una variable " + locExpr.getLineNumber()
							+ " columna: " + locExpr.getColumnNumber());
				}
			}
			return var.getType();
		}
	}// fin visit LocationExpression

	@Override
	public GenericType visit(BinOpExpr expr) {
		GenericType typeLeft = expr.getLeftExpr().accept(this);
		GenericType typeRight = expr.getRightExpr().accept(this);
		// chequeo que los dos operandos sean del mismo tipo
		if (!typeLeft.getType().equals(typeRight.getType())) {
			errorList.add("error de tipos, no se puede hacer " + typeLeft.toString() + expr.getOperator().toString()
					+ typeRight.toString() + ", linea: " + expr.getLineNumber() + " columna: "
					+ expr.getColumnNumber());
		}
		BinOpType op = expr.getOperator();
		// Chequeo que si son operaciones relacionales o logicas el tipo sea
		// booleano
		if (op.isConditional() || op.isEquational() || op.isRelational()) {
			expr.setType(new GenericType(Type.BOOL.toString()));
			return expr.getType();
		} else {// Si es aritmetico retorno el tipo de algun operando
			expr.setType(typeLeft);
			return typeLeft;
		}
	}// fin visit BinOperationExpression

	@Override
	public GenericType visit(UnaryOpExpr unaryExpr) {
		GenericType typeExpr = unaryExpr.getExpression().accept(this);
		if (unaryExpr.getOperator() == UnaryOpType.MINUS) {
			// El tipo de la expresion debe ser float o integer
			if (!typeExpr.isFloat() && !typeExpr.isInteger()) {
				errorList.add("no se puede aplicar '-' a una expresión de tipo " + typeExpr.toString() + ", linea: "
						+ unaryExpr.getLineNumber() + " columna: " + unaryExpr.getColumnNumber());
			} else {
				return typeExpr;
			}
		} else {
			// El tipo debe ser booleano
			if (!typeExpr.isBool()) {
				errorList.add("no se puede aplicar '!' a una expresión de tipo " + typeExpr.toString() + ", linea: "
						+ unaryExpr.getLineNumber() + " columna: " + unaryExpr.getColumnNumber());
			} else {
				return typeExpr;
			}
		}
		return typeExpr;
	}// fin visit UnaryOperationExpression

	@Override
	public GenericType visit(MethodCallExpr methExpr) {
		// saco el metodo de la tabla de simbolos
		MethodSymbolTable methSymbolTable = symbolTable.getMethodSymbolTable(symbolTable.getLastClass(),
				methExpr.getIdName().toString());
		// si la cantidad de parametros del metodo en la declaracion es
		// distinta a la cantidad en la invocacion
		if (methSymbolTable.getParameterList() != null && methExpr.getExpressionList() != null
				&& methExpr.getExpressionList().getExpressionList() != null) {
			if (methSymbolTable.getParameterList().size() != methExpr.getExpressionList().getExpressionList().size()) {
				errorList.add("Error en la cantidad de parametros en la llamada del metodo "
						+ methExpr.getIdName().toString() + ", linea: " + methExpr.getLineNumber());
			}
		}
		// si los tipos de los parametros del metodo en la declaracion es
		// distinta a los tipos en la invocacion
		if (methExpr.getExpressionList() != null && methExpr.getExpressionList().getExpressionList() != null) {
			for (int i = 0; i < methExpr.getExpressionList().getExpressionList().size(); i++) {
				if (!methExpr.getExpressionList().getExpressionList().get(i).accept(this).toString()
						.equals((methSymbolTable.getParameterList().get(i).getType().getType()))) {
					errorList.add("Error de tipo de parametros en la llamada del metodo " + methExpr.getIdName().getId()
							+ ", linea: " + methExpr.getLineNumber());
				}
			}
		}
		return methSymbolTable.getReturnType();
	}// fin visit MethodCallExpression

	@Override
	public GenericType visit(IntegerLiteral lit) {
		return lit.getType();
	}// fin visit IntegerLiteral

	@Override
	public GenericType visit(BoolLiteral lit) {
		return lit.getType();
	}// fin visit BoolLiteral

	@Override
	public GenericType visit(FloatLiteral lit) {
		return lit.getType();
	}// fin visit FloatLiteral

	@Override
	public GenericType visit(Identifier id) {
		// el identificador de por si solo no tiene un tipo
		return null;
	}// fin visit Identifier

	@Override
	public GenericType visit(FieldDeclaration fd) {
		return fd.getType();
	}// fin visit FieldDeclaration

	@Override
	public GenericType visit(FieldDeclarationList fdl) {
		// si la lista de declaraciones de atributos no es vacia
		if (fdl.getFieldDecList() != null && !fdl.getFieldDecList().isEmpty())
			for (FieldDeclaration fd : fdl.getFieldDecList()) {
				for (Identifier identifier : fd.getIdentifiersList().getListIdentifier()) {
					AttributeSymbolTable attr;// Creo un atributo
					if (identifier.getSize() == null) {
						attr = new AttributeSymbolTable(null, fd.getType(), identifier.getId().getId());
					} else {
						attr = new AttributeSymbolTable(null, fd.getType(), identifier.getId().getId(),
								identifier.getSize().getValue());
					}
					// Agrego el atributo al bloque actual en la tabla de
					// simbolos
					symbolTable.setVarBlockSymbolTable(attr);
					// Agrego el atributo a la clase tope en la tabla de
					// simbolos
					symbolTable.insertAttrClassSymbolTable(symbolTable.getLastClass(), attr);
				}
			}
		return null;
	}// fin visit FieldDeclarationList

	@Override
	public GenericType visit(MethodDeclaration methD) {
		BlockSymbolTable block = new BlockSymbolTable();
		// meto un nuevo bloque a la tabla de simbolos
		symbolTable.pushBlockSymbolTable(block);
		// si el metodo tiene argumentos
		if (methD.getArguments() != null) {
			methD.getArguments().accept(this);
		}
		GenericType ret = methD.getBody().accept(this);
		// si es null quiere decir que "Body" es "extern", entonces lo ignoro
		if (ret != null) {
			// si el tipo del metodo no es igual al tipo que está retornando
			if (!methD.getType().getType().equals(ret.getType())) {
				errorList.add("Error de tipo, el tipo de retorno del metodo " + methD.getId().getId() + " es "
						+ methD.getType().getType() + " y el tipo retornado es " + ret.getType());
			}
		}
		// saco el bloque de la tabla de simbolos
		symbolTable.popBlockSymbolTable();
		return methD.getType();
	}// fin visit MethodDeclaration

	@Override
	public GenericType visit(MethodDeclarationList methDecList) {
		// Si la lista de metodos no es vacia
		if (methDecList.getMethodDecList() != null && !methDecList.getMethodDecList().isEmpty()) {
			// Recorro la definicion de metodos
			for (MethodDeclaration m : methDecList.getMethodDecList()) {
				m.accept(this);
				List<Parameter> listParameter = new LinkedList<>();
				if (m.getArguments() != null && m.getArguments().getArgumentsList() != null
						&& !m.getArguments().getArgumentsList().isEmpty()) {
					listParameter = m.getArguments().getArgumentsList();
				}
				// Creo un nuevo metodo con los datos del metodo actual
				MethodSymbolTable methST = new MethodSymbolTable(m.getId().getId(), m.getType(), listParameter);
				// Agrego el metodo en la clase al tope de la pila
				symbolTable.insertMethClassSymbolTable(symbolTable.getLastClass(), methST);
			}
		}
		return null;
	}// fin visit MethodDeclarationList

	@Override
	public GenericType visit(Parameter p) {
		AttributeSymbolTable attr;
		// Chequeo que no haya una variable en el metodo declarada con el mismo
		// nombre que un parametro
		if (symbolTable.getVariableBlockSymbolTable(p.getId().getId()) == null) {
			attr = new AttributeSymbolTable(null, p.getType(), p.getId().getId());
			symbolTable.setVarBlockSymbolTable(attr);
		} else {
			errorList.add("Error, ya existe una parametro en el metodo '" + p.getId().getId() + "'");
		}
		return null;
	}// fin visit Parameter

	@Override
	public GenericType visit(IdentifiersList identifiersList) {
		// La lista de identificadores, de por si solos, no tienen tipo
		return null;
	}// fin visit IdentifierList

}
