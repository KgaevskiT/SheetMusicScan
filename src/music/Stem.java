package music;

import java.io.FileWriter;
import java.io.IOException;

import music.xmlWriting.XMLWritable;

public class Stem implements XMLWritable {
	public static String UP = "up";
	public static String DOWN = "down";

	private final int y;
	private final String direction;

	public Stem(int y, String direction) {
		super();
		this.y = y;
		this.direction = direction;
	}

	@Override
	public void writeXML(FileWriter file, String tab) {
		try {
			file.write(tab + "<stem default-y=\"" + this.y + "\">"
					+ this.direction + "</stem>"
					+ java.lang.System.getProperty("line.separator"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
