package tools;

import java.lang.reflect.Method;

import basePokerAI.IPokerAgentListener;

public class Util
{
    public static Method getIPokerAgentListenerMethod(String p_name, Class<?>... p_classes)
    {
        try
        {
            return IPokerAgentListener.class.getMethod(p_name, p_classes);
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
