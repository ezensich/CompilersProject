package compilers.data_structures;

public class Pair<First, Second> {

    private First f;
    private Second s;

    public Pair(){
    	this.s = null;
    	this.f = null;
    }
    
    public Pair(First f, Second s) {
        this.f = f;
        this.s = s;
    }

	public First getFirst() {
		return this.f;
	}

	public Second getSecond() {
		return this.s;
	}
	
	public void setFirst(First f) {
		this.f = f;
	}

	public void setSecond(Second s) {
		this.s = s;
	}

	public boolean equals(Pair<First,Second> p2){
		return (this.f==p2.getFirst() && this.s==p2.getSecond());
	}
	
	public Pair<First,Second> clone(){
		Pair<First,Second> clone = new Pair<First,Second>();
		clone.setFirst(this.getFirst());
		clone.setSecond(this.getSecond());
		return clone;
	}
	
	public String toString(){
		return "("+ f.toString() +","+ s.toString() + ")"; 
	}
	
}