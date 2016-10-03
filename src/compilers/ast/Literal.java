package compilers.ast;

import compilers.ast.enumerated_types.GenericType;
import compilers.ast.enumerated_types.Type;

public abstract class Literal extends Expression{

	
	public abstract GenericType getType();
	
}
