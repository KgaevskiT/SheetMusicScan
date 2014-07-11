package imageProcessing.tools;

import imageProcessing.colorMode.VisualMode;

import java.awt.image.BufferedImage;

public class ObjectSize {
	private BufferedImage image;
	private int min;
	private int max;
	private int threshold;

	public int getObjectHeight(BufferedImage image, int x, int y, int threshold) {
		int height;
		if (image.getRGB(x, y) != VisualMode.OBJECT.getRGB()) {
			height = 0;
		} else {
			this.image = Tools.duplicate(image);
			this.min = y;
			this.max = y;
			this.threshold = threshold;
			setHeight(x, y);
			height = this.max - this.min + 1;
		}
		return height;
	}

	public int getObjectWidth(BufferedImage image, int x, int y, int threshold) {
		int width;

		if (image.getRGB(x, y) != VisualMode.OBJECT.getRGB()) {
			width = 0;
		} else {
			this.image = Tools.duplicate(image);
			this.min = x;
			this.max = x;
			this.threshold = threshold;
			setWidth(x, y);
			width = this.max - this.min + 1;
		}
		return width;
	}

	private void setHeight(int x, int y) {
		if (this.image.getRGB(x, y) != VisualMode.BACKGROUND.getRGB()) {
			this.image.setRGB(x, y, VisualMode.BACKGROUND.getRGB());
			if (y < this.min) {
				this.min = y;
			}
			if (y > this.max) {
				this.max = y;
			}
			if (this.max - this.min + 1 < this.threshold) {
				setHeight(x + 1, y);
				setHeight(x - 1, y);
				setHeight(x, y + 1);
				setHeight(x, y - 1);
			}
		}
	}

	private void setWidth(int x, int y) {
		if (this.image.getRGB(x, y) != VisualMode.BACKGROUND.getRGB()) {
			this.image.setRGB(x, y, VisualMode.BACKGROUND.getRGB());
			if (x < this.min) {
				this.min = x;
			}
			if (x > this.max) {
				this.max = x;
			}
			if (this.max - this.min + 1 < this.threshold) {
				setWidth(x + 1, y);
				setWidth(x - 1, y);
				setWidth(x, y + 1);
				setWidth(x, y - 1);
			}
		}
	}
}
