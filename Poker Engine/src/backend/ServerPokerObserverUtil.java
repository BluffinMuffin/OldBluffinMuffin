package backend;

import java.lang.reflect.Method;


/**
 * @author HOCUS
 *         Utilities for the Hold'Em Poker simulateur
 */
public class ServerPokerObserverUtil
{
    
    /**
     * Find a method of the IHoldEmObserver class
     * 
     * @param p_name
     *            The name of the method
     * @param p_classes
     *            The classes of the parameters
     * @return
     *         The method requested
     */
    public static Method getIHoldEmObserverMethod(String p_name, Class<?>... p_classes)
    {
        try
        {
            return IServerPokerObserver.class.getMethod(p_name, p_classes);
        }
        catch (final SecurityException e)
        {
            e.printStackTrace();
        }
        catch (final NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        
        return null;
    }
}
