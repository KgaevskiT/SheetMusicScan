package imageProcessing.tools;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Tools {
	public static void eraseShape(BufferedImage image, int x, int y) {
		if (image.getRGB(x, y) == Color.white.getRGB()) {
			image.setRGB(x, y, Color.black.getRGB());
			eraseShape(image, x, y - 1);
			eraseShape(image, x, y + 1);
			eraseShape(image, x + 1, y);
			eraseShape(image, x - 1, y);
		}
	}

	public static void fill(BufferedImage image, Color color) {
		for (int h = 0; h < image.getHeight(); h++) {
			for (int w = 0; w < image.getWidth(); w++) {
				image.setRGB(w, h, color.getRGB());
			}
		}
	}

	public static BufferedImage ignoreSurround(BufferedImage image) {
		int startX = image.getWidth() - 1;
		int startY = image.getHeight() - 1;
		int endX = 0;
		int endY = 0;

		for (int h = 0; h < image.getHeight(); h++) {
			for (int w = 0; w < image.getWidth(); w++) {
				if (image.getRGB(w, h) == Color.white.getRGB()) {
					if (w < startX)
						startX = w;
					if (w > endX)
						endX = w;
					if (h < startY)
						startY = h;
					if (h > endY)
						endY = h;
				}
			}
		}

		return image.getSubimage(startX, startY, endX - startX, endY - startY);
	}
}
