package protocolGame;

import gameLogic.GameCard;

import java.util.ArrayList;
import java.util.StringTokenizer;

import newPokerLogic.PokerMoneyPot;
import newPokerLogic.PokerPlayerInfo;
import newPokerLogic.PokerTableInfo;
import protocolGameTools.SummarySeatInfo;
import protocolTools.IPokerCommand;
import utility.Constants;

public class GameTableInfoCommand implements IPokerCommand
{
    private final int m_totalPotAmount;
    private final int m_nbSeats;
    private final ArrayList<Integer> m_potsAmount;
    private final ArrayList<Integer> m_boardCardIDs;
    private final int m_nbPlayers;
    private final ArrayList<SummarySeatInfo> m_seats;
    
    public static String COMMAND_NAME = "gameTABLE_INFO";
    
    public GameTableInfoCommand(StringTokenizer argsToken)
    {
        m_potsAmount = new ArrayList<Integer>();
        m_boardCardIDs = new ArrayList<Integer>();
        m_seats = new ArrayList<SummarySeatInfo>();
        
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
            m_seats.add(new SummarySeatInfo(argsToken));
        }
    }
    
    public GameTableInfoCommand(int totalPotAmount, int nbSeats, ArrayList<Integer> potsAmount, ArrayList<Integer> boardCardIDs, int nbPlayers, ArrayList<SummarySeatInfo> seats)
    {
        m_totalPotAmount = totalPotAmount;
        m_nbSeats = nbSeats;
        m_potsAmount = potsAmount;
        m_boardCardIDs = boardCardIDs;
        m_nbPlayers = nbPlayers;
        m_seats = seats;
    }
    
    public GameTableInfoCommand(PokerTableInfo info, PokerPlayerInfo pPlayer)
    {
        m_potsAmount = new ArrayList<Integer>();
        m_boardCardIDs = new ArrayList<Integer>();
        m_seats = new ArrayList<SummarySeatInfo>();
        
        m_totalPotAmount = info.getTotalPotAmount();
        m_nbSeats = info.getNbMaxSeats();
        m_nbPlayers = info.getNbMaxSeats();
        
        for (final PokerMoneyPot pot : info.getPots())
        {
            m_potsAmount.add(pot.getAmount());
        }
        
        for (int i = info.getPots().size(); i < m_nbSeats; i++)
        {
            m_potsAmount.add(0);
        }
        final GameCard[] boardCards = new GameCard[5];
        info.getCurrentBoardCards().toArray(boardCards);
        for (int i = 0; i < 5; ++i)
        {
            if (boardCards[i] == null)
            {
                m_boardCardIDs.add(GameCard.NO_CARD_ID);
            }
            else
            {
                m_boardCardIDs.add(boardCards[i].getId());
            }
        }
        
        for (int i = 0; i < info.getNbMaxSeats(); ++i)
        {
            final SummarySeatInfo seat = new SummarySeatInfo(i);
            m_seats.add(seat);
            final PokerPlayerInfo player = info.getPlayer(i);
            seat.m_isEmpty = (player == null);
            
            if (seat.m_isEmpty)
            {
                continue;
            }
            
            seat.m_playerName = player.getPlayerName(); // playerName
            seat.m_money = player.getCurrentSafeMoneyAmount(); // playerMoney
            
            final boolean showCard = (i == pPlayer.getCurrentTablePosition());
            
            // Player cards
            final GameCard[] holeCards = player.getCurrentHand(showCard);
            for (int j = 0; j < 2; ++j)
            {
                seat.m_holeCardIDs.add(holeCards[j].getId());
            }
            
            seat.m_isDealer = info.getCurrentDealerNoSeat() == i; // isDealer
            seat.m_isSmallBlind = info.getCurrentSmallBlindNoSeat() == i; // isSmallBlind
            seat.m_isBigBlind = info.getCurrentBigBlindNoSeat() == i; // isBigBlind
            seat.m_isCurrentPlayer = info.getCurrentPlayerNoSeat() == i; // isCurrentPlayer
            seat.m_timeRemaining = 0; // timeRemaining
            seat.m_bet = player.getCurrentBetMoneyAmount(); // betAmount
            seat.m_isPlaying = player.isPlaying();
        }
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(GameTableInfoCommand.COMMAND_NAME);
        sb.append(Constants.DELIMITER);
        sb.append(m_totalPotAmount);
        sb.append(Constants.DELIMITER);
        sb.append(m_nbSeats);
        sb.append(Constants.DELIMITER);
        for (int i = 0; i < m_potsAmount.size(); ++i)
        {
            sb.append(m_potsAmount.get(i));
            sb.append(Constants.DELIMITER);
        }
        for (int i = 0; i < m_boardCardIDs.size(); ++i)
        {
            sb.append(m_boardCardIDs.get(i));
            sb.append(Constants.DELIMITER);
        }
        sb.append(m_nbPlayers);
        sb.append(Constants.DELIMITER);
        for (int i = 0; i < m_seats.size(); ++i)
        {
            sb.append(m_seats.get(i).toString(Constants.DELIMITER));
        }
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
    
    public ArrayList<SummarySeatInfo> getSeats()
    {
        return m_seats;
    }
}
