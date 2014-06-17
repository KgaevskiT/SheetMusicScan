package imageProcessing.processes.notes;

import imageProcessing.processes.staves.StaffPosition;

public class NoteImage {
	private final int x;
	private final int staff;
	private final StaffPosition position;
	private final int index;

	public NoteImage(int x, int staff, StaffPosition position, int index) {
		this.x = x;
		this.staff = staff;
		this.position = position;
		this.index = index;
	}

	public int getIndex() {
		return this.index;
	}

	public int getStaff() {
		return this.staff;
	}

	public int getX() {
		return this.x;
	}

	@Override
	public String toString() {
		return "(" + staff + ", " + x + ")->(" + position.position + ", "
				+ (position.isOnLine ? "Line" : "Space") + ")";
	}
}
