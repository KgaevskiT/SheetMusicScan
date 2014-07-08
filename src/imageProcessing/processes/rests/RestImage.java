package imageProcessing.processes.rests;

import imageProcessing.processes.ElementImage;
import music.Type;

public class RestImage extends ElementImage {
	private final Type type;

	protected RestImage(int x, int y, int staff, int index, Type type) {
		super(x, y, staff, index);
		this.type = type;
	}

	public Type getType() {
		return this.type;
	}

}
