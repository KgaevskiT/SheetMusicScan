package music;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import music.xmlWriting.XMLWritable;

public class Note implements XMLWritable {
	private final int x;
	private final Pitch pitch;
	private final int duration;
	private final int voice;
	private final Type type;
	private final Stem stem;
	private final int staff;
	private final ArrayList<Beam> beams;
	private final ArrayList<Notation> notations;
	private final boolean isRest;

	public static boolean NOTE = false;
	public static boolean REST = true;

	public Note(int x, Pitch pitch, int duration, int voice, Type type,
			Stem stem, int staff, ArrayList<Beam> beams,
			ArrayList<Notation> notations, boolean isRest) {
		super();
		this.x = x;
		this.pitch = pitch;
		this.duration = duration;
		this.voice = voice;
		this.type = type;
		this.stem = stem;
		this.staff = staff;
		this.beams = beams;
		this.notations = notations;
		this.isRest = isRest;
	}

	public int getX() {
		return this.x;
	}

	@Override
	public String toString() {
		return "Note [x = " + x + ", pitch = " + pitch.toString() + "]";
	}

	@Override
	public void writeXML(FileWriter file, String tab) {
		try {
			// Head
			file.write(tab + "<note"
			// facultative
			// + "default-x=\"" + this.x + "\""
					+ ">" + java.lang.System.getProperty("line.separator"));
			// Rest ?
			if (this.isRest) {

				file.write(tab + "\t" + "<rest/>"
						+ java.lang.System.getProperty("line.separator"));

			}
			// Pitch
			if (this.pitch != null) {
				this.pitch.writeXML(file, tab + "\t");
			}
			// Duration
			file.write(tab + "\t" + "<duration>" + this.duration
					+ "</duration>"
					+ java.lang.System.getProperty("line.separator"));
			// Voice
			file.write(tab + "\t" + "<voice>" + this.voice + "</voice>"
					+ java.lang.System.getProperty("line.separator"));
			// Type
			// if (this.isRest
			// && (this.type == Type.DOUBLE || this.type == Type.QUADRUPLE)) {
			this.type.writeXML(file, tab + "\t");
			// }

			// Stem (facultative)
			// this.stem.writeXML(file, tab + "\t");

			// Staff
			file.write(tab + "\t" + "<staff>" + this.staff + "</staff>"
					+ java.lang.System.getProperty("line.separator"));
			// Beams
			if (this.beams != null) {
				for (Beam beam : this.beams) {
					beam.writeXML(file, tab + "\t");
				}
			}

			// Notation
			if (this.notations != null && this.notations.size() > 0) {
				file.write(tab + "\t" + "<notations>"
						+ java.lang.System.getProperty("line.separator"));
				for (Notation notation : this.notations) {
					notation.writeXML(file, tab + "\t");
				}
				file.write(tab + "\t" + "</notations>"
						+ java.lang.System.getProperty("line.separator"));
			}

			// End
			file.write(tab + "</note>"
					+ java.lang.System.getProperty("line.separator"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
