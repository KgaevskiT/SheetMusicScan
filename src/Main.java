import imageProcessing.colorMode.VisualMode;
import imageProcessing.processes.ElementImage;
import imageProcessing.processes.ImageReader;
import parallelism.MultipleTasks;

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
import timer.Timer;

public class Main {
	private static String usage = "Usage: java -jar SheetMusicScan [-r] (image/forlder)";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println(usage);
			System.exit(1);
		}
		
		switch(args.length)
		{
	
		case 1 :
			oneFile(new File(args[0]));
			break;
		
		case 2 :
			if (args[0].compareTo("-r") != 0)
			{
				System.err.println(usage);
				System.exit(1);
			}
			else
				multipleFiles(new File(args[1]));
			break;
			
		default :
			System.err.println(usage);
			System.exit(1);
			break;
			
		}
	}
	
	private static void multipleFiles(File rep)
	{
		
	    ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() + 1);
	    // +1 car le premier pool ne fait qu'attendre les autres
	    MultipleTasks myTasks = new MultipleTasks(rep);
	    
	    Long start = System.currentTimeMillis();
	    
	    pool.invoke(myTasks);
	    
	    Long end = System.currentTimeMillis();
	    System.out.println("Temps de traitement : " + (end - start));
	}
	
	private static void oneFile(File input)
	{
		Timer timer = new Timer();

		String name = input.getName();
		name = name.substring(0, name.length() - 4);

		VisualMode.enable = true;
		VisualMode.name = name;

		try {
			BufferedImage image = ImageIO.read(input);
			writeMusicXML(image, name);
		} catch (IOException e) {
			System.err.println("Error: Couldn't read image: " + input.getName());
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
		PartsAndTitle partsAndTitle = musicInitializer.getMusic(name, attributes,
				elementImage);
		// Partwise
		ScorePartwise partwise = new ScorePartwise("1.0", partsAndTitle.getTitle(), partsAndTitle.getParts());


		MusicXMLWriter xmlWriter = new MusicXMLWriter();
		xmlWriter.writeMusicXML(name + ".xml", partwise);
	}
}
