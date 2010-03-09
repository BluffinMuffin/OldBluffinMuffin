package newPokerClientAI;


public class FactoryPokerAI
{
    public static AbstractPokerAI create(TypePokerAI ai)
    {
        switch (ai)
        {
            case BASIC:
                return new PokerAIBasic();
                // case SVM:
                // return new PokerSVM();
                // case GENETIC:
                // return new PokerGeneticBasic();
                // case RANDOM:
                // return new PokerRandomAI();
        }
        
        return null;
    }
    
    private FactoryPokerAI()
    {
    }
}
