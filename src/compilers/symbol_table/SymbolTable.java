package compilers.symbol_table;


import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class SymbolTable {
	private Map<String, ClassSymbolTable> mapClass;
    private LinkedList<BlockSymbolTable> stackBlock;
    private String lastClass;
    private List<String> errorList;
    

    public SymbolTable() {
    	errorList = new LinkedList<>();
        mapClass = new HashMap<>();
        stackBlock = new LinkedList<BlockSymbolTable>();
        lastClass = "";
    }

    public List<String> getErrorList(){
    	this.errorList = new LinkedList<>();
    	for(ClassSymbolTable c : mapClass.values()){
    		this.errorList.addAll(c.getErrorList());
    	}
    	for(BlockSymbolTable b : stackBlock){
    		this.errorList.addAll(b.getErrorList());
    	}
    	return this.errorList;
    }
    
    public void pushClassSymbolTable(String name, ClassSymbolTable classSymbolTable) {
        if (mapClass.containsKey(name)) {
            errorList.add("ya existe la ClassSymbolTable '" + name + "'");
        }
        mapClass.put(name, classSymbolTable);
        lastClass = name;
    }

    public boolean existClassSymbolTable(String name) {
        return mapClass.containsKey(name);
    }

    public Map<String, ClassSymbolTable> getMapClass() {
        return mapClass;
    }

    /**
     * Inserta un AttributeSymbolTable a una ClassSymbolTable
     *
     * @param atr
     */
    public void insertAttrClassSymbolTable(String nameClassSymbolTable, AttributeSymbolTable attr) {
        if (existClassSymbolTable(nameClassSymbolTable)) {
            ClassSymbolTable c = mapClass.get(nameClassSymbolTable);
            c.setAttributeSymbolTable(attr);
            mapClass.replace(nameClassSymbolTable, c);
        } else {
            errorList.add("error, no existe la ClassSymbolTable '" + nameClassSymbolTable + "'");
             
        }
    }

    /**
     * Inserta un AttributeSymbolTable a una ClassSymbolTable
     *
     * @param atr
     */
    public void insertMethClassSymbolTable(String nameClassSymbolTable, MethodSymbolTable meth) {
        if (existClassSymbolTable(nameClassSymbolTable)) {
            ClassSymbolTable c = mapClass.get(nameClassSymbolTable);
            c.setMethodSymbolTable(meth);
            mapClass.replace(nameClassSymbolTable, c);
        } else {
            errorList.add("error, no existe la ClassSymbolTable '" + nameClassSymbolTable + "'");
             
        }
    }

    public AttributeSymbolTable getAttributeSymbolTable(String id) {
        for (int i = stackBlock.size()-1; i >=0; i--) {
            AttributeSymbolTable attr = stackBlock.get(i).getAttributeSymbolTable(id);
            if (attr != null) {
                return attr;
            }
        }
        return null;
    }

    public AttributeSymbolTable getAttributeSymbolTableSameBlock(String id) {
        return stackBlock.getLast().getAttributeSymbolTable(id);
    }

    public MethodSymbolTable getMethodSymbolTable(String nameClassSymbolTable, String nameMethodSymbolTable) {
        ClassSymbolTable c = mapClass.get(nameClassSymbolTable);
        return c.getMethodSymbolTable(nameMethodSymbolTable);
    }
    
     public MethodSymbolTable getMethodSymbolTableHard(String nameMethodSymbolTable) {
        Iterator<Entry<String, ClassSymbolTable>> it = mapClass.entrySet().iterator();
        while (it.hasNext()){
            ClassSymbolTable c = (ClassSymbolTable) ((Entry<String, ClassSymbolTable>) it.next()).getValue();
            MethodSymbolTable m = c.getMethodSymbolTable(nameMethodSymbolTable);
            if (m != null){
                return m;
            }
        }
        return null;
    }


    public void pushBlockSymbolTable(BlockSymbolTable ambiente) {
        stackBlock.add(ambiente);
    }

    public BlockSymbolTable getBlockSymbolTable() {
        return stackBlock.getLast();
    }

    public void popBlockSymbolTable() {
        stackBlock.removeLast();
    }

    public boolean isEmptyBlockSymbolTable() {
        return stackBlock.size() == 0;
    }

    public String getLastClassName() {
        return lastClass;
    }
    
    public ClassSymbolTable getLastClass(){
    	return mapClass.get(lastClass);
    } 

    public AttributeSymbolTable getVariableBlockSymbolTable(String id) {
        for (int i = stackBlock.size()-1; i>0; i--) {//arranco de de 1 para evitar rev
            AttributeSymbolTable attr = stackBlock.get(i).getAttributeSymbolTable(id);
            if (attr != null) {
                return attr;
            }
        }
        return null;
    }

    /**
     * agrega una variable al BlockSymbolTable corriente
     *
     * @param id
     * @return
     */
    public AttributeSymbolTable setVarBlockSymbolTable(AttributeSymbolTable attr) {
        BlockSymbolTable b = stackBlock.getLast();
        b.setAttributeSymbolTable(attr);
        stackBlock.set(stackBlock.size() - 1, b);
        return null;
    }

    /**
     * agrega una variable al BlockSymbolTable corriente
     *
     * @param id
     * @return
     */
    public AttributeSymbolTable setVarBlockSymbolTable(List<AttributeSymbolTable> attr) {
        BlockSymbolTable b = stackBlock.getLast();
        b.setAttrList(attr);
        stackBlock.set(stackBlock.size() - 1, b);
        return null;
    }
}
