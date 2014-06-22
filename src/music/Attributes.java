package music;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import music.xmlWriting.XMLWritable;

public class Attributes implements XMLWritable {
	private final int divisions;
	private final Key key;
	private final int staves;
	private final ArrayList<Clef> clefs;

	public static Attributes DEFAULT_ATTRIBUTES = createDefaultAttributes();

	private static Attributes createDefaultAttributes() {
		ArrayList<Clef> clefs = new ArrayList<Clef>();
		clefs.add(new Clef(1, Step.G, 2));
		Key key = new Key(0, Key.MODE_MAJOR);

		return new Attributes(8, key, 1, clefs);
	}

	public Attributes(int divisions, Key key, int staves, ArrayList<Clef> clefs) {
		super();
		this.divisions = divisions;
		this.key = key;
		this.staves = staves;
		this.clefs = clefs;
	}

	public ArrayList<Clef> getClefs() {
		return clefs;
	}

	public int getDivisions() {
		return divisions;
	}

	public Key getKey() {
		return key;
	}

	public int getStaves() {
		return staves;
	}

	@Override
	public void writeXML(FileWriter file, String tab) {
		try {
			// Head
			file.write(tab + "<attributes>"
					+ java.lang.System.getProperty("line.separator"));
			// Divisions
			file.write(tab + "\t" + "<divisions>" + this.divisions
					+ "</divisions>"
					+ java.lang.System.getProperty("line.separator"));
			// Key
			this.key.writeXML(file, tab + "\t");
			// Staves
			file.write(tab + "\t" + "<staves>" + this.staves + "</staves>"
					+ java.lang.System.getProperty("line.separator"));
			// Clefs
			for (Clef clef : clefs)
				clef.writeXML(file, tab + "\t");
			// End
			file.write(tab + "</attributes>"
					+ java.lang.System.getProperty("line.separator"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
