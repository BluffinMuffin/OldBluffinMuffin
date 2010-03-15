package clientAI;

import utility.Bundle;

/**
 * @author Hocus
 *         This enum represents type of agent.
 */
public enum OldTypeAgent
{
    AI_BASIC, AI_GENETIC, AI_RANDOM;
    
    /**
     * Internationnalized string.
     */
    @Override
    public String toString()
    {
        return Bundle.getIntance().get("typeAgent." + this.name());
    }
}
