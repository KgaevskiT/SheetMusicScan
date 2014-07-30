package imageProcessing.filters;

import java.awt.image.BufferedImage;

import parallelism.Parallelizer;

public class NoiseClosing implements Filter {
	@Override
	public BufferedImage apply(BufferedImage image) {
		if (Parallelizer.enable()) {
			return multiCores(image);
		} else {
			return singleCore(image);
		}
	}

	private BufferedImage multiCores(BufferedImage image) {
		NoiseDilationParallel dilation = new NoiseDilationParallel(image, true);
		Parallelizer.invoke(dilation);
		NoiseErosionParallel erosion = new NoiseErosionParallel(
				dilation.join(), true);
		Parallelizer.invoke(erosion);
		return erosion.join();
	}

	private BufferedImage singleCore(BufferedImage image) {
		return new NoiseDilation().apply(new NoiseErosion().apply(image));
	}
}
