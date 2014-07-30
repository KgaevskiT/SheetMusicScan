package imageProcessing.filters;

import imageProcessing.filters.structElt.StructElt;

import java.awt.Color;
import java.awt.image.BufferedImage;

import parallelism.Parallelizer;

public class Erosion implements Filter {
	private BufferedImage image = null;
	private BufferedImage result = null;
	private final StructElt structElt;
	private final Color color;

	public Erosion(StructElt structElt) {
		this.structElt = structElt;
		this.color = Color.black;
	}

	public Erosion(StructElt structElt, Color color) {
		this.structElt = structElt;
		this.color = color;
	}

	@Override
	public BufferedImage apply(BufferedImage image) {
		this.image = image;
		if (Parallelizer.enable()) {
			ErosionParallel erosion = new ErosionParallel(structElt, color,
					image, true);
			Parallelizer.invoke(erosion);
			this.result = erosion.join();
		} else {
			ErosionSequential erosion = new ErosionSequential(structElt, color);
			this.result = erosion.apply(image);
		}
		return this.result;
	}

	public BufferedImage getEroded() {
		if (result == null) {
			return null;
		} else {
			return new Substract().apply(image, result);
		}
	}
}
