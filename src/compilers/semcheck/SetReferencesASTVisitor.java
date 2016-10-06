package compilers.semcheck;

import java.util.List;
import compilers.ASTVisitor;
import compilers.ast.*;
import compilers.int_code_gen.LabelExpr;
import compilers.symbol_table.*;

/*
 * 
 *  Esta clase es la encargada de hacer el seteo de referencias internas 
 * externas a metodos y atributos tanto locales como globales de una clase
 *
 */
public class SetReferencesASTVisitor implements ASTVisitor<Object> {

	private SymbolTable symbolTable;

	public SetReferencesASTVisitor() {
		symbolTable = new SymbolTable();
	}

	@Override
	public Object visit(Program prog) {
		if (prog.getListDeclarationClass() != null) {
			for (DeclarationClass dc : prog.getListDeclarationClass()) {
				dc.accept(this);
			}
		}
		return null;
	}

	@Override
	public Object visit(DeclarationClass decClass) {
		ClassSymbolTable c = new ClassSymbolTable();

		decClass.setReference(c);
		symbolTable.pushClassSymbolTable(decClass.getIdName().getId(), c);
		symbolTable.pushBlockSymbolTable(new BlockSymbolTable());
		if (decClass.getFieldDecList() != null) {
			decClass.getFieldDecList().accept(this);
		}
		if (decClass.getMethodDecList() != null) {
			decClass.getMethodDecList().accept(this);
		}
		symbolTable.popBlockSymbolTable();
		return null;
	}

	@Override
	public Object visit(StatementList stmtList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(AssignStmt stmt) {
		if (stmt.getExpression() != null) {
			stmt.getExpression().accept(this);
		}
		stmt.getLocation().accept(this);
		return null;
	}

	@Override
	public Object visit(ReturnStmt stmt) {
		if (stmt.getExpression() != null) {
			stmt.getExpression().accept(this);
		}
		return null;
	}

	@Override
	public Object visit(IfStmt stmt) {
		stmt.getCondition().accept(this);
		if (stmt.getIfStatement() != null) {
			stmt.getIfStatement().accept(this);
		}
		if (stmt.getElseStatement() != null) {
			stmt.getElseStatement().accept(this);
		}
		return null;
	}

	@Override
	public Object visit(WhileStmt stmt) {
		stmt.getCondition().accept(this);
		if (stmt.getStatementCondition() != null) {
			stmt.getStatementCondition().accept(this);
		}
		return null;
	}

	@Override
	public Object visit(ForStmt stmt) {
		stmt.getInit().accept(this);
		stmt.getCota().accept(this);
		if (stmt.getForStatement() != null) {
			stmt.getForStatement().accept(this);
		}
		return null;
	}

	@Override
	public Object visit(BreakStmt stmt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ContinueStmt stmt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(SemicolonStmt stmt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(MethodCallStmt methStmt) {
		methStmt.getMethodCallExpr().accept(this);
		return null;
	}

	@Override
	public Object visit(ArgumentDeclaration argDec) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Body body) {
		if (body.getBlock() != null) {
			body.getBlock().accept(this);
		}
		return null;
	}

	@Override
	public Object visit(Block block) {
		symbolTable.pushBlockSymbolTable(new BlockSymbolTable());
		if (block.getFieldDecList() != null && block.getFieldDecList().getFieldDecList() != null) {
			for (FieldDeclaration fd : block.getFieldDecList().getFieldDecList()) {
				if (fd.getIdentifiersList() != null && fd.getIdentifiersList().getListIdentifier() != null) {
					for (Identifier id : fd.getIdentifiersList().getListIdentifier()) {
						// creo un atributo
						AttributeSymbolTable attr;
						if (symbolTable.getAttributeSymbolTableSameBlock(id.getId().getId()) == null) {
							if (id.getSize() == null) {
								attr = new AttributeSymbolTable(null, fd.getType(), id.getId().getId());
							} else {
								attr = new AttributeSymbolTable(null, fd.getType(), id.getId().getId(),
										id.getSize().getValue());
							}
							symbolTable.setVarBlockSymbolTable(attr);
							id.setReference(attr);

						}
					}
				}
			}
		}
		if (block.getStmtList() != null && block.getStmtList().getStatementList() != null) {
			for (Statement s : block.getStmtList().getStatementList()) {
				s.accept(this);
			}
		}
		symbolTable.popBlockSymbolTable();
		return null;
	}

	@Override
	public Object visit(ExpressionList exprList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(LocationExpr locExpr) {
		AttributeSymbolTable var = symbolTable.getAttributeSymbolTable(locExpr.getId().getId());
		// si no lo encontre en la tabla de simbolos por su nombre,
		// debo buscar el atributo como global en otras clases
		if (var == null) {
			// saco el nombre del atributo que es externo a la clase
			// se encuentra en la primer posicion de la lista
			// las otras posiciones son para los atributos id de clase que lo
			// invocan
			List<String> listAttrName = locExpr.getId().getIdList();
			String attrName = listAttrName.get(0);
			// saco el ultimo identificador que deberia ser del tipo de la clase
			// donde esta
			// definido el metodo que busco
			int lastIndex = (listAttrName.size() - 1);
			var = symbolTable.getAttributeSymbolTable(listAttrName.get(lastIndex));
		}
		if (locExpr.getExpr() != null) {
			locExpr.getExpr().accept(this);
		}
		locExpr.setReference(var);
		return null;
	}

	@Override
	public Object visit(BinOpExpr expr) {
		expr.getLeftExpr().accept(this);
		expr.getRightExpr().accept(this);
		return null;
	}

	@Override
	public Object visit(UnaryOpExpr unaryExpr) {
		unaryExpr.getExpression().accept(this);
		return null;
	}

	@Override
	public Object visit(MethodCallExpr methExpr) {
		MethodSymbolTable methST = symbolTable.getMethodSymbolTable(symbolTable.getLastClassName(),
				methExpr.getIdName().getId());
		// si es null no esta en la clase actual debo buscarlo en otra clase
		if (methST == null) {
			// saco el nombre del metodo que es externo a la clase
			// se encuentra en la primer posicion de la lista
			// las otras posiciones son para los atributos id de clase que lo
			// invocan
			List<String> listMethodName = methExpr.getIdName().getIdList();
			String methodName = listMethodName.get(0);
			// saco el ultimo identificador que deberia ser del tipo de la clase
			// donde esta
			// definido el metodo que busco
			int lastIndex = (listMethodName.size() - 1);
			AttributeSymbolTable attr = symbolTable.getAttributeSymbolTable(listMethodName.get(lastIndex));
			// si puedo realizar la busqueda del metodo, sino lo referencio a
			// null
			if (attr != null && methodName != null) {
				methST = symbolTable.getMethodSymbolTable(attr.getType().getType(), methodName);
			} else {
				methST = null;
			}
		}
		methExpr.setReference(methST);
		if (methExpr.getExpressionList() != null && methExpr.getExpressionList().getExpressionList() != null) {
			for (Expression exp : methExpr.getExpressionList().getExpressionList()) {
				exp.accept(this);
			}
		}
		return null;
	}

	@Override
	public Object visit(IntegerLiteral lit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(BoolLiteral lit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(FloatLiteral lit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Identifier id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(IdentifiersList identifiersList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(FieldDeclaration fd) {
		for (Identifier ld : fd.getIdentifiersList().getListIdentifier()) {
			// creo un atributo
			AttributeSymbolTable attr;
			if (ld.getSize() == null) {
				attr = new AttributeSymbolTable(null, fd.getType(), ld.getId().getId());
				attr.setIsGlobal(true);
			} else {
				attr = new AttributeSymbolTable(null, fd.getType(), ld.getId().getId(), ld.getSize().getValue());
				attr.setIsGlobal(true);
			}
			ld.setReference(attr);
			symbolTable.setVarBlockSymbolTable(attr);
			symbolTable.insertAttrClassSymbolTable(symbolTable.getLastClassName(), attr);
		}
		return null;
	}

	@Override
	public Object visit(FieldDeclarationList fdl) {
		if (fdl.getFieldDecList() != null) {
			for (FieldDeclaration fd : fdl.getFieldDecList()) {
				fd.accept(this);
			}
		}
		return null;
	}

	@Override
	public Object visit(MethodDeclaration methD) {
		BlockSymbolTable block = new BlockSymbolTable();
		symbolTable.pushBlockSymbolTable(block);
		// si el metodo tiene parametros
		if (methD.getArguments() != null && methD.getArguments().getArgumentsList() != null) {
			for (Parameter p : methD.getArguments().getArgumentsList()) {
				p.accept(this);
			}
		}
		methD.getBody().accept(this);
		symbolTable.popBlockSymbolTable();
		return null;
	}

	@Override
	public Object visit(MethodDeclarationList methDecList) {
		if (methDecList.getMethodDecList() != null) {
			for (MethodDeclaration m : methDecList.getMethodDecList()) {
				// si el metodo tiene parametros
				MethodSymbolTable methST;
				if (m.getArguments() != null && m.getArguments().getArgumentsList() != null) {
					methST = new MethodSymbolTable(m.getId().getId(), m.getType(), m.getArguments().getArgumentsList());
				} else {
					// si no tiene parametros
					methST = new MethodSymbolTable(m.getId().getId(), m.getType(), null);
				}
				// si el bloque del metodo es null, quiere decir que esta
				// definido externamete
				if (m.getBody().getBlock() == null) {
					methST.setIsExtern(true);
				}
				m.setReference(methST);
				symbolTable.insertMethClassSymbolTable(symbolTable.getLastClassName(), methST);
				m.accept(this);
			}
		}
		return null;
	}

	@Override
	public Object visit(Parameter parameter) {
		AttributeSymbolTable attr = new AttributeSymbolTable(null, parameter.getType(), parameter.getId().getId());
		symbolTable.setVarBlockSymbolTable(attr);
		parameter.setReference(attr);
		return null;
	}

	@Override
	public Object visit(IdName idName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(GenericType genericType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(LabelExpr labelExpr) {
		// TODO Auto-generated method stub
		return null;
	}

}
