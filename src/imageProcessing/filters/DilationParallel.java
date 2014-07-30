package imageProcessing.filters;

import imageProcessing.filters.structElt.StructElt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.concurrent.RecursiveTask;

public class DilationParallel extends RecursiveTask<BufferedImage> {
	private final StructElt structElt;
	private final Color color;
	private final BufferedImage image;
	private final boolean fork;

	public DilationParallel(StructElt structElt, Color color,
			BufferedImage image, boolean fork) {
		this.structElt = structElt;
		this.color = color;
		this.image = image;
		this.fork = fork;
	}

	@Override
	protected BufferedImage compute() {
		if (fork) {
			int width = image.getWidth() / 2;
			int height = image.getHeight() / 2;
			int wStruct = structElt.getImage().getWidth() / 2;
			int hStruct = structElt.getImage().getHeight() / 2;
			int wEven = image.getWidth() % 2 == 0 ? 0 : 1;
			int hEven = image.getWidth() % 2 == 0 ? 0 : 1;

			BufferedImage iul = image.getSubimage(0, 0, width + wStruct, height
					+ hStruct);
			BufferedImage iur = image.getSubimage(width - wStruct, 0, width
					+ wStruct + wEven, height + hStruct);
			BufferedImage idl = image.getSubimage(0, height - hStruct, width
					+ wStruct, height + hStruct + hEven);
			BufferedImage idr = image.getSubimage(width - wStruct, height
					- hStruct, width + wStruct + wEven, height + hStruct
					+ hEven);

			DilationParallel ul = new DilationParallel(structElt, color, iul,
					false);
			DilationParallel ur = new DilationParallel(structElt, color, iur,
					false);
			DilationParallel dl = new DilationParallel(structElt, color, idl,
					false);
			DilationParallel dr = new DilationParallel(structElt, color, idr,
					false);

			ur.fork();
			dl.fork();
			dr.fork();

			iul = ul.compute();
			iur = ur.join();
			idl = dl.join();
			idr = dr.join();

			BufferedImage result = new BufferedImage(image.getWidth(),
					image.getHeight(), BufferedImage.TYPE_INT_RGB);

			Graphics g = result.getGraphics();
			g.drawImage(iul, 0, 0, width, height, 0, 0, width, height, null);
			g.drawImage(iur, width, 0, image.getWidth(), height, wStruct, 0,
					width + wStruct + wEven, height, null);
			g.drawImage(idl, 0, height, width, image.getHeight(), 0, hStruct,
					width, height + hStruct + hEven, null);
			g.drawImage(idr, width, height, image.getWidth(),
					image.getHeight(), wStruct, hStruct, width + wStruct
							+ wEven, height + hStruct + hEven, null);

			return result;
		} else {
			return new DilationSequential(structElt, color).apply(image);
		}
	}
}
