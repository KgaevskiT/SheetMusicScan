package imageProcessing.processes.staves;

import java.util.ArrayList;

public class Staves {
	private int topSystemDistance = 0;
	private final ArrayList<Staff> staves = new ArrayList<Staff>();

	public void addStaff(int[] lines) {
		if (this.topSystemDistance == 0) {
			this.topSystemDistance = lines[4];
		}
		staves.add(new Staff(lines));
	}

	public ArrayList<Staff> getStaves() {
		return staves;
	}

	public int getTopSystemDistance() {
		return topSystemDistance;
	}
}