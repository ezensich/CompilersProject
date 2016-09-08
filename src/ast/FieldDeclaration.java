package ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class FieldDeclaration extends DeclarationClass {

	
	private List<Map<Type,Identifier>> listTypeAndId;
	
	public FieldDeclaration(){
		
	}
}
