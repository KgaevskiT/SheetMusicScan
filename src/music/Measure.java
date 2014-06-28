package music;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Measure {
	private final int number;
	private final int width;
	private final boolean newSystem;
	private final Print print;
	private final Attributes attributes;
	private final ArrayList<Note> notes;

	public Measure(int number, int width, boolean newSystem, Print print,
			Attributes attributes, ArrayList<Note> notes) {
		this.number = number;
		this.width = width;
		this.newSystem = newSystem;
		this.print = print;
		this.attributes = attributes;
		this.notes = notes;
	}

	@Override
	public String toString() {
		String result = "Measure " + number
				+ java.lang.System.getProperty("line.separator") + "{"
				+ java.lang.System.getProperty("line.separator");
		for (Note note : notes) {
			result += "\t" + note.toString()
					+ java.lang.System.getProperty("line.separator");
		}
		result += "}" + java.lang.System.getProperty("line.separator");

		return result;
	}

	public void writeXML(FileWriter file, String tab) {
		try {
			// Head
			file.write(tab + "<measure number=\"" + this.number + "\">"
					+ java.lang.System.getProperty("line.separator"));

			// System
			if (this.newSystem) {
				this.attributes.writeXML(file, tab + "\t");
			} else {
				file.write(tab + "\t<print new-system=\"no\"/>"
						+ java.lang.System.getProperty("line.separator"));
			}

			// Notes
			for (Note note : this.notes) {
				note.writeXML(file, tab + "\t");
			}

			// End
			file.write(tab + "</measure>"
					+ java.lang.System.getProperty("line.separator"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeXML_Complete(FileWriter file, String tab) {
		try {
			// Head
			file.write(tab + "<measure number=\"" + this.number + "\" width=\""
					+ this.width + "\">"
					+ java.lang.System.getProperty("line.separator"));

			// System
			if (this.newSystem) {
				this.print.writeXML(file, tab + "\t");
				this.attributes.writeXML(file, tab + "\t");
			} else {
				file.write(tab + "\t<print new-system=\"no\"/>"
						+ java.lang.System.getProperty("line.separator"));
			}

			// Notes
			for (Note note : this.notes) {
				note.writeXML(file, tab + "\t");
			}

			// End
			file.write(tab + "</measure>"
					+ java.lang.System.getProperty("line.separator"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
