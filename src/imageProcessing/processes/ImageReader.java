package imageProcessing.processes;

import imageProcessing.colorMode.VisualMode;
import imageProcessing.processes.measures.MeasureFinder;
import imageProcessing.processes.measures.MeasureImage;
import imageProcessing.processes.notes.BlackNoteFinder;
import imageProcessing.processes.notes.EighthFinder;
import imageProcessing.processes.notes.NoteImage;
import imageProcessing.processes.staves.Staff;
import imageProcessing.processes.staves.StavesAnalyzer;
import imageProcessing.processes.staves.StavesEraser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import timer.Timer;

public class ImageReader {
	private ArrayList<ElementImage> addLineCrossing(
			ArrayList<ElementImage> eltImages) {
		for (int i = 0; i + 1 < eltImages.size(); i++) {
			if (eltImages.get(i).getStaff() != eltImages.get(i + 1).getStaff()) {
				if (eltImages.get(i).getClass() == NoteImage.class
						&& eltImages.get(i + 1).getClass() == MeasureImage.class) {
					((MeasureImage) eltImages.get(i + 1)).setNewSystem(true);
				} else if (eltImages.get(i).getClass() == MeasureImage.class
						&& eltImages.get(i + 1).getClass() == NoteImage.class) {
					eltImages.get(i).setStaff(eltImages.get(i).getStaff() + 1);
					((MeasureImage) eltImages.get(i)).setNewSystem(true);
				} else if (eltImages.get(i).getClass() == MeasureImage.class
						&& eltImages.get(i + 1).getClass() == MeasureImage.class) {
					((MeasureImage) eltImages.get(i + 1)).setNewSystem(true);
					eltImages.remove(i);
				} else if (eltImages.get(i).getClass() == NoteImage.class
						&& eltImages.get(i + 1).getClass() == NoteImage.class) {
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
		image = stavesEraser.EraseStaffs(image);
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

		// Get black notes
		BlackNoteFinder blackNoteFinder = new BlackNoteFinder(staves);
		ArrayList<ElementImage> blackNoteImages = blackNoteFinder
				.getBlackNotesList(image);
		timer.step("[Main] Get black notes");

		// Get eighths notes
		EighthFinder eighthFinder = new EighthFinder(blackNoteImages, image);
		blackNoteImages = eighthFinder.findEighth();
		timer.step("[Main] Get eighths notes");

		ArrayList<ElementImage> elementImages = mergeElements(blackNoteImages,
				measureImages);

		// Write output (color)
		if (VisualMode.enable) {
			VisualMode.save();
		}

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

		return addLineCrossing(elementImages);
	}
}
