package imageProcessing.filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Substract {
	public BufferedImage apply(BufferedImage image1, BufferedImage image2) {
		BufferedImage result = new BufferedImage(image1.getWidth(),
				image1.getHeight(), BufferedImage.TYPE_INT_RGB);

		for (int h = 0; h < image1.getHeight() && h < image2.getHeight(); h++) {
			for (int w = 0; w < image1.getWidth() && w < image2.getWidth(); w++) {
				if (image1.getRGB(w, h) == Color.white.getRGB()
						&& image2.getRGB(w, h) == Color.black.getRGB()) {
					result.setRGB(w, h, Color.white.getRGB());
				} else {
					result.setRGB(w, h, Color.black.getRGB());
				}
			}
		}

		return result;
	}
}
