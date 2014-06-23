package imageProcessing.processes;

import imageProcessing.processes.measures.MeasureFinder;
import imageProcessing.processes.notes.BlackNoteFinder;
import imageProcessing.processes.staves.Staff;
import imageProcessing.processes.staves.StavesAnalyzer;
import imageProcessing.processes.staves.StavesEraser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImageReader {
	public ArrayList<ElementImage> getElementImages(BufferedImage image) {
		// Reduce image to main data
		// image = Tools.ignoreSurround(image);

		// Erase staves on image
		StavesEraser stavesEraser = new StavesEraser();
		image = stavesEraser.EraseStaffs(image);

		// Get staves
		BufferedImage stavesImage = stavesEraser.getStaffs();
		stavesImage = stavesEraser.completeStaffs(stavesImage);
		StavesAnalyzer stavesAnalyzer = new StavesAnalyzer(stavesImage);
		ArrayList<Staff> staves = stavesAnalyzer.analyzeStaves();

		// Get black notes
		BlackNoteFinder blackNoteFinder = new BlackNoteFinder(staves);
		ArrayList<ElementImage> blackNoteImages = blackNoteFinder
				.getBlackNotesList(image);

		for (ElementImage note : blackNoteImages)
			System.out.println(note.toString());

		// TODO
		MeasureFinder measureFinder = new MeasureFinder(staves);
		ArrayList<ElementImage> measureImages = measureFinder
				.getMeasureImages(image);

		ArrayList<ElementImage> elementImages = mergeElements(blackNoteImages,
				measureImages);

		// TODO debug
		try {
			ImageIO.write(stavesImage, "png", new File("staves.png"));
			ImageIO.write(image, "png", new File("noStaves.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return elementImages;
	}

	private ArrayList<ElementImage> mergeElements(
			ArrayList<ElementImage> blackNoteImages,
			ArrayList<ElementImage> measureImages) {

		ArrayList<ElementImage> elementImages = new ArrayList<ElementImage>();

		int noteI = 0;
		int measureI = 0;
		while (noteI < blackNoteImages.size()
				|| measureI < measureImages.size()) {

			if (measureI == measureImages.size()) {
				elementImages.add(blackNoteImages.get(noteI));
				noteI++;
				continue;
			}
			if (noteI == blackNoteImages.size()) {
				elementImages.add(measureImages.get(measureI));
				measureI++;
				continue;
			}
			if (blackNoteImages.get(noteI).getIndex() < measureImages.get(
					measureI).getIndex()) {
				elementImages.add(blackNoteImages.get(noteI));
				noteI++;
			} else {
				elementImages.add(measureImages.get(measureI));
				measureI++;
			}
		}

		return elementImages;
	}
}
