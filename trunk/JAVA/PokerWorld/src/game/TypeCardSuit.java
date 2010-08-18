package game;

public enum TypeCardSuit
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
    public static TypeCardSuit fromChar(char c)
    {
        
        final int i = TypeCardSuit.SUIT_CHARS.indexOf(Character.toLowerCase(c));
        if (i >= 0)
        {
            return TypeCardSuit.values()[i];
        }
        throw new IllegalArgumentException("'" + c + "'");
    }
    
    public static TypeCardSuit fromInt(int i)
    {
        if (i >= 0)
        {
            return TypeCardSuit.values()[i];
        }
        throw new IllegalArgumentException("'" + i + "'");
    }
    
    /**
     * @return the character in {@link #SUIT_CHARS} denoting this suit.
     */
    public char toChar()
    {
        return TypeCardSuit.SUIT_CHARS.charAt(this.ordinal());
    }
}
