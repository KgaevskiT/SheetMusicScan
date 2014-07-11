package imageProcessing.processes.staves;

public class Staff {
	/*
	 * 4______________________________________________________________________
	 * 3______________________________________________________________________
	 * 2______________________________________________________________________
	 * 1______________________________________________________________________
	 * 0______________________________________________________________________
	 */
	private final int lines[];

	private int begin;

	public Staff(int line5, int line4, int line3, int line2, int line1) {
		this.lines = new int[] { line1, line2, line3, line4, line5 };
	}

	public Staff(int[] lines) {
		this.lines = lines;
	}

	public int getBegin() {
		return this.begin;
	}

	public int getLine(int line) {
		return this.lines[line];
	}

	public int[] getLines() {
		return lines;
	}

	public void setBegin(int x) {
		this.begin = x;
	}

	@Override
	public String toString() {
		return lines[4] + " (" + (lines[3] - lines[4]) + ")"
				+ System.getProperty("line.separator") + lines[3] + " ("
				+ (lines[2] - lines[3]) + ")"
				+ System.getProperty("line.separator") + lines[2] + " ("
				+ (lines[1] - lines[2]) + ")"
				+ System.getProperty("line.separator") + lines[1] + " ("
				+ (lines[0] - lines[1]) + ")"
				+ System.getProperty("line.separator") + lines[0];
	}
}
