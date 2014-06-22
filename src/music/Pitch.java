package music;

import imageProcessing.processes.staves.StaffPosition;

import java.io.FileWriter;
import java.io.IOException;

public class Pitch implements XMLWritable {

	private static Pitch downgrade(Pitch pitch) {
		switch (pitch.getStep().getValue()) {
		case "A":
			pitch.setStep(Step.G);
			pitch.setOctave(pitch.getOctave() - 1);
			break;
		case "B":
			pitch.setStep(Step.A);
			break;
		case "C":
			pitch.setStep(Step.B);
			break;
		case "D":
			pitch.setStep(Step.C);
			break;
		case "E":
			pitch.setStep(Step.D);
			break;
		case "F":
			pitch.setStep(Step.E);
			break;
		case "G":
			pitch.setStep(Step.F);
			break;
		}
		return pitch;
	}

	private static Pitch downgrade(Pitch pitch, int n) {
		while (n > 0) {
			pitch = Pitch.downgrade(pitch);
			n--;
		}
		return pitch;
	}

	/*
	 * Currently only considerating G clef TODO : Algorithme to compute pitch
	 * for any clef at any position
	 */
	public static Pitch getPitch(Attributes attributes,
			StaffPosition staffPosition) {
		Pitch pitch = null;

		// Clef attributes
		Step clefStep = attributes.getClefs().get(0).getSign();
		int clefLine = attributes.getClefs().get(0).getLine();

		// If classic G clef (line 2)
		if (clefStep == Step.G && clefLine == 2) {

			// TODO check attributes.getKey()
			pitch = new Pitch(clefStep, 0, 5);

			if (!staffPosition.isOnLine()) {
				pitch = Pitch.upgrade(pitch);
			}
			for (int i = 0; staffPosition.getPosition() > clefLine + i; i++) {
				pitch = Pitch.upgrade(pitch, 2);
			}
			for (int i = 0; staffPosition.getPosition() < clefLine + i; i--) {
				pitch = Pitch.downgrade(pitch, 2);
			}
		}
		return pitch;
	}

	private static Pitch upgrade(Pitch pitch) {
		switch (pitch.getStep().getValue()) {
		case "A":
			pitch.setStep(Step.B);
			break;
		case "B":
			pitch.setStep(Step.C);
			break;
		case "C":
			pitch.setStep(Step.D);
			break;
		case "D":
			pitch.setStep(Step.E);
			break;
		case "E":
			pitch.setStep(Step.F);
			break;
		case "F":
			pitch.setStep(Step.G);
			break;
		case "G":
			pitch.setStep(Step.A);
			pitch.setOctave(pitch.getOctave() + 1);
			break;
		}
		return pitch;
	}

	private static Pitch upgrade(Pitch pitch, int n) {
		while (n > 0) {
			pitch = Pitch.upgrade(pitch);
			n--;
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
