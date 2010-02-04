package serverLogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Stack;
import java.util.StringTokenizer;

import pokerLogic.Card;
import pokerLogic.PokerPlayerAction;
import pokerLogic.Pot;
import pokerLogic.TypePlayerAction;
import protocolGame.TypeMessageTableToClient;
import utility.Constants;



/**
 * @author HOCUS
 *         This player is a network player that work over the TCP/IP protocol.
 *         A new message is sent to the client when the table change.
 *         This class implements IHoldEmObserver in order to be notify when the table change.
 *         When an action is required, we send a TAKE_ACTION message to the client
 *         and wait for his answer. *
 *         When a connection is lost, this player will automatically sit out at the next hand
 *         and will play Check or Fold actions.
 */
public class ServerNetworkPokerPlayerInfo extends ServerPokerPlayerInfo implements IServerPokerObserver
{
    
    public static final int PING_INTERVAL = 10000;
    
    // TCP socket
    private Socket m_socket;
    
    // Communication channel to the client
    private final PrintWriter m_output;
    
    // Communication channel from the client
    private final BufferedReader m_input;
    
    // if the player is connected to a client
    private boolean m_isConnected;
    
    private long m_lastPing;
    
    /**
     * Create a new player
     * 
     * @param p_name
     *            The name of the player
     * @param p_money
     *            The starting chips of the player
     * @param p_socket
     *            The TCP socket with the client
     * @throws IOException
     *             Error with the TCP connection
     */
    public ServerNetworkPokerPlayerInfo(String p_name, int p_money, Socket p_socket) throws IOException
    {
        super(p_name, p_money);
        m_socket = p_socket;
        m_output = new PrintWriter(m_socket.getOutputStream(), true);
        m_input = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
        m_isConnected = false;
    }
    
    @Override
    public void bigBlindPosted(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player, int p_bigBlind)
    {
        send(TypeMessageTableToClient.PLAYER_TURN_ENDED.toString() + Constants.DELIMITER + p_player.getTablePosition() + Constants.DELIMITER + p_player.getBet() + Constants.DELIMITER + p_player.getMoney() + Constants.DELIMITER + p_table.getTotalPot() + Constants.DELIMITER + TypePlayerAction.BIG_BLIND.name() + Constants.DELIMITER + p_bigBlind);
    }
    
    @Override
    public boolean canStartGame()
    {
        return m_isConnected && super.canStartGame();
    }
    
    @Override
    public void endBettingTurn(ServerTableCommunicator p_table)
    {
        final StringBuilder message = new StringBuilder();
        
        message.append(TypeMessageTableToClient.BETTING_TURN_ENDED.toString());
        
        final Stack<Pot> pots = p_table.getPots();
        
        for (final Pot pot : pots)
        {
            message.append(Constants.DELIMITER + pot.getAmount());
        }
        
        for (int i = pots.size(); i < p_table.getNbSeats(); i++)
        {
            message.append(Constants.DELIMITER + 0);
        }
        
        message.append(Constants.DELIMITER + p_table.getGameState().toString());
        
        send(message.toString());
    }
    
    @Override
    public void flopDealt(ServerTableCommunicator p_table, Card[] p_board)
    {
        send(TypeMessageTableToClient.BOARD_CHANGED + Constants.DELIMITER + p_board[0].getId() + Constants.DELIMITER + p_board[1].getId() + Constants.DELIMITER + p_board[2].getId() + Constants.DELIMITER + Card.NO_CARD + Constants.DELIMITER + Card.NO_CARD);
    }
    
    @Override
    public void gameEnded(ServerTableCommunicator p_table)
    {
        send(TypeMessageTableToClient.GAME_ENDED.toString());
    }
    
    @Override
    public void gameStarted(ServerTableCommunicator p_table)
    {
        send(TypeMessageTableToClient.GAME_STARTED.toString() + Constants.DELIMITER + p_table.getNoSeatDealer() + Constants.DELIMITER + p_table.getNoSeatSmallBlind() + Constants.DELIMITER + p_table.getNoSeatBigBlind());
    }
    
    // ---------------------------------------------
    // Player
    // ---------------------------------------------
    @Override
    protected PokerPlayerAction getActionFromUser(boolean p_canCheck, boolean p_canFold, boolean p_canCall, int p_callOf, boolean p_canRaise, int p_minimumRaise, int p_maximumRaise)
    {
        PokerPlayerAction action = null;
        int nbTry = 0;
        if (m_isConnected)
        {
            try
            {
                while ((action == null) && m_isConnected && (nbTry < 10))
                {
                    ++nbTry;
                    send(TypeMessageTableToClient.TAKE_ACTION.toString() + Constants.DELIMITER + p_canCheck + Constants.DELIMITER + p_canFold + Constants.DELIMITER + p_canCall + Constants.DELIMITER + p_callOf + Constants.DELIMITER + p_canRaise + Constants.DELIMITER + p_minimumRaise + Constants.DELIMITER + p_maximumRaise);
                    
                    final String line = m_input.readLine();
                    if (line != null)
                    {
                        final StringTokenizer message = new StringTokenizer(line, Constants.DELIMITER);
                        String token;
                        if (message.hasMoreTokens())
                        {
                            token = message.nextToken();
                            final TypePlayerAction actionType = TypePlayerAction.valueOf(token);
                            switch (actionType)
                            {
                                case CHECK:
                                    if (p_canCheck)
                                    {
                                        action = new PokerPlayerAction(actionType, 0);
                                    }
                                    break;
                                case CALL:
                                    if (p_canCall)
                                    {
                                        action = new PokerPlayerAction(actionType, p_callOf);
                                    }
                                    break;
                                case FOLD:
                                    if (p_canFold)
                                    {
                                        action = new PokerPlayerAction(actionType, 0);
                                    }
                                    break;
                                case RAISE:
                                    try
                                    {
                                        if (message.hasMoreTokens())
                                        {
                                            final int amount = Integer.parseInt(message.nextToken());
                                            if (p_canRaise && (amount >= p_minimumRaise) && (amount <= p_maximumRaise))
                                            {
                                                action = new PokerPlayerAction(actionType, amount);
                                            }
                                        }
                                    }
                                    catch (final NumberFormatException e)
                                    {
                                        action = null;
                                    }
                                    break;
                            }
                        }
                    }
                    else
                    {
                        m_isConnected = false;
                        m_sitOutNextHand = true;
                    }
                }
            }
            catch (final IOException e)
            {
                m_isConnected = false;
                m_sitOutNextHand = true;
            }
        }
        
        if (action == null)
        {
            if (p_canCheck)
            {
                action = new PokerPlayerAction(TypePlayerAction.CHECK, 0);
            }
            else
            {
                action = new PokerPlayerAction(TypePlayerAction.FOLD, 0);
            }
            
            if (nbTry >= 10)
            {
                System.out.println("Player kicked out :" + m_name);
                m_isConnected = false;
                m_sitOutNextHand = true;
                try
                {
                    m_socket.close();
                }
                catch (final IOException e)
                {
                }
                m_socket = null;
            }
        }
        
        return action;
    }
    
    @Override
    public void holeCardDeal(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player)
    {
        if (p_player.getTablePosition() != getTablePosition())
        {
            send(TypeMessageTableToClient.PLAYER_CARD_CHANGED.toString() + Constants.DELIMITER + p_player.getTablePosition() + Constants.DELIMITER + Card.HIDDEN_CARD + Constants.DELIMITER + Card.HIDDEN_CARD);
        }
        else
        {
            send(TypeMessageTableToClient.PLAYER_CARD_CHANGED.toString() + Constants.DELIMITER + p_player.getTablePosition() + Constants.DELIMITER + p_player.getHand()[0].getId() + Constants.DELIMITER + p_player.getHand()[1].getId());
        }
        
    }
    
    @Override
    public void playerEndTurn(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player, PokerPlayerAction p_action)
    {
        send(TypeMessageTableToClient.PLAYER_TURN_ENDED.toString() + Constants.DELIMITER + p_player.getTablePosition() + Constants.DELIMITER + p_player.getBet() + Constants.DELIMITER + p_player.getMoney() + Constants.DELIMITER + p_table.getTotalPot() + Constants.DELIMITER + p_action.getType().name() + Constants.DELIMITER + p_action.getAmount());
    }
    
    @Override
    public void playerJoinedTable(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player)
    {
        send(TypeMessageTableToClient.PLAYER_JOINED.toString() + Constants.DELIMITER + p_player.getTablePosition() + Constants.DELIMITER + p_player.getName() + Constants.DELIMITER + p_player.getMoney());
    }
    
    @Override
    public void playerLeftTable(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player)
    {
        send(TypeMessageTableToClient.PLAYER_LEFT.toString() + Constants.DELIMITER + p_player.getTablePosition());
    }
    
    @Override
    public void playerMoneyChanged(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player)
    {
        send(TypeMessageTableToClient.PLAYER_MONEY_CHANGED + Constants.DELIMITER + p_player.getTablePosition() + Constants.DELIMITER + p_player.getMoney());
    }
    
    @Override
    public void playerShowCard(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player)
    {
        send(TypeMessageTableToClient.PLAYER_CARD_CHANGED + Constants.DELIMITER + p_player.getTablePosition() + Constants.DELIMITER + p_player.getHand()[0].getId() + Constants.DELIMITER + p_player.getHand()[1].getId());
    }
    
    @Override
    public void playerTurnStarted(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player)
    {
        send(TypeMessageTableToClient.PLAYER_TURN_BEGAN.toString() + Constants.DELIMITER + p_player.getTablePosition());
    }
    
    @Override
    public void potWon(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player, Pot p_pot, int p_share)
    {
        send(TypeMessageTableToClient.POT_WON + Constants.DELIMITER + p_player.getTablePosition() + Constants.DELIMITER + p_pot.getId() + Constants.DELIMITER + p_share + Constants.DELIMITER + p_player.getMoney());
    }
    
    @Override
    public void riverDeal(ServerTableCommunicator p_table, Card[] p_board)
    {
        send(TypeMessageTableToClient.BOARD_CHANGED + Constants.DELIMITER + p_board[0].getId() + Constants.DELIMITER + p_board[1].getId() + Constants.DELIMITER + p_board[2].getId() + Constants.DELIMITER + p_board[3].getId() + Constants.DELIMITER + p_board[4].getId());
    }
    
    /**
     * Send a message to the client
     * 
     * @param p_msg
     *            The message to send
     */
    protected void send(String p_msg)
    {
        // System.out.println("Server SEND TO " + this.m_name + " [" + p_msg + "]");
        m_output.println(p_msg);
    }
    
    /**
     * Make the player connected
     */
    public void setIsConnected()
    {
        m_isConnected = true;
    }
    
    /**
     * To test if the player is connected, we send a ping to the client and wait his repsonse.
     * We do not send a new ping until the PING_INTERVAL milliseconds have past.
     */
    @Override
    public boolean sitOutNextHand()
    {
        if (m_isConnected && !m_sitOutNextHand && ((m_lastPing + ServerNetworkPokerPlayerInfo.PING_INTERVAL) <= System.currentTimeMillis()))
        {
            m_lastPing = System.currentTimeMillis();
            try
            {
                send(TypeMessageTableToClient.PING.toString());
                
                final String line = m_input.readLine();
                if (line != null)
                {
                    final StringTokenizer message = new StringTokenizer(line, Constants.DELIMITER);
                    if (!message.hasMoreTokens())
                    {
                        m_isConnected = false;
                        m_sitOutNextHand = true;
                    }
                }
                else
                {
                    m_isConnected = false;
                    m_sitOutNextHand = true;
                }
            }
            catch (final IOException e)
            {
                m_isConnected = false;
                m_sitOutNextHand = true;
            }
        }
        
        return m_sitOutNextHand;
    }
    
    @Override
    public void smallBlindPosted(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player, int p_smallBlind)
    {
        send(TypeMessageTableToClient.PLAYER_TURN_ENDED.toString() + Constants.DELIMITER + p_player.getTablePosition() + Constants.DELIMITER + p_player.getBet() + Constants.DELIMITER + p_player.getMoney() + Constants.DELIMITER + p_table.getTotalPot() + Constants.DELIMITER + TypePlayerAction.SMALL_BLIND.name() + Constants.DELIMITER + p_smallBlind);
    }
    
    @Override
    public void tableEnded(ServerTableCommunicator p_table)
    {
        send(TypeMessageTableToClient.TABLE_CLOSED.toString());
    }
    
    @Override
    public void tableInfos(ServerTableCommunicator p_table)
    {
        send(TypeMessageTableToClient.TABLE_INFOS + Constants.DELIMITER + m_table.marshal(this));
    }
    
    // ---------------------------------------------
    // IHoldEmObserver
    // ---------------------------------------------
    @Override
    public void tableStarted(ServerTableCommunicator p_table)
    {
        // do nothing
    }
    
    @Override
    public void turnDeal(ServerTableCommunicator p_table, Card[] p_board)
    {
        send(TypeMessageTableToClient.BOARD_CHANGED + Constants.DELIMITER + p_board[0].getId() + Constants.DELIMITER + p_board[1].getId() + Constants.DELIMITER + p_board[2].getId() + Constants.DELIMITER + p_board[3].getId() + Constants.DELIMITER + Card.NO_CARD);
    }
    
    @Override
    public void waitingForPlayers(ServerTableCommunicator p_table)
    {
        send(TypeMessageTableToClient.WAITING_FOR_PLAYERS.toString());
    }
    
}
