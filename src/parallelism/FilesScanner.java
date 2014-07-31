package parallelism;

import java.io.File;
import java.util.ArrayList;

public class FilesScanner {
	
	public FilesScanner ()
	{
		initExtensions();
	}

	private static ArrayList<String> authorisedExtensions = new ArrayList<String>();

	private static void initExtensions() {
		authorisedExtensions.add(".png");
		authorisedExtensions.add(".PNG");
		/* d'autres ? */
	}

	public ArrayList<File> getFilesInRep(File folder) {

		ArrayList<File> result = new ArrayList<File>();
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				for (String ext : authorisedExtensions) {
					if (listOfFiles[i].getName().endsWith(ext))
						result.add(listOfFiles[i].getAbsoluteFile());
				}
			}
		}
		return result;
	}

}
