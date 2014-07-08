package imageProcessing.processes.notes;

import imageProcessing.filters.Erosion;
import imageProcessing.filters.structElt.StructElt;
import imageProcessing.processes.ElementImage;
import imageProcessing.processes.staves.Staff;
import imageProcessing.processes.staves.StaffPosition;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import music.Type;

public class BlackNoteFinder {
	public static NoteImage getNoteImage(ArrayList<Staff> staves,
			Point noteCenter, int imageWidth) {
		int x = noteCenter.x;
		int y = noteCenter.y;
		int staffNumber = -1;
		StaffPosition position = null;

		boolean notFound = true;

		// i : staff number
		for (int i = 0; notFound && i < staves.size(); i++) {
			Staff staff = staves.get(i);
			int lines[] = staff.getLines();

			// j : line number (from top to bottom)
			for (int j = 4; notFound && j >= 0; j--) {

				// Position found
				if (y < lines[j]) {
					notFound = false;

					// Between 2 staves
					if (j == 4 && i != 0) {

						// Below previous staff
						if ((lines[4] - y) > (y - staves.get(i - 1).getLines()[0])) {
							staffNumber = i - 1;
							position = StaffPosition.getStaffPosition(
									staves.get(i - 1), y, 0);
						}

						// Above current staff
						else {
							staffNumber = i;
							position = StaffPosition.getStaffPosition(staff, y,
									4);
						}
					}

					// Above first staff
					else if (j == 4 && i == 0) {
						staffNumber = 0;
						position = StaffPosition.getStaffPosition(staff, y, 4);
					}

					// On current staff
					else {
						staffNumber = i;
						position = StaffPosition.getStaffPosition(staff, y,
								j + 1);
					}
				}
			}
		}

		// Below last staff
		if (notFound) {
			staffNumber = staves.size() - 1;
			position = StaffPosition.getStaffPosition(
					staves.get(staves.size() - 1), y, 0);
		}

		return new NoteImage(x, y, staffNumber, x + staffNumber * imageWidth,
				position, Type.QUARTER);
	}

	private final ArrayList<Staff> staves;
	private final BufferedImage image;

	public BlackNoteFinder(ArrayList<Staff> staves, BufferedImage image) {
		this.staves = staves;
		this.image = image;
	}

	public ArrayList<ElementImage> getBlackNotesList() {
		ArrayList<ElementImage> notes = new ArrayList<ElementImage>();
		BufferedImage blackNotesImage = new Erosion(StructElt.QUARTER)
				.apply(image);
		NoteCenterFinder centerFinder = new NoteCenterFinder(blackNotesImage);

		for (int h = 0; h < blackNotesImage.getHeight(); h++) {
			for (int w = 0; w < blackNotesImage.getWidth(); w++) {
				if (blackNotesImage.getRGB(w, h) == Color.white.getRGB()) {

					Point noteCenter = centerFinder.findNoteCenter(w, h);
					NoteImage noteImage = getNoteImage(staves, noteCenter,
							image.getWidth());

					ElementImage.addElt(notes, noteImage);
				}
			}
		}

		return notes;
	}
}
