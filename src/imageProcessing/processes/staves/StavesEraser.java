package imageProcessing.processes.staves;

import imageProcessing.filters.ErodeAndExpand;
import imageProcessing.filters.ExpandAndErode;
import imageProcessing.filters.masks.Mask;

import java.awt.image.BufferedImage;


public class StavesEraser {
	private BufferedImage staffs = null;

	public BufferedImage completeStaffs(BufferedImage image) {
		ErodeAndExpand erex = new ErodeAndExpand(Mask.LineHorizontal5);
		image = erex.apply(image);
		ExpandAndErode exer = new ExpandAndErode(Mask.LineHorizontal20);
		image = exer.apply(image);
		return image;
	}

	public BufferedImage EraseStaffs(BufferedImage image) {
		ErodeAndExpand ee = new ErodeAndExpand(Mask.LineVertical5);
		image = ee.apply(image);

		this.staffs = ee.getErased();

		return image;
	}

	public BufferedImage getStaffs() {
		return this.staffs;
	}
}
