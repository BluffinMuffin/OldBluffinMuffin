package clientGame;


/**
 * @author Hocus
 *         This class contains tool(s) used in the client application.
 */
public class Util
{
    /**
     * Get the right IPokerAgentListerner's Method depending on its name
     * and the parameter type it takes.
     * 
     * @param p_name
     *            - Name of the requested Method.
     * @param p_classes
     *            - List of parameter's types.
     * @return
     *         Requested Method
     * 
     *         public static Method getIPokerAgentListenerMethod(String p_name, Class<?>... p_classes)
     *         {
     *         try
     *         {
     *         return IPokerAgentListener.class.getMethod(p_name, p_classes);
     *         }
     *         catch (final SecurityException e)
     *         {
     *         e.printStackTrace();
     *         }
     *         catch (final NoSuchMethodException e)
     *         {
     *         e.printStackTrace();
     *         }
     * 
     *         return null;
     *         }
     */
}
