package pokerLogic;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * 
 * This class is the representation of a playing card
 * 
 * @author Hokus
 * 
 */

public class Card implements Comparable<Card>
{
    
    public static final int NO_CARD = -1;
    public static final int HIDDEN_CARD = -2;
    
    public static final Card C2 = Card.getInstance().get(0);
    public static final Card C3 = Card.getInstance().get(1);
    public static final Card C4 = Card.getInstance().get(2);
    public static final Card C5 = Card.getInstance().get(3);
    public static final Card C6 = Card.getInstance().get(4);
    public static final Card C7 = Card.getInstance().get(5);
    public static final Card C8 = Card.getInstance().get(6);
    public static final Card C9 = Card.getInstance().get(7);
    public static final Card CT = Card.getInstance().get(8);
    public static final Card CJ = Card.getInstance().get(9);
    public static final Card CQ = Card.getInstance().get(10);
    public static final Card CK = Card.getInstance().get(11);
    public static final Card CA = Card.getInstance().get(12);
    
    public static final Card D2 = Card.getInstance().get(13);
    public static final Card D3 = Card.getInstance().get(14);
    public static final Card D4 = Card.getInstance().get(15);
    public static final Card D5 = Card.getInstance().get(16);
    public static final Card D6 = Card.getInstance().get(17);
    public static final Card D7 = Card.getInstance().get(18);
    public static final Card D8 = Card.getInstance().get(19);
    public static final Card D9 = Card.getInstance().get(20);
    public static final Card DT = Card.getInstance().get(21);
    public static final Card DJ = Card.getInstance().get(22);
    public static final Card DQ = Card.getInstance().get(23);
    public static final Card DK = Card.getInstance().get(24);
    public static final Card DA = Card.getInstance().get(25);
    
    public static final Card H2 = Card.getInstance().get(26);
    public static final Card H3 = Card.getInstance().get(27);
    public static final Card H4 = Card.getInstance().get(28);
    public static final Card H5 = Card.getInstance().get(29);
    public static final Card H6 = Card.getInstance().get(30);
    public static final Card H7 = Card.getInstance().get(31);
    public static final Card H8 = Card.getInstance().get(32);
    public static final Card H9 = Card.getInstance().get(33);
    public static final Card HT = Card.getInstance().get(34);
    public static final Card HJ = Card.getInstance().get(35);
    public static final Card HQ = Card.getInstance().get(36);
    public static final Card HK = Card.getInstance().get(37);
    public static final Card HA = Card.getInstance().get(38);
    
    public static final Card S2 = Card.getInstance().get(39);
    public static final Card S3 = Card.getInstance().get(40);
    public static final Card S4 = Card.getInstance().get(41);
    public static final Card S5 = Card.getInstance().get(42);
    public static final Card S6 = Card.getInstance().get(43);
    public static final Card S7 = Card.getInstance().get(44);
    public static final Card S8 = Card.getInstance().get(45);
    public static final Card S9 = Card.getInstance().get(46);
    public static final Card ST = Card.getInstance().get(47);
    public static final Card SJ = Card.getInstance().get(48);
    public static final Card SQ = Card.getInstance().get(49);
    public static final Card SK = Card.getInstance().get(50);
    public static final Card SA = Card.getInstance().get(51);
    
    private static Card m_instance = null;
    
    /**
     * Number of card of a regular deck
     */
    public static final int CARDS_PER_DECK = 54;
    
    /**
     * Convert a card arraylist into a string of code
     * 
     * @param p_card
     *            card array
     * @return string of code
     */
    public static String CardArrayListToCode(ArrayList<Card> p_card)
    {
        String result = "";
        for (int i = 0; i < p_card.size(); i++)
        {
            result += p_card.get(i).getCode() + " ";
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
    public static String CardArrayToCode(Card[] p_card)
    {
        String result = "";
        for (final Card element : p_card)
        {
            if (element != null)
            {
                result += element.getCode() + " ";
            }
        }
        return result;
    }
    
    /**
     * Get the String code of this card. <br>
     * The first character is the kind (c, d, h, s, j) and the second character
     * and the second and third character the rank (2 to 9, T, J, Q, K, A)
     * 
     * @return
     *         The code of the card
     */
    public static String getCode(int p_id)
    {
        return Card.rankToString(Card.getRank(p_id)) + Card.kindToString(Card.getKind(p_id));
    }
    
    public static Card getInstance()
    {
        if (Card.m_instance == null)
        {
            Card.m_instance = new Card();
        }
        
        return Card.m_instance;
    }
    
    /**
     * Get the kind from the card identifier.
     * 
     * @return
     *         the kind of the card (0 to 4)
     */
    public static int getKind(int p_id)
    {
        return (p_id / 13);
    }
    
    /**
     * Get the rank from the card identifier.
     * 
     * @return
     *         the rank of the card (0 to 12)
     */
    public static int getRank(int p_id)
    {
        return (p_id % 13);
    }
    
    /**
     * Get the String representation of the specified kind
     * 
     * @param p_kind
     *            The kind (0 to 4) of the card
     * @return
     *         The String representation of the kind (c, d, h, s, j)
     */
    public static String kindToString(int p_kind)
    {
        String sKind;
        
        switch (p_kind)
        {
            case 0:
                sKind = "c";
                break;
            case 1:
                sKind = "d";
                break;
            case 2:
                sKind = "h";
                break;
            case 3:
                sKind = "s";
                break;
            case 4:
                sKind = "j";
                break;
            default:
                sKind = "";
                break;
        }
        
        return sKind;
    }
    
    /**
     * Get the String representation of the specified rank.
     * 
     * @param p_rank
     *            The rank (0 to 12) of a card
     * @return
     *         The String representation of the rank (2 to 9, T, J, Q, K, A).
     */
    public static String rankToString(int p_rank)
    {
        String sRank = "";
        if ((p_rank < 8) && (p_rank >= 0))
        {
            sRank += (p_rank + 2);
        }
        else if (p_rank == 8)
        {
            sRank = "T";
        }
        else if (p_rank == 9)
        {
            sRank = "J";
        }
        else if (p_rank == 10)
        {
            sRank = "Q";
        }
        else if (p_rank == 11)
        {
            sRank = "K";
        }
        else if (p_rank == 12)
        {
            sRank = "A";
        }
        return sRank;
    }
    
    /**
     * Array of cards of a regular deck.
     */
    private TreeMap<Integer, Card> m_cards = new TreeMap<Integer, Card>();
    
    private TreeMap<String, Card> m_cardsByCode = new TreeMap<String, Card>(String.CASE_INSENSITIVE_ORDER);
    
    /**
     * Identifier of the card
     */
    private int m_id;
    
    /**
     * Default Card constructor.
     */
    private Card()
    {
        for (int i = -2; i != 52; ++i)
        {
            get(i);
        }
    }
    
    /**
     * Card constructor.
     * 
     * @deprecated This constructor should be set to private. Use Card.getInstance() instead.
     * @see {@link #getInstance getInstance(int p_idCard)}
     * @param p_id
     *            Identifier of this card
     */
    @Deprecated
    public Card(int p_id)
    {
        setId(p_id);
    }
    
    /**
     * Compare 2 cards
     */
    public int compareTo(Card o)
    {
        return ((Integer) this.getId()).compareTo(o.getId());
    }
    
    /**
     * Retrieves the instance of a card using its ID.
     */
    public Card get(int p_idCard)
    {
        if (m_cards == null)
        {
            m_cards = new TreeMap<Integer, Card>();
        }
        
        if (m_cardsByCode == null)
        {
            m_cardsByCode = new TreeMap<String, Card>(String.CASE_INSENSITIVE_ORDER);
        }
        
        if (!m_cards.containsKey(p_idCard))
        {
            m_cards.put(p_idCard, new Card(p_idCard));
            m_cardsByCode.put(m_cards.get(p_idCard).getCode(), m_cards.get(p_idCard));
        }
        
        return m_cards.get(p_idCard);
    }
    
    public Card get(String p_code)
    {
        return m_cardsByCode.get(p_code);
    }
    
    /**
     * Get the String code of this card. <br>
     * The first character is the kind (c, d, h, s, j) and the second character
     * is the rank (2 to 9, T, J, Q, K, A)
     * 
     * @return
     *         The code of the card
     */
    public String getCode()
    {
        return (m_id >= 0) ? Card.getCode(m_id) : "" + m_id;
    }
    
    /**
     * Get the identifier
     * 
     * @return
     *         The identifier of this card
     */
    public int getId()
    {
        return m_id;
    }
    
    /**
     * Get the kind of this card.
     * 
     * @return
     *         The kind of the card (0 to 4)
     */
    public int getKind()
    {
        return (m_id >= 0) ? Card.getKind(m_id) : m_id;
        
    }
    
    /**
     * Get the rank of this card.
     * 
     * @return
     *         the rank of the card (0 to 12)
     */
    public int getRank()
    {
        return (m_id >= 0) ? Card.getRank(m_id) : m_id;
    }
    
    /**
     * Check if the card is an hidden card
     * 
     * @return true if the card is hidden
     */
    public boolean isHidden()
    {
        return (m_id == Card.HIDDEN_CARD);
    }
    
    /**
     * Check if the card represent a null card
     * 
     * @return true if the card represent a null card
     */
    public boolean isNoCard()
    {
        return (m_id == Card.NO_CARD);
    }
    
    /**
     * Set the identifier.
     * 
     * @param p_id
     *            New identifier of this card
     */
    public void setId(int p_id)
    {
        if (p_id < Card.CARDS_PER_DECK)
        {
            m_id = p_id;
        }
    }
    
    /**
     * Return the code of the card
     */
    @Override
    public String toString()
    {
        return this.getCode();
    }
    
}
