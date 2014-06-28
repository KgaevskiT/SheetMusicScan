package imageProcessing.colorMode;

import imageProcessing.processes.staves.StavesAnalyzer;
import imageProcessing.tools.Tools;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import timer.Timer;

public class VisualMode {
	public static boolean enable;
	private BufferedImage image;
	Timer extra = new Timer();

	private static VisualMode instance = new VisualMode();

	public static Color BACKGROUND = Color.BLACK;
	public static Color OBJECT = Color.WHITE;
	public static Color STAVES = Color.DARK_GRAY;
	public static Color MEASURE = Color.GREEN;
	public static Color QUARTER = Color.red;

	private static int NOTE_RADIUS = 10;

	public static void addNote(int x, int y) {
		Tools.objectSetColor(instance.image, x, y, VisualMode.QUARTER,
				NOTE_RADIUS);
	}

	public static void addStaves(BufferedImage staves, BufferedImage content) {
		instance.image = StavesAnalyzer.completeStaves(staves);
		replace(instance.image, VisualMode.OBJECT, VisualMode.STAVES);
		Tools.merge(instance.image, content);
	}

	public static void colorMeasure(int x, int y) {
		Tools.objectSetColor(instance.image, x, y, VisualMode.MEASURE);
	}

	public static VisualMode getInstance() {
		return instance;
	}

	private static void replace(BufferedImage image, Color origin, Color change) {
		for (int h = 0; h < image.getHeight(); h++) {
			for (int w = 0; w < image.getWidth(); w++) {
				if (image.getRGB(w, h) == origin.getRGB()) {
					image.setRGB(w, h, change.getRGB());
				}
			}
		}
	}

	public static void save() {
		try {
			ImageIO.write(instance.image, "png", new File("output.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private VisualMode() {

	}

	public BufferedImage getImage() {
		return this.image;
	}
}
