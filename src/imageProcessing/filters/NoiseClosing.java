package imageProcessing.filters;

import java.awt.image.BufferedImage;

public class NoiseClosing implements Filter {
	@Override
	public BufferedImage apply(BufferedImage image) {
		return new NoiseErosion().apply(new NoiseDilation().apply(image));
	}
}
