package imageProcessing.filters;

import java.awt.image.BufferedImage;

public interface Filter {
	public BufferedImage apply(BufferedImage image);
}
