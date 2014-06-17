package imageProcessing.filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Expand implements Filter {

	private BufferedImage expanded;
	private final BufferedImage mask;
	private final Color color;

	public Expand(BufferedImage mask) {
		this.mask = mask;
		this.color = Color.white;
	}

	public Expand(BufferedImage mask, Color color) {
		this.mask = mask;
		this.color = color;
	}

	@Override
	public BufferedImage apply(BufferedImage image) {
		BufferedImage result = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_INT_RGB);
		int midH = mask.getHeight() / 2;
		int midW = mask.getWidth() / 2;
		boolean valid = false;

		for (int h = midH; h < image.getHeight() - midH; h++) {
			for (int w = midW; w < image.getWidth() - midW; w++) {

				// If front ground -> stay front ground
				if (image.getRGB(w, h) == Color.white.getRGB()) {
					result.setRGB(w, h, Color.white.getRGB());
				}
				// If background -> Match mask ?
				else {

					// Trying to match the mask.
					valid = false;
					for (int mh = 0; mh < mask.getHeight() && !valid; mh++) {
						for (int mw = 0; mw < mask.getWidth() && !valid; mw++) {

							if (mask.getRGB(mw, mh) == Color.white.getRGB()) {

								// If mask contain front ground.
								if (image.getRGB(w + mw - midW, h + mh - midH) == Color.white
										.getRGB()) {
									valid = true;
								}
							}
						}
					}
					// Mask contain front ground -> become front ground.
					if (valid) {
						result.setRGB(w, h, this.color.getRGB());
					}
					// Mask is full background -> stay background.
					else {
						result.setRGB(w, h, Color.black.getRGB());
					}
				}
			}
		}
		expanded = new Substract().apply(result, image);
		return result;
	}

	public BufferedImage getExpanded() {
		return this.expanded;
	}

}
