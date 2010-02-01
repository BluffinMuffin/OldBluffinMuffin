package utility;

/**
 * @author Hocus
 *         Get notified when the object T close.
 */
public interface ClosingListener<T>
{
    /**
     * What the user want to do when the object e is closing
     * 
     * @param e
     *            Closing object
     */
    public void closing(T e);
}
