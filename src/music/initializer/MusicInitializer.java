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
import music.ScorePartwise;
import music.Stem;
import music.Type;

public class MusicInitializer {
	private ArrayList<Measure> getMeasures(Attributes attributes,
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

			measures.add(new Measure(number, width, number == 1,
					Print.DEFAULT_PRINT, attributes, notes));
		}

		return measures;
	}

	public ScorePartwise getMusic(String title, Attributes attributes,
			ArrayList<NoteImage> noteImages) {

		ArrayList<Part> parts = new ArrayList<Part>();

		// Part
		ArrayList<Measure> measures = getMeasures(attributes, noteImages);
		parts.add(new Part("P1", measures));

		// Partwise
		ScorePartwise partwise = new ScorePartwise("1.0", title, parts);

		return partwise;
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
}
