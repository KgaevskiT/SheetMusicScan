package imageProcessing.processes.notes;

import imageProcessing.filters.Erode;
import imageProcessing.filters.masks.Mask;
import imageProcessing.processes.staves.Staff;
import imageProcessing.processes.staves.StaffPosition;
import imageProcessing.processes.staves.Staves;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BlackNoteFinder {
	public BufferedImage getBlackNotesImage(BufferedImage image) {
		return new Erode(Mask.Quarter, Color.black).apply(image);
	}

	public ArrayList<NoteImage> getBlackNotesList(BufferedImage image,
			Staves staves) {
		ArrayList<NoteImage> notes = new ArrayList<NoteImage>();
		BufferedImage blackNotesImage = getBlackNotesImage(image);
		NoteCenterFinder centerFinder = new NoteCenterFinder(blackNotesImage);

		for (int h = 0; h < blackNotesImage.getHeight(); h++) {
			for (int w = 0; w < blackNotesImage.getWidth(); w++) {
				if (blackNotesImage.getRGB(w, h) == Color.white.getRGB()) {

					Point noteCenter = centerFinder.findNoteCenter(w, h);
					NoteImage noteImage = getNoteImage(staves, noteCenter,
							image.getWidth());

					int i = 0;
					while (i < notes.size()
							&& noteImage.getIndex() > notes.get(i).getIndex()) {
						i++;
					}

					notes.add(i, noteImage);
				}
			}
		}

		return notes;
	}

	private NoteImage getNoteImage(Staves staves, Point noteCenter,
			int imageWidth) {
		int x = noteCenter.x;
		int staffNumber = -1;
		StaffPosition position = null;

		ArrayList<Staff> stavesList = staves.getStaves();
		boolean notFound = true;

		// i : staff number
		for (int i = 0; notFound && i < stavesList.size(); i++) {
			Staff staff = stavesList.get(i);
			int lines[] = staff.getLines();

			// j : line number (from top to bottom)
			for (int j = 4; notFound && j >= 0; j--) {

				// Position found
				if (noteCenter.y < lines[j]) {
					notFound = false;

					// Between 2 staves
					if (j == 4 && i != 0) {

						// Below previous staff
						if ((lines[4] - noteCenter.y) > (noteCenter.y - stavesList
								.get(i - 1).getLines()[0])) {
							staffNumber = i - 1;
							position = getStaffPosition(stavesList.get(i - 1),
									noteCenter.y, 0);
						}

						// Above current staff
						else {
							staffNumber = i;
							position = getStaffPosition(staff, noteCenter.y, 4);
						}
					}

					// Above first staff
					else if (j == 4 && i == 0) {
						staffNumber = 0;
						position = getStaffPosition(staff, noteCenter.y, 4);
					}

					// On current staff
					else {
						staffNumber = i;
						position = getStaffPosition(staff, noteCenter.y, j + 1);
					}
				}
			}
		}

		// Below last staff
		if (notFound) {
			staffNumber = stavesList.size() - 1;
			position = getStaffPosition(stavesList.get(stavesList.size() - 1),
					noteCenter.y, 0);
		}

		return new NoteImage(x, staffNumber, position, x + staffNumber
				* imageWidth);
	}

	private StaffPosition getStaffPosition(Staff staff, int y, int line) {
		int staffPosition;
		boolean isOnLine;

		int gap = (staff.getLines()[0] - staff.getLines()[4]) / 4;
		int position = staff.getLines()[line];

		while (position > y) {
			position -= gap;
			line--;
		}
		while (position + gap < y) {
			position += gap;
			line++;
		}

		if (y - position < gap / 4) {
			staffPosition = line + 1;
			isOnLine = true;
		} else if (y - position < 3 * gap / 4) {
			staffPosition = line;
			isOnLine = false;
		} else {
			staffPosition = line;
			isOnLine = true;
		}

		return new StaffPosition(staffPosition, isOnLine);
	}
}
