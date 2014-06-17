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

	/*
	 * Currently only considerating G clef TODO : Algorithme to compute pitch
	 * for any clef at any position
	 */
	public Pitch getPitch(Attributes attributes, StaffPosition staffPosition) {

	}

	public Step getStep() {
		return step;
	}
}
