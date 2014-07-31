import imageProcessing.colorMode.VisualMode;
import imageProcessing.processes.ElementImage;
import imageProcessing.processes.ImageReader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

import javax.imageio.ImageIO;

import music.Attributes;
import music.ScorePartwise;
import music.initializer.MusicInitializer;
import music.initializer.MusicInitializer.PartsAndTitle;
import music.xmlWriting.MusicXMLWriter;
import parallelism.MultipleTasks;
import parallelism.Parallelizer;
import timer.Timer;

public class Main {

	public static void main(String[] args) {
		Option option = new Option(args);

		if (option.parallel()) {
			Parallelizer.setEnable(true);
		}
		if (option.recursive()) {
			File folder = option.getFile();
			multipleFiles(folder);
		} else {
			File file = option.getFile();
			oneFile(file);
		}
	}

	private static void multipleFiles(File rep) {

		// Nous récupérons le nombre de processeurs disponibles
		int processeurs = Runtime.getRuntime().availableProcessors();
		// Nous créons notre pool de thread pour nos tâches de fond
		ForkJoinPool pool = new ForkJoinPool(processeurs);
		MultipleTasks myTasks = new MultipleTasks(rep);

		Timer timer = new Timer();
		Long start = System.currentTimeMillis();
		// Nous lançons le traitement de notre tâche principale via le pool
		pool.invoke(myTasks);

		timer.step("Total time ");
	}

	private static void oneFile(File input) {
		Timer timer = new Timer();

		String name = input.getName();
		name = name.substring(0, name.length() - 4);

		VisualMode.enable = true;
		VisualMode.name = name;

		try {
			BufferedImage image = ImageIO.read(input);
			writeMusicXML(image, name);
		} catch (IOException e) {
			System.err
					.println("Error: Couldn't read image: " + input.getName());
			e.printStackTrace();
		}

		timer.step("Total time");
	}

	private static void writeMusicXML(BufferedImage image, String name) {
		ImageReader imageReader = new ImageReader();
		ArrayList<ElementImage> elementImage = imageReader
				.getElementImages(image);

		Attributes attributes = Attributes.DEFAULT_ATTRIBUTES;

		MusicInitializer musicInitializer = new MusicInitializer();
		PartsAndTitle partsAndTitle = musicInitializer.getMusic(name,
				attributes, elementImage);
		// Partwise
		ScorePartwise partwise = new ScorePartwise("1.0",
				partsAndTitle.getTitle(), partsAndTitle.getParts());

		MusicXMLWriter xmlWriter = new MusicXMLWriter();
		xmlWriter.writeMusicXML(name + ".xml", partwise);
	}
}
