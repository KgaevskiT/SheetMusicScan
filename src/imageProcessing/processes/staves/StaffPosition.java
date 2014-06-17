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
	public int position;
	public boolean isOnLine;

	public StaffPosition(int position, boolean isOnLine) {
		super();
		this.position = position;
		this.isOnLine = isOnLine;
	}

}
