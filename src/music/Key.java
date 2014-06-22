package music;

import java.io.FileWriter;
import java.io.IOException;

import music.xmlWriting.XMLWritable;

public class Key implements XMLWritable {
	public static String MODE_MAJOR = "major";
	public static String MODE_MINOR = "minor";
	private final int fifths;

	private final String mode;

	public Key(int fifths, String mode) {
		super();
		this.fifths = fifths;
		this.mode = mode;
	}

	@Override
	public void writeXML(FileWriter file, String tab) {
		try {
			// HEAD
			file.write(tab + "<key>"
					+ java.lang.System.getProperty("line.separator"));
			// Fifths
			file.write(tab + "\t" + "<fifths>" + this.fifths + "</fifths>"
					+ java.lang.System.getProperty("line.separator"));
			// Mode
			file.write(tab + "\t" + "<mode>" + this.mode + "</mode>"
					+ java.lang.System.getProperty("line.separator"));
			// END
			file.write(tab + "</key>"
					+ java.lang.System.getProperty("line.separator"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
