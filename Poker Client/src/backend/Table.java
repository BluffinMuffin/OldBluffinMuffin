package backend;

import java.util.ArrayList;
import java.util.TreeMap;

import basePoker.Card;

import basePoker.TypePokerRound;

/**
 * @author Hocus
 *         This class represents a poker table on the client side.
 */
public class Table
{
    public int m_nbSeats = 9;
    
    public int m_nbPlayers;
    public int m_nbPlayingPlayers;
    public int m_nbRemainingPlayers;
    public String m_name;
    
    public TreeMap<Integer, Player> m_players = new TreeMap<Integer, Player>();
    public ArrayList<Card> m_boardCards = new ArrayList<Card>();
    public ArrayList<Integer> m_pots = new ArrayList<Integer>();
    
    public int m_totalPotAmount = 0;
    
    public int m_noSeatDealer = -1;
    public int m_noSeatSmallBlind = -1;
    public int m_noSeatBigBlind = -1;
    
    public Player m_localPlayer = null;
    public Player m_currentPlayer = null;
    public Player m_dealer = null;
    public Player m_smallBlind = null;
    public Player m_bigBlind = null;
    
    public int m_smallBlindAmount;
    public int m_bigBlindAmount;
    
    public TypePokerRound m_gameState = TypePokerRound.BEGINNING;
    
    public int m_currentBet;
    
    /**
     * Determine player positions and initialize other attributs.
     */
    public void setPlayerPositions()
    {
        // Initialize all player's attributes to default value.
        for (final Player player : m_players.values())
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
            final Player player = m_players.get(i);
            if ((player != null) && player.m_isPlaying)
            {
                player.m_relativePosition = position;
                ++position;
            }
            
            i = (i + 1) % m_nbSeats;
        }
        
        // Set boolean attributes depending the player relative position.
        m_nbPlayingPlayers = 0;
        for (final Player player : m_players.values())
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
