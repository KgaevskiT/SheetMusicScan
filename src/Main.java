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

	static String testImage = "Training_BW/BW_0002.png";

	// static String testImage = "Training_BW/BW_0022.png";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long tStart = System.currentTimeMillis();

		File input = new File(testImage);
		String name = input.getName();
		VisualMode.enable = true;
		try {
			BufferedImage image = ImageIO.read(input);

			writeMusicXML(image, name);

		} catch (IOException e) {
			System.err.println("Error: Couldn't read image : " + testImage);
			e.printStackTrace();
		}

		long tEnd = System.currentTimeMillis();
		System.out.println("Time elapsed: "
				+ Timer.getTimeElapsed(tStart, tEnd));
	}

	public static void writeMusicXML(BufferedImage image, String name) {
		ImageReader imageReader = new ImageReader();
		ArrayList<ElementImage> elementImage = imageReader
				.getElementImages(image);

		Attributes attributes = Attributes.DEFAULT_ATTRIBUTES;

		MusicInitializer musicInitializer = new MusicInitializer();
		ScorePartwise partwise = musicInitializer.getMusic(name, attributes,
				elementImage);

		MusicXMLWriter xmlWriter = new MusicXMLWriter();
		xmlWriter.writeMusicXML("music.xml", partwise);
	}
}
