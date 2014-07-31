package music.initializer;

import imageProcessing.processes.ElementImage;
import imageProcessing.processes.measures.MeasureImage;
import imageProcessing.processes.notes.NoteImage;
import imageProcessing.processes.rests.RestImage;

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

			if (elementImages.get(i).getClass() != MeasureImage.class) {
				if (elementImages.get(i).getClass() == NoteImage.class) {
					notes.add(getNote((NoteImage) elementImages.get(i),
							attributes));
				} else if (elementImages.get(i).getClass() == NoteImage.class) {
					notes.add(getRest((RestImage) elementImages.get(i)));
				}
			}
			i++;

			// Get all notes in a measure
			for (; i < elementImages.size()
					&& elementImages.get(i).getClass() != MeasureImage.class; i++) {
				if (elementImages.get(i).getClass() == NoteImage.class) {
					notes.add(getNote((NoteImage) elementImages.get(i),
							attributes));
				} else if (elementImages.get(i).getClass() == RestImage.class) {
					notes.add(getRest((RestImage) elementImages.get(i)));
				}
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
	
	public class PartsAndTitle
	{
		ArrayList<Part> parts;
		String title;
		
		public PartsAndTitle(ArrayList<Part> parts, String title)
		{
			this.parts = parts;
			this.title = title;
		}

		public ArrayList<Part> getParts() {
			return parts;
		}
		public void setParts(ArrayList<Part> parts) {
			this.parts = parts;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}

	}

	public PartsAndTitle getMusic(String title, Attributes attributes,
			ArrayList<ElementImage> elementImages) {

		ArrayList<Part> parts = new ArrayList<Part>();

		// Part
		ArrayList<Measure> measures = getMeasures(attributes, elementImages);
		parts.add(new Part("P1", measures));
		
		PartsAndTitle result = new PartsAndTitle(parts, title);

		return result;
	}

	private Note getNote(NoteImage noteImage, Attributes attributes) {
		Pitch pitch = Pitch.getPitch(attributes, noteImage.getStaffPosition());

		// TODO Compute Stem
		// TODO Notation

		int duration = 0;
		if (noteImage.getType() == Type.EIGHTH) {
			duration = 4;
		} else if (noteImage.getType() == Type.QUARTER) {
			duration = 8;
		}
		ArrayList<Beam> beams = new ArrayList<Beam>();
		Beam beam = noteImage.getBeam();
		if (beam != null)
			beams.add(beam);

		Note note = new Note(noteImage.getX(), pitch, duration, 3,
				noteImage.getType(), new Stem(7, Stem.UP), 1, beams,
				new ArrayList<Notation>(), Note.NOTE);

		return note;
	}

	private Note getRest(RestImage restImage) {
		int duration = 0;
		if (restImage.getType() == Type.EIGHTH) {
			duration = 4;
		} else if (restImage.getType() == Type.QUARTER) {
			duration = 8;
		} else if (restImage.getType() == Type.HALF) {
			duration = 16;
		} else if (restImage.getType() == Type.WHOLE) {
			duration = 32;
		} else if (restImage.getType() == Type.DOUBLE) {
			duration = 64;
		} else if (restImage.getType() == Type.QUADRUPLE) {
			duration = 128;
		}
		return new Note(restImage.getX(), null, duration, 3,
				restImage.getType(), null, 1, null, null, Note.REST);
	}
}
