package imageProcessing.tools;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ObjectSize {
	private final BufferedImage image;
	private final int x;
	private final int y;
	private int min;
	private int max;
	private int width;

	public ObjectSize(BufferedImage image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.min = x;
		this.max = x;
	}

	public int getObjectWidth() {
		setWidth(Tools.duplicate(this.image), this.x, this.y);
		int width = this.max - this.min + 1;
		return width;
	}

	private void setWidth(BufferedImage image, int x, int y) {
		if (image.getRGB(x, y) == Color.white.getRGB()) {
			image.setRGB(x, y, Color.black.getRGB());
			if (x < this.min) {
				this.min = x;
			}
			if (x > this.max) {
				this.max = x;
			}
			setWidth(image, x + 1, y);
			setWidth(image, x - 1, y);
			setWidth(image, x, y + 1);
			setWidth(image, x, y - 1);
		}
	}
}
