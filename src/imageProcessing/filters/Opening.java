package imageProcessing.filters;

import imageProcessing.filters.structElt.StructElt;

import java.awt.image.BufferedImage;

public class Opening implements Filter {
	private final StructElt structElt;
	private BufferedImage erased = null;

	public Opening(StructElt structElt) {
		this.structElt = structElt;
	}

	@Override
	public BufferedImage apply(BufferedImage image) {
		Erosion erode = new Erosion(this.structElt);
		image = erode.apply(image);
		BufferedImage eroded = erode.getEroded();
		Dilation expand = new Dilation(this.structElt);
		image = expand.apply(image);
		BufferedImage expanded = expand.getExpanded();
		this.erased = new Substract().apply(eroded, expanded);
		return image;
	}

	public BufferedImage getErased() {
		return this.erased;
	}
}
