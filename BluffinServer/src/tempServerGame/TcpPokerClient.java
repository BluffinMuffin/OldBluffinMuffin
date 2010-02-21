package tempServerGame;

import gameLogic.GameCard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import newPokerLogic.PokerGame;
import newPokerLogic.PokerPlayerInfo;
import newPokerLogic.PokerTableInfo;
import newPokerLogicTools.PokerGameAdapter;
import newPokerLogicTools.PokerGameObserver;
import pokerLogic.TypePlayerAction;
import protocolGame.GameBoardChangedCommand;
import protocolGame.GamePlayerJoinedCommand;
import protocolGame.GamePlayerLeftCommand;
import protocolGame.GamePlayerTurnEndedCommand;
import protocolGame.GameSendActionCommand;
import protocolGame.GameTableClosedCommand;
import protocolGameTools.GameServerSideAdapter;
import protocolGameTools.GameServerSideObserver;
import protocolTools.IPokerCommand;

public class TcpPokerClient
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
    
    public void setPokerObserver(PokerGameObserver observer)
    {
        m_pokerObserver = observer;
        initializePokerObserver();
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
    
    private void initializePokerObserver()
    {
        m_pokerObserver.subscribe(new PokerGameAdapter()
        {
            
            @Override
            public void end()
            {
                send(new GameTableClosedCommand());
            }
            
            @Override
            public void actionNeeded(PokerPlayerInfo p, int amountToCall, int maxRaise)
            {
            }
            
            @Override
            public void bigBlindPosted(PokerPlayerInfo p, int bbAmount)
            {
                // TODO: le 0 est total pot !!
                send(new GamePlayerTurnEndedCommand(p.getCurrentTablePosition(), p.getCurrentBetMoneyAmount(), p.getCurrentSafeMoneyAmount(), 0, TypePlayerAction.BIG_BLIND, bbAmount));
            }
            
            @Override
            public void blindsNeeded(PokerPlayerInfo sb, PokerPlayerInfo bb, int sbValue, int bbValue)
            {
            }
            
            @Override
            public void boardCardsChanged(PokerTableInfo t)
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
                send(new GameBoardChangedCommand(cards[0].getId(), cards[1].getId(), cards[2].getId(), cards[3].getId(), cards[3].getId()));
            }
            
            @Override
            public void playerCalled(PokerPlayerInfo p, int playedAmount, int totalCallValue)
            {
                // TODO: le 0 est total pot !!
                send(new GamePlayerTurnEndedCommand(p.getCurrentTablePosition(), p.getCurrentBetMoneyAmount(), p.getCurrentSafeMoneyAmount(), 0, TypePlayerAction.CALL, totalCallValue));
            }
            
            @Override
            public void playerFolded(PokerPlayerInfo p)
            {
                // TODO: le 0 est total pot !!
                send(new GamePlayerTurnEndedCommand(p.getCurrentTablePosition(), p.getCurrentBetMoneyAmount(), p.getCurrentSafeMoneyAmount(), 0, TypePlayerAction.FOLD, -1));
            }
            
            @Override
            public void playerJoined(PokerPlayerInfo p)
            {
                send(new GamePlayerJoinedCommand(p.getCurrentTablePosition(), p.getPlayerName(), p.getCurrentSafeMoneyAmount()));
            }
            
            @Override
            public void playerLeaved(PokerPlayerInfo p)
            {
                send(new GamePlayerLeftCommand(p.getCurrentTablePosition()));
            }
            
            @Override
            public void playerRaised(PokerPlayerInfo p, int playedAmount, int totalRaiseValue)
            {
                // TODO: le 0 est total pot !!
                send(new GamePlayerTurnEndedCommand(p.getCurrentTablePosition(), p.getCurrentBetMoneyAmount(), p.getCurrentSafeMoneyAmount(), 0, TypePlayerAction.RAISE, totalRaiseValue));
            }
            
            @Override
            public void smallBlindPosted(PokerPlayerInfo p, int sbAmount)
            {
                // TODO: le 0 est total pot !!
                send(new GamePlayerTurnEndedCommand(p.getCurrentTablePosition(), p.getCurrentBetMoneyAmount(), p.getCurrentSafeMoneyAmount(), 0, TypePlayerAction.BIG_BLIND, sbAmount));
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
                System.out.println("<" + m_game.getPokerTable().getTableName() + "> RECV from " + m_player.getPlayerName() + " [" + command + "]");
            }
            
            @Override
            public void sendActionCommandReceived(GameSendActionCommand command)
            {
                if (command.getAction().getType() == TypePlayerAction.DISCONNECT)
                {
                    m_isConnected = false;
                }
            }
        });
    }
}
