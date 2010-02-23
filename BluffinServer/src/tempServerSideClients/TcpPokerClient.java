package tempServerSideClients;

import gameLogic.GameCard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Stack;

import newPokerLogic.PokerGame;
import newPokerLogic.PokerMoneyPot;
import newPokerLogic.PokerPlayerInfo;
import newPokerLogic.PokerTableInfo;
import newPokerLogic.TypePokerGameAction;
import newPokerLogic.TypePokerGameRound;
import newPokerLogicTools.PokerGameAdapter;
import newPokerLogicTools.PokerGameObserver;
import pokerLogic.TypePlayerAction;
import pokerLogic.TypePokerRound;
import protocolGame.GameAskActionCommand;
import protocolGame.GameBetTurnEndedCommand;
import protocolGame.GameBoardChangedCommand;
import protocolGame.GameEndedCommand;
import protocolGame.GameHoleCardsChangedCommand;
import protocolGame.GamePlayerJoinedCommand;
import protocolGame.GamePlayerLeftCommand;
import protocolGame.GamePlayerMoneyChangedCommand;
import protocolGame.GamePlayerTurnBeganCommand;
import protocolGame.GamePlayerTurnEndedCommand;
import protocolGame.GamePlayerWonPotCommand;
import protocolGame.GameSendActionCommand;
import protocolGame.GameStartedCommand;
import protocolGame.GameTableClosedCommand;
import protocolGame.GameTableInfoCommand;
import protocolGameTools.GameServerSideAdapter;
import protocolGameTools.GameServerSideObserver;
import protocolTools.IPokerCommand;

public class TcpPokerClient implements Runnable
{
    // TCP Things
    public static final int PING_INTERVAL = 10000;
    private final Socket m_socket;
    private final PrintWriter m_output;
    private final BufferedReader m_input;
    private boolean m_isConnected;
    // TODO: private long m_lastPing;
    
    // POKER Things
    private final PokerPlayerInfo m_player;
    private final PokerGame m_game;
    private final GameServerSideObserver m_commandObserver = new GameServerSideObserver();
    private PokerGameObserver m_pokerObserver;
    
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
    public TcpPokerClient(PokerGame game, String name, int money, Socket socket) throws IOException
    {
        m_game = game;
        m_player = new PokerPlayerInfo(name, money);
        m_socket = socket;
        m_output = new PrintWriter(m_socket.getOutputStream(), true);
        m_input = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
        m_isConnected = false;
        initializeCommandObserver();
    }
    
    protected String receive() throws IOException
    {
        final String line = m_input.readLine();
        m_commandObserver.receiveSomething(line);
        return line;
    }
    
    public boolean canStartGame()
    {
        return m_isConnected && m_player.canPlay();
    }
    
    /**
     * Send a message to the client
     * 
     * @param p_msg
     *            The message to send
     */
    protected void send(IPokerCommand command)
    {
        // System.out.println("Server SEND TO " + this.m_name + " [" + p_msg + "]");
        m_output.println(command.encodeCommand());
    }
    
    /**
     * Make the player connected
     */
    public void setIsConnected()
    {
        m_isConnected = true;
    }
    
    public PokerPlayerInfo getPlayer()
    {
        return m_player;
    }
    
    public boolean joinGame()
    {
        m_pokerObserver = m_game.getGameObserver();
        initializePokerObserver();
        return m_game.joinGame(m_player);
    }
    
    public void sitIn()
    {
        send(new GameTableInfoCommand(m_game.getPokerTable(), m_player));
        m_game.sitInGame(m_player);
    }
    
    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                receive();
            }
        }
        catch (final IOException e)
        {
            return;
        }
    }
    
    public void start()
    {
        new Thread(this).start();
    }
    
    private void initializePokerObserver()
    {
        m_pokerObserver.subscribe(new PokerGameAdapter()
        {
            @Override
            public void gameBettingRoundEnded(PokerTableInfo t, TypePokerGameRound r)
            {
                final Stack<PokerMoneyPot> pots = t.getPots();
                final ArrayList<Integer> amounts = new ArrayList<Integer>();
                for (final PokerMoneyPot pot : pots)
                {
                    amounts.add(pot.getAmount());
                }
                
                for (int i = pots.size(); i < t.getNbMaxSeats(); i++)
                {
                    amounts.add(0);
                }
                // TODO: eventuellement, GameBetTurnEndedCommand devrait utiliser directement TypePokerGameRound
                switch (r)
                {
                    case PREFLOP:
                        send(new GameBetTurnEndedCommand(amounts, TypePokerRound.PREFLOP));
                        break;
                    case FLOP:
                        send(new GameBetTurnEndedCommand(amounts, TypePokerRound.FLOP));
                        break;
                    case TURN:
                        send(new GameBetTurnEndedCommand(amounts, TypePokerRound.TURN));
                        break;
                    case RIVER:
                        send(new GameBetTurnEndedCommand(amounts, TypePokerRound.RIVER));
                        break;
                }
            }
            
            @Override
            public void playerHoleCardsChanged(PokerTableInfo t, PokerPlayerInfo p)
            {
                final GameCard[] holeCards = p.getCurrentHand(p.getCurrentTablePosition() == m_player.getCurrentTablePosition());
                send(new GameHoleCardsChangedCommand(p.getCurrentTablePosition(), holeCards[0].getId(), holeCards[1].getId()));
            }
            
            @Override
            public void gameEnded(PokerTableInfo t)
            {
                send(new GameEndedCommand());
            }
            
            @Override
            public void playerWonPot(PokerTableInfo t, PokerPlayerInfo p, PokerMoneyPot pot, int wonAmount)
            {
                send(new GamePlayerWonPotCommand(p.getCurrentTablePosition(), pot.getId(), wonAmount, p.getCurrentSafeMoneyAmount()));
            }
            
            @Override
            public void playerActionTaken(PokerTableInfo t, PokerPlayerInfo p, TypePokerGameAction reason, int playedAmount)
            {
                // TODO: eventuellement, GamePlayerTurnEndedCommand devrait utiliser directement TypePokerGameAction
                switch (reason)
                {
                    case FOLDED:
                        send(new GamePlayerTurnEndedCommand(p.getCurrentTablePosition(), p.getCurrentBetMoneyAmount(), p.getCurrentSafeMoneyAmount(), t.getTotalPotAmount(), TypePlayerAction.FOLD, playedAmount));
                        break;
                    case CALLED:
                        send(new GamePlayerTurnEndedCommand(p.getCurrentTablePosition(), p.getCurrentBetMoneyAmount(), p.getCurrentSafeMoneyAmount(), t.getTotalPotAmount(), TypePlayerAction.CALL, playedAmount));
                        break;
                    case RAISED:
                        send(new GamePlayerTurnEndedCommand(p.getCurrentTablePosition(), p.getCurrentBetMoneyAmount(), p.getCurrentSafeMoneyAmount(), t.getTotalPotAmount(), TypePlayerAction.RAISE, playedAmount));
                        break;
                    case SMALL_BLIND_POSTED:
                        send(new GamePlayerTurnEndedCommand(p.getCurrentTablePosition(), p.getCurrentBetMoneyAmount(), p.getCurrentSafeMoneyAmount(), t.getTotalPotAmount(), TypePlayerAction.SMALL_BLIND, playedAmount));
                        break;
                    case BIG_BLIND_POSTED:
                        send(new GamePlayerTurnEndedCommand(p.getCurrentTablePosition(), p.getCurrentBetMoneyAmount(), p.getCurrentSafeMoneyAmount(), t.getTotalPotAmount(), TypePlayerAction.BIG_BLIND, playedAmount));
                        break;
                }
            }
            
            @Override
            public void playerMoneyChanged(PokerTableInfo t, PokerPlayerInfo p)
            {
                send(new GamePlayerMoneyChangedCommand(p.getCurrentTablePosition(), p.getCurrentSafeMoneyAmount()));
            }
            
            @Override
            public void everythingEnded(PokerTableInfo t)
            {
                send(new GameTableClosedCommand());
            }
            
            @Override
            public void playerActionNeeded(PokerTableInfo t, PokerPlayerInfo p)
            {
                send(new GamePlayerTurnBeganCommand(p.getCurrentTablePosition()));
                if (p.getCurrentTablePosition() == m_player.getCurrentTablePosition())
                {
                    send(new GameAskActionCommand(t.getCurrentHigherBet() == p.getCurrentBetMoneyAmount(), t.getCurrentHigherBet() > p.getCurrentBetMoneyAmount(), t.getCurrentHigherBet() > p.getCurrentBetMoneyAmount(), t.getCurrentHigherBet(), true, t.getCurrentHigherBet(), p.getCurrentSafeMoneyAmount()));
                }
            }
            
            @Override
            public void gameBlindsNeeded(PokerTableInfo t)
            {
                send(new GameStartedCommand(t.getCurrentDealerNoSeat(), t.getCurrentSmallBlindNoSeat(), t.getCurrentBigBlindNoSeat()));
                // TODO: eventuellement le client devrait par lui meme répondre a cette question
                if (m_player.getCurrentTablePosition() == t.getCurrentSmallBlindNoSeat())
                {
                    m_game.playMoney(m_player, t.getSmallBlindAmount());
                }
                else if (m_player.getCurrentTablePosition() == t.getCurrentBigBlindNoSeat())
                {
                    m_game.playMoney(m_player, t.getBigBlindAmount());
                }
            }
            
            @Override
            public void gameBoardCardsChanged(PokerTableInfo t)
            {
                final GameCard[] cards = new GameCard[5];
                t.getCurrentBoardCards().toArray(cards);
                for (int i = 0; i < 5; ++i)
                {
                    if (cards[i] == null)
                    {
                        cards[i] = GameCard.NO_CARD;
                    }
                }
                send(new GameBoardChangedCommand(cards[0].getId(), cards[1].getId(), cards[2].getId(), cards[3].getId(), cards[4].getId()));
            }
            
            @Override
            public void playerJoined(PokerTableInfo t, PokerPlayerInfo p)
            {
                send(new GamePlayerJoinedCommand(p.getCurrentTablePosition(), p.getPlayerName(), p.getCurrentSafeMoneyAmount()));
            }
            
            @Override
            public void playerLeaved(PokerTableInfo t, PokerPlayerInfo p)
            {
                send(new GamePlayerLeftCommand(p.getCurrentTablePosition()));
            }
        });
    }
    
    private void initializeCommandObserver()
    {
        m_commandObserver.subscribe(new GameServerSideAdapter()
        {
            @Override
            public void commandReceived(String command)
            {
                System.out.println("<TcpPokerClient :" + m_player.getPlayerName() + "> RECV [" + command + "]");
            }
            
            @Override
            public void sendActionCommandReceived(GameSendActionCommand command)
            {
                // TODO: en theorie, ca devrait etre pluss cool ! :p
                switch (command.getAction().getType())
                {
                    case DISCONNECT:
                        m_isConnected = false;
                        m_game.leaveGame(m_player);
                        break;
                    case FOLD:
                        m_game.playMoney(m_player, -1);
                        break;
                    case CALL:
                        m_game.playMoney(m_player, command.getAction().getAmount() - m_player.getCurrentBetMoneyAmount());
                        break;
                    case CHECK:
                    case BIG_BLIND:
                    case SMALL_BLIND:
                    case RAISE:
                        m_game.playMoney(m_player, command.getAction().getAmount());
                }
            }
        });
    }
    // TODO: lots of things
    // @Override
    // protected PokerPlayerAction getActionFromUser(boolean p_canCheck, boolean p_canFold, boolean p_canCall, int p_callOf, boolean p_canRaise, int p_minimumRaise, int p_maximumRaise)
    // {
    // PokerPlayerAction action = null;
    // int nbTry = 0;
    // if (m_isConnected)
    // {
    // try
    // {
    // while ((action == null) && m_isConnected && (nbTry < 10))
    // {
    // ++nbTry;
    // send(new GameAskActionCommand(p_canCheck, p_canFold, p_canCall, p_callOf, p_canRaise, p_minimumRaise, p_maximumRaise));
    //                    
    // final String line = receive();
    // if (line != null)
    // {
    // final StringTokenizer message = new StringTokenizer(line, Constants.DELIMITER);
    // String token;
    // if (message.hasMoreTokens())
    // {
    // token = message.nextToken();
    // if (token.equals(GameSendActionCommand.COMMAND_NAME))
    // {
    // final GameSendActionCommand command = new GameSendActionCommand(message);
    // final TypePlayerAction actionType = command.getAction().getType();
    // switch (actionType)
    // {
    // case DISCONNECT:
    // m_isConnected = false;
    // m_sitOutNextHand = true;
    // break;
    // case CHECK:
    // if (p_canCheck)
    // {
    // action = new PokerPlayerAction(actionType, 0);
    // }
    // break;
    // case CALL:
    // if (p_canCall)
    // {
    // action = new PokerPlayerAction(actionType, p_callOf);
    // }
    // break;
    // case FOLD:
    // if (p_canFold)
    // {
    // action = new PokerPlayerAction(actionType, 0);
    // }
    // break;
    // case RAISE:
    // try
    // {
    // final int amount = command.getAction().getAmount();
    // if (p_canRaise && (amount >= p_minimumRaise) && (amount <= p_maximumRaise))
    // {
    // action = new PokerPlayerAction(actionType, amount);
    // }
    // }
    // catch (final NumberFormatException e)
    // {
    // action = null;
    // }
    // break;
    // }
    // }
    // }
    // }
    // else
    // {
    // m_isConnected = false;
    // m_sitOutNextHand = true;
    // }
    // }
    // }
    // catch (final IOException e)
    // {
    // m_isConnected = false;
    // m_sitOutNextHand = true;
    // }
    // }
    //        
    // if (action == null)
    // {
    // if (p_canCheck)
    // {
    // action = new PokerPlayerAction(TypePlayerAction.CHECK, 0);
    // }
    // else
    // {
    // action = new PokerPlayerAction(TypePlayerAction.FOLD, 0);
    // }
    //            
    // if (nbTry >= 10)
    // {
    // System.out.println("Player kicked out :" + m_name);
    // m_isConnected = false;
    // m_sitOutNextHand = true;
    // }
    // }
    // if (!m_isConnected && m_socket != null)
    // {
    // try
    // {
    // m_socket.close();
    // }
    // catch (final IOException e)
    // {
    // }
    // m_socket = null;
    // }
    //        
    // return action;
    // }
    
    /**
     * To test if the player is connected, we send a ping to the client and wait his repsonse.
     * We do not send a new ping until the PING_INTERVAL milliseconds have past.
     */
    // @Override
    // public boolean sitOutNextHand()
    // {
    // if (m_isConnected && !m_sitOutNextHand && ((m_lastPing + TempServerNetworkPokerPlayerInfo.PING_INTERVAL) <= System.currentTimeMillis()))
    // {
    // m_lastPing = System.currentTimeMillis();
    // try
    // {
    // send(new GamePINGCommand());
    //                
    // final String line = m_input.readLine();
    // if (line != null)
    // {
    // final StringTokenizer message = new StringTokenizer(line, Constants.DELIMITER);
    // if (!message.hasMoreTokens())
    // {
    // m_isConnected = false;
    // m_sitOutNextHand = true;
    // }
    // }
    // else
    // {
    // m_isConnected = false;
    // m_sitOutNextHand = true;
    // }
    // }
    // catch (final IOException e)
    // {
    // m_isConnected = false;
    // m_sitOutNextHand = true;
    // }
    // }
    //        
    // return m_sitOutNextHand;
    // }
    
}
