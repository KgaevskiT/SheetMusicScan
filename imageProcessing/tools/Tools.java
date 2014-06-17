package imageProcessing.tools;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Tools {
	public static void fill(BufferedImage image, Color color) {
		for (int h = 0; h < image.getHeight(); h++) {
			for (int w = 0; w < image.getWidth(); w++) {
				image.setRGB(w, h, color.getRGB());
			}
		}
	}
}
