package imageProcessing.filters;

import imageProcessing.filters.structElt.StructElt;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class DilationSequential implements Filter {
	private final StructElt structElt;
	private final Color color;

	public DilationSequential(StructElt structElt, Color color) {
		this.structElt = structElt;
		this.color = color;
	}

	@Override
	public BufferedImage apply(BufferedImage image) {
		BufferedImage result = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_INT_RGB);
		BufferedImage mask = this.structElt.getImage();

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
		return result;
	}
}
