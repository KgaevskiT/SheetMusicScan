package imageProcessing.filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Noise implements Filter {
	@Override
	public BufferedImage apply(BufferedImage image) {
		for (int h = 1; h < image.getHeight() - 1; h++) {
			for (int w = 1; w < image.getWidth() - 1; w++) {
				if (image.getRGB(w, h) == Color.white.getRGB()) {
					if (image.getRGB(w, h - 1) == Color.black.getRGB()
							&& image.getRGB(w - 1, h) == Color.black.getRGB()
							&& image.getRGB(w + 1, h) == Color.black.getRGB()
							&& image.getRGB(w, h + 1) == Color.black.getRGB()) {
						image.setRGB(w, h, Color.black.getRGB());
					}
				}
			}
		}

		return image;
	}
}
