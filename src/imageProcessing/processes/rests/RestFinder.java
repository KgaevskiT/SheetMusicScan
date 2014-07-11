package imageProcessing.processes.rests;

import imageProcessing.colorMode.VisualMode;
import imageProcessing.filters.Erosion;
import imageProcessing.filters.structElt.StructElt;
import imageProcessing.processes.ElementImage;
import imageProcessing.processes.staves.Staff;
import imageProcessing.processes.staves.StavesAnalyzer;
import imageProcessing.tools.ObjectEditor;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import music.Type;

public class RestFinder {
	private final ArrayList<Staff> staves;
	private final BufferedImage image;
	private final ArrayList<ElementImage> rests;

	public RestFinder(ArrayList<Staff> staves, BufferedImage image) {
		super();
		this.staves = staves;
		this.image = image;
		this.rests = new ArrayList<ElementImage>();
	}

	private void findBreves() {
		BufferedImage crotchetsImage = new Erosion(StructElt.BREVE)
				.apply(this.image);

		for (int h = 0; h < image.getHeight(); h++) {
			for (int w = 0; w < image.getWidth(); w++) {
				if (crotchetsImage.getRGB(w, h) == VisualMode.OBJECT.getRGB()) {
					Integer staff = StavesAnalyzer.getStaffNumber(staves, h);
					if (staff != null) {
						RestImage rest = new RestImage(w, h, staff, staff
								* image.getWidth() + w, Type.DOUBLE);
						ElementImage.addElt(rests, rest);
						new ObjectEditor().eraseShape(image, w, h);
					}
				}
			}
		}
	}

	private void findCrotchets() {
		BufferedImage crotchetsImage = new Erosion(StructElt.CROTCHET)
				.apply(this.image);

		for (int h = 0; h < image.getHeight(); h++) {
			for (int w = 0; w < image.getWidth(); w++) {
				if (crotchetsImage.getRGB(w, h) == VisualMode.OBJECT.getRGB()) {
					Integer staff = StavesAnalyzer.getStaffNumber(staves, h);
					if (staff != null) {
						RestImage rest = new RestImage(w, h, staff, staff
								* image.getWidth() + w, Type.QUARTER);
						ElementImage.addElt(rests, rest);
						new ObjectEditor().eraseShape(image, w, h);
					}
				}
			}
		}
	}

	private void findLongs() {
		BufferedImage crotchetsImage = new Erosion(StructElt.LONG)
				.apply(this.image);

		for (int h = 0; h < image.getHeight(); h++) {
			for (int w = 0; w < image.getWidth(); w++) {
				if (crotchetsImage.getRGB(w, h) == VisualMode.OBJECT.getRGB()) {
					Integer staff = StavesAnalyzer.getStaffNumber(staves, h);
					if (staff != null) {
						RestImage rest = new RestImage(w, h, staff, staff
								* image.getWidth() + w, Type.QUADRUPLE);
						ElementImage.addElt(rests, rest);
						new ObjectEditor().eraseShape(image, w, h);
					}
				}
			}
		}
	}

	private void findMinimSemibreves() {
		BufferedImage crotchetsImage = new Erosion(StructElt.MINIM_SEMIBREVE)
				.apply(this.image);

		for (int h = 0; h < image.getHeight(); h++) {
			for (int w = 0; w < image.getWidth(); w++) {
				if (crotchetsImage.getRGB(w, h) == VisualMode.OBJECT.getRGB()) {
					Integer staffNb = StavesAnalyzer.getStaffNumber(staves, h);
					if (staffNb != null) {
						Type type = getType(staffNb, w, h);

						RestImage rest = new RestImage(w, h, staffNb, staffNb
								* image.getWidth() + w, type);
						ElementImage.addElt(rests, rest);
						new ObjectEditor().eraseShape(image, w, h);
					}
				}
			}
		}
	}

	private void findQuavers() {
		BufferedImage crotchetsImage = new Erosion(StructElt.QUAVER)
				.apply(this.image);

		for (int h = 0; h < image.getHeight(); h++) {
			for (int w = 0; w < image.getWidth(); w++) {
				if (crotchetsImage.getRGB(w, h) == VisualMode.OBJECT.getRGB()) {
					Integer staff = StavesAnalyzer.getStaffNumber(staves, h);
					if (staff != null) {
						RestImage rest = new RestImage(w, h, staff, staff
								* image.getWidth() + w, Type.EIGHTH);
						ElementImage.addElt(rests, rest);
						new ObjectEditor().eraseShape(image, w, h);
					}
				}
			}
		}
	}

	public ArrayList<ElementImage> findRests() {
		findLongs();
		findBreves();
		findMinimSemibreves();
		findCrotchets();
		findQuavers();
		return rests;
	}

	private Type getType(int staffNb, int x, int y) {
		Staff staff = this.staves.get(staffNb);
		int sup = y;
		int inf = y;
		for (; this.image.getRGB(x, sup) == VisualMode.OBJECT.getRGB(); sup--)
			;
		for (; this.image.getRGB(x, inf) == VisualMode.OBJECT.getRGB(); inf++)
			;

		if (Math.abs(sup - staff.getLine(3)) < Math.abs(inf - staff.getLine(2))) {
			return Type.WHOLE;
		} else {
			return Type.HALF;
		}
	}
}
