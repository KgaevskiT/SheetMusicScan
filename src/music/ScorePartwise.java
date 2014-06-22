package music;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import music.xmlWriting.XMLWritable;

public class ScorePartwise implements XMLWritable {
	private final String version;
	private final String movementTitle;
	private final ArrayList<Part> parts;

	public ScorePartwise(String version, String movementTitle,
			ArrayList<Part> parts) {
		super();
		this.version = version;
		this.movementTitle = movementTitle;
		this.parts = parts;
	}

	@Override
	public void writeXML(FileWriter file, String tab) {
		try {
			// Head
			file.write(tab + "<score-partwise version=\"" + this.version
					+ "\">" + java.lang.System.getProperty("line.separator"));
			// Movement title
			file.write(tab + "<movement-title>" + this.movementTitle
					+ "</movement-title>"
					+ java.lang.System.getProperty("line.separator"));
			// Part
			for (Part part : this.parts)
				part.writeXML(file, tab + "\t");
			// End
			file.write(tab + "</score-partwise>"
					+ java.lang.System.getProperty("line.separator"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
