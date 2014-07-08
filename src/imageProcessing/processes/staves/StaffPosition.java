package imageProcessing.processes.staves;

/*
 * Represents the position on a staff
 * 
 * etc...
 * 		6
 * 5
 * ---- 5
 * 4
 * ---- 4
 * 3
 * ---- 3
 * 2
 * ---- 2
 * 1
 * ---- 1
 * 0
 * 		0
 * -1
 *	etc...
 *
 */

public class StaffPosition {
	public static StaffPosition getStaffPosition(Staff staff, int y, int line) {
		int staffPosition;
		boolean isOnLine;

		int gap = (staff.getLines()[0] - staff.getLines()[4]) / 4;
		int position = staff.getLines()[line];

		// while note is above current line
		while (position > y) {
			position -= gap;
			line++;
		}
		// while note is under current line - 1
		while (position + gap < y) {
			position += gap;
			line--;
		}

		// position is now the line above note

		// On current line
		if (y - position < gap / 4) {
			staffPosition = line + 1;
			isOnLine = true;
		}
		// Between current line and the line below
		else if (y - position < 3 * gap / 4) {
			staffPosition = line;
			isOnLine = false;
		}
		// On the line below
		else {
			staffPosition = line;
			isOnLine = true;
		}

		return new StaffPosition(staffPosition, isOnLine);
	}

	public int position;

	public boolean isOnLine;

	public StaffPosition(int position, boolean isOnLine) {
		super();
		this.position = position;
		this.isOnLine = isOnLine;
	}

	public boolean equals(StaffPosition o) {
		return (this.position == o.getPosition() && this.isOnLine == o
				.isOnLine());
	}

	public int getPosition() {
		return this.position;
	}

	public boolean isOnLine() {
		return this.isOnLine;
	}

	@Override
	public String toString() {
		return (isOnLine ? "Line " : "Space ") + position;
	}

}
