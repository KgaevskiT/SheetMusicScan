package imageProcessing.filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Erode implements Filter {

	private BufferedImage eroded = null;
	private final BufferedImage mask;
	private final Color color;

	public Erode(BufferedImage mask) {
		this.mask = mask;
		this.color = Color.black;
	}

	public Erode(BufferedImage mask, Color color) {
		this.mask = mask;
		this.color = color;
	}

	@Override
	public BufferedImage apply(BufferedImage image) {
		BufferedImage result = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_INT_RGB);
		int midH = mask.getHeight() / 2;
		int midW = mask.getWidth() / 2;
		boolean valid = true;

		for (int h = midH; h < image.getHeight() - midH; h++) {
			for (int w = midW; w < image.getWidth() - midW; w++) {

				// If background -> stay background
				if (image.getRGB(w, h) == Color.black.getRGB()) {
					result.setRGB(w, h, Color.black.getRGB());
				}
				// If front ground -> match mask ?
				else {

					// Trying to match the mask.
					valid = true;
					for (int mh = 0; mh < mask.getHeight() && valid; mh++) {
						for (int mw = 0; mw < mask.getWidth() && valid; mw++) {

							if (mask.getRGB(mw, mh) == Color.white.getRGB()) {

								// If in mask and in image background.
								if (image.getRGB(w + mw - midW, h + mh - midH) == Color.black
										.getRGB()) {
									valid = false;
								}
							}
						}
					}
					// Mask matched -> stay in front ground
					if (valid) {
						result.setRGB(w, h, Color.white.getRGB());
					}
					// Mask did not matched -> become background
					else {
						result.setRGB(w, h, this.color.getRGB());
					}
				}
			}
		}
		eroded = new Substract().apply(image, result);
		return result;
	}

	public BufferedImage getEroded() {
		return this.eroded;
	}
}
