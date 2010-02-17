package newPokerLogic;

public enum TypeGameCardRank
{
    TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;
    
    public static final String RANK_CHARS = "23456789TJQKA";
    
    /**
     * @param c
     *            a character present in {@link #RANK_CHARS} (case insensitive)
     * @return the Rank denoted by character.
     * @throws IllegalArgumentException
     *             if c not in {@link #RANK_CHARS}
     */
    public static TypeGameCardRank fromChar(char c)
    {
        
        final int i = TypeGameCardRank.RANK_CHARS.indexOf(Character.toUpperCase(c));
        if (i >= 0)
        {
            return TypeGameCardRank.values()[i];
        }
        throw new IllegalArgumentException("'" + c + "'");
    }
    
    public static TypeGameCardRank fromInt(int i)
    {
        if (i >= 0)
        {
            return TypeGameCardRank.values()[i];
        }
        throw new IllegalArgumentException("'" + i + "'");
    }
    
    /**
     * @return the pip value of this Rank, ranging from 2 for
     *         a <code>TWO</code> (deuce) to 14 for an <code>ACE</code>.
     */
    public int pipValue()
    {
        return this.ordinal() + 2;
    }
    
    /**
     * @return the character in {@link #RANK_CHARS} denoting this rank.
     */
    public char toChar()
    {
        return TypeGameCardRank.RANK_CHARS.charAt(this.ordinal());
    }
}
