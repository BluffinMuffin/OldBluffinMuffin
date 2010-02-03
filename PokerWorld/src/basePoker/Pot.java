package basePoker;

import java.util.Vector;


/**
 * @author Hocus
 *         This class represent a pot of a Poker game.
 *         A pot have a certain number of chips and some participant.
 */
public class Pot
{
    
    private int m_id;
    private int m_amount;
    private Vector<PokerPlayerInfo> m_participant;
    
    /**
     * Create a new pot with zero chips, no participant and the id 0.
     */
    public Pot()
    {
        this(0, 0, new Vector<PokerPlayerInfo>());
    }
    
    /**
     * Create a new pot with zero chips, no participant and the specified id.
     * 
     * @param p_id
     *            ID if the pot
     */
    public Pot(int p_id)
    {
        this(p_id, 0, new Vector<PokerPlayerInfo>());
    }
    
    /**
     * create a new pot.
     * 
     * @param p_id
     *            ID of the pot
     * @param p_amount
     *            Number of chips in the pot
     * @param p_participants
     *            Participant to the pot
     */
    public Pot(int p_id, int p_amount, Vector<PokerPlayerInfo> p_participants)
    {
        m_id = p_id;
        m_amount = p_amount;
        m_participant = p_participants;
    }
    
    /**
     * Add some chips to the pot
     * 
     * @param p_amount
     *            The number of chips to add
     */
    public void addAmount(int p_amount)
    {
        m_amount += p_amount;
    }
    
    /**
     * Add a participant to the pot
     * 
     * @param p_participant
     *            The new participant
     * @return
     *         True if the participant was added
     */
    public boolean addParticipant(PokerPlayerInfo p_participant)
    {
        return m_participant.add(p_participant);
    }
    
    /**
     * Return the number of chips in the pot
     * 
     * @return
     *         The number of chips in the pot
     */
    public int getAmount()
    {
        return m_amount;
    }
    
    /**
     * Return the ID of the pot
     * 
     * @return
     */
    public int getId()
    {
        return m_id;
    }
    
    /**
     * Return the list of participant
     * 
     * @return
     *         The list of participant
     */
    public Vector<PokerPlayerInfo> getParticipant()
    {
        return m_participant;
    }
    
    /**
     * Remove all the participant of the pot
     */
    public void removeAllParticipant()
    {
        m_participant = new Vector<PokerPlayerInfo>();
    }
    
    /**
     * Remove a participant to the pot
     * 
     * @param p_participant
     *            The participant to remove
     * @return
     *         True if the participant was removed
     */
    public boolean removeParticipant(int p_participant)
    {
        return m_participant.remove(new Integer(p_participant));
    }
    
    /**
     * Change the number of chips in the pot
     * 
     * @param p_amount
     *            The new number of chips
     */
    public void setAmount(int p_amount)
    {
        m_amount = p_amount;
    }
    
    /**
     * Change the ID of the pot
     * 
     * @param p_id
     *            The new ID
     */
    public void setId(int p_id)
    {
        m_id = p_id;
    }
    
    /**
     * Change the list of participant
     * 
     * @param p_participant
     *            The new list of participant
     */
    public void setParticipant(Vector<PokerPlayerInfo> p_participant)
    {
        m_participant = p_participant;
    }
    
}
