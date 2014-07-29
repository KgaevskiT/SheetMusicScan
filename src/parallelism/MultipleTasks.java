package parallelism;

import imageProcessing.colorMode.VisualMode;
import imageProcessing.processes.ElementImage;
import imageProcessing.processes.ImageReader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

import javax.imageio.ImageIO;

import music.Attributes;
import music.Part;
import music.ScorePartwise;
import music.initializer.MusicInitializer;
import music.initializer.MusicInitializer.PartsAndTitle;
import music.xmlWriting.MusicXMLWriter;

public class MultipleTasks extends RecursiveTask<PartsAndTitle> {

	public MultipleTasks(File file) {
		if (file.isDirectory()) {
			FilesScanner fs = new FilesScanner();
			allFiles = fs.getFilesInRep(file);
			oneFile = file;
		} else
			oneFile = file;
	}

	private static final long serialVersionUID = 5558415781653492418L;
	ArrayList<File> allFiles;
	File oneFile;

	@Override
	protected PartsAndTitle compute() {
		if (allFiles != null) {
			MultipleTasks task;
			List<MultipleTasks> tasks = new ArrayList<MultipleTasks>();
			for (File file : allFiles) {
				task = new MultipleTasks(file);
				tasks.add(task);
				task.fork();
			}
			ArrayList<Part> listParts = new ArrayList<Part>();
			for (MultipleTasks t : tasks)
				listParts.addAll(t.join().getParts());

			// Partwise
			ScorePartwise partwise = new ScorePartwise("1.0",
					oneFile.getName(), listParts);
			writeMusicXML(partwise, oneFile.getName());
		} else {
			return oneFile(this.oneFile);
		}
		return null;
	}

	private static PartsAndTitle oneFile(File input) {
		String name = input.getName();
		name = name.substring(0, name.length() - 4);

		VisualMode.enable = true;
		VisualMode.name = name;
		BufferedImage image = null;

		try {
			image = ImageIO.read(input);
		} catch (IOException e) {
			System.err
					.println("Error: Couldn't read image: " + input.getName());
			e.printStackTrace();
		}
		ImageReader imageReader = new ImageReader();
		ArrayList<ElementImage> elementImage = imageReader
				.getElementImages(image);

		Attributes attributes = Attributes.DEFAULT_ATTRIBUTES;

		MusicInitializer musicInitializer = new MusicInitializer();
		PartsAndTitle partsAndTitle = musicInitializer.getMusic(name,
				attributes, elementImage);
		return partsAndTitle;

	}

	private static void writeMusicXML(ScorePartwise partwise, String name) {
		MusicXMLWriter xmlWriter = new MusicXMLWriter();
		xmlWriter.writeMusicXML(name + ".xml", partwise);
	}
}
