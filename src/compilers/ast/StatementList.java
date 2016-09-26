package compilers.ast;

import java.util.LinkedList;
import java.util.List;

import compilers.ASTVisitor;

public class StatementList extends Statement {

	private List<Statement> listStmt = new LinkedList<>();
	
	public StatementList(Statement stmt){
		this.listStmt.add(stmt);
	}
	
	public void addStatementToList(Statement stmt) {
		listStmt.add(stmt);
	}

	public List<Statement> getStatementList() {
		return listStmt;
	}

	@Override
    public String toString(){
		String result = "";
		if (listStmt != null && !listStmt.isEmpty()){
    		for(Statement stmt : listStmt){
    			result += stmt.toString()+'\n';
    		}
    	}
    	return result;
    }
	
	@Override
    public <T> T accept(ASTVisitor<T> v) {
        return v.visit(this);
    }

}
