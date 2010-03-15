package clientAI;

/**
 * @author Hocus
 *         This class is a factory pattern for creating poker Agent.
 */
public class OldFactoryAgent
{
    /**
     * Create a PokerAI depending on the TypeAgent.
     * 
     * @param p_agent
     *            - Type of the agent to create.
     * @return
     *         Newly created PokerAI corresponding to the given TypeAgent.
     */
    public static OldPokerAI create(OldTypeAgent p_agent)
    {
        switch (p_agent)
        {
            case AI_BASIC:
                return new OldPokerBasic();
            case AI_GENETIC:
                return new OldPokerGeneticBasic();
            case AI_RANDOM:
                return new OldPokerRandomAI();
        }
        
        return null;
    }
    
    private OldFactoryAgent()
    {
    }
}
