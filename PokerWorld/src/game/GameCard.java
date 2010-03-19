package game;

import java.util.ArrayList;


public class GameCard implements Comparable<GameCard>
{
    
    private final TypeGameCardRank m_rank;
    
    private final TypeGameCardSuit m_suit;
    private final int m_ID;
    
    /**
     * Constructs a card of the specified rank and suit.
     * 
     * @param rank
     *            a {@link Rank}
     * @param suit
     *            a {@link Suit}
     * @see #getInstance(handEvaluation.HandEvalCard.poker.Card.Rank, handEvaluation.HandEvalCard.poker.Card.Suit)
     */
    private GameCard(TypeGameCardRank rank, TypeGameCardSuit suit)
    {
        m_rank = rank;
        m_suit = suit;
        m_ID = suit.ordinal() * 13 + rank.ordinal();
    }
    
    private GameCard(int ID)
    {
        if (ID < 0)
        {
            m_rank = null;
            m_suit = null;
        }
        else
        {
            m_rank = TypeGameCardRank.fromInt(ID % 13);
            m_suit = TypeGameCardSuit.fromInt(ID / 13);
        }
        m_ID = ID;
    }
    
    /**
     * Constructs a card of the specified rank and suit.
     * 
     * @param rs
     *            a {@link String} of length 2, where the first character is in {@link HandEvalCard.Rank#RANK_CHARS} and
     *            the second is in {@link HandEvalCard.Suit#SUIT_CHARS} (case insensitive).
     * @throws IllegalArgumentException
     *             on the first character in rs which is not found in the respective string.
     * @see #getInstance(String)
     */
    private GameCard(String rs)
    {
        
        if (rs.length() != 2)
        {
            throw new IllegalArgumentException('"' + rs + "\".length != 2");
        }
        try
        {
            this.m_rank = TypeGameCardRank.fromChar(rs.charAt(0));
            this.m_suit = TypeGameCardSuit.fromChar(rs.charAt(1));
            m_ID = m_suit.ordinal() * 13 + m_rank.ordinal();
        }
        catch (final IllegalArgumentException e)
        {
            throw e; // indicates the first erroneous character
        }
    }
    
    public final static int NO_CARD_ID = -1;
    public final static int HIDDEN_CARD_ID = -2;
    public final static GameCard NO_CARD = new GameCard(GameCard.NO_CARD_ID);
    public final static GameCard HIDDEN_CARD = new GameCard(GameCard.HIDDEN_CARD_ID);
    private final static GameCard[] theCards = new GameCard[52];
    static
    {
        int i = 0;
        for (final TypeGameCardSuit s : TypeGameCardSuit.values())
        {
            for (final TypeGameCardRank r : TypeGameCardRank.values())
            {
                GameCard.theCards[i++] = new GameCard(r, s);
            }
        }
    }
    
    /**
     * Returns a pre-existing instance of {@link HandEvalCard} of the specified rank and suit.
     * 
     * @param rank
     *            a {@link Rank}
     * @param suit
     *            a {@link Suit}
     * @return an instance of {@link HandEvalCard} of the specified rank and suit.
     */
    public static GameCard getInstance(int id)
    {
        if (id == GameCard.NO_CARD_ID)
        {
            return GameCard.NO_CARD;
        }
        if (id == GameCard.HIDDEN_CARD_ID)
        {
            return GameCard.HIDDEN_CARD;
        }
        return GameCard.theCards[id];
    }
    
    /**
     * Returns a pre-existing instance of {@link HandEvalCard} of the specified rank and suit.
     * 
     * @param rank
     *            a {@link Rank}
     * @param suit
     *            a {@link Suit}
     * @return an instance of {@link HandEvalCard} of the specified rank and suit.
     */
    public static GameCard getInstance(TypeGameCardRank rank, TypeGameCardSuit suit)
    {
        return GameCard.getInstance(suit.ordinal() * 13 + rank.ordinal());
    }
    
    /**
     * Returns a pre-existing instance of {@link HandEvalCard} of the specified rank and suit.
     * 
     * @param rs
     *            a {@link String} of length 2, where the first character is in {@link HandEvalCard.Rank#RANK_CHARS} and
     *            the second is in {@link HandEvalCard.Suit#SUIT_CHARS} (case insensitive).
     * @return an instance of {@link HandEvalCard} of the specified rank and suit.
     * @throws IllegalArgumentException
     *             on the first character in rs which is not found in the respective string.
     */
    public static GameCard getInstance(String rs)
    {
        if (rs.length() != 2)
        {
            throw new IllegalArgumentException('"' + rs + "\".length != 2");
        }
        try
        {
            final TypeGameCardRank rank = TypeGameCardRank.fromChar(rs.charAt(0));
            final TypeGameCardSuit suit = TypeGameCardSuit.fromChar(rs.charAt(1));
            return GameCard.getInstance(rank, suit);
        }
        catch (final IllegalArgumentException e)
        {
            throw e; // indicates the first erroneous character
        }
    }
    
    @Override
    public int compareTo(GameCard that)
    {
        return ((Integer) m_ID).compareTo(that.m_ID);
    }
    
    /**
     * Compares the parameter to this card.
     * 
     * @return <code>true</code> if the parameter is a {@link HandEvalCard} of the same rank and suit
     *         as this card; <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object that)
    {
        if (!(that instanceof GameCard))
        {
            return false;
        }
        final GameCard c = (GameCard) that;
        return ((Integer) m_ID).equals(c.m_ID);
    }
    
    /* result is a perfect hash code */
    @Override
    public int hashCode()
    {
        return m_ID;
    }
    
    /**
     * Returns the {@link Rank} of this card.
     * 
     * @return the {@link Rank} of this card.
     */
    public TypeGameCardRank getRank()
    {
        return m_rank;
    }
    
    /**
     * Returns the {@link Suit} of this card.
     * 
     * @return the {@link Suit} of this card.
     */
    public TypeGameCardSuit getSuit()
    {
        return m_suit;
    }
    
    public int getId()
    {
        return m_ID;
    }
    
    public boolean isNoCard()
    {
        return m_ID == GameCard.NO_CARD_ID;
    }
    
    public boolean isHiddenCard()
    {
        return m_ID == GameCard.HIDDEN_CARD_ID;
    }
    
    /**
     * Convert a card arraylist into a string of code
     * 
     * @param p_card
     *            card array
     * @return string of code
     */
    public static String CardArrayListToCode(ArrayList<GameCard> p_card)
    {
        String result = "";
        for (int i = 0; i < p_card.size(); i++)
        {
            result += p_card.get(i).toString() + " ";
        }
        return result;
    }
    
    /**
     * Convert a card array into a string of code
     * 
     * @param p_card
     *            card array
     * @return string of code
     */
    public static String CardArrayToCode(GameCard[] p_card)
    {
        String result = "";
        for (final GameCard element : p_card)
        {
            if (element != null)
            {
                result += element.toString() + " ";
            }
        }
        return result;
    }
    
    /**
     * Returns a {@link String} of length 2 denoting the rank and suit of this card.
     * 
     * @return a {@link String} of length 2 containing a character in {@link HandEvalCard.Rank#RANK_CHARS} denoting this
     *         card&#39;s rank followed by a character in {@link HandEvalCard.Suit#SUIT_CHARS} denoting this
     *         card&#39;s suit.
     */
    @Override
    public String toString()
    {
        if (m_ID >= 0)
        {
            return String.format("%c%c", m_rank.toChar(), m_suit.toChar());
        }
        return "" + m_ID;
    }
}
