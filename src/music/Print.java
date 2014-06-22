package music;

import java.io.FileWriter;
import java.io.IOException;

import music.xmlWriting.XMLWritable;

public class Print implements XMLWritable {
	private final int topSystemDistance;
	private final int staffLayout;
	private final int staffDistance;
	private final boolean measureNumbering;

	public static Print DEFAULT_PRINT = new Print(200, 2, 75, false);

	public Print(int topSystemDistance, int staffLayout, int staffDistance,
			boolean measureNumbering) {
		super();
		this.topSystemDistance = topSystemDistance;
		this.staffLayout = staffLayout;
		this.staffDistance = staffDistance;
		this.measureNumbering = measureNumbering;
	}

	@Override
	public void writeXML(FileWriter file, String tab) {
		try {
			// Head
			file.write(tab + "<print>"
					+ java.lang.System.getProperty("line.separator"));

			// System layout
			file.write(tab + "\t" + "<system-layout>"
					+ java.lang.System.getProperty("line.separator"));
			file.write(tab + "\t\t" + "<top-system-distance>"
					+ this.topSystemDistance + "</top-system-distance>"
					+ java.lang.System.getProperty("line.separator"));
			file.write(tab + "\t" + "</system-layout>"
					+ java.lang.System.getProperty("line.separator"));

			// Staff layout
			file.write(tab + "\t" + "<staff-layout number=\""
					+ this.staffLayout + "\">"
					+ java.lang.System.getProperty("line.separator"));
			file.write(tab + "\t\t" + "<staff-distance>" + this.staffDistance
					+ "</staff-distance>"
					+ java.lang.System.getProperty("line.separator"));
			file.write(tab + "\t" + "</staff-layout>"
					+ java.lang.System.getProperty("line.separator"));

			// Measure numbering
			// TODO What if measureNumbering == true ???
			file.write(tab + "\t" + "<measure-numbering>"
					+ (this.measureNumbering ? "yes" : "none")
					+ "</measure-numbering>"
					+ java.lang.System.getProperty("line.separator"));

			// End
			file.write(tab + "</print>"
					+ java.lang.System.getProperty("line.separator"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
