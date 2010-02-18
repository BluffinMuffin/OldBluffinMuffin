import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import parser.FullTiltParser;
import parser.PokerStarsParser;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ArrayList<String> logPaths = PathBuilder.getlogPaths("C:/Users/BUNNI/Desktop/New folder", ".txt");
		// ArrayList<String> logPaths = PathBuilder.getlogPaths("C:/Users/BUNNI/Desktop/Poker Archive/Poker Archive", ".txt");
		// ArrayList<String> logPaths = PathBuilder.getlogPaths("C:/Users/BUNNI/Desktop/Poker Archive/Poker Archive/PokerTracker/hhdb2.mdb", ".txt");

		FullTiltParser ftp = FullTiltParser.getInstance();
		PokerStarsParser psp = PokerStarsParser.getInstance();

		String fileContent = null;

		for (String s : logPaths) {
			// System.out.println(s);
			// Open the file

			try {
				fileContent = readFileAsString(s);

				// System.out.print(s + "  :  ");
				// Read the header & spawn parser accordingly
				if (fileContent.startsWith("PokerStars")) {
					psp.parse(fileContent);
				} else if (fileContent.startsWith("Full Tilt Poker") || fileContent.startsWith("FullTiltPoker")) {
					ftp.parse(fileContent);
				} else {
					System.out.println("Unknown log type at" + s);
				}

			} catch (IOException e) {
				System.out.println("Can't read file at: " + s);
			}
		}

		System.out.println(logPaths.size());

	}

	private static String readFileAsString(String filePath) throws java.io.IOException {
		byte[] buffer = new byte[(int) new File(filePath).length()];
		BufferedInputStream f = new BufferedInputStream(new FileInputStream(filePath));
		f.read(buffer);
		return new String(buffer);
	}

}
