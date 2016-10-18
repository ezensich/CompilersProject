package compilers.semcheck;

import java.util.LinkedList;
import java.util.List;

import compilers.ast.*;
import compilers.ast.enumerated_types.*;
import compilers.int_code_gen.LabelExpr;
import compilers.ASTVisitor;
import compilers.symbol_table.*;

/*
 *   Esta clase es la encargada de hacer el chequeo semantico de diferentes reglas provistas por
 * la descripcion del lenguaje. Las reglas que valida son las siguientes:
 *   
 *   Reglas 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 y 17.
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
			if (symbolTable.existClassSymbolTable("main")) {
				// Busco el metodo "main" dentro de la clase "Main"
				MethodSymbolTable meth = symbolTable.getMethodSymbolTable("main", "main");
				if (meth == null) {// Si no existe el metodo "main"
					errorList.add("error. La clase debe contener un metodo main");
				}
				// la lista de parametros de "main" debe ser vacia
				if (!meth.getParameterList().isEmpty()) {
					errorList.add("error. El metodo main no debe contener parametros");
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
		if(stmtList != null && stmtList.getStatementList().isEmpty()){
			return lastType;
		}
		else {
			//saco el primer tipo return para compararlo con el resto
			//todos los tipos de retorno deben ser iguales
			for(Statement s : stmtList.getStatementList()){
				lastType = s.accept(this);
				if(s.isReturnStmt()){
					break;//termino el ciclo porque encontre el primer return
				}
			}
			for (Statement s : stmtList.getStatementList()) {
				GenericType actualType = s.accept(this);
				//si el stmt actual es un return
				if(s.isReturnStmt()){
					//si no coinciden los tipos de los return
					if(!lastType.getType().equals(actualType.getType())){
						errorList.add("Error de tipo de retorno. Las sentencias 'return' no coinciden en sus tipos.");
					}
				}
			}
			return lastType;
		}
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
			if (!loc.getType().getType().equals(typeStmt.getType())) {
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
		stmt.setReturnStmtTrue();//seteo que el stmt es de tipo return
		if (stmt.getExpression() != null) {
			return stmt.getExpression().accept(this);
		} else {
			return new GenericType(Type.VOID.toString());
		}
	}// fin visit ReturnStmt

	@Override
	public GenericType visit(IfStmt stmt) {
		GenericType typeIf = new GenericType(Type.VOID.toString());
		GenericType typeElse = new GenericType(Type.VOID.toString());
		// chequeo que la condicion del if sea booleana
		if (!stmt.getCondition().accept(this).isBool()) {
			errorList.add("Condicion del if no es booleana, linea: " + stmt.getCondition().getLineNumber()
					+ " columna: " + stmt.getCondition().getColumnNumber());
		} else {
			typeIf = stmt.getIfStatement().accept(this);
			if (stmt.getElseStatement() != null) {
				typeElse = stmt.getElseStatement().accept(this);
				if(!typeElse.getType().equals(typeIf.getType())){
					errorList.add("Error de tipo de retorno. Las sentencias 'return' no coinciden en sus tipos.");
				}
			}
		}
		return typeIf;
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
		return methStmt.getMethodCallExpr().accept(this);
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
			//si es extern retorno tipo 'VOID'
			return new GenericType(Type.VOID.toString());
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
		AttributeSymbolTable var = (AttributeSymbolTable) locExpr.getReference();
		// si la variable no existe
		if (var == null) {
			errorList.add("variable '" + locExpr.getId().getId() + "' no definida local ni globalmente, linea: " + locExpr.getLineNumber()
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
		MethodSymbolTable methSymbolTable = (MethodSymbolTable) methExpr.getReference();
		
		if(methSymbolTable != null){
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
		}else{
			// si es null no esta declarado ni en esta clase ni en ninguna otra, hay un error
			errorList.add("Error, el metodo " + methExpr.getIdName().getId() + " no fue declarado en ninguna clase, linea: "
					+ methExpr.getLineNumber());
			return null;
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
					symbolTable.insertAttrClassSymbolTable(symbolTable.getLastClassName(), attr);
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
				errorList.add("Error de tipo, el tipo de retorno definido del metodo " + methD.getId().getId() + " es "
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
				symbolTable.insertMethClassSymbolTable(symbolTable.getLastClassName(), methST);
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

	@Override
	public GenericType visit(IdName idName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericType visit(GenericType genericType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericType visit(LabelExpr labelExpr) {
		// TODO Auto-generated method stub
		return null;
	}

}
