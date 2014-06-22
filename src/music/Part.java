package music;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import music.xmlWriting.XMLWritable;

public class Part implements XMLWritable {
	private final String id;
	private final ArrayList<Measure> measures;

	public Part(String id, ArrayList<Measure> measures) {
		this.id = id;
		this.measures = measures;
	}

	@Override
	public void writeXML(FileWriter file, String tab) {
		try {
			// Head
			file.write(tab
					+ "<!--=======================================================-->"
					+ java.lang.System.getProperty("line.separator"));
			file.write(tab + "<part id=\"" + id + "\">"
					+ java.lang.System.getProperty("line.separator"));
			// Measures
			for (Measure measure : this.measures) {
				if (measure != this.measures.get(0)) {
					file.write(tab
							+ "\t"
							+ "<!--=======================================================-->"
							+ java.lang.System.getProperty("line.separator"));
				}
				measure.writeXML(file, tab + "\t");
			}
			// End
			file.write(tab + "</part>"
					+ java.lang.System.getProperty("line.separator"));
			file.write(tab
					+ "<!--=======================================================-->"
					+ java.lang.System.getProperty("line.separator"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
