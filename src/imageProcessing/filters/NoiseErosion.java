package imageProcessing.filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class NoiseErosion implements Filter {
	@Override
	public BufferedImage apply(BufferedImage image) {
		BufferedImage result = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_INT_RGB);

		for (int h = 1; h < image.getHeight() - 1; h++) {
			for (int w = 1; w < image.getWidth() - 1; w++) {
				if (image.getRGB(w, h) == Color.white.getRGB()) {
					if (image.getRGB(w, h - 1) == Color.black.getRGB()
							|| image.getRGB(w - 1, h) == Color.black.getRGB()
							|| image.getRGB(w + 1, h) == Color.black.getRGB()
							|| image.getRGB(w, h + 1) == Color.black.getRGB()) {
						result.setRGB(w, h, Color.black.getRGB());
					} else {
						result.setRGB(w, h, Color.white.getRGB());
					}
				} else {
					result.setRGB(w, h, Color.black.getRGB());
				}
			}
		}
		return result;
	}
}
