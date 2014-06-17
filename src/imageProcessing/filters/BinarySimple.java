package imageProcessing.filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class BinarySimple implements Filter {
	@Override
	public BufferedImage apply(BufferedImage image) {
		for (int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getWidth(); j++) {
				Color color = new Color(image.getRGB(j, i));
				if (color.getRed() + color.getGreen() + color.getBlue() > 381) {
					image.setRGB(j, i, Color.black.getRGB());
				} else {
					image.setRGB(j, i, Color.white.getRGB());
				}
			}
		}
		return image;
	}
}
