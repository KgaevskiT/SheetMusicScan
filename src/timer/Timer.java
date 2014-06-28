package timer;

public class Timer {
	public static String getTimeElapsed(long tStart, long tEnd) {
		long elapsed = tEnd - tStart;
		long minutes = elapsed / 60000;
		long seconds = elapsed / 1000 - minutes * 60;
		long millis = elapsed - seconds * 1000 - minutes * 60000;

		String time = "";
		if (minutes != 0)
			time += minutes + "'";
		if (seconds != 0 || minutes != 0)
			time += seconds + "\"";
		time += millis;
		if (minutes == 0 && seconds == 0) {
			time += "ms";
		}
		return time;
	}

	private final long start;
	private long step;

	public Timer() {
		this.start = System.currentTimeMillis();
		this.step = this.start;
	}

	public void step() {
		this.step = System.currentTimeMillis();
	}

	public void step(String msg) {
		long now = System.currentTimeMillis();
		System.out.println(msg + " (" + getTimeElapsed(this.step, now) + ")");
		this.step = now;
	}
}
