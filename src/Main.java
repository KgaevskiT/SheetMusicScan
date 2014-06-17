import imageProcessing.processes.notes.BlackNoteFinder;
import imageProcessing.processes.notes.NoteImage;
import imageProcessing.processes.staves.Staves;
import imageProcessing.processes.staves.StavesAnalyzer;
import imageProcessing.processes.staves.StavesEraser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Main {

	static String workingDir = "C:/Users/Thomas/Documents/MusicSheetScan/";
	static String testImage = workingDir + "Training_BW/BW_0001.png";// "Ballade pour Adeline.png";
	static String outputFile = workingDir + "output.png";

	public static void deleteStaffs(BufferedImage image) {
		StavesEraser staffEraser = new StavesEraser();
		image = staffEraser.EraseStaffs(image);
		BufferedImage stavesImage = staffEraser.getStaffs();
		stavesImage = staffEraser.completeStaffs(stavesImage);

		StavesAnalyzer stavesAnalyzer = new StavesAnalyzer(stavesImage);
		Staves staves = stavesAnalyzer.analyzeStaves();

		BlackNoteFinder blackNoteFinder = new BlackNoteFinder();
		ArrayList<NoteImage> blackNotes = blackNoteFinder.getBlackNotesList(
				image, staves);

		for (NoteImage note : blackNotes) {
			System.out.println(note.toString());
		}

		try {
			ImageIO.write(stavesImage, "png",
					new File(workingDir + "staff.png"));
			ImageIO.write(image, "png", new File(outputFile));
		} catch (IOException e) {
			System.err.println("Error: Couldn't write image");
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long tStart = System.currentTimeMillis();

		try {
			BufferedImage image = ImageIO.read(new File(testImage));
			deleteStaffs(image);

		} catch (IOException e) {
			System.err.println("Error: Couldn't read image : " + testImage);
			e.printStackTrace();
		}

		long tEnd = System.currentTimeMillis();
		printTimeElapsed(tStart, tEnd);
	}

	public static void printTimeElapsed(long tStart, long tEnd) {
		long elapsed = tEnd - tStart;
		long minutes = elapsed / 60000;
		long seconds = elapsed / 1000 - minutes * 60;
		long millis = elapsed - seconds * 1000 - minutes * 60000;
		System.out.print("Time elapsed: ");
		if (minutes != 0)
			System.out.print(minutes + "'");
		if (seconds != 0 || minutes != 0)
			System.out.print(seconds + "\"");
		System.out.println(millis);
	}
}
