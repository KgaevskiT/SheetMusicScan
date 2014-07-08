package music;

import java.io.FileWriter;
import java.io.IOException;

import music.xmlWriting.XMLWritable;

public class Type implements XMLWritable {
	public static Type QUADRUPLE = new Type("quadruple whole");
	public static Type DOUBLE = new Type("double whole");
	public static Type WHOLE = new Type("whole");
	public static Type HALF = new Type("half");
	public static Type QUARTER = new Type("quarter");
	public static Type EIGHTH = new Type("eighth");

	private final String value;

	private Type(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return this.value;
	}

	@Override
	public void writeXML(FileWriter file, String tab) {
		try {
			file.write(tab + "<type>" + this.value + "</type>"
					+ java.lang.System.getProperty("line.separator"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
