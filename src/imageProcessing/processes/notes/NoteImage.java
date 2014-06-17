package imageProcessing.processes.notes;

import imageProcessing.processes.staves.StaffPosition;

public class NoteImage {
	private final int x;
	private final int staff;
	private final StaffPosition staffPosition;
	private final int index;

	public NoteImage(int x, int staff, StaffPosition staffPosition, int index) {
		this.x = x;
		this.staff = staff;
		this.staffPosition = staffPosition;
		this.index = index;
	}

	public int getIndex() {
		return this.index;
	}

	public int getStaff() {
		return this.staff;
	}

	public StaffPosition getStaffPosition() {
		return this.staffPosition;
	}

	public int getX() {
		return this.x;
	}

	@Override
	public String toString() {
		return "(" + staff + ", " + x + ")->(" + staffPosition.position + ", "
				+ (staffPosition.isOnLine ? "Line" : "Space") + ")";
	}
}
