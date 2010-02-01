package utility;

/**
 * @author Hocus
 *         This enum list the action that a player can take
 */
public enum TypePlayerAction
{
    UNKNOWN, NOTHING, FOLD, CHECK, CALL, RAISE, SMALL_BLIND, BIG_BLIND, PONG, DISCONNECT, UNCALLED;
    
    /**
     * Returns the string associated with the action of the player
     */
    @Override
    public String toString()
    {
        return Bundle.getIntance().get("typePlayerAction." + this.name());
    }
}
