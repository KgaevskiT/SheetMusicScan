package imageProcessing.processes.notes;

import imageProcessing.filters.Erosion;
import imageProcessing.filters.structElt.StructElt;
import imageProcessing.processes.ElementImage;
import imageProcessing.processes.staves.Staff;
import imageProcessing.processes.staves.StaffPosition;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import music.Type;

public class BlackNoteFinder {
	private final ArrayList<Staff> staves;

	public BlackNoteFinder(ArrayList<Staff> staves) {
		this.staves = staves;
	}

	public BufferedImage getBlackNotesImage(BufferedImage image) {
		return new Erosion(StructElt.QUARTER, Color.black).apply(image);
	}

	public ArrayList<ElementImage> getBlackNotesList(BufferedImage image) {

		ArrayList<ElementImage> notes = new ArrayList<ElementImage>();
		BufferedImage blackNotesImage = getBlackNotesImage(image);
		NoteCenterFinder centerFinder = new NoteCenterFinder(blackNotesImage);

		// TODO Save black notes for debug
		try {
			ImageIO.write(blackNotesImage, "png", new File("blackNotes.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int h = 0; h < blackNotesImage.getHeight(); h++) {
			for (int w = 0; w < blackNotesImage.getWidth(); w++) {
				if (blackNotesImage.getRGB(w, h) == Color.white.getRGB()) {

					Point noteCenter = centerFinder.findNoteCenter(w, h);
					NoteImage noteImage = getNoteImage(noteCenter,
							image.getWidth());

					ElementImage.addElt(notes, noteImage);
				}
			}
		}

		return notes;
	}

	private NoteImage getNoteImage(Point noteCenter, int imageWidth) {
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
							position = getStaffPosition(staves.get(i - 1), y, 0);
						}

						// Above current staff
						else {
							staffNumber = i;
							position = getStaffPosition(staff, y, 4);
						}
					}

					// Above first staff
					else if (j == 4 && i == 0) {
						staffNumber = 0;
						position = getStaffPosition(staff, y, 4);
					}

					// On current staff
					else {
						staffNumber = i;
						position = getStaffPosition(staff, y, j + 1);
					}
				}
			}
		}

		// Below last staff
		if (notFound) {
			staffNumber = staves.size() - 1;
			position = getStaffPosition(staves.get(staves.size() - 1), y, 0);
		}

		return new NoteImage(x, y, staffNumber, x + staffNumber * imageWidth,
				position, Type.QUARTER);
	}

	private StaffPosition getStaffPosition(Staff staff, int y, int line) {
		int staffPosition;
		boolean isOnLine;

		int gap = (staff.getLines()[0] - staff.getLines()[4]) / 4;
		int position = staff.getLines()[line];

		// while note is above current line
		while (position > y) {
			position -= gap;
			line++;
		}
		// while note is under current line - 1
		while (position + gap < y) {
			position += gap;
			line--;
		}

		// position is now the line above note

		// On current line
		if (y - position < gap / 4) {
			staffPosition = line + 1;
			isOnLine = true;
		}
		// Between current line and the line below
		else if (y - position < 3 * gap / 4) {
			staffPosition = line;
			isOnLine = false;
		}
		// On the line below
		else {
			staffPosition = line;
			isOnLine = true;
		}

		return new StaffPosition(staffPosition, isOnLine);
	}
}
