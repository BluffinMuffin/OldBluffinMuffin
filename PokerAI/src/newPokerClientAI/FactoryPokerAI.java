package newPokerClientAI;

public class FactoryPokerAI
{
    public static AbstractPokerAI create(TypePokerAI ai)
    {
        switch (ai)
        {
            case BASIC:
                return new PokerAIBasic();
            case SVM:
                return new PokerAISVM();
            case GENETIC:
                return new PokerAIGeneticBasic();
            case RANDOM:
                return new PokerAIRandom();
        }
        
        return null;
    }
    
    private FactoryPokerAI()
    {
    }
}
