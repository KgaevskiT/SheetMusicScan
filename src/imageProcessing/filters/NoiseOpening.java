package imageProcessing.filters;

import java.awt.image.BufferedImage;

import parallelism.Parallelizer;

public class NoiseOpening implements Filter {
	@Override
	public BufferedImage apply(BufferedImage image) {
		if (Parallelizer.enable()) {
			return multiCores(image);
		} else {
			return singleCore(image);
		}
	}

	private BufferedImage multiCores(BufferedImage image) {
		NoiseErosionParallel erosion = new NoiseErosionParallel(image, true);
		Parallelizer.invoke(erosion);
		NoiseDilationParallel dilation = new NoiseDilationParallel(
				erosion.join(), true);
		Parallelizer.invoke(dilation);
		return dilation.join();
	}

	private BufferedImage singleCore(BufferedImage image) {
		return new NoiseDilation().apply(new NoiseErosion().apply(image));
	}
}
