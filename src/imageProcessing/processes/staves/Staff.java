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

	public Staff(int line5, int line4, int line3, int line2, int line1) {
		this.lines = new int[] { line1, line2, line3, line4, line5 };
	}

	public Staff(int[] lines) {
		this.lines = lines;
	}

	public int[] getLines() {
		return lines;
	}

	@Override
	public String toString() {
		return lines[4] + " " + lines[3] + " " + lines[2] + " " + lines[1]
				+ " " + lines[0];
	}
}
