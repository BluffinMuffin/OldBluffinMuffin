package pokerLogic;

import utility.Bundle;

/**
 * @author Hocus
 *         This enum list the possible type of holdEm game
 */
public enum OldTypePokerGame
{
    NO_LIMIT, 
    FIXED_LIMIT, 
    POT_LIMIT;
    
    /**
     * Returns the string associated with the type of the game
     */
    @Override
    public String toString()
    {
        return Bundle.getIntance().get("typeHoldemGame." + this.name());
    }
}
