package imageProcessing.processes;

import imageProcessing.colorMode.VisualMode;
import imageProcessing.processes.notes.NoteImage;

import java.util.ArrayList;

public class ElementImage {

	private static int ELT_MARGIN = 10;

	public static boolean addElt(ArrayList<ElementImage> list, ElementImage elt) {

		int i = 0;
		while (i < list.size() && elt.getIndex() > list.get(i).getIndex()) {
			i++;
		}

		boolean alreadyExist = false;
		if (list.size() != 0) {

			// Check next
			if (i != list.size()
					&& Math.abs(elt.getX() - list.get(i).getX()) < ELT_MARGIN
					&& Math.abs(elt.getY() - list.get(i).getY()) < ELT_MARGIN) {
				alreadyExist = true;

			}
			// Check previous
			if (i != 0
					&& Math.abs(elt.getX() - list.get(i - 1).getX()) < ELT_MARGIN
					&& Math.abs(elt.getY() - list.get(i - 1).getY()) < ELT_MARGIN) {
				alreadyExist = true;
			}
		}
		if (!alreadyExist) {
			list.add(i, elt);
			if (elt.getClass() == NoteImage.class && VisualMode.enable) {
				VisualMode.addNote(elt.getX(), elt.getY());
			}
		}

		return !alreadyExist;
	}

	protected final int x;
	protected final int y;
	protected int staff;
	protected final int index;

	protected ElementImage(int x, int y, int staff, int index) {
		this.x = x;
		this.y = y;
		this.staff = staff;
		this.index = index;
	}

	public int getIndex() {
		return this.index;
	}

	public int getStaff() {
		return this.staff;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setStaff(int staff) {
		this.staff = staff;
	}

	@Override
	public String toString() {
		return "x = " + x + "; staff = " + staff;
	}
}
