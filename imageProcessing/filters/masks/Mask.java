package imageProcessing.filters.masks;

import imageProcessing.tools.Tools;

import java.awt.Color;
import java.awt.image.BufferedImage;


public class Mask {
	public static BufferedImage LineHorizontal3 = initLineHorizontal(3);
	public static BufferedImage LineHorizontal5 = initLineHorizontal(5);
	public static BufferedImage LineHorizontal10 = initLineHorizontal(10);
	public static BufferedImage LineHorizontal15 = initLineHorizontal(15);
	public static BufferedImage LineHorizontal20 = initLineHorizontal(20);
	public static BufferedImage LineHorizontal50 = initLineHorizontal(50);
	public static BufferedImage LineHorizontal100 = initLineHorizontal(100);
	public static BufferedImage LineVertical5 = initLineVertical(5);
	public static BufferedImage Circle1 = initCircle1();
	public static BufferedImage Pixel = initPixel();
	public static BufferedImage Square3 = initSquare3();
	public static BufferedImage Square5 = initSquare5();
	public static BufferedImage Quarter = initQuarter();

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

	private static BufferedImage initPixel() {
		BufferedImage image = new BufferedImage(1, 1,
				BufferedImage.TYPE_INT_RGB);
		image.setRGB(0, 0, Color.white.getRGB());
		return image;
	}

	private static BufferedImage initQuarter() {
		BufferedImage image = new BufferedImage(13, 13,
				BufferedImage.TYPE_INT_RGB);
		image.setRGB(0, 0, -16777216);
		image.setRGB(1, 0, -16777216);
		image.setRGB(2, 0, -16777216);
		image.setRGB(3, 0, -16777216);
		image.setRGB(4, 0, -16777216);
		image.setRGB(5, 0, -16777216);
		image.setRGB(6, 0, -16777216);
		image.setRGB(7, 0, -1);
		image.setRGB(8, 0, -1);
		image.setRGB(9, 0, -1);
		image.setRGB(10, 0, -1);
		image.setRGB(11, 0, -1);
		image.setRGB(12, 0, -16777216);
		image.setRGB(0, 1, -16777216);
		image.setRGB(1, 1, -16777216);
		image.setRGB(2, 1, -16777216);
		image.setRGB(3, 1, -16777216);
		image.setRGB(4, 1, -1);
		image.setRGB(5, 1, -1);
		image.setRGB(6, 1, -1);
		image.setRGB(7, 1, -1);
		image.setRGB(8, 1, -1);
		image.setRGB(9, 1, -1);
		image.setRGB(10, 1, -1);
		image.setRGB(11, 1, -1);
		image.setRGB(12, 1, -1);
		image.setRGB(0, 2, -16777216);
		image.setRGB(1, 2, -16777216);
		image.setRGB(2, 2, -16777216);
		image.setRGB(3, 2, -1);
		image.setRGB(4, 2, -1);
		image.setRGB(5, 2, -1);
		image.setRGB(6, 2, -1);
		image.setRGB(7, 2, -1);
		image.setRGB(8, 2, -1);
		image.setRGB(9, 2, -1);
		image.setRGB(10, 2, -1);
		image.setRGB(11, 2, -1);
		image.setRGB(12, 2, -1);
		image.setRGB(0, 3, -16777216);
		image.setRGB(1, 3, -16777216);
		image.setRGB(2, 3, -1);
		image.setRGB(3, 3, -1);
		image.setRGB(4, 3, -1);
		image.setRGB(5, 3, -1);
		image.setRGB(6, 3, -1);
		image.setRGB(7, 3, -1);
		image.setRGB(8, 3, -1);
		image.setRGB(9, 3, -1);
		image.setRGB(10, 3, -1);
		image.setRGB(11, 3, -1);
		image.setRGB(12, 3, -1);
		image.setRGB(0, 4, -16777216);
		image.setRGB(1, 4, -1);
		image.setRGB(2, 4, -1);
		image.setRGB(3, 4, -1);
		image.setRGB(4, 4, -1);
		image.setRGB(5, 4, -1);
		image.setRGB(6, 4, -1);
		image.setRGB(7, 4, -1);
		image.setRGB(8, 4, -1);
		image.setRGB(9, 4, -1);
		image.setRGB(10, 4, -1);
		image.setRGB(11, 4, -1);
		image.setRGB(12, 4, -1);
		image.setRGB(0, 5, -16777216);
		image.setRGB(1, 5, -1);
		image.setRGB(2, 5, -1);
		image.setRGB(3, 5, -1);
		image.setRGB(4, 5, -1);
		image.setRGB(5, 5, -1);
		image.setRGB(6, 5, -1);
		image.setRGB(7, 5, -1);
		image.setRGB(8, 5, -1);
		image.setRGB(9, 5, -1);
		image.setRGB(10, 5, -1);
		image.setRGB(11, 5, -1);
		image.setRGB(12, 5, -1);
		image.setRGB(0, 6, -16777216);
		image.setRGB(1, 6, -1);
		image.setRGB(2, 6, -1);
		image.setRGB(3, 6, -1);
		image.setRGB(4, 6, -1);
		image.setRGB(5, 6, -1);
		image.setRGB(6, 6, -1);
		image.setRGB(7, 6, -1);
		image.setRGB(8, 6, -1);
		image.setRGB(9, 6, -1);
		image.setRGB(10, 6, -1);
		image.setRGB(11, 6, -1);
		image.setRGB(12, 6, -16777216);
		image.setRGB(0, 7, -1);
		image.setRGB(1, 7, -1);
		image.setRGB(2, 7, -1);
		image.setRGB(3, 7, -1);
		image.setRGB(4, 7, -1);
		image.setRGB(5, 7, -1);
		image.setRGB(6, 7, -1);
		image.setRGB(7, 7, -1);
		image.setRGB(8, 7, -1);
		image.setRGB(9, 7, -1);
		image.setRGB(10, 7, -1);
		image.setRGB(11, 7, -1);
		image.setRGB(12, 7, -16777216);
		image.setRGB(0, 8, -1);
		image.setRGB(1, 8, -1);
		image.setRGB(2, 8, -1);
		image.setRGB(3, 8, -1);
		image.setRGB(4, 8, -1);
		image.setRGB(5, 8, -1);
		image.setRGB(6, 8, -1);
		image.setRGB(7, 8, -1);
		image.setRGB(8, 8, -1);
		image.setRGB(9, 8, -1);
		image.setRGB(10, 8, -1);
		image.setRGB(11, 8, -1);
		image.setRGB(12, 8, -16777216);
		image.setRGB(0, 9, -1);
		image.setRGB(1, 9, -1);
		image.setRGB(2, 9, -1);
		image.setRGB(3, 9, -1);
		image.setRGB(4, 9, -1);
		image.setRGB(5, 9, -1);
		image.setRGB(6, 9, -1);
		image.setRGB(7, 9, -1);
		image.setRGB(8, 9, -1);
		image.setRGB(9, 9, -1);
		image.setRGB(10, 9, -1);
		image.setRGB(11, 9, -16777216);
		image.setRGB(12, 9, -16777216);
		image.setRGB(0, 10, -1);
		image.setRGB(1, 10, -1);
		image.setRGB(2, 10, -1);
		image.setRGB(3, 10, -1);
		image.setRGB(4, 10, -1);
		image.setRGB(5, 10, -1);
		image.setRGB(6, 10, -1);
		image.setRGB(7, 10, -1);
		image.setRGB(8, 10, -1);
		image.setRGB(9, 10, -1);
		image.setRGB(10, 10, -16777216);
		image.setRGB(11, 10, -16777216);
		image.setRGB(12, 10, -16777216);
		image.setRGB(0, 11, -1);
		image.setRGB(1, 11, -1);
		image.setRGB(2, 11, -1);
		image.setRGB(3, 11, -1);
		image.setRGB(4, 11, -1);
		image.setRGB(5, 11, -1);
		image.setRGB(6, 11, -1);
		image.setRGB(7, 11, -1);
		image.setRGB(8, 11, -1);
		image.setRGB(9, 11, -16777216);
		image.setRGB(10, 11, -16777216);
		image.setRGB(11, 11, -16777216);
		image.setRGB(12, 11, -16777216);
		image.setRGB(0, 12, -16777216);
		image.setRGB(1, 12, -1);
		image.setRGB(2, 12, -1);
		image.setRGB(3, 12, -1);
		image.setRGB(4, 12, -1);
		image.setRGB(5, 12, -1);
		image.setRGB(6, 12, -16777216);
		image.setRGB(7, 12, -16777216);
		image.setRGB(8, 12, -16777216);
		image.setRGB(9, 12, -16777216);
		image.setRGB(10, 12, -16777216);
		image.setRGB(11, 12, -16777216);
		image.setRGB(12, 12, -16777216);

		return image;
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
}
