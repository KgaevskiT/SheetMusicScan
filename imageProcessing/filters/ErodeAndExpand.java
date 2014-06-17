package imageProcessing.filters;

import java.awt.image.BufferedImage;

public class ErodeAndExpand implements Filter {
	private final BufferedImage mask;
	private BufferedImage erased = null;

	public ErodeAndExpand(BufferedImage mask) {
		this.mask = mask;
	}

	@Override
	public BufferedImage apply(BufferedImage image) {
		Erode erode = new Erode(this.mask);
		image = erode.apply(image);
		BufferedImage eroded = erode.getEroded();
		Expand expand = new Expand(this.mask);
		image = expand.apply(image);
		BufferedImage expanded = expand.getExpanded();
		this.erased = new Substract().apply(eroded, expanded);
		return image;
	}

	public BufferedImage getErased() {
		return this.erased;
	}
}
