package compilers.symbol_table;

import java.util.LinkedList;
import java.util.List;

/*
 * Block definition to create a symbol table.
 */
public class BlockSymbolTable {

    private List<AttributeSymbolTable> listAttr;
    private List<String> errorList;

    public BlockSymbolTable() {
    	this.errorList = new LinkedList<>();
        this.listAttr = new LinkedList<>();
    }

    public List<String> getErrorList(){
    	return this.errorList;
    }
    public BlockSymbolTable(List<AttributeSymbolTable> listAttr) {
        this.listAttr = listAttr;
    }

    public List<AttributeSymbolTable> getAttrList() {
        return this.listAttr;
    }

    public void setAttrList(List<AttributeSymbolTable> listAttr) {
        this.listAttr = listAttr;
    }
    
    public AttributeSymbolTable getAttributeSymbolTable(String id){
        int i = 0;
        while (i < listAttr.size()) {
            if (listAttr.get(i).getName().equals(id)) {
                return listAttr.get(i);
            }
            i++;
        }
        return null;
    }

    public void setAttributeSymbolTable(AttributeSymbolTable attr) {
        if (existAttributeSymbolTable(attr)) {
             errorList.add("error, ya existe la variable '" + attr.getName() + "'");
             
        }
        this.listAttr.add(attr);
    }

    public boolean existAttributeSymbolTable(AttributeSymbolTable attr) {
        boolean exist = false;
        int i = 0;
        while (!exist && i < listAttr.size()) {
            if (listAttr.get(i).getName() == attr.getName()) {
                exist = true;
            }
            i++;
        }
        return exist;
    }
    
    @Override
    public String toString(){
    	String result = "";
    	for(AttributeSymbolTable attr : listAttr){
    		result += attr.toString()+'\n';
    	}
    	return result;
    }
}
