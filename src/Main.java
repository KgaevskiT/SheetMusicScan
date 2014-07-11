import imageProcessing.colorMode.VisualMode;
import imageProcessing.processes.ElementImage;
import imageProcessing.processes.ImageReader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import music.Attributes;
import music.ScorePartwise;
import music.initializer.MusicInitializer;
import music.xmlWriting.MusicXMLWriter;
import timer.Timer;

public class Main {
	private static String usage = "Usage: java -jar SheetMusicScan image";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println(usage);
			System.exit(1);
		}

		Timer timer = new Timer();
		File input = new File(args[0]);

		if (!input.exists()) {
			System.err.println("Error: File do not exist: " + args[0]);
			System.exit(1);
		}

		String name = input.getName();
		name = name.substring(0, name.length() - 4);

		VisualMode.enable = true;
		VisualMode.name = name;

		try {
			BufferedImage image = ImageIO.read(input);
			writeMusicXML(image, name);
		} catch (IOException e) {
			System.err.println("Error: Couldn't read image: " + args[0]);
			e.printStackTrace();
		}

		timer.step("Total time ");
	}

	private static void writeMusicXML(BufferedImage image, String name) {
		ImageReader imageReader = new ImageReader();
		ArrayList<ElementImage> elementImage = imageReader
				.getElementImages(image);

		Attributes attributes = Attributes.DEFAULT_ATTRIBUTES;

		MusicInitializer musicInitializer = new MusicInitializer();
		ScorePartwise partwise = musicInitializer.getMusic(name, attributes,
				elementImage);

		MusicXMLWriter xmlWriter = new MusicXMLWriter();
		xmlWriter.writeMusicXML(name + ".xml", partwise);
	}
}
