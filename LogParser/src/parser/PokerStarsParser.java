package parser;

public class PokerStarsParser extends AbsParser {

	private static final PokerStarsParser INSTANCE = new PokerStarsParser();

	public static PokerStarsParser getInstance() {
		return INSTANCE;
	}

	@Override
	void understand(String fileContent) {
		// TODO Auto-generated method stub
		System.out.println("I am a PokerStar");
	}

}
