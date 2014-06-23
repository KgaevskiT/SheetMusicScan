package music.initializer;

import imageProcessing.processes.ElementImage;
import imageProcessing.processes.measures.MeasureImage;
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
			ArrayList<ElementImage> elementImages) {

		ArrayList<Measure> measures = new ArrayList<Measure>();
		int number = 1;

		// Browse all ElementImage
		for (int i = 0; i < elementImages.size(); number++) {

			ArrayList<Note> notes = new ArrayList<Note>();

			if (elementImages.get(i).getClass() == NoteImage.class) {
				notes.add(getNote((NoteImage) elementImages.get(i), attributes));
			}
			i++;

			// Get all notes in a measure
			for (; i < elementImages.size()
					&& elementImages.get(i).getClass() == NoteImage.class; i++) {
				notes.add(getNote((NoteImage) elementImages.get(i), attributes));
			}

			if (i < elementImages.size()
					&& elementImages.get(i).getClass() == MeasureImage.class) {
				// Width
				// TODO is 20 ok ?
				int width = 20;
				if (notes.size() > 0) {

					width += notes.get(notes.size() - 1).getX()
							- notes.get(0).getX();
				}

				measures.add(new Measure(number, width, number == 1,
						Print.DEFAULT_PRINT, attributes, notes));
			}
		}
		return measures;
	}

	public ScorePartwise getMusic(String title, Attributes attributes,
			ArrayList<ElementImage> elementImages) {

		ArrayList<Part> parts = new ArrayList<Part>();

		// Part
		ArrayList<Measure> measures = getMeasures(attributes, elementImages);
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
