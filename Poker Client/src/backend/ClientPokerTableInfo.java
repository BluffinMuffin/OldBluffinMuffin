package backend;

import basePoker.PokerPlayerInfo;
import basePoker.PokerTableInfo;

/**
 * @author Hocus
 *         This class represents a poker table on the client side.
 */
public class ClientPokerTableInfo extends PokerTableInfo
{
    
    /**
     * Determine player positions and initialize other attributs.
     */
    public void setPlayerPositions()
    {
        // Initialize all player's attributes to default value.
        for (final PokerPlayerInfo player : getPlayers())
        {
            player.m_isPlaying = player.m_money > 0;
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
        i = (i + 1) % m_nbSeats;
        while (i != m_noSeatDealer)
        {
            final PokerPlayerInfo player = m_players[i];
            if ((player != null) && player.m_isPlaying)
            {
                player.m_relativePosition = position;
                ++position;
            }
            
            i = (i + 1) % m_nbSeats;
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
            
            // D, SB and BB are already set by PokerClient.
            switch (m_nbPlayers + tmpPlayers)
            {
                case 9:
                    switch (player.m_relativePosition)
                    {
                        case 4:
                            player.m_isEarlyPos = true;
                            player.m_isMidPos = false;
                            player.m_isCutOff = false;
                            break;
                        case 5:
                            player.m_isEarlyPos = true;
                            player.m_isMidPos = false;
                            player.m_isCutOff = false;
                            break;
                        case 6:
                            player.m_isEarlyPos = false;
                            player.m_isMidPos = true;
                            player.m_isCutOff = false;
                            break;
                        case 7:
                            player.m_isEarlyPos = false;
                            player.m_isMidPos = true;
                            player.m_isCutOff = false;
                            break;
                        case 8:
                            player.m_isEarlyPos = false;
                            player.m_isMidPos = true;
                            player.m_isCutOff = false;
                            break;
                        case 9:
                            player.m_isEarlyPos = false;
                            player.m_isMidPos = false;
                            player.m_isCutOff = true;
                            break;
                    }
                    break;
                case 8:
                    switch (player.m_relativePosition)
                    {
                        case 4:
                            player.m_isEarlyPos = true;
                            player.m_isMidPos = false;
                            player.m_isCutOff = false;
                            break;
                        case 5:
                            player.m_isEarlyPos = true;
                            player.m_isMidPos = false;
                            player.m_isCutOff = false;
                            break;
                        case 6:
                            player.m_isEarlyPos = false;
                            player.m_isMidPos = true;
                            player.m_isCutOff = false;
                            break;
                        case 7:
                            player.m_isEarlyPos = false;
                            player.m_isMidPos = true;
                            player.m_isCutOff = false;
                            break;
                        case 8:
                            player.m_isEarlyPos = false;
                            player.m_isMidPos = false;
                            player.m_isCutOff = true;
                            break;
                    }
                    break;
                case 7:
                    switch (player.m_relativePosition)
                    {
                        case 4:
                            player.m_isEarlyPos = true;
                            player.m_isMidPos = false;
                            player.m_isCutOff = false;
                            break;
                        case 5:
                            player.m_isEarlyPos = false;
                            player.m_isMidPos = true;
                            player.m_isCutOff = false;
                            break;
                        case 6:
                            player.m_isEarlyPos = false;
                            player.m_isMidPos = true;
                            player.m_isCutOff = false;
                            break;
                        case 7:
                            player.m_isEarlyPos = false;
                            player.m_isMidPos = false;
                            player.m_isCutOff = true;
                            break;
                    }
                    break;
                case 6:
                    switch (player.m_relativePosition)
                    {
                        case 4:
                            player.m_isEarlyPos = true;
                            player.m_isMidPos = false;
                            player.m_isCutOff = false;
                            break;
                        case 5:
                            player.m_isEarlyPos = false;
                            player.m_isMidPos = true;
                            player.m_isCutOff = false;
                            break;
                        case 6:
                            player.m_isEarlyPos = false;
                            player.m_isMidPos = false;
                            player.m_isCutOff = true;
                            break;
                    }
                    break;
                case 5:
                    switch (player.m_relativePosition)
                    {
                        case 4:
                            player.m_isEarlyPos = true;
                            player.m_isMidPos = false;
                            player.m_isCutOff = false;
                            break;
                        case 5:
                            player.m_isEarlyPos = false;
                            player.m_isMidPos = false;
                            player.m_isCutOff = true;
                            break;
                    }
                    break;
                case 4:
                    if (player.m_relativePosition == 4)
                    {
                        player.m_isEarlyPos = false;
                        player.m_isMidPos = false;
                        player.m_isCutOff = true;
                    }
                    break;
            }
        }
        
        m_nbRemainingPlayers = m_nbPlayingPlayers;
    }
}
