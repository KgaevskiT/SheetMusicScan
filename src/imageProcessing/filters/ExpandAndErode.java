package imageProcessing.filters;

import java.awt.image.BufferedImage;

public class ExpandAndErode implements Filter {
	private final BufferedImage mask;
	private BufferedImage added = null;

	public ExpandAndErode(BufferedImage mask) {
		this.mask = mask;
	}

	@Override
	public BufferedImage apply(BufferedImage image) {
		Expand expand = new Expand(this.mask);
		image = expand.apply(image);
		BufferedImage expanded = expand.getExpanded();
		Erode erode = new Erode(this.mask);
		image = erode.apply(image);
		BufferedImage eroded = erode.getEroded();
		this.added = new Substract().apply(expanded, eroded);
		return image;
	}

	public BufferedImage getAdded() {
		return this.added;
	}
}
