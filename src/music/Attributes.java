package music;

import java.util.ArrayList;

public class Attributes {
	private int division;
	private Key key;
	private int staves;
	private ArrayList<Clef> clefs;

	public ArrayList<Clef> getClefs() {
		return clefs;
	}

	public int getDivision() {
		return division;
	}

	public Key getKey() {
		return key;
	}

	public int getStaves() {
		return staves;
	}
}
