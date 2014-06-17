package imageProcessing.processes.notes;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class NoteCenterFinder {
	private final BufferedImage image;

	public NoteCenterFinder(BufferedImage image) {
		this.image = image;
		try {
			ImageIO.write(
					image,
					"png",
					new File(
							"C:/Users/Thomas/Documents/MusicSheetScan/NoteCenterFinder.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void eraseShape(int x, int y) {
		if (image.getRGB(x, y) == Color.white.getRGB()) {
			image.setRGB(x, y, Color.black.getRGB());
			eraseShape(x, y - 1);
			eraseShape(x, y + 1);
			eraseShape(x + 1, y);
			eraseShape(x - 1, y);
		}
	}

	private int findBestX(int x, int y) {
		int max = getNoteHeight(x, y);

		// Search max on the left
		int maxL = max;
		int maxXL = x + 1;
		do {
			max = maxL;
			maxXL = maxXL - 1;
			maxL = getNoteHeight(maxXL - 1, y);
		} while (maxL > max);

		// Search max on the right
		int maxR = max;
		int maxXR = x - 1;
		do {
			max = maxR;
			maxXR = maxXR + 1;
			maxR = getNoteHeight(maxXL + 1, y);
		} while (maxR > max);

		return maxR > maxL ? maxXR : maxXL;
	}

	private int findBestY(int x, int y) {
		int max = getNoteWidth(x, y);

		// TODO maxL = getNoteWidth(x, y + maxXL - 1);

		// Search max on the left
		int maxL = max;
		int maxXL = y + 1;
		do {
			max = maxL;
			maxXL = maxXL - 1;
			maxL = getNoteWidth(x, y + maxXL - 1);
		} while (maxL > max);

		// Search max on the right
		int maxR = max;
		int maxXR = y - 1;
		do {
			max = maxR;
			maxXR = maxXR + 1;
			maxR = getNoteWidth(x, y + maxXL + 1);
		} while (maxR > max);

		return maxR > maxL ? maxXR : maxXL;
	}

	public Point findNoteCenter(int x, int y) {

		// Initializing optimal center point
		int h;
		for (h = y; this.image.getRGB(x, h) == Color.white.getRGB(); h++)
			;
		y = (h + y) / 2;

		Point center;

		// TODO Currently note computing optimal point (maybe not useful)
		// computing real optimal point
		/*
		 * Point center = new Point(findBestX(x, y), findBestY(x, y));
		 */
		center = new Point(x, y);
		eraseShape(center.x, center.y);
		return center;
	}

	private int getNoteHeight(int x, int y) {
		int height = 1;

		for (int h = y; image.getRGB(x, h) == Color.white.getRGB(); h++) {
			height++;
		}
		for (int h = y; image.getRGB(x, h) == Color.white.getRGB(); h--) {
			height++;
		}

		return height;
	}

	private int getNoteWidth(int x, int y) {
		int width = 1;

		for (int w = x; image.getRGB(w, y) == Color.white.getRGB(); w++) {
			width++;
		}
		for (int w = x; image.getRGB(w, y) == Color.white.getRGB(); w--) {
			width++;
		}

		return width;
	}
}
