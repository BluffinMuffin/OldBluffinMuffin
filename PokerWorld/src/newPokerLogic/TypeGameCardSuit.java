package newPokerLogic;

public enum TypeGameCardSuit
{
    CLUB, DIAMOND, HEART, SPADE;
    
    public static final String SUIT_CHARS = "cdhs";
    
    /**
     * @param c
     *            a character present in {@link #SUIT_CHARS} (case insensitive)
     * @return the Suit denoted by the character.
     * @throws IllegalArgumentException
     *             if c not in {@link #SUIT_CHARS}
     */
    public static TypeGameCardSuit fromChar(char c)
    {
        
        final int i = TypeGameCardSuit.SUIT_CHARS.indexOf(Character.toLowerCase(c));
        if (i >= 0)
        {
            return TypeGameCardSuit.values()[i];
        }
        throw new IllegalArgumentException("'" + c + "'");
    }
    
    public static TypeGameCardSuit fromInt(int i)
    {
        if (i >= 0)
        {
            return TypeGameCardSuit.values()[i];
        }
        throw new IllegalArgumentException("'" + i + "'");
    }
    
    /**
     * @return the character in {@link #SUIT_CHARS} denoting this suit.
     */
    public char toChar()
    {
        return TypeGameCardSuit.SUIT_CHARS.charAt(this.ordinal());
    }
}
