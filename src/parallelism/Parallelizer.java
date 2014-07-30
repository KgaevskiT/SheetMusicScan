package parallelism;

import java.awt.image.BufferedImage;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Parallelizer {
	private static boolean enable;
	private static int processors = Runtime.getRuntime().availableProcessors();
	private static ForkJoinPool pool = new ForkJoinPool(processors);

	public static boolean enable() {
		return Parallelizer.enable;
	}

	public static ForkJoinPool getPool() {
		return pool;
	}

	public static int getProcessors() {
		return processors;
	}

	public static void invoke(RecursiveTask<BufferedImage> task) {
		pool.invoke(task);
	}

	public static void setEnable(boolean enable) {
		Parallelizer.enable = enable;
	}
}
