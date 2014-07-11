package imageProcessing.processes.measures;

import imageProcessing.colorMode.VisualMode;
import imageProcessing.filters.Erosion;
import imageProcessing.filters.structElt.StructElt;
import imageProcessing.processes.ElementImage;
import imageProcessing.processes.staves.Staff;
import imageProcessing.processes.staves.StavesAnalyzer;
import imageProcessing.tools.ObjectEditor;
import imageProcessing.tools.ObjectSize;

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
				if (measures.getRGB(w, h) == VisualMode.OBJECT.getRGB()) {

					// If measure is in a staff
					Integer staffNb = StavesAnalyzer.getStaffNumber(staves, h);
					if (staffNb != null) {

						// Parallelize: getObjectWidth() takes a lot of time !
						int width = new ObjectSize().getObjectWidth(image, w,
								h, 20);
						if (width != 0 && width < 20) {
							MeasureImage measure = new MeasureImage(w, h,
									staffNb, staffNb * measures.getWidth() + w,
									false);

							ElementImage.addElt(measureImages, measure);

							new ObjectEditor().eraseShape(image, w, h);
						}
						new ObjectEditor().eraseShape(measures, w, h);
					}
				}
			}
		}

		return measureImages;
	}
}
