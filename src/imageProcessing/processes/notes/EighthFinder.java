package imageProcessing.processes.notes;

import imageProcessing.colorMode.VisualMode;
import imageProcessing.processes.ElementImage;
import imageProcessing.tools.Tools;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import music.Beam;
import music.Type;

public class EighthFinder {
	private final ArrayList<ElementImage> notes;
	private final BufferedImage image;
	private ArrayList<ElementImage> eighths;

	public EighthFinder(ArrayList<ElementImage> notes, BufferedImage image) {
		this.notes = notes;
		this.image = image;
	}

	public ArrayList<ElementImage> findEighth() {

		// Initialize
		for (ElementImage note : notes) {
			image.setRGB(note.getX(), note.getY(), VisualMode.QUARTER.getRGB());
		}

		for (ElementImage note : notes) {
			this.eighths = new ArrayList<ElementImage>();
			getEighths(note.getX() + 1, note.getY());
			getEighths(note.getX() - 1, note.getY());
			getEighths(note.getX(), note.getY() + 1);
			getEighths(note.getX(), note.getY() - 1);

			if (this.eighths.size() > 1) {
				for (int i = 0; i < this.eighths.size(); i++) {
					int index = searchNote(this.eighths.get(i).getX(),
							this.eighths.get(i).getY());
					String beamPos;
					if (i == 0) {
						beamPos = Beam.BEGIN;
					} else if (i == this.eighths.size() - 1) {
						beamPos = Beam.END;
					} else {
						beamPos = Beam.CONTINUE;
					}
					((NoteImage) this.eighths.get(i)).setType(Type.EIGHTH);
					((NoteImage) this.notes.get(index)).setBeam(new Beam(1,
							beamPos));
				}
			}
		}

		// Delete black notes in main image
		Tools.replaceColor(image, VisualMode.EIGHTH, VisualMode.BACKGROUND);
		return this.notes;
	}

	private void getEighths(int x, int y) {
		if (this.image.getRGB(x, y) == VisualMode.OBJECT.getRGB()) {
			this.image.setRGB(x, y, VisualMode.EIGHTH.getRGB());
			getEighths(x + 1, y);
			getEighths(x - 1, y);
			getEighths(x, y + 1);
			getEighths(x, y - 1);
		} else if (this.image.getRGB(x, y) == VisualMode.QUARTER.getRGB()) {
			this.image.setRGB(x, y, VisualMode.EIGHTH.getRGB());
			ElementImage.addElt(eighths, this.notes.get(searchNote(x, y)));
		}
	}

	private int searchNote(int x, int y) {
		for (int i = 0; i < this.notes.size(); i++) {
			if (this.notes.get(i).getX() == x && this.notes.get(i).getY() == y) {
				return i;
			}
		}
		return -1;
	}
}
