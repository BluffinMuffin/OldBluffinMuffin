package bluffinmuffin.protocol.commands.game;


import java.util.ArrayList;
import java.util.StringTokenizer;

import bluffinmuffin.game.entities.Card;
import bluffinmuffin.poker.entities.PotInfo;
import bluffinmuffin.poker.entities.PlayerInfo;
import bluffinmuffin.poker.entities.TableInfo;
import bluffinmuffin.poker.entities.type.GameBetLimitType;
import bluffinmuffin.protocol.TuplePlayerInfo;
import bluffinmuffin.protocol.commands.Command;
import bluffinmuffin.protocol.commands.ICommand;


public class TableInfoCommand implements ICommand
{
    private final int m_totalPotAmount;
    private final int m_nbSeats;
    private final ArrayList<Integer> m_potsAmount;
    private final ArrayList<Integer> m_boardCardIDs;
    private final int m_nbPlayers;
    private final ArrayList<TuplePlayerInfo> m_seats;
    private final GameBetLimitType m_limit;
    
    public static String COMMAND_NAME = "gameTABLE_INFO";
    
    public TableInfoCommand(StringTokenizer argsToken)
    {
        m_potsAmount = new ArrayList<Integer>();
        m_boardCardIDs = new ArrayList<Integer>();
        m_seats = new ArrayList<TuplePlayerInfo>();
        
        m_totalPotAmount = Integer.parseInt(argsToken.nextToken());
        m_nbSeats = Integer.parseInt(argsToken.nextToken());
        for (int i = 0; i < m_nbSeats; ++i)
        {
            m_potsAmount.add(Integer.parseInt(argsToken.nextToken()));
        }
        for (int i = 0; i < 5; ++i)
        {
            m_boardCardIDs.add(Integer.parseInt(argsToken.nextToken()));
        }
        m_nbPlayers = Integer.parseInt(argsToken.nextToken());
        for (int i = 0; i < m_nbPlayers; ++i)
        {
            m_seats.add(new TuplePlayerInfo(argsToken));
        }
        m_limit = GameBetLimitType.values()[Integer.parseInt(argsToken.nextToken())];
    }
    
    public TableInfoCommand(int totalPotAmount, int nbSeats, ArrayList<Integer> potsAmount, ArrayList<Integer> boardCardIDs, int nbPlayers, ArrayList<TuplePlayerInfo> seats, GameBetLimitType limit)
    {
        m_totalPotAmount = totalPotAmount;
        m_nbSeats = nbSeats;
        m_potsAmount = potsAmount;
        m_boardCardIDs = boardCardIDs;
        m_nbPlayers = nbPlayers;
        m_seats = seats;
        m_limit = limit;
    }
    
    public TableInfoCommand(TableInfo info, PlayerInfo pPlayer)
    {
        m_potsAmount = new ArrayList<Integer>();
        m_boardCardIDs = new ArrayList<Integer>();
        m_seats = new ArrayList<TuplePlayerInfo>();
        
        m_totalPotAmount = info.getTotalPotAmnt();
        m_nbSeats = info.getNbMaxSeats();
        m_nbPlayers = info.getNbMaxSeats();
        
        for (final PotInfo pot : info.getPots())
        {
            m_potsAmount.add(pot.getAmount());
        }
        
        for (int i = info.getPots().size(); i < m_nbSeats; i++)
        {
            m_potsAmount.add(0);
        }
        final Card[] boardCards = new Card[5];
        info.getCards().toArray(boardCards);
        for (int i = 0; i < 5; ++i)
        {
            if (boardCards[i] == null)
            {
                m_boardCardIDs.add(Card.NO_CARD_ID);
            }
            else
            {
                m_boardCardIDs.add(boardCards[i].getId());
            }
        }
        
        for (int i = 0; i < info.getNbMaxSeats(); ++i)
        {
            final TuplePlayerInfo seat = new TuplePlayerInfo(i);
            m_seats.add(seat);
            final PlayerInfo player = info.getPlayer(i);
            seat.m_isEmpty = (player == null);
            
            if (seat.m_isEmpty)
            {
                continue;
            }
            
            seat.m_playerName = player.getName(); // playerName
            seat.m_money = player.getMoneySafeAmnt(); // playerMoney
            
            final boolean showCard = (i == pPlayer.getNoSeat());
            
            // Player cards
            final Card[] holeCards = player.getCards(showCard);
            for (int j = 0; j < 2; ++j)
            {
                seat.m_holeCardIDs.add(holeCards[j].getId());
            }
            
            seat.m_isDealer = info.getNoSeatDealer() == i; // isDealer
            seat.m_isSmallBlind = info.getNoSeatSmallBlind() == i; // isSmallBlind
            seat.m_isBigBlind = info.getNoSeatBigBlind() == i; // isBigBlind
            seat.m_isCurrentPlayer = info.getNoSeatCurrPlayer() == i; // isCurrentPlayer
            seat.m_timeRemaining = 0; // timeRemaining
            seat.m_bet = player.getMoneyBetAmnt(); // betAmount
            seat.m_isPlaying = player.isPlaying();
        }
        m_limit = info.getBetLimit();
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(TableInfoCommand.COMMAND_NAME);
        sb.append(Command.G_DELIMITER);
        sb.append(m_totalPotAmount);
        sb.append(Command.G_DELIMITER);
        sb.append(m_nbSeats);
        sb.append(Command.G_DELIMITER);
        for (int i = 0; i < m_potsAmount.size(); ++i)
        {
            sb.append(m_potsAmount.get(i));
            sb.append(Command.G_DELIMITER);
        }
        for (int i = 0; i < m_boardCardIDs.size(); ++i)
        {
            sb.append(m_boardCardIDs.get(i));
            sb.append(Command.G_DELIMITER);
        }
        sb.append(m_nbPlayers);
        sb.append(Command.G_DELIMITER);
        for (int i = 0; i < m_seats.size(); ++i)
        {
            sb.append(m_seats.get(i).toString(Command.G_DELIMITER));
        }
        sb.append(m_limit.ordinal());
        sb.append(Command.G_DELIMITER);
        return sb.toString();
    }
    
    public int getTotalPotAmount()
    {
        return m_totalPotAmount;
    }
    
    public int getNbSeats()
    {
        return m_nbSeats;
    }
    
    public ArrayList<Integer> getPotsAmount()
    {
        return m_potsAmount;
    }
    
    public ArrayList<Integer> getBoardCardIDs()
    {
        return m_boardCardIDs;
    }
    
    public int getNbPlayers()
    {
        return m_nbPlayers;
    }
    
    public ArrayList<TuplePlayerInfo> getSeats()
    {
        return m_seats;
    }
    
    public GameBetLimitType getLimit()
    {
        return m_limit;
    }
}
