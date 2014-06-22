package imageProcessing.processes.staves;

import imageProcessing.filters.Opening;
import imageProcessing.filters.Closing;
import imageProcessing.filters.structElt.StructElt;

import java.awt.image.BufferedImage;


public class StavesEraser {
	private BufferedImage staffs = null;

	public BufferedImage completeStaffs(BufferedImage image) {
		Opening erex = new Opening(StructElt.LineHorizontal5);
		image = erex.apply(image);
		Closing exer = new Closing(StructElt.LineHorizontal20);
		image = exer.apply(image);
		return image;
	}

	public BufferedImage EraseStaffs(BufferedImage image) {
		Opening ee = new Opening(StructElt.LineVertical5);
		image = ee.apply(image);

		this.staffs = ee.getErased();

		return image;
	}

	public BufferedImage getStaffs() {
		return this.staffs;
	}
}
