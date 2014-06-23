package imageProcessing.processes.measures;

import imageProcessing.filters.Erode;
import imageProcessing.filters.structElt.StructElt;
import imageProcessing.processes.ElementImage;
import imageProcessing.processes.staves.Staff;
import imageProcessing.processes.staves.StavesAnalyzer;
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

		BufferedImage measures = new Erode(StructElt.MEASURE_BAR).apply(image);

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

					int staffNb = StavesAnalyzer.getStaffNumber(staves, h);

					// If measure is in a staff
					if (staffNb != -1) {
						MeasureImage measure = new MeasureImage(w, staffNb,
								staffNb * measures.getWidth() + w);

						ElementImage.addElt(measureImages, measure);
					}
					// Erase measure bar
					Tools.eraseShape(measures, w, h);
				}
			}
		}
		return measureImages;
	}
}
