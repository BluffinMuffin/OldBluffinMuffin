package parser;

import parser.data.GameSet;

public abstract class AbsParser {

	protected GameSet gameSet = new GameSet();

	private AbsParser() {
	}

	public AbsParser(String fileContent) {
		parse(fileContent);
	}

	abstract protected void parse(String fileContent);

	public GameSet getGameSet() {
		return gameSet;
	}

}
