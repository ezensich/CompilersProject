package ast;

import java.util.LinkedList;
import java.util.List;

import compilers.ASTVisitor;
import data_structures.Pair;

public class Identifier extends AST {

	private List<Pair<IdName, IntegerLiteral>> listIdentifier;

	public Identifier(Pair<IdName, IntegerLiteral> pair) {
		listIdentifier = new LinkedList<>();
		this.listIdentifier.add(pair);
	}

	public void addIdentifierToList(Pair<IdName, IntegerLiteral> pair) {
		this.listIdentifier.add(pair);
	}

	public List<Pair<IdName, IntegerLiteral>> getIdentifierList() {
		return this.listIdentifier;
	}

	@Override
	public String toString() {
		String result = "";
		if (!listIdentifier.isEmpty()) {
			if ((listIdentifier.get(0)).getSecond() != null) {
				result = (listIdentifier.get(0)).getFirst().toString() + "["
						+ (listIdentifier.get(0)).getSecond().toString() + "]";
			}
			else {
				result = (listIdentifier.get(0)).getFirst().toString();
			}
			for (int i = 1; i < listIdentifier.size(); i++) {
				if ((listIdentifier.get(0)).getSecond() != null) {
					result += "," + (listIdentifier.get(0)).getFirst().toString() + "["
							+ (listIdentifier.get(0)).getSecond().toString() + "]";
				}
				else {
					result = "," + (listIdentifier.get(0)).getFirst().toString();
				}
			}

		}

		return result;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}
