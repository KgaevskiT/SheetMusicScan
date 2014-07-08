package imageProcessing.filters.structElt;

import image.learning.StructEltBuilder;
import imageProcessing.tools.Tools;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class StructElt {
	private static StructElt initBreve() {
		StructEltBuilder builder = new StructEltBuilder();
		StructElt structElt = builder.getStructElt("breve");

		return structElt;
	}

	private static StructElt initMinimSemibreve() {
		StructEltBuilder builder = new StructEltBuilder();
		StructElt structElt = builder.getStructElt("minim_semibreve");

		return structElt;
	}

	/*
	 * Object: WHITE ; Background: BLACK
	 */
	private final BufferedImage image;
	public static StructElt LineHorizontal3 = new StructElt(
			initLineHorizontal(3));
	public static StructElt LineHorizontal5 = new StructElt(
			initLineHorizontal(5));
	public static StructElt LineHorizontal10 = new StructElt(
			initLineHorizontal(10));
	public static StructElt LineHorizontal15 = new StructElt(
			initLineHorizontal(15));
	public static StructElt LineHorizontal20 = new StructElt(
			initLineHorizontal(20));
	public static StructElt LineHorizontal50 = new StructElt(
			initLineHorizontal(50));
	public static StructElt LineHorizontal100 = new StructElt(
			initLineHorizontal(100));
	public static StructElt Circle1 = new StructElt(initCircle1());

	public static StructElt Square3 = new StructElt(initSquare3());
	public static StructElt Square5 = new StructElt(initSquare5());

	public static StructElt STAFF = new StructElt(initLineVertical(5));

	public static StructElt MEASURE_BAR = initMeasureBar();
	// Notes
	public static StructElt QUARTER = initQuarter();
	public static StructElt WHOLE = initWhole();
	// Rests
	public static StructElt LONG = initLong();
	public static StructElt BREVE = initBreve();
	public static StructElt MINIM_SEMIBREVE = initMinimSemibreve();
	public static StructElt CROTCHET = initCrotchet();

	public static StructElt QUAVER = initQuaver();

	private static BufferedImage initCircle1() {
		BufferedImage image = new BufferedImage(3, 3,
				BufferedImage.TYPE_INT_RGB);
		Tools.fill(image, Color.white);
		image.setRGB(0, 0, Color.black.getRGB());
		image.setRGB(2, 0, Color.black.getRGB());
		image.setRGB(0, 2, Color.black.getRGB());
		image.setRGB(2, 2, Color.black.getRGB());
		return image;
	}

	private static StructElt initCrotchet() {
		StructEltBuilder builder = new StructEltBuilder();
		StructElt structElt = builder.getStructElt("crotchet");

		return structElt;
	}

	private static BufferedImage initLineHorizontal(int length) {
		BufferedImage image = new BufferedImage(length, 1,
				BufferedImage.TYPE_INT_RGB);
		Tools.fill(image, Color.white);
		return image;
	}

	private static BufferedImage initLineVertical(int length) {
		BufferedImage image = new BufferedImage(1, length,
				BufferedImage.TYPE_INT_RGB);
		Tools.fill(image, Color.white);
		return image;
	}

	private static StructElt initLong() {
		StructEltBuilder builder = new StructEltBuilder();
		StructElt structElt = builder.getStructElt("long");

		return structElt;
	}

	private static StructElt initMeasureBar() {
		StructEltBuilder builder = new StructEltBuilder();
		StructElt structElt = builder.getStructElt("measure");

		return structElt;
	}

	private static StructElt initQuarter() {
		StructEltBuilder builder = new StructEltBuilder();
		StructElt structElt = builder.getStructElt("quarter");

		return structElt;
	}

	private static StructElt initQuaver() {
		StructEltBuilder builder = new StructEltBuilder();
		StructElt structElt = builder.getStructElt("quaver");

		return structElt;
	}

	private static BufferedImage initSquare3() {
		BufferedImage image = new BufferedImage(3, 3,
				BufferedImage.TYPE_INT_RGB);
		Tools.fill(image, Color.white);
		return image;
	}

	private static BufferedImage initSquare5() {
		BufferedImage image = new BufferedImage(5, 5,
				BufferedImage.TYPE_INT_RGB);
		Tools.fill(image, Color.white);
		return image;
	}

	private static StructElt initWhole() {
		StructEltBuilder builder = new StructEltBuilder();
		StructElt structElt = builder.getStructElt("whole");
		return structElt;
	}

	public StructElt(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getImage() {
		return this.image;
	}
}
