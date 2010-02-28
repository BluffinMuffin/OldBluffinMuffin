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

		// // EXAMPLE DB USE
		// PostgresConnector p = new PostgresConnector("srv-prj-05.dmi.usherb.ca", "BluffinWifEnum", "postgres", "27053");
		// ResultSet r = p.query("select * from domain");
		// try {
		// while (r.next()) {
		// System.out.println(r.getString(0));
		// }
		// } catch (SQLException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// System.exit(1);

		// ArrayList<String> logPaths = PathBuilder.getlogPaths("C:/Users/BUNNI/Desktop/New folder", ".txt");
		ArrayList<String> logPaths = PathBuilder.getlogPaths("C:/Users/BUNNI/Desktop/Poker Archive/Poker Archive", ".txt");
		// ArrayList<String> logPaths = PathBuilder.getlogPaths("C:/Users/BUNNI/Desktop/Poker Archive/Poker Archive/PokerTracker/hhdb2.mdb", ".txt");

		FullTiltParser ftp = FullTiltParser.getInstance();
		PokerStarsParser psp = PokerStarsParser.getInstance();

		String fileContent = null;
		/*
		 * Integer nbGames = 0; Integer nbPokerStarGames = 0; Integer nbFullTiltGames = 0; Integer wtf = 0;
		 */

		for (String s : logPaths) {
			// System.out.println(s);
			// Open the file

			try {
				fileContent = readFileAsString(s);
				/*
				 * String[] gameLogs = null;
				 * 
				 * gameLogs = fileContent.split("\r\n\r\n\r\n");
				 * 
				 * for (String log : gameLogs) { nbGames++; if (fileContent.startsWith("PokerStars")) { nbPokerStarGames++; } else if (fileContent.startsWith("Full")) { nbFullTiltGames++; } else { wtf++; System.out.println("Path: " + s); }
				 * 
				 * }
				 */

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
		/*
		 * System.out.println("You has " + nbGames + " games in your poker archive"); System.out.println(nbPokerStarGames + " of then are from pokerStars"); System.out.println(nbFullTiltGames + " of then are from Full Tilt modafucka"); System.out.println(wtf + " of then are i don't know the fuck where"); System.out.println(nbGames - (nbPokerStarGames + nbFullTiltGames + wtf) + "games are unaccounted for");
		 */
		System.out.println(logPaths.size());

	}

	private static String readFileAsString(String filePath) throws java.io.IOException {
		byte[] buffer = new byte[(int) new File(filePath).length()];
		BufferedInputStream f = new BufferedInputStream(new FileInputStream(filePath));
		f.read(buffer);
		return new String(buffer);
	}

}
