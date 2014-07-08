package imageProcessing.processes.clefs;

import imageProcessing.colorMode.VisualMode;
import imageProcessing.processes.ElementImage;
import imageProcessing.processes.staves.Staff;
import imageProcessing.tools.Tools;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ClefFinder {
	private final BufferedImage image;
	private final ArrayList<Staff> staves;

	public ClefFinder(ArrayList<Staff> staves, BufferedImage image) {
		super();
		this.staves = staves;
		this.image = image;
	}

	public ArrayList<ElementImage> findClefs() {
		ArrayList<ElementImage> clefs = new ArrayList<ElementImage>();
		int staffNb = 0;
		for (Staff staff : this.staves) {
			int x = 0;
			int y = staff.getLine(2);
			for (x = 0; image.getRGB(x, y) == VisualMode.BACKGROUND.getRGB(); x++)
				;
			// TODO: get Clef info
			clefs.add(new ClefImage(x, y, staffNb, staffNb * image.getWidth()
					+ x));
			Tools.eraseShape(image, x, y);

			staffNb++;
		}
		return clefs;
	}

}
