package compilers.data_structures;

public class Pair<First, Second> {

    private final First f;
    private final Second s;

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
	
}