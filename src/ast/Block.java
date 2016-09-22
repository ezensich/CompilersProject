package ast;

import java.util.LinkedList;
import java.util.List;

public class Block {
	
	private StatementList listStatement;
	private FieldDeclarationList listFieldDeclarations;
	
	
	public Block(StatementList stmtList,FieldDeclarationList fieldList){
		this.listStatement = stmtList;
		this.listFieldDeclarations = fieldList;
	}
}
