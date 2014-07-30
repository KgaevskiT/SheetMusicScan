package imageProcessing.filters;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.concurrent.RecursiveTask;

public class NoiseDilationParallel extends RecursiveTask<BufferedImage> {
	private static final long serialVersionUID = -2102808428334022017L;
	private final BufferedImage image;
	private final boolean fork;

	public NoiseDilationParallel(BufferedImage image, boolean fork) {
		super();
		this.image = image;
		this.fork = fork;
	}

	@Override
	protected BufferedImage compute() {
		if (fork) {
			int width = image.getWidth() / 2;
			int height = image.getHeight() / 2;
			int wEven = image.getWidth() % 2 == 0 ? 0 : 1;
			int hEven = image.getWidth() % 2 == 0 ? 0 : 1;

			NoiseDilationParallel ul = new NoiseDilationParallel(
					image.getSubimage(0, 0, width + 1, height + 1), false);
			NoiseDilationParallel ur = new NoiseDilationParallel(
					image.getSubimage(width - 1, 0, width + 1 + wEven,
							height + 1), false);
			NoiseDilationParallel dl = new NoiseDilationParallel(
					image.getSubimage(0, height - 1, width + 1, height + 1
							+ hEven), false);
			NoiseDilationParallel dr = new NoiseDilationParallel(
					image.getSubimage(width - 1, height - 1, width + 1 + wEven,
							height + 1 + hEven), false);

			ur.fork();
			dl.fork();
			dr.fork();

			BufferedImage iul = ul.compute();
			BufferedImage iur = ur.join();
			BufferedImage idl = dl.join();
			BufferedImage idr = dr.join();

			BufferedImage result = new BufferedImage(image.getWidth(),
					image.getHeight(), BufferedImage.TYPE_INT_RGB);

			Graphics g = result.getGraphics();

			g.drawImage(iul, 0, 0, width, height, 0, 0, width, height, null);
			g.drawImage(iur, width, 0, image.getWidth(), height, 1, 0, width
					+ 1 + wEven, height, null);
			g.drawImage(idl, 0, height, width, image.getHeight(), 0, 1, width,
					height + 1 + hEven, null);
			g.drawImage(idr, width, height, image.getWidth(),
					image.getHeight(), 1, 1, width + 1 + wEven, height + 1
							+ hEven, null);

			return result;

		} else {
			return new NoiseDilation().apply(image);
		}
	}
}
