package music.initializer;

import imageProcessing.processes.notes.NoteImage;

import java.util.ArrayList;

import music.Attributes;
import music.Beam;
import music.Measure;
import music.Notation;
import music.Note;
import music.Part;
import music.Pitch;
import music.Print;
import music.Stem;
import music.Type;

public class PartInitializer {
	public ArrayList<Measure> getMeasures(Attributes attributes,
			ArrayList<NoteImage> noteImages) {

		ArrayList<Measure> measures = new ArrayList<Measure>();
		int number = 1;

		// Browse all NoteImage
		for (int i = 0; i < noteImages.size(); number++) {
			int staff = noteImages.get(i).getStaff();

			ArrayList<Note> notes = new ArrayList<Note>();
			notes.add(getNote(noteImages.get(i), attributes));

			// Get all notes on a staff
			for (i += 1; i < noteImages.size()
					&& noteImages.get(i).getStaff() == staff; i++) {
				notes.add(getNote(noteImages.get(i), attributes));
			}

			// Width
			int width = notes.get(notes.size() - 1).getX()
					- notes.get(0).getX() + 20; // TODO is 20 ok ?

			measures.add(new Measure(number, width, true, Print.DEFAULT_PRINT,
					attributes, notes));
		}

		return measures;
	}

	private Note getNote(NoteImage noteImage, Attributes attributes) {
		Pitch pitch = Pitch.getPitch(attributes, noteImage.getStaffPosition());

		// TODO Compute Stem
		// TODO Beam
		// TODO Notation
		Note note = new Note(noteImage.getX(), pitch, 8, 3, Type.QUARTER,
				new Stem(7, Stem.UP), 1, new ArrayList<Beam>(),
				new ArrayList<Notation>());

		return note;
	}

	public Part getPart(String id, Attributes attributes,
			ArrayList<NoteImage> noteImages) {
		ArrayList<Measure> measures = getMeasures(attributes, noteImages);

		return new Part(id, measures);
	}
}
