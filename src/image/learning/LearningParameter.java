package image.learning;

public class LearningParameter {
	public static LearningParameter MEASURE = new LearningParameter(25, 30, 200);
	// Notes
	public static LearningParameter QUARTER = new LearningParameter(50, 50, 50);
	public static LearningParameter WHOLE = new LearningParameter(50, 50, 50);
	// Rests
	public static LearningParameter LONG = new LearningParameter(0, 75, 75);
	public static LearningParameter BREVE = new LearningParameter(0, 75, 75);
	public static LearningParameter MINIM_SEMIBREVE = new LearningParameter(25,
			75, 75);
	public static LearningParameter CROTCHET = new LearningParameter(50, 150,
			150);
	public static LearningParameter QUAVER = new LearningParameter(50, 100, 100);

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
