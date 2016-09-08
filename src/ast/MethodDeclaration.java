package ast;

import java.util.List;

public abstract class MethodDeclaration extends DeclarationClass {
	
	
	private Type type;
	private List<ArgumentDeclaration> listArgDec;
	private Block block;
	
	public MethodDeclaration(){
		
	}

}
