package music.xmlWriting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MusicXMLWriter {
	private void writeHeading(FileWriter file) {
		try {
			file.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ System.getProperty("line.separator"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeMusicXML(String path, XMLWritable music) {
		try {
			FileWriter file = new FileWriter(new File(path));
			writeHeading(file);
			music.writeXML(file, "");
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
