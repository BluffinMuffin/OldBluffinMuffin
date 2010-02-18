import java.io.File;
import java.util.ArrayList;

public class PathBuilder {

	private static ArrayList<String> logPaths = null;
	private static String limitExtensions = "";

	public static ArrayList<String> getlogPaths(String path, String limitExtensions) {
		logPaths = new ArrayList<String>();
		PathBuilder.limitExtensions = limitExtensions;

		buildPathList(path, limitExtensions);
		return logPaths;
	}

	private static void buildPathList(String rootDirectoryPath, String limitExtensions) {

		File rootPath = new File(rootDirectoryPath.trim());
		if (!rootPath.exists()) {
			System.out.println("ERROR: Path (" + rootDirectoryPath + ") not found.\nExitting...");
			System.exit(1);
		}

		// Create list of available files.
		if (rootPath.isFile()) {
			addFilePath(rootPath);
		} else if (rootPath.isDirectory()) {
			addPathsFromFolder(rootPath);
		}

		/*
		 * for(String s:logPaths){ System.out.println(s); }
		 */

	}

	private static void addFilePath(File f) {
		if (f.getName().endsWith(limitExtensions)) {
			logPaths.add(f.getPath());
		} else {
			System.out.println("ERR : Invalid extension. " + f.getAbsolutePath());
		}
	}

	private static void addPathsFromFolder(File f) {
		File[] fList = f.listFiles();

		for (File currFile : fList) {
			if (currFile.isFile()) {
				addFilePath(currFile);
			} else if (currFile.isDirectory()) {
				addPathsFromFolder(currFile);
			}
		}
	}

}
