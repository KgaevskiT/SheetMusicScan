package imageProcessing.processes;

import imageProcessing.colorMode.VisualMode;
import imageProcessing.processes.clefs.ClefFinder;
import imageProcessing.processes.measures.MeasureFinder;
import imageProcessing.processes.measures.MeasureImage;
import imageProcessing.processes.notes.BlackNoteFinder;
import imageProcessing.processes.notes.EighthFinder;
import imageProcessing.processes.notes.WholeFinder;
import imageProcessing.processes.rests.RestFinder;
import imageProcessing.processes.staves.Staff;
import imageProcessing.processes.staves.StavesAnalyzer;
import imageProcessing.processes.staves.StavesEraser;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import timer.Timer;

public class ImageReader {
	private ArrayList<ElementImage> addLineCrossing(
			ArrayList<ElementImage> eltImages) {
		for (int i = 0; i + 1 < eltImages.size(); i++) {
			if (eltImages.get(i).getStaff() != eltImages.get(i + 1).getStaff()) {
				if (eltImages.get(i).getClass() != MeasureImage.class
						&& eltImages.get(i + 1).getClass() == MeasureImage.class) {
					((MeasureImage) eltImages.get(i + 1)).setNewSystem(true);
				} else if (eltImages.get(i).getClass() == MeasureImage.class
						&& eltImages.get(i + 1).getClass() != MeasureImage.class) {
					eltImages.get(i).setStaff(eltImages.get(i).getStaff() + 1);
					((MeasureImage) eltImages.get(i)).setNewSystem(true);
				} else if (eltImages.get(i).getClass() == MeasureImage.class
						&& eltImages.get(i + 1).getClass() == MeasureImage.class) {
					((MeasureImage) eltImages.get(i + 1)).setNewSystem(true);
					eltImages.remove(i);
				} else if (eltImages.get(i).getClass() != MeasureImage.class
						&& eltImages.get(i + 1).getClass() != MeasureImage.class) {
					eltImages.add(i + 1,
							new MeasureImage(0, 0, eltImages.get(i + 1)
									.getStaff(), 0, true));
				}
			}
		}

		while (eltImages.get(eltImages.size() - 1).getClass() == MeasureImage.class) {
			eltImages.remove(eltImages.size() - 1);
		}

		return eltImages;
	}

	public ArrayList<ElementImage> getElementImages(BufferedImage image) {
		Timer timer = new Timer();

		// Reduce image to main data
		// image = Tools.ignoreSurround(image);

		// Erase staves on image
		StavesEraser stavesEraser = new StavesEraser();
		image = stavesEraser.EraseStaves(image);
		timer.step("[Main] Erase staves");

		// Delete staves
		BufferedImage stavesImage = stavesEraser.getStaffs();
		StavesAnalyzer stavesAnalyzer = new StavesAnalyzer(stavesImage);
		timer.step("[Main] Delete staves");

		// Get staves for visual
		VisualMode visualMode;
		if (VisualMode.enable) {
			Timer t = new Timer();
			VisualMode.addStaves(stavesImage, image);
			t.step("\t[ColorMode] Complete staves");
			timer.step();
		}

		// Analyze staves
		ArrayList<Staff> staves = stavesAnalyzer.analyzeStaves();
		timer.step("[Main] Analyze staves");

		// Get measure bars
		MeasureFinder measureFinder = new MeasureFinder(staves);
		ArrayList<ElementImage> measureImages = measureFinder
				.getMeasureImages(image);
		timer.step("[Main] Get measure bars");

		// Delete noise
		// image = new NoiseClosing().apply(image);
		// image = new NoiseOpening().apply(image);

		// Get clefs TODO !
		ClefFinder clefFinder = new ClefFinder(staves, image);
		ArrayList<ElementImage> clefs = clefFinder.findClefs();
		timer.step("[Main] Get clefs");

		// Get rests
		RestFinder restFinder = new RestFinder(staves, image);
		ArrayList<ElementImage> restImages = restFinder.findRests();
		timer.step("[Main] Get rests");

		// Get black notes
		BlackNoteFinder blackNoteFinder = new BlackNoteFinder(staves, image);
		ArrayList<ElementImage> blackNoteImages = blackNoteFinder
				.getBlackNotesList();
		timer.step("[Main] Get black notes");

		// Get eighth notes
		EighthFinder eighthFinder = new EighthFinder(blackNoteImages, image);
		blackNoteImages = eighthFinder.findEighth();
		timer.step("[Main] Get eighth notes");

		// Get whole notes
		WholeFinder wholeFinder = new WholeFinder(staves, image);
		ArrayList<ElementImage> wholeImages = wholeFinder.findWholes();
		timer.step("[Main] Get whole notes");

		blackNoteImages = mergeElements(blackNoteImages, wholeImages);

		// Add elements and write output (color)
		if (VisualMode.enable) {
			Timer t = new Timer();
			VisualMode.addClefs(clefs);
			VisualMode.addMeasures(measureImages);
			VisualMode.addNotes(blackNoteImages);
			VisualMode.addRests(restImages);
			VisualMode.save();
			t.step("\t[ColorMode] Color elements (Measures, Quarter, Half)");
			timer.step();
		}

		ArrayList<ElementImage> noteImages = mergeElements(blackNoteImages,
				restImages);
		ArrayList<ElementImage> elementImages = mergeElements(noteImages,
				measureImages);
		addLineCrossing(elementImages);

		return elementImages;
	}

	private ArrayList<ElementImage> mergeElements(
			ArrayList<ElementImage> elementImages1,
			ArrayList<ElementImage> elementImages2) {

		ArrayList<ElementImage> elementImages = new ArrayList<ElementImage>();

		int noteI = 0;
		int measureI = 0;
		while (noteI < elementImages1.size()
				|| measureI < elementImages2.size()) {

			if (measureI == elementImages2.size()) {
				elementImages.add(elementImages1.get(noteI));
				noteI++;
				continue;
			}
			if (noteI == elementImages1.size()) {
				elementImages.add(elementImages2.get(measureI));
				measureI++;
				continue;
			}
			if (elementImages1.get(noteI).getIndex() < elementImages2.get(
					measureI).getIndex()) {
				elementImages.add(elementImages1.get(noteI));
				noteI++;
			} else {
				elementImages.add(elementImages2.get(measureI));
				measureI++;
			}
		}

		return elementImages;
	}
}
