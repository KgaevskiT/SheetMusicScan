package imageProcessing.colorMode;

import imageProcessing.processes.ElementImage;
import imageProcessing.processes.clefs.ClefImage;
import imageProcessing.processes.measures.MeasureImage;
import imageProcessing.processes.notes.NoteImage;
import imageProcessing.processes.rests.RestImage;
import imageProcessing.tools.ObjectEditor;
import imageProcessing.tools.Tools;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import music.Type;
import timer.Timer;

public class VisualMode {
	public static boolean enable;
	public static String name;
	private BufferedImage image;
	Timer extra = new Timer();

	private static VisualMode instance = new VisualMode();

	public static Color BACKGROUND = Color.BLACK;
	public static Color OBJECT = Color.WHITE;

	public static Color STAVES = new Color(32, 32, 32); // Dark grey
	public static Color MEASURE = new Color(64, 64, 64); // Dark grey
	public static Color CLEF = new Color(128, 64, 0); // Brown

	public static Color WHOLE = new Color(0, 128, 255); // Blue
	public static Color HALF = new Color(255, 255, 128); // Yellow
	public static Color QUARTER = new Color(255, 64, 64); // Red
	public static Color EIGHTH = new Color(64, 255, 64); // Green

	public static Color LONG = new Color(128, 0, 128); // Pink
	public static Color BREVE = new Color(0, 128, 128); // Blue/Green
	public static Color SEMIBREVE = new Color(0, 0, 128); // Dark blue
	public static Color MINIM = new Color(255, 255, 0); // Yellow
	public static Color CROTCHET = new Color(64, 0, 0); // Dark red
	public static Color QUAVER = new Color(0, 64, 0); // Dark green

	private static int NOTE_RADIUS = 20;

	public static void addClefs(ArrayList<ElementImage> clefs) {
		for (ElementImage elt : clefs) {
			ClefImage clef = (ClefImage) elt;
			new ObjectEditor().objectSetColor(instance.image, clef.getX(),
					clef.getY(), VisualMode.CLEF);
		}
	}

	public static void addMeasures(ArrayList<ElementImage> measures) {
		for (ElementImage elt : measures) {
			MeasureImage measure = (MeasureImage) elt;
			new ObjectEditor().objectSetColor(instance.image, measure.getX(),
					measure.getY(), VisualMode.MEASURE);
		}
	}

	public static void addNotes(ArrayList<ElementImage> notes) {
		for (ElementImage elt : notes) {
			NoteImage note = (NoteImage) elt;
			if (note.getType() == Type.WHOLE) {
				new ObjectEditor().objectSetColor(instance.image, note.getX(),
						note.getY(), VisualMode.WHOLE, NOTE_RADIUS);
			} else if (note.getType() == Type.HALF) {
				new ObjectEditor().objectSetColor(instance.image, note.getX(),
						note.getY(), VisualMode.HALF, NOTE_RADIUS);
			} else if (note.getType() == Type.QUARTER) {
				new ObjectEditor().objectSetColor(instance.image, note.getX(),
						note.getY(), VisualMode.QUARTER, NOTE_RADIUS);
			} else if (note.getType() == Type.EIGHTH) {
				new ObjectEditor().objectSetColor(instance.image, note.getX(),
						note.getY(), VisualMode.EIGHTH, NOTE_RADIUS);
			} else {
				System.err.print("Error: Unknown note type ["
						+ note.getType().getValue() + "]");
			}
		}
	}

	public static void addRests(ArrayList<ElementImage> rests) {
		for (ElementImage elt : rests) {
			RestImage rest = (RestImage) elt;

			if (rest.getType() == Type.QUADRUPLE) {
				new ObjectEditor().objectSetColor(instance.image, rest.getX(),
						rest.getY(), VisualMode.LONG);
			} else if (rest.getType() == Type.DOUBLE) {
				new ObjectEditor().objectSetColor(instance.image, rest.getX(),
						rest.getY(), VisualMode.BREVE);
			} else if (rest.getType() == Type.WHOLE) {
				new ObjectEditor().objectSetColor(instance.image, rest.getX(),
						rest.getY(), VisualMode.SEMIBREVE);
			} else if (rest.getType() == Type.HALF) {
				new ObjectEditor().objectSetColor(instance.image, rest.getX(),
						rest.getY(), VisualMode.MINIM);
			} else if (rest.getType() == Type.QUARTER) {
				new ObjectEditor().objectSetColor(instance.image, rest.getX(),
						rest.getY(), VisualMode.CROTCHET);
			} else if (rest.getType() == Type.EIGHTH) {
				new ObjectEditor().objectSetColor(instance.image, rest.getX(),
						rest.getY(), VisualMode.QUAVER);
			}
		}
	}

	public static void addStaves(BufferedImage staves, BufferedImage content) {
		instance.image = staves;
		replace(instance.image, VisualMode.OBJECT, VisualMode.STAVES);
		Tools.merge(instance.image, content);
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
			ImageIO.write(instance.image, "png", new File(name + ".png"));
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
