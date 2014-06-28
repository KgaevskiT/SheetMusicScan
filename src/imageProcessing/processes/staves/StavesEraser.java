package imageProcessing.processes.staves;

import imageProcessing.filters.Opening;
import imageProcessing.filters.structElt.StructElt;

import java.awt.image.BufferedImage;

public class StavesEraser {
	private BufferedImage staffs = null;

	public BufferedImage EraseStaffs(BufferedImage image) {
		Opening opening = new Opening(StructElt.STAFF);
		image = opening.apply(image);

		this.staffs = opening.getErased();

		return image;
	}

	public BufferedImage getStaffs() {
		return this.staffs;
	}
}
