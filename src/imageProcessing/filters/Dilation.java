package imageProcessing.filters;

import imageProcessing.filters.structElt.StructElt;

import java.awt.Color;
import java.awt.image.BufferedImage;

import parallelism.Parallelizer;

public class Dilation implements Filter {
	private BufferedImage image;
	private BufferedImage result;
	private final StructElt structElt;
	private final Color color;

	public Dilation(StructElt structElt) {
		this.structElt = structElt;
		this.color = Color.white;
	}

	public Dilation(StructElt structElt, Color color) {
		this.structElt = structElt;
		this.color = color;
	}

	@Override
	public BufferedImage apply(BufferedImage image) {
		this.image = image;
		if (Parallelizer.enable()) {
			DilationParallel dilation = new DilationParallel(structElt, color,
					image, true);
			Parallelizer.invoke(dilation);
			this.result = dilation.join();
		} else {
			DilationSequential dilation = new DilationSequential(structElt,
					color);
			this.result = dilation.apply(image);
		}
		return this.result;
	}

	public BufferedImage getExpanded() {
		if (result == null) {
			return null;
		} else {
			return new Substract().apply(result, image);
		}
	}

}
