package music;

public class Note {
	private final int x;
	private final Pitch pitch;
	private final int duration;
	private final int voice;
	private final Type type;
	private final Stem stem;
	private final int staff;

	public Note(int x, Pitch pitch, int duration, int voice, Type type,
			Stem stem, int staff) {
		super();
		this.x = x;
		this.pitch = pitch;
		this.duration = duration;
		this.voice = voice;
		this.type = type;
		this.stem = stem;
		this.staff = staff;
	}

}
