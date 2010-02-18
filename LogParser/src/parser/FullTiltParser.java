package parser;

public class FullTiltParser extends AbsParser {

	private static final FullTiltParser INSTANCE = new FullTiltParser();

	public static FullTiltParser getInstance() {
		return INSTANCE;

	}

	@Override
	void understand(String fileContent) {

		System.out.println("I am a FullTilt");
		String[] gameLogs = null;

		gameLogs = fileContent.split("\r\n\r\n\r\n");

	}

}
