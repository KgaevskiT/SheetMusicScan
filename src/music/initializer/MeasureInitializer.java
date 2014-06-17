package music.initializer;

import imageProcessing.processes.notes.NoteImage;

import java.util.ArrayList;

import music.Attributes;
import music.Note;
import music.Pitch;
import music.Stem;
import music.Type;

public class MeasureInitializer {
	public ArrayList<Note> getNoteList(ArrayList<NoteImage> noteImageList,
			Attributes attributes) {
		ArrayList<Note> notes = new ArrayList<Note>();

		for (NoteImage noteImage : noteImageList) {
			Pitch pitch = Pitch.getPitch(attributes,
					noteImage.getStaffPosition());

			// TODO Compute Stem
			Note note = new Note(noteImage.getX(), pitch, 8, 3, Type.QUARTER,
					new Stem(7, Stem.UP), 1);
		}
		return notes;
	}
}
