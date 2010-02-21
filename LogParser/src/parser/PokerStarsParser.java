package parser;

public class PokerStarsParser extends AbsParser
{
    
    private static final PokerStarsParser INSTANCE = new PokerStarsParser();
    
    public static PokerStarsParser getInstance()
    {
        return PokerStarsParser.INSTANCE;
    }
    
    @Override
    void understand(String fileContent)
    {
        System.out.println("I am a PokerStar");
    }
    
}
