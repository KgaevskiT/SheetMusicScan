public class Option {
	private static String usage = "Usage: java -jar SheetMusicScan.jar [-p] image" + ;
	private boolean r = false;
	private boolean p = false;

	public Option(String[] args) {
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
						break;
					}
				}
			}
		}
	}
}
