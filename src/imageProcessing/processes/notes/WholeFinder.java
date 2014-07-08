package imageProcessing.processes.notes;

import imageProcessing.colorMode.VisualMode;
import imageProcessing.filters.Erosion;
import imageProcessing.filters.structElt.StructElt;
import imageProcessing.processes.ElementImage;
import imageProcessing.processes.staves.Staff;
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

		for (int h = 0; h < image.getHeight(); h++) {
			for (int w = 0; w < image.getWidth(); w++) {
				if (wholeImage.getRGB(w, h) == VisualMode.OBJECT.getRGB()) {
					ObjectSize objectSize = new ObjectSize(image);
					int x = w;
					for (x = w; image.getRGB(x, h) != VisualMode.OBJECT
							.getRGB(); x--)
						;
					int height = objectSize.getObjectHeight(x, h);
					int width = objectSize.getObjectWidth(x, h);
					if (height < 50 && width < 70) {
						NoteImage noteImage = BlackNoteFinder.getNoteImage(
								staves, new Point(w, h), image.getWidth());
						noteImage.setType(Type.WHOLE);

						ElementImage.addElt(wholes, noteImage);
					}
				}
			}
		}
		return wholes;
	}
}
