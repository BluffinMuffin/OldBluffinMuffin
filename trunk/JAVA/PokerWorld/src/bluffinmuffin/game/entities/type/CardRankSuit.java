package bluffinmuffin.game.entities.type;

public enum CardRankSuit
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
    public static CardRankSuit fromChar(char c)
    {
        
        final int i = CardRankSuit.SUIT_CHARS.indexOf(Character.toLowerCase(c));
        if (i >= 0)
        {
            return CardRankSuit.values()[i];
        }
        throw new IllegalArgumentException("'" + c + "'");
    }
    
    public static CardRankSuit fromInt(int i)
    {
        if (i >= 0)
        {
            return CardRankSuit.values()[i];
        }
        throw new IllegalArgumentException("'" + i + "'");
    }
    
    /**
     * @return the character in {@link #SUIT_CHARS} denoting this suit.
     */
    public char toChar()
    {
        return CardRankSuit.SUIT_CHARS.charAt(this.ordinal());
    }
}
