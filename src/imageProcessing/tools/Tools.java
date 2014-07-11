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
}
