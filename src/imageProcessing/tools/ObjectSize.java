package imageProcessing.tools;

import imageProcessing.colorMode.VisualMode;

import java.awt.image.BufferedImage;

public class ObjectSize {
	private final BufferedImage image;
	private int min;
	private int max;

	public ObjectSize(BufferedImage image) {
		this.image = image;
	}

	public int getObjectHeight(int x, int y) {
		min = y;
		max = y;
		setHeight(Tools.duplicate(this.image), x, y);
		int height = this.max - this.min + 1;
		return height;
	}

	public int getObjectWidth(int x, int y) {
		min = x;
		max = x;
		setWidth(Tools.duplicate(this.image), x, y);
		int width = this.max - this.min + 1;
		return width;
	}

	private void setHeight(BufferedImage image, int x, int y) {
		if (image.getRGB(x, y) == VisualMode.OBJECT.getRGB()) {
			image.setRGB(x, y, VisualMode.BACKGROUND.getRGB());
			if (y < this.min) {
				this.min = y;
			}
			if (y > this.max) {
				this.max = y;
			}
			setHeight(image, x + 1, y);
			setHeight(image, x - 1, y);
			setHeight(image, x, y + 1);
			setHeight(image, x, y - 1);
		}
	}

	private void setWidth(BufferedImage image, int x, int y) {
		if (image.getRGB(x, y) == VisualMode.OBJECT.getRGB()) {
			image.setRGB(x, y, VisualMode.BACKGROUND.getRGB());
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
