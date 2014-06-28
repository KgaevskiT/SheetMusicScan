package imageProcessing.processes.notes;

import imageProcessing.processes.ElementImage;
import imageProcessing.processes.staves.StaffPosition;
import music.Beam;
import music.Type;

public class NoteImage extends ElementImage {

	private final StaffPosition staffPosition;
	private Type type;
	private Beam beam;

	public NoteImage(int x, int y, int staff, int index,
			StaffPosition staffPosition, Type type) {
		super(x, y, staff, index);
		this.staffPosition = staffPosition;
		this.type = type;
		this.beam = null;
	}

	public Beam getBeam() {
		return beam;
	}

	public StaffPosition getStaffPosition() {
		return this.staffPosition;
	}

	public Type getType() {
		return this.type;
	}

	public void setBeam(Beam beam) {
		this.beam = beam;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "[x = " + x + "; staff = " + staff + "; staffPosition = "
				+ staffPosition.toString() + "]";
	}
}
