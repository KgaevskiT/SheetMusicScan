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

import music.Attributes;
import music.ScorePartwise;
import music.initializer.MusicInitializer;
import music.xmlWriting.MusicXMLWriter;

public class Main {

	static String testImage = "Training_BW/BW_0005.png";

	// static String testImage = "Training_BW/BW_0022.png";

	public static void compute(BufferedImage image, String name) {
		StavesEraser staffEraser = new StavesEraser();
		image = staffEraser.EraseStaffs(image);
		BufferedImage stavesImage = staffEraser.getStaffs();
		stavesImage = staffEraser.completeStaffs(stavesImage);

		StavesAnalyzer stavesAnalyzer = new StavesAnalyzer(stavesImage);
		Staves staves = stavesAnalyzer.analyzeStaves();

		BlackNoteFinder blackNoteFinder = new BlackNoteFinder();
		ArrayList<NoteImage> blackNotes = blackNoteFinder.getBlackNotesList(
				image, staves);

		Attributes attributes = Attributes.DEFAULT_ATTRIBUTES;

		MusicInitializer musicInitializer = new MusicInitializer();
		ScorePartwise partwise = musicInitializer.getMusic(name, attributes,
				blackNotes);

		MusicXMLWriter xmlWriter = new MusicXMLWriter();
		xmlWriter.writeMusicXML("music.xml", partwise);

		try {
			ImageIO.write(stavesImage, "png", new File("staves.png"));
			ImageIO.write(image, "png", new File("noStaves.png"));
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

		File input = new File(testImage);
		String name = input.getName();
		try {
			BufferedImage image = ImageIO.read(input);
			compute(image, name);

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
