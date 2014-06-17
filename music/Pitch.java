package music;

import imageProcessing.processes.staves.StaffPosition;

public class Pitch {
	private final Step step;
	private final int alter;
	private final int octave;

	public Pitch(Step step, int alter, int octave) {
		this.step = step;
		this.alter = alter;
		this.octave = octave;
	}

	public int getOctave() {
		return octave;
	}

	public Pitch getPitch(Attributes attributes, StaffPosition staffPosition) {

	}

	public Step getStep() {
		return step;
	}
}
