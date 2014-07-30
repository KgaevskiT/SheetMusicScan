package imageProcessing.tools;

import imageProcessing.colorMode.VisualMode;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ObjectEditor {
	private BufferedImage image;
	private BufferedImage parts;

	public BufferedImage eraseObjectParts(BufferedImage parts, int x, int y,
			BufferedImage image) {
		this.image = image;
		this.parts = parts;
		eraseObjectPartsRec(x, y);
		return parts;
	}

	private void eraseObjectPartsRec(int x, int y) {
		if (image.getRGB(x, y) != VisualMode.BACKGROUND.getRGB()) {
			parts.setRGB(x, y, VisualMode.BACKGROUND.getRGB());
			image.setRGB(x, y, VisualMode.BACKGROUND.getRGB());
			eraseObjectPartsRec(x, y - 1);
			eraseObjectPartsRec(x, y + 1);
			eraseObjectPartsRec(x + 1, y);
			eraseObjectPartsRec(x - 1, y);
		}
	}

	public BufferedImage eraseShape(BufferedImage image, int x, int y) {
		objectSetColor(image, x, y, VisualMode.BACKGROUND);
		return this.image;
	}

	private double euclidianDistance(int x1, int x2, int y1, int y2) {
		return Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2);
	}

	public BufferedImage objectSetColor(BufferedImage image, int x, int y,
			Color color) {

		this.image = image;
		objectSetColorRec(x, y, color);
		return this.image;
	}

	public BufferedImage objectSetColor(BufferedImage image, int x, int y,
			Color color, int radius) {

		for (int h = y - radius; h < y + radius; h++) {
			for (int w = x - radius; w < x + radius; w++) {
				if (euclidianDistance(x, w, y, h) < radius * radius) {
					if (image.getRGB(w, h) == VisualMode.OBJECT.getRGB()) {
						image.setRGB(w, h, color.getRGB());
					}
				}
			}
		}
		return this.image;

		/*
		 * ColorSetter.IMAGE = image; ColorSetter.COLOR = color;
		 * ColorSetter.RADIUS = radius; ColorSetter start = new ColorSetter(x,
		 * y); int cores = Runtime.getRuntime().availableProcessors();
		 * ForkJoinPool pool = new ForkJoinPool(cores); pool.invoke(start);
		 * return ColorSetter.IMAGE;
		 */
	}

	private void objectSetColorRec(int x, int y, Color color) {
		if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
			if (image.getRGB(x, y) == VisualMode.OBJECT.getRGB()) {
				image.setRGB(x, y, color.getRGB());
				objectSetColorRec(x, y - 1, color);
				objectSetColorRec(x, y + 1, color);
				objectSetColorRec(x + 1, y, color);
				objectSetColorRec(x - 1, y, color);
			}
		}
	}

	public void replaceColor(BufferedImage image, Color from, Color to) {
		for (int h = 0; h < image.getHeight(); h++) {
			for (int w = 0; w < image.getWidth(); w++) {
				if (image.getRGB(w, h) == from.getRGB()) {
					image.setRGB(w, h, to.getRGB());
				}
			}
		}
	}
}
