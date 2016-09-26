package compilers.symbol_table;

import compilers.ast.Parameter;
import compilers.ast.enumerated_types.GenericType;
import java.util.List;
/*
 * Method definition to create a symbol table.
 */
public class MethodSymbolTable {
	private String name;
    private GenericType returnType;
    private List<Parameter> listParameter;
    private boolean isExtern;

    public MethodSymbolTable(String name, GenericType returnType, List<Parameter> listParameter) {
        this.name = name;
        this.returnType = returnType;
        this.listParameter = listParameter;
         isExtern = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GenericType getReturnType() {
        return returnType;
    }

    public void setReturnType(GenericType returnType) {
        this.returnType = returnType;
    }

    public List<Parameter> getParameterList() {
        return listParameter;
    }

    public void setParameterList(List<Parameter> listParameter) {
        this.listParameter = listParameter;
    }
    public boolean isExtern() {
        return isExtern;
    }

    public void setIsExtern(boolean isExtern) {
        this.isExtern = isExtern;
    }
    
   
}
