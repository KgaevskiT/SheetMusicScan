package music;

public class Clef {
	private final int number;
	private final Step sign;
	private final int line;

	public Clef(int number, Step sign, int line) {
		super();
		this.number = number;
		this.sign = sign;
		this.line = line;
	}

	public int getLine() {
		return line;
	}

	public int getNumber() {
		return number;
	}

	public Step getSign() {
		return sign;
	}
}
