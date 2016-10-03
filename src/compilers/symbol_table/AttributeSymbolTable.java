package compilers.symbol_table;

import compilers.ast.enumerated_types.GenericType;

/*
 * Attribute definition to create a symbol table.
 */
public class AttributeSymbolTable {

    private Object value[] = new Object[100];
    private GenericType type;
    private String name;
    private Integer size = 0;
    private Integer offset = 0;
    private Boolean isGlobal = false;

    public AttributeSymbolTable(Object value, GenericType type, String name, Integer size) {
        this.type = type;
        this.name = name;
        if (size != null) 
            this.size = size;
        this.value[0] = value;
    }

    public AttributeSymbolTable(Object value, GenericType type, String name) {     
        this.value[0] = value;
        this.type = type;
        this.name = name;
        this.size = 0;
    }

    public Object getValue() {
        return value[0];
    }
    
    public Object getValueInPos(int pos) {
        if (pos >= 0){
            return value[pos];
        }
        return null;
    }

    public void setValueInPos(Object value, int pos) {
        if (pos >= 0) {
            this.value[pos] = value;
        }
    }

    public void setValue(Object value) {
        this.value[0] = value;
    }

    public GenericType getType() {
        return type;
    }

    public void setType(GenericType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public void setIsGlobal(boolean isGlobal) {
        this.isGlobal = isGlobal;
    }    
    


    
    @Override
    public String toString(){
    	String result = "( Tipo="+type.getType()+" Nombre="+name+""
    			+ " Size="+size+" offset="+offset+" isGlonal="+isGlobal
    			+ " value="+value.toString()+")";
    	return result;
    }
}
