package imageProcessing.filters;

import java.awt.image.BufferedImage;

public class NoiseOpening implements Filter {
	@Override
	public BufferedImage apply(BufferedImage image) {
		return new NoiseDilation().apply(new NoiseErosion().apply(image));
	}
}
