package imageProcessing.filters;

import imageProcessing.filters.structElt.StructElt;

import java.awt.image.BufferedImage;

public class Closing implements Filter {
	private final StructElt structElt;
	private BufferedImage added = null;

	public Closing(StructElt structElt) {
		this.structElt = structElt;
	}

	@Override
	public BufferedImage apply(BufferedImage image) {
		Dilation expand = new Dilation(this.structElt);
		image = expand.apply(image);
		BufferedImage expanded = expand.getExpanded();
		Erosion erode = new Erosion(this.structElt);
		image = erode.apply(image);
		BufferedImage eroded = erode.getEroded();
		this.added = new Substract().apply(expanded, eroded);
		return image;
	}

	public BufferedImage getAdded() {
		return this.added;
	}
}
