package imageProcessing.processes.staves;

import imageProcessing.colorMode.VisualMode;
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
		Dilation dilation = new Dilation(StructElt.LineHorizontal100);
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

	private final BufferedImage stavesImageFull;
	private BufferedImage stavesImage;

	public StavesAnalyzer(BufferedImage stavesImage) {
		this.stavesImageFull = stavesImage;
		this.stavesImage = getSubimage(stavesImage);
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

		findStavesStart(staves);
		return staves;
	}

	private void findStavesStart(ArrayList<Staff> staves) {
		for (Staff staff : staves) {
			boolean lineFound = false;
			int x = 0;
			int y = staff.getLine(2);

			for (x = 0; !lineFound; x++) {
				for (int i = -5; i <= 5; i++) {
					if (this.stavesImageFull.getRGB(x, y) != VisualMode.BACKGROUND
							.getRGB()) {
						lineFound = true;
					}
				}
			}
			staff.setBegin(x);
		}
	}

	private BufferedImage getSubimage(BufferedImage image) {
		return image.getSubimage(image.getWidth() / 2 - 100, 0, 200,
				image.getHeight());
	}
}
