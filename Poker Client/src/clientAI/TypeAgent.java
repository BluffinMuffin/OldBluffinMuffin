package clientAI;

import utility.Bundle;

/**
 * @author Hocus
 *         This enum represents type of agent.
 */
public enum TypeAgent
{
    AI_BASIC, AI_SVM, AI_GENETIC;
    
    /**
     * Internationnalized string.
     */
    @Override
    public String toString()
    {
        return Bundle.getIntance().get("typeAgent." + this.name());
    }
}
