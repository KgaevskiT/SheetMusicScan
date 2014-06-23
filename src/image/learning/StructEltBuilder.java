package image.learning;

import imageProcessing.filters.structElt.StructElt;
import imageProcessing.tools.Tools;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class StructEltBuilder {

	private BufferedImage buildStructElt(String path,
			LearningParameter parameter) throws FileNotFoundException {
		BufferedImage mean = new BufferedImage(parameter.getWidth(),
				parameter.getHeight(), BufferedImage.TYPE_INT_RGB);
		Tools.fill(mean, Color.WHITE);
		File folder = new File(path + "learning/");

		if (!folder.exists()) {
			throw new FileNotFoundException();
		}

		int weight = 256 / folder.listFiles().length;

		// Init with learning images
		for (File file : folder.listFiles()) {
			try {
				BufferedImage image = ImageIO.read(file);

				for (int h = 0; h < image.getHeight(); h++) {
					for (int w = 0; w < image.getWidth(); w++) {
						if (image.getRGB(w, h) == Color.WHITE.getRGB()) {
							int red = new Color(mean.getRGB(w, h)).getRed();
							red = red - weight < 0 ? 0 : red - weight;
							Color color = new Color(red, 0, 0);
							mean.setRGB(w, h, color.getRGB());
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// TODO
		try {
			ImageIO.write(mean, "png", new File(path + "mean.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Binarize
		for (int h = 0; h < mean.getHeight(); h++) {
			for (int w = 0; w < mean.getWidth(); w++) {
				if (new Color(mean.getRGB(w, h)).getRed() > parameter.getStep()) {
					mean.setRGB(w, h, Color.BLACK.getRGB());
				} else {
					mean.setRGB(w, h, Color.WHITE.getRGB());
				}
			}
		}

		// Resize
		mean = Tools.ignoreSurround(mean);

		// Save
		try {
			ImageIO.write(mean, "png", new File(path + "elt.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mean;
	}

	public StructElt getStructElt(String type) {
		BufferedImage image = null;
		String path = "resources/" + type + "/";
		LearningParameter parameter;

		switch (type) {
		case "quarter":
			parameter = LearningParameter.QUARTER;
			break;
		case "measure":
			parameter = LearningParameter.MEASURE;
			break;
		default:
			parameter = null;
			break;
		}

		File file = new File(path + "elt.png");
		if (file.exists()) {
			try {
				image = ImageIO.read(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				image = buildStructElt(path, parameter);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.err.println("Error: " + path + " does not exist");
				System.exit(1);
			}
		}

		return new StructElt(image);
	}
}
