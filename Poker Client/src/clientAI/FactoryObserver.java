package clientAI;

import pokerAI.IPokerAgentListener;
import clientGameGUI.TableGUIViewer;

/**
 * @author Hocus
 *         This class is a factory pattern for creating poker observer
 *         (IPokerAgentListener).
 */
public class FactoryObserver
{
    /**
     * Create a IPokerAgentListener depending on the TypeObserver.
     * 
     * @param p_observer
     *            - Type of the observer to create.
     * @return
     *         Newly created IPokerAgentListener corresponding to the given
     *         TypeObserver.
     */
    public static IPokerAgentListener create(TypeObserver p_observer)
    {
        switch (p_observer)
        {
            case VIEWER:
                return new TableGUIViewer();
            case STATS:
                return new PokerAI();
        }
        
        return null;
    }
    
    private FactoryObserver()
    {
    }
}
