package compilers.ast;

import compilers.ASTVisitor;

public class Body extends AST {

	private Boolean isExtern = false;
	private Block block;

	public Body(){
		this.block = null;
	}
	
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
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }
}
