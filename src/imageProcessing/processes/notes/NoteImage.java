package imageProcessing.processes.notes;

import imageProcessing.processes.ElementImage;
import imageProcessing.processes.staves.StaffPosition;

public class NoteImage extends ElementImage {

	private final StaffPosition staffPosition;

	public NoteImage(int x, int staff, StaffPosition staffPosition, int index) {
		super(x, staff, index);
		this.staffPosition = staffPosition;
	}

	public StaffPosition getStaffPosition() {
		return this.staffPosition;
	}

	@Override
	public String toString() {
		return "[x = " + x + "; staff = " + staff + "; staffPosition = "
				+ staffPosition.toString() + "]";
	}
}
