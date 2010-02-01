package backend.agent;

/**
 * @author Hocus
 *         This interface represents an observer that will be notify when T will
 *         be closing.
 */
public interface ClosingListener<T>
{
    public void closing(T e);
}
