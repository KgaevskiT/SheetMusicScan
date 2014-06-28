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
		boolean newSystem = true;

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

			int width = 20;
			if (notes.size() > 0) {
				width += notes.get(notes.size() - 1).getX()
						- notes.get(0).getX();
			}

			measures.add(new Measure(number, width, newSystem,
					Print.DEFAULT_PRINT, attributes, notes));

			if (i < elementImages.size()) {
				newSystem = ((MeasureImage) elementImages.get(i)).isNewSystem();
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
		// TODO Notation

		int duration = 2;
		if (noteImage.getType() == Type.EIGHTH) {
			duration = 1;
		}
		ArrayList<Beam> beams = new ArrayList<Beam>();
		Beam beam = noteImage.getBeam();
		if (beam != null)
			beams.add(beam);

		Note note = new Note(noteImage.getX(), pitch, duration, 3,
				noteImage.getType(), new Stem(7, Stem.UP), 1, beams,
				new ArrayList<Notation>());

		return note;
	}
}
