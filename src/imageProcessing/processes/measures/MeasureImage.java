package imageProcessing.processes.measures;

import imageProcessing.processes.ElementImage;

public class MeasureImage extends ElementImage {
	private boolean newSystem;

	public MeasureImage(int x, int y, int staff, int index, boolean newSystem) {
		super(x, y, staff, index);
		this.newSystem = newSystem;
	}

	public boolean isNewSystem() {
		return newSystem;
	}

	public void setNewSystem(boolean newSystem) {
		this.newSystem = newSystem;
	}

	@Override
	public String toString() {
		return "[x = " + x + "; staff = " + staff + "; newSystem = "
				+ newSystem + "]";
	}
}
