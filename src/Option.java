import java.io.File;

public class Option {
	private static String usage = "Usage: java -jar SheetMusicScan.jar [-p] image"
			+ System.getProperty("line.separator")
			+ "       java -jar SheetMusicScan.jar [-r] directory";

	private boolean r = false;
	private boolean p = false;
	private File file = null;

	public Option(String[] args) {
		String path = null;

		for (String arg : args) {
			if (arg.charAt(0) == '-') {
				for (int i = 1; i < arg.length(); i++) {
					switch (arg.charAt(i)) {
					case 'r':
						r = true;
						break;
					case 'p':
						p = true;
						break;
					default:
						System.err.println("Error: Incorrect option: "
								+ arg.charAt(i));
						System.err.println(usage);
						System.exit(1);
						break;
					}
				}
			} else {
				if (path == null) {
					path = arg;
				} else {
					System.err.println("Error: incorrect argument: " + arg);
					System.err.println(usage);
					System.exit(1);
				}
			}
		}

		if (path == null) {
			System.err.println("Error: Missing path");
			System.err.println(usage);
			System.exit(1);
		} else {
			file = new File(path);
			if (r) {
				if (!file.isDirectory()) {
					System.err.println("Error: \"" + path
							+ "\" is not a directory");
					System.err.println(usage);
					System.exit(1);
				}
			} else {
				if (!file.isFile()) {
					System.err.println("Error: \"" + path + "\" is not a file");
					System.err.println(usage);
					System.exit(1);
				}
			}
		}
	}

	public File getFile() {
		return file;
	}

	public boolean parallel() {
		return p;
	}

	public boolean recursive() {
		return r;
	}
}
