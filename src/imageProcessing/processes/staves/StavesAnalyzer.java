package imageProcessing.processes.staves;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class StavesAnalyzer {
	/*
	 * Return the staff that contains position. 0 if position is not in any
	 * staff
	 */
	public static int getStaffNumber(ArrayList<Staff> staves, int position) {
		int staffNb = -1;
		for (int i = 0; i < staves.size(); i++) {
			Staff staff = staves.get(i);
			if (position > staff.getLines()[4]
					&& position < staff.getLines()[0]) {
				staffNb = i;
				break;
			}
		}
		return staffNb;
	}

	private final BufferedImage stavesImage;

	public StavesAnalyzer(BufferedImage stavesImage) {
		this.stavesImage = stavesImage;
	}

	public ArrayList<Staff> analyzeStaves() {
		ArrayList<Staff> staves = new ArrayList<Staff>();

		int x = this.stavesImage.getWidth() / 2;

		for (int y = 0; y < this.stavesImage.getHeight(); y++) {
			int lines[] = new int[5];

			// Analyzes one staff (= 5 lines)
			for (int i = 4; i >= 0; i--) {
				int top;
				int bottom;
				while (y < this.stavesImage.getHeight()
						&& this.stavesImage.getRGB(x, y) == Color.black
								.getRGB()) {
					y++;
				}
				top = y;
				while (y < this.stavesImage.getHeight()
						&& this.stavesImage.getRGB(x, y) == Color.white
								.getRGB()) {
					y++;
				}
				bottom = y;
				lines[i] = (top + bottom) / 2;
			}
			staves.add(new Staff(lines));
		}

		return staves;
	}
}
