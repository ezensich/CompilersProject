package ast;

import java.util.List;

public abstract class MethodDeclaration extends AST {
	
	
	private Type type;
	private List<ArgumentDeclaration> listArgDec;
	private Block block;
	
	public MethodDeclaration(){
		
	}

}
