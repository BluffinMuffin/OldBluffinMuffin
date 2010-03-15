package newPokerClientAI;

import newPokerLogic.IPokerGame;

public class FactoryPokerAI
{
    public static AbstractPokerAI create(TypePokerAI ai, IPokerGame game, int seatViewed)
    {
        switch (ai)
        {
            case BASIC:
                return new PokerAIBasic(game, seatViewed);
            case GENETIC:
                return new PokerAIGenetic(game, seatViewed);
            case GENETIC_BASIC:
                return new PokerAIGeneticBasic(game, seatViewed);
            case RANDOM:
                return new PokerAIRandom(game, seatViewed);
        }
        
        return null;
    }
    
    private FactoryPokerAI()
    {
    }
}
