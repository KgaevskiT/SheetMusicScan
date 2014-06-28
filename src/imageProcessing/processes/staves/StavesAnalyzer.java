package imageProcessing.processes.staves;

import imageProcessing.filters.Dilation;
import imageProcessing.filters.Erosion;
import imageProcessing.filters.structElt.StructElt;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class StavesAnalyzer {
	public static BufferedImage completeStaves(BufferedImage image) {
		Erosion erosion = new Erosion(StructElt.LineHorizontal10);
		image = erosion.apply(image);
		Dilation dilation = new Dilation(StructElt.LineHorizontal50);
		image = dilation.apply(image);
		return image;
	}

	/*
	 * Return the staff that contains position. 0 if position is not in any
	 * staff
	 */
	public static Integer getStaffNumber(ArrayList<Staff> staves, int position) {
		Integer staffNb = null;
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

	private BufferedImage stavesImage;

	public StavesAnalyzer(BufferedImage stavesImage) {
		this.stavesImage = stavesImage;
		this.stavesImage = getSubimage(this.stavesImage);
		this.stavesImage = completeStaves(this.stavesImage);
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

			if (y != this.stavesImage.getHeight()) {
				staves.add(new Staff(lines));
			}
		}

		return staves;
	}

	private BufferedImage getSubimage(BufferedImage image) {
		return image.getSubimage(image.getWidth() / 2 - 100, 0, 200,
				image.getHeight());
	}
}
