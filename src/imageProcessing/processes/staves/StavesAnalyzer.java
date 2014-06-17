package imageProcessing.processes.staves;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class StavesAnalyzer {
	private final BufferedImage stavesImage;

	public StavesAnalyzer(BufferedImage stavesImage) {
		this.stavesImage = stavesImage;
	}

	public Staves analyzeStaves() {
		Staves staves = new Staves();

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
			staves.addStaff(lines);
		}

		return staves;
	}
}
