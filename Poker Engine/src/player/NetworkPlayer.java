package player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

import miscUtil.Constants;

import backend.HoldEmTable;
import backend.IHoldEmObserver;
import backend.Pot;
import basePoker.Card;
import basePoker.PokerPlayerAction;
import basePoker.TypePlayerAction;

@Deprecated
public class NetworkPlayer extends ServerPokerPlayer implements IHoldEmObserver
{
    
    private boolean m_isConnected;
    private final Socket m_socket;
    private final PrintWriter m_output;
    private final BufferedReader m_input;
    
    public NetworkPlayer(String p_name, int p_money, Socket p_socket) throws IOException
    {
        super(p_name, p_money);
        m_socket = p_socket;
        m_output = new PrintWriter(m_socket.getOutputStream(), true);
        m_input = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
        m_isConnected = true;
    }
    
    public void bigBlindPosted(HoldEmTable p_table, ServerPokerPlayer p_player, int p_bigBlind)
    {
        m_output.println(makeNormalMessage());
    }
    
    // ---------------------------------------------
    // IHoldEmObserver
    // ---------------------------------------------
    
    public void dealerChanged(HoldEmTable p_table, ServerPokerPlayer p_old, ServerPokerPlayer p_new)
    {
        m_output.println(makeNormalMessage());
    }
    
    public void endBettingTurn(HoldEmTable p_table)
    {
        m_output.println(makeNormalMessage());
    }
    
    public void flopDealt(HoldEmTable p_table, Card[] p_board)
    {
        m_output.println(makeNormalMessage());
    }
    
    public void gameEnded(HoldEmTable p_table)
    {
        m_output.println(makeNormalMessage());
    }
    
    public void gameStarted(HoldEmTable p_table)
    {
        m_output.println(makeNormalMessage());
    }
    
    // ---------------------------------------------
    // Player
    // ---------------------------------------------
    @Override
    protected PokerPlayerAction getActionFromUser(boolean p_canCheck, boolean p_canFold, boolean p_canCall, int p_callOf, boolean p_canRaise, int p_minimumRaise, int p_maximumRaise)
    {
        PokerPlayerAction action = null;
        if (m_isConnected)
        {
            try
            {
                while (action == null)
                {
                    // m_output.println(p_canCheck + Constants.DELIMITER
                    // + p_canFold + Constants.DELIMITER + p_canCall + Constants.DELIMITER + p_callOf + Constants.DELIMITER
                    // + p_canRaise + Constants.DELIMITER + p_minimumRaise + Constants.DELIMITER + p_maximumRaise + Constants.DELIMITER
                    // + m_table.marshal(this, new ArrayList<Integer>()));
                    
                    final StringTokenizer message = new StringTokenizer(m_input.readLine(), Constants.DELIMITER);
                    System.out.println(message.hasMoreTokens());
                    String token;
                    if (message.hasMoreTokens())
                    {
                        token = message.nextToken();
                        action = new PokerPlayerAction(TypePlayerAction.valueOf(token), 0);
                        if (action.getType() == TypePlayerAction.CALL)
                        {
                            action.setAmount(p_callOf);
                        }
                        else if (action.getType() == TypePlayerAction.RAISE)
                        {
                            action.setAmount(Integer.parseInt(message.nextToken()));
                        }
                    }
                }
            }
            catch (final IllegalArgumentException e)
            {
                action = null;
            }
            catch (final IOException e)
            {
                m_isConnected = false;
                m_sitOutNextHand = true;
            }
        }
        
        if (!m_isConnected)
        {
            if (p_canCheck)
            {
                action = new PokerPlayerAction(TypePlayerAction.CHECK, 0);
            }
            else
            {
                action = new PokerPlayerAction(TypePlayerAction.FOLD, 0);
            }
        }
        
        return action;
    }
    
    public void holeCardDeal(HoldEmTable p_table, ServerPokerPlayer p_player)
    {
        m_output.println(makeNormalMessage());
    }
    
    private String makeNormalMessage()
    {
        return null;
        // return false + Constants.DELIMITER
        // + false + Constants.DELIMITER + false + Constants.DELIMITER + 0 + Constants.DELIMITER
        // + false + Constants.DELIMITER + 0 + Constants.DELIMITER + 0 + Constants.DELIMITER
        // + m_table.marshal(this, new ArrayList<Integer>());
    }
    
    public void playerEndTurn(HoldEmTable p_table, ServerPokerPlayer p_player, PokerPlayerAction p_action)
    {
        m_output.println(makeNormalMessage());
    }
    
    public void playerJoinedTable(HoldEmTable p_table, ServerPokerPlayer p_player)
    {
        m_output.println(makeNormalMessage());
    }
    
    public void playerLeftTable(HoldEmTable p_table, ServerPokerPlayer p_player)
    {
        m_output.println(makeNormalMessage());
    }
    
    public void playerMoneyChanged(HoldEmTable p_table, ServerPokerPlayer p_player)
    {
        m_output.println(makeNormalMessage());
    }
    
    public void playerShowCard(HoldEmTable p_table, ServerPokerPlayer p_player)
    {
        m_output.println(makeNormalMessage());
    }
    
    public void playerTurnStarted(HoldEmTable p_table, ServerPokerPlayer p_player)
    {
        m_output.println(makeNormalMessage());
    }
    
    public void potWon(HoldEmTable p_table, ServerPokerPlayer p_player, Pot p_pot, int p_share)
    {
        final ArrayList<Integer> winners = new ArrayList<Integer>();
        winners.add(p_player.getTablePosition());
        // m_output.println(false + Constants.DELIMITER
        // + false + Constants.DELIMITER + false + Constants.DELIMITER + 0 + Constants.DELIMITER
        // + false + Constants.DELIMITER + 0 + Constants.DELIMITER + 0 + Constants.DELIMITER
        // + m_table.marshal(this, winners));
    }
    
    public void riverDeal(HoldEmTable p_table, Card[] p_board)
    {
        m_output.println(makeNormalMessage());
    }
    
    public void smallBlindPosted(HoldEmTable p_table, ServerPokerPlayer p_player, int p_smallBlind)
    {
        m_output.println(makeNormalMessage());
    }
    
    public void tableEnded(HoldEmTable p_table)
    {
        // To Do
    }
    
    public void tableInfos(HoldEmTable pTable)
    {
    }
    
    public void tableStarted(HoldEmTable p_table)
    {
        // do nothing
    }
    
    public void turnDeal(HoldEmTable p_table, Card[] p_board)
    {
        m_output.println(makeNormalMessage());
    }
    
    public void waitingForPlayers(HoldEmTable p_table)
    {
        // nothing
    }
    
}
