package imageProcessing.processes.measures;

import imageProcessing.colorMode.VisualMode;
import imageProcessing.filters.Erosion;
import imageProcessing.filters.structElt.StructElt;
import imageProcessing.processes.ElementImage;
import imageProcessing.processes.staves.Staff;
import imageProcessing.processes.staves.StavesAnalyzer;
import imageProcessing.tools.ObjectSize;
import imageProcessing.tools.Tools;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class MeasureFinder {
	private final ArrayList<Staff> staves;

	public MeasureFinder(ArrayList<Staff> staves) {
		this.staves = staves;
	}

	public ArrayList<ElementImage> getMeasureImages(BufferedImage image) {
		ArrayList<ElementImage> measureImages = new ArrayList<ElementImage>();

		BufferedImage measures = new Erosion(StructElt.MEASURE_BAR)
				.apply(image);

		// TODO save Measures for debug
		try {
			ImageIO.write(measures, "png", new File("measures.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int h = 0; h < measures.getHeight(); h++) {
			for (int w = 0; w < measures.getWidth(); w++) {
				if (measures.getRGB(w, h) == Color.white.getRGB()) {

					Integer staffNb = StavesAnalyzer.getStaffNumber(staves, h);

					// If measure is in a staff
					if (staffNb != null) {

						// Parallelize: getObjectWidth() takes a lot of time !
						ObjectSize objectSize = new ObjectSize(image, w, h);
						int width = objectSize.getObjectWidth();
						if (width < 20) {
							MeasureImage measure = new MeasureImage(w, h,
									staffNb, staffNb * measures.getWidth() + w,
									false);

							ElementImage.addElt(measureImages, measure);

							// Color measure
							if (VisualMode.enable) {
								VisualMode.colorMeasure(w, h);
							}

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
