package clientGame;

import java.util.ArrayList;

import pokerLogic.PokerPlayerInfo;
import pokerLogic.PokerTableInfo;

/**
 * @author Hocus
 *         This class represents a poker table on the client side.
 */
public class ClientPokerTableInfo extends PokerTableInfo
{
    
    public ArrayList<Integer> m_pots = new ArrayList<Integer>();
    public PokerPlayerInfo m_localPlayer = null;
    public PokerPlayerInfo m_bigBlind = null;
    public PokerPlayerInfo m_smallBlind = null;
    public PokerPlayerInfo m_dealer = null;
    public PokerPlayerInfo m_currentPlayer = null;
    public int m_nbRemainingPlayers;
    
    /**
     * Determine player positions and initialize other attributs.
     */
    public void setPlayerPositions()
    {
        // Initialize all player's attributes to default value.
        for (final PokerPlayerInfo player : getPlayers())
        {
            player.m_isPlaying = player.getMoney() > 0;
            player.m_isEarlyPos = false;
            player.m_isCutOff = false;
            player.m_isMidPos = false;
            player.m_relativePosition = -1;
        }
        
        int tmpPlayers = 0;
        
        // Set Dealer
        if (m_dealer != null)
        {
            m_dealer.m_relativePosition = 1;
        }
        else
        {
            ++tmpPlayers;
        }
        
        // Set Small Blind
        if (m_smallBlind != null)
        {
            m_smallBlind.m_relativePosition = 2;
        }
        else
        {
            ++tmpPlayers;
        }
        
        // Set Big Blind
        m_bigBlind.m_relativePosition = 3;
        
        int position = 4;
        int i = m_noSeatBigBlind;
        
        // Determine position of the players.
        i = (i + 1) % getNbSeats();
        while (i != m_noSeatDealer)
        {
            final PokerPlayerInfo player = getPlayer(i);
            if ((player != null) && player.m_isPlaying)
            {
                player.m_relativePosition = position;
                ++position;
            }
            
            i = (i + 1) % getNbSeats();
        }
        
        // Set boolean attributes depending the player relative position.
        m_nbPlayingPlayers = 0;
        for (final PokerPlayerInfo player : getPlayers())
        {
            if (!player.m_isPlaying)
            {
                continue;
            }
            
            ++m_nbPlayingPlayers;
            
            final int nb = getNbPlayers() + tmpPlayers;
            if (i > 3)
            {
                player.m_isCutOff = (nb == player.m_relativePosition);
                if (i > 4)
                {
                    player.m_isEarlyPos = player.m_relativePosition < (2 + (nb / 2));
                    if (i > 5)
                    {
                        player.m_isMidPos = !player.m_isCutOff && !player.m_isEarlyPos;
                    }
                    else
                    {
                        player.m_isMidPos = false;
                    }
                }
                else
                {
                    player.m_isEarlyPos = false;
                    player.m_isMidPos = false;
                }
            }
            else
            {
                player.m_isEarlyPos = false;
                player.m_isMidPos = false;
                player.m_isCutOff = false;
            }
            
            m_nbRemainingPlayers = m_nbPlayingPlayers;
        }
    }
}
