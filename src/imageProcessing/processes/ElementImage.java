package imageProcessing.processes;

import imageProcessing.processes.notes.NoteImage;

import java.util.ArrayList;

public class ElementImage {
	public static ArrayList<ElementImage> addElt(ArrayList<ElementImage> list,
			ElementImage elt) {

		int i = 0;
		while (i < list.size() && elt.getIndex() > list.get(i).getIndex()) {
			i++;
		}

		boolean alreadyExist = false;
		if (list.size() != 0) {

			// Check next
			if (i != list.size()) {
				if (Math.abs(elt.getIndex() - list.get(i).getIndex()) < 10) {
					if (elt.getClass() == NoteImage.class) {
						if (((NoteImage) elt).getStaffPosition().equals(
								((NoteImage) list.get(i)).getStaffPosition())) {
							alreadyExist = true;
						}
					} else {
						alreadyExist = true;
					}
				}
			}
			// Check previous
			if ((i != 0)
					&& Math.abs(elt.getIndex() - list.get(i - 1).getIndex()) < 10) {
				if (elt.getClass() == NoteImage.class) {
					if (((NoteImage) elt).getStaffPosition().equals(
							((NoteImage) list.get(i - 1)).getStaffPosition())) {
						alreadyExist = true;
					}
				} else {
					alreadyExist = true;
				}
			}
		}
		if (!alreadyExist)
			list.add(i, elt);

		return list;
	}

	protected final int x;
	protected final int staff;

	protected final int index;

	protected ElementImage(int x, int staff, int index) {
		this.x = x;
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

	@Override
	public String toString() {
		return "x = " + x + "; staff = " + staff;
	}
}
