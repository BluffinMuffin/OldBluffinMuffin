package protocol;

import game.Card;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import poker.game.MoneyPot;
import poker.game.PlayerInfo;
import poker.game.PokerGame;
import poker.game.TypeAction;
import poker.game.TypeRound;
import poker.game.observer.PokerGameAdapter;
import protocol.commands.DisconnectCommand;
import protocol.commands.ICommand;
import protocol.commands.game.BetTurnEndedCommand;
import protocol.commands.game.BetTurnStartedCommand;
import protocol.commands.game.GameEndedCommand;
import protocol.commands.game.GameStartedCommand;
import protocol.commands.game.PlayerHoleCardsChangedCommand;
import protocol.commands.game.PlayerJoinedCommand;
import protocol.commands.game.PlayerLeftCommand;
import protocol.commands.game.PlayerMoneyChangedCommand;
import protocol.commands.game.PlayerPlayMoneyCommand;
import protocol.commands.game.PlayerTurnBeganCommand;
import protocol.commands.game.PlayerTurnEndedCommand;
import protocol.commands.game.PlayerWonPotCommand;
import protocol.commands.game.TableClosedCommand;
import protocol.commands.game.TableInfoCommand;
import protocol.observer.game.GameServerAdapter;
import protocol.observer.game.GameServerObserver;

public class GameTCPServer implements Runnable
{
    // TCP Things
    public static final int PING_INTERVAL = 10000;
    private Socket m_socket;
    private PrintWriter m_output;
    private BufferedReader m_input;
    private boolean m_isConnected;
    
    // POKER Things
    private final PlayerInfo m_player;
    private final PokerGame m_game;
    private final GameServerObserver m_commandObserver = new GameServerObserver();
    
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
    public GameTCPServer(PokerGame game, String name, int money, Socket socket) throws IOException
    {
        m_game = game;
        m_player = new PlayerInfo(name, money);
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
    protected void send(ICommand command)
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
    
    public PlayerInfo getPlayer()
    {
        return m_player;
    }
    
    public boolean joinGame()
    {
        initializePokerObserver();
        return m_game.joinGame(m_player);
    }
    
    public void sitIn()
    {
        send(new TableInfoCommand(m_game.getTable(), m_player));
        m_game.sitInGame(m_player);
    }
    
    public boolean isConnected()
    {
        return (m_socket != null) && m_socket.isConnected() && !m_socket.isClosed();
    }
    
    @Override
    public void run()
    {
        try
        {
            while (isConnected())
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
        m_game.attach(new PokerGameAdapter()
        {
            @Override
            public void gameBettingRoundEnded(TypeRound r)
            {
                final List<MoneyPot> pots = m_game.getTable().getPots();
                final ArrayList<Integer> amounts = new ArrayList<Integer>();
                for (final MoneyPot pot : pots)
                {
                    amounts.add(pot.getAmount());
                }
                
                for (int i = pots.size(); i < m_game.getTable().getNbMaxSeats(); i++)
                {
                    amounts.add(0);
                }
                send(new BetTurnEndedCommand(amounts, r));
            }
            
            @Override
            public void playerHoleCardsChanged(PlayerInfo p)
            {
                final Card[] holeCards = p.getCards(p.getNoSeat() == m_player.getNoSeat());
                send(new PlayerHoleCardsChangedCommand(p.getNoSeat(), holeCards[0].getId(), holeCards[1].getId(), p.isPlaying()));
            }
            
            @Override
            public void gameEnded()
            {
                send(new GameEndedCommand());
            }
            
            @Override
            public void playerWonPot(PlayerInfo p, MoneyPot pot, int wonAmount)
            {
                send(new PlayerWonPotCommand(p.getNoSeat(), pot.getId(), wonAmount, p.getMoneySafeAmnt()));
            }
            
            @Override
            public void playerActionTaken(PlayerInfo p, TypeAction reason, int playedAmount)
            {
                send(new PlayerTurnEndedCommand(p.getNoSeat(), p.getMoneyBetAmnt(), p.getMoneySafeAmnt(), m_game.getTable().getTotalPotAmnt(), reason, playedAmount, p.isPlaying()));
            }
            
            @Override
            public void playerMoneyChanged(PlayerInfo p)
            {
                send(new PlayerMoneyChangedCommand(p.getNoSeat(), p.getMoneySafeAmnt()));
            }
            
            @Override
            public void everythingEnded()
            {
                send(new TableClosedCommand());
                try
                {
                    m_output.close();
                    m_input.close();
                    m_socket.close();
                }
                catch (final IOException e)
                {
                    e.printStackTrace();
                }
                m_output = null;
                m_input = null;
                m_socket = null;
            }
            
            @Override
            public void playerActionNeeded(PlayerInfo p)
            {
                send(new PlayerTurnBeganCommand(p.getNoSeat()));
            }
            
            @Override
            public void gameBlindsNeeded()
            {
                send(new GameStartedCommand(m_game.getTable().getNoSeatDealer(), m_game.getTable().getNoSeatSmallBlind(), m_game.getTable().getNoSeatBigBlind()));
                // TODO: RICK: eventuellement le client devrait par lui meme repondre a cette question
                if (m_player.getNoSeat() == m_game.getTable().getNoSeatSmallBlind())
                {
                    m_game.playMoney(m_player, m_game.getTable().getSmallBlindAmnt());
                }
                else if (m_player.getNoSeat() == m_game.getTable().getNoSeatBigBlind())
                {
                    m_game.playMoney(m_player, m_game.getTable().getBigBlindAmnt());
                }
            }
            
            @Override
            public void gameBettingRoundStarted()
            {
                final Card[] cards = new Card[5];
                m_game.getTable().getCards().toArray(cards);
                for (int i = 0; i < 5; ++i)
                {
                    if (cards[i] == null)
                    {
                        cards[i] = Card.NO_CARD;
                    }
                }
                send(new BetTurnStartedCommand(cards[0].getId(), cards[1].getId(), cards[2].getId(), cards[3].getId(), cards[4].getId(), m_game.getRound()));
            }
            
            @Override
            public void playerJoined(PlayerInfo p)
            {
                send(new PlayerJoinedCommand(p.getNoSeat(), p.getName(), p.getMoneySafeAmnt()));
            }
            
            @Override
            public void playerLeaved(PlayerInfo p)
            {
                send(new PlayerLeftCommand(p.getNoSeat()));
            }
        });
    }
    
    private void initializeCommandObserver()
    {
        m_commandObserver.subscribe(new GameServerAdapter()
        {
            @Override
            public void disconnectCommandReceived(DisconnectCommand command)
            {
                m_isConnected = false;
                m_game.leaveGame(m_player);
                
                try
                {
                    m_output.close();
                    m_input.close();
                    m_socket.close();
                }
                catch (final IOException e)
                {
                    e.printStackTrace();
                }
            }
            
            @Override
            public void commandReceived(String command)
            {
                System.out.println("<TcpPokerClient :" + m_player.getName() + "> RECV [" + command + "]");
            }
            
            @Override
            public void playMoneyCommandReceived(PlayerPlayMoneyCommand command)
            {
                m_game.playMoney(m_player, command.getPlayed());
            }
        });
    }
}