package music;

import imageProcessing.processes.staves.StaffPosition;

import java.io.FileWriter;
import java.io.IOException;

import music.xmlWriting.XMLWritable;

public class Pitch implements XMLWritable {

	public static Pitch getPitch(Attributes attributes,
			StaffPosition staffPosition) {
		Pitch pitch = null;

		// Clef attributes
		Step clefStep = attributes.getClefs().get(0).getSign();
		int clefLine = attributes.getClefs().get(0).getLine();

		// TODO check attributes.getKey()
		// TODO octave (4)
		pitch = new Pitch(clefStep, 0, 4);

		if (!staffPosition.isOnLine()) {
			pitch.upgrade();
		}
		for (int i = 0; staffPosition.getPosition() > clefLine + i; i++) {
			pitch.upgrade(2);
		}
		for (int i = 0; staffPosition.getPosition() < clefLine + i; i--) {
			pitch.downgrade(2);
		}

		return pitch;
	}

	private Step step;
	private final int alter;
	private int octave;

	public Pitch(Step step, int alter, int octave) {
		this.step = step;
		this.alter = alter;
		this.octave = octave;
	}

	private void downgrade() {
		switch (this.step.getValue()) {
		case "A":
			this.step = Step.G;
			break;
		case "B":
			this.step = Step.A;
			break;
		case "C":
			this.step = Step.B;
			this.octave--;
			break;
		case "D":
			this.step = Step.C;
			break;
		case "E":
			this.step = Step.D;
			break;
		case "F":
			this.step = Step.E;
			break;
		case "G":
			this.step = Step.F;
			break;
		}
	}

	private void downgrade(int n) {
		while (n > 0) {
			this.downgrade();
			n--;
		}
	}

	public int getOctave() {
		return octave;
	}

	public Step getStep() {
		return step;
	}

	public void setOctave(int octave) {
		this.octave = octave;
	}

	public void setStep(Step step) {
		this.step = step;
	}

	@Override
	public String toString() {
		return "step = " + step.getValue() + "; octave = " + octave
				+ "; alter = " + octave;
	}

	private void upgrade() {
		switch (this.step.getValue()) {
		case "A":
			this.step = Step.B;
			break;
		case "B":
			this.step = Step.C;
			this.octave++;
			break;
		case "C":
			this.step = Step.D;
			break;
		case "D":
			this.step = Step.E;
			break;
		case "E":
			this.step = Step.F;
			break;
		case "F":
			this.step = Step.G;
			break;
		case "G":
			this.step = Step.A;
			break;
		}
	}

	private void upgrade(int n) {
		while (n > 0) {
			this.upgrade();
			n--;
		}
	}

	@Override
	public void writeXML(FileWriter file, String tab) {
		try {
			// Head
			file.write(tab + "<pitch>"
					+ java.lang.System.getProperty("line.separator"));
			// Step
			file.write(tab + "\t" + "<step>" + this.step.getValue() + "</step>"
					+ java.lang.System.getProperty("line.separator"));
			// Alter
			if (this.alter != 0)
				file.write(tab + "\t" + "<alter>" + this.alter + "</alter>"
						+ java.lang.System.getProperty("line.separator"));
			// Octave
			file.write(tab + "\t" + "<octave>" + this.octave + "</octave>"
					+ java.lang.System.getProperty("line.separator"));
			// End
			file.write(tab + "</pitch>"
					+ java.lang.System.getProperty("line.separator"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
