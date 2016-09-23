package ast;

import compilers.ASTVisitor;

public class Body extends AST {

	private Boolean isExtern = false;
	private Block block;

	public Body(){}
	
	public Body(Block b){
		this.block = b;
	}
	
	public void setIsExternTrue(){
		this.isExtern = true;
	}
	
	public Boolean isExtern(){
		return this.isExtern;
	}
	
	public Block getBlock(){
		return this.block;
	}
	
	@Override
	public String toString(){
		if (isExtern){
			return "extern ;";
		}
		else{
			return block.toString();
		}
	}

	@Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }
}
