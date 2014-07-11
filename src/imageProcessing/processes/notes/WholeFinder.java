package imageProcessing.processes.notes;

import imageProcessing.colorMode.VisualMode;
import imageProcessing.filters.Erosion;
import imageProcessing.filters.structElt.StructElt;
import imageProcessing.processes.ElementImage;
import imageProcessing.processes.staves.Staff;
import imageProcessing.tools.ObjectEditor;
import imageProcessing.tools.ObjectSize;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import music.Type;

public class WholeFinder {
	private final ArrayList<Staff> staves;
	private final BufferedImage image;

	public WholeFinder(ArrayList<Staff> staves, BufferedImage image) {
		this.staves = staves;
		this.image = image;
	}

	public ArrayList<ElementImage> findWholes() {
		BufferedImage wholeImage = new Erosion(StructElt.WHOLE)
				.apply(this.image);
		ArrayList<ElementImage> wholes = new ArrayList<ElementImage>();

		for (int h = 0; h < this.image.getHeight(); h++) {
			for (int w = 0; w < this.image.getWidth(); w++) {
				if (wholeImage.getRGB(w, h) == VisualMode.OBJECT.getRGB()) {
					if (isCircle(w, h)) {
						int x = w;
						for (x = w; this.image.getRGB(x, h) != VisualMode.OBJECT
								.getRGB(); x--)
							;

						ObjectSize objectSize = new ObjectSize();

						int height = objectSize.getObjectHeight(this.image, x,
								h, 50);
						int width = objectSize.getObjectWidth(this.image, x, h,
								70);

						if (height != 0 && height < 50 && width != 0
								&& width < 70) {
							NoteImage noteImage = BlackNoteFinder.getNoteImage(
									staves, new Point(w, h), image.getWidth());
							noteImage.setType(Type.WHOLE);

							ElementImage.addElt(wholes, noteImage);
							new ObjectEditor().eraseShape(image, w, h);
						}
					}
					new ObjectEditor().eraseShape(wholeImage, w, h);
				}
			}
		}
		return wholes;
	}

	private boolean isCircle(int w, int h) {
		int x;
		int y;

		// Left
		for (x = w; x >= 0 && x >= w - 20
				&& this.image.getRGB(x, h) != VisualMode.OBJECT.getRGB(); x--)
			;
		if (x < 0 || x < w - 20) {
			return false;
		}

		// Right
		for (x = w; x < this.image.getWidth() && x <= w + 20
				&& this.image.getRGB(x, h) != VisualMode.OBJECT.getRGB(); x++)
			;
		if (x >= this.image.getWidth() || x > w + 20) {
			return false;
		}

		// Above
		for (y = h; y >= 0 && y >= h - 20
				&& this.image.getRGB(w, y) != VisualMode.OBJECT.getRGB(); y--)
			;
		if (y < 0 || y < h - 20) {
			return false;
		}

		// Below
		for (y = h; y < this.image.getHeight() && y <= h + 20
				&& this.image.getRGB(w, y) != VisualMode.OBJECT.getRGB(); y++)
			;
		if (y >= this.image.getHeight() || y > h + 20) {
			return false;
		}

		return true;
	}
}
