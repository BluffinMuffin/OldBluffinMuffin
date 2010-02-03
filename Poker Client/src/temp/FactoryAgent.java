package temp;

/**
 * @author Hocus
 *         This class is a factory pattern for creating poker Agent.
 */
public class FactoryAgent
{
    /**
     * Create a PokerAI depending on the TypeAgent.
     * 
     * @param p_agent
     *            - Type of the agent to create.
     * @return
     *         Newly created PokerAI corresponding to the given TypeAgent.
     */
    public static PokerAI create(TypeAgent p_agent)
    {
        switch (p_agent)
        {
            case AI_BASIC:
                return new PokerBasic();
            case AI_SVM:
                return new PokerSVM();
            case AI_GENETIC:
                return new PokerGeneticBasic();
        }
        
        return null;
    }
    
    private FactoryAgent()
    {
    }
}
