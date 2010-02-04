package backendAgent;

import miscUtil.Bundle;

/**
 * @author Hocus
 *         This enum represents type of observer.
 */
public enum TypeObserver
{
    VIEWER, STATS;
    
    /**
     * Internationnalized string.
     */
    @Override
    public String toString()
    {
        return Bundle.getIntance().get("typeObserver." + this.name());
    }
}
