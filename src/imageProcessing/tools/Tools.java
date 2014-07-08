package imageProcessing.tools;

import imageProcessing.colorMode.VisualMode;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class Tools {
	public static BufferedImage duplicate(BufferedImage image) {
		ColorModel cm = image.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = image.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	public static void eraseObjectParts(BufferedImage parts, int x, int y,
			BufferedImage image) {
		if (image.getRGB(x, y) == VisualMode.OBJECT.getRGB()) {
			parts.setRGB(x, y, VisualMode.BACKGROUND.getRGB());
			image.setRGB(x, y, VisualMode.BACKGROUND.getRGB());
			eraseObjectParts(parts, x, y - 1, image);
			eraseObjectParts(parts, x, y + 1, image);
			eraseObjectParts(parts, x + 1, y, image);
			eraseObjectParts(parts, x - 1, y, image);
		}
	}

	public static void eraseShape(BufferedImage image, int x, int y) {
		objectSetColor(image, x, y, VisualMode.BACKGROUND);
	}

	private static double euclidianDistance(int x1, int x2, int y1, int y2) {
		return Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2);
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
				if (image.getRGB(w, h) == VisualMode.OBJECT.getRGB()) {
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

	public static void merge(BufferedImage base, BufferedImage mask) {
		for (int h = 0; h < base.getHeight() && h < mask.getHeight(); h++) {
			for (int w = 0; w < base.getWidth() && w < base.getWidth(); w++) {
				if (mask.getRGB(w, h) != VisualMode.BACKGROUND.getRGB()) {
					base.setRGB(w, h, mask.getRGB(w, h));
				}
			}
		}
	}

	public static void objectSetColor(BufferedImage image, int x, int y,
			Color color) {
		if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
			if (image.getRGB(x, y) == VisualMode.OBJECT.getRGB()) {
				image.setRGB(x, y, color.getRGB());
				objectSetColor(image, x, y - 1, color);
				objectSetColor(image, x, y + 1, color);
				objectSetColor(image, x + 1, y, color);
				objectSetColor(image, x - 1, y, color);
			}
		}
	}

	public static void objectSetColor(BufferedImage image, int x, int y,
			Color color, int rad) {
		for (int h = y - rad; h < y + rad; h++) {
			for (int w = x - rad; w < x + rad; w++) {
				if (euclidianDistance(x, w, y, h) < rad * rad) {
					if (image.getRGB(w, h) == VisualMode.OBJECT.getRGB()) {
						image.setRGB(w, h, color.getRGB());
					}
				}
			}
		}

	}

	public static void replaceColor(BufferedImage image, Color from, Color to) {
		for (int h = 0; h < image.getHeight(); h++) {
			for (int w = 0; w < image.getWidth(); w++) {
				if (image.getRGB(w, h) == from.getRGB()) {
					image.setRGB(w, h, to.getRGB());
				}
			}
		}
	}
}
