package parser;


public class PokerStarsParser extends AbsParser {

	public PokerStarsParser(String path) {
		super(path);
	}

	@Override
	protected void parse(String fileContent) {
		System.out.println("I am a PokerStar");
	}

}
