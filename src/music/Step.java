package music;

public class Step {
	private final String value;

	public static Step A = new Step("A");
	public static Step B = new Step("B");
	public static Step C = new Step("C");
	public static Step D = new Step("D");
	public static Step E = new Step("E");
	public static Step F = new Step("F");
	public static Step G = new Step("G");

	private Step(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
