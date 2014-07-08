package imageProcessing.processes.measures;

import imageProcessing.filters.Erosion;
import imageProcessing.filters.structElt.StructElt;
import imageProcessing.processes.ElementImage;
import imageProcessing.processes.staves.Staff;
import imageProcessing.processes.staves.StavesAnalyzer;
import imageProcessing.tools.ObjectSize;
import imageProcessing.tools.Tools;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MeasureFinder {
	private final ArrayList<Staff> staves;

	public MeasureFinder(ArrayList<Staff> staves) {
		this.staves = staves;
	}

	public ArrayList<ElementImage> getMeasureImages(BufferedImage image) {
		ArrayList<ElementImage> measureImages = new ArrayList<ElementImage>();

		BufferedImage measures = new Erosion(StructElt.MEASURE_BAR)
				.apply(image);

		for (int h = 0; h < measures.getHeight(); h++) {
			for (int w = 0; w < measures.getWidth(); w++) {
				if (measures.getRGB(w, h) == Color.white.getRGB()) {

					Integer staffNb = StavesAnalyzer.getStaffNumber(staves, h);

					// If measure is in a staff
					if (staffNb != null) {

						// Parallelize: getObjectWidth() takes a lot of time !
						ObjectSize objectSize = new ObjectSize(image);
						int width = objectSize.getObjectWidth(w, h);
						if (width < 20) {
							MeasureImage measure = new MeasureImage(w, h,
									staffNb, staffNb * measures.getWidth() + w,
									false);

							ElementImage.addElt(measureImages, measure);

							// Erase measure bar
							Tools.eraseShape(measures, w, h);
							// Erase measure bar in main image
							// TODO is it a good idea ? Don't we lose
							// information ?
							Tools.eraseShape(image, w, h);
						} else {
							// Erase all fake measure bars
							Tools.eraseObjectParts(measures, w, h,
									Tools.duplicate(image));
						}
					}
				}
			}
		}
		return measureImages;
	}
}
