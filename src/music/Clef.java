package music;

import java.io.FileWriter;
import java.io.IOException;

public class Clef implements XMLWritable {
	private final int number;
	private final Step sign;
	private final int line;

	public Clef(int number, Step sign, int line) {
		super();
		this.number = number;
		this.sign = sign;
		this.line = line;
	}

	public int getLine() {
		return line;
	}

	public int getNumber() {
		return number;
	}

	public Step getSign() {
		return sign;
	}

	@Override
	public void writeXML(FileWriter file, String tab) {
		try {
			// Head
			file.write(tab + "<clef number=\"" + this.number + "\">"
					+ java.lang.System.getProperty("line.separator"));
			// Sign
			file.write(tab + "\t" + "<sign>" + this.sign.getValue() + "</sign>"
					+ java.lang.System.getProperty("line.separator"));
			// Line
			file.write(tab + "\t" + "<line>" + this.line + "</line>"
					+ java.lang.System.getProperty("line.separator"));
			// End
			file.write(tab + "</clef>"
					+ java.lang.System.getProperty("line.separator"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
