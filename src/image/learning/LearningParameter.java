package image.learning;

public class LearningParameter {
	public static LearningParameter QUARTER = new LearningParameter(32, 50, 50);
	public static LearningParameter MEASURE = new LearningParameter(0, 30, 200);

	private final int step;
	private final int width;
	private final int height;

	private LearningParameter(int step, int width, int height) {
		super();
		this.step = step;
		this.width = width;
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public int getStep() {
		return step;
	}

	public int getWidth() {
		return width;
	}

}
