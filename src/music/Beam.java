package music;

import java.io.FileWriter;
import java.io.IOException;

import music.xmlWriting.XMLWritable;

public class Beam implements XMLWritable {
	public static String BEGIN = "begin";
	public static String CONTINUE = "continue";
	public static String END = "end";

	private final int number;
	private final String position;

	public Beam(int number, String position) {
		this.number = number;
		this.position = position;
	}

	@Override
	public void writeXML(FileWriter file, String tab) {
		try {
			// With optional "number"
			/*
			 * file.write(tab + "<beam number=\"" + this.number + "\">" +
			 * this.position + "</beam>" +
			 * java.lang.System.getProperty("line.separator"));
			 */
			file.write(tab + "<beam>" + this.position + "</beam>"
					+ java.lang.System.getProperty("line.separator"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
