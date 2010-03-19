package protocol.game;

import game.GameCard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import poker.PokerGame;
import poker.PokerMoneyPot;
import poker.PokerPlayerInfo;
import poker.TypePokerGameAction;
import poker.TypePokerGameRound;
import poker.observer.PokerGameAdapter;
import poker.observer.PokerGameObserver;
import protocol.IPokerCommand;
import protocol.game.commands.GameBetTurnEndedCommand;
import protocol.game.commands.GameBetTurnStartedCommand;
import protocol.game.commands.GameEndedCommand;
import protocol.game.commands.GameHoleCardsChangedCommand;
import protocol.game.commands.GamePlayMoneyCommand;
import protocol.game.commands.GamePlayerJoinedCommand;
import protocol.game.commands.GamePlayerLeftCommand;
import protocol.game.commands.GamePlayerMoneyChangedCommand;
import protocol.game.commands.GamePlayerTurnBeganCommand;
import protocol.game.commands.GamePlayerTurnEndedCommand;
import protocol.game.commands.GamePlayerWonPotCommand;
import protocol.game.commands.GameStartedCommand;
import protocol.game.commands.GameTableClosedCommand;
import protocol.game.commands.GameTableInfoCommand;

public class ServerSidePokerTcpClient implements Runnable
{
    // TCP Things
    public static final int PING_INTERVAL = 10000;
    private final Socket m_socket;
    private final PrintWriter m_output;
    private final BufferedReader m_input;
    private boolean m_isConnected;
    
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
    public ServerSidePokerTcpClient(PokerGame game, String name, int money, Socket socket) throws IOException
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
            public void gameBettingRoundEnded(TypePokerGameRound r)
            {
                final List<PokerMoneyPot> pots = m_game.getPokerTable().getPots();
                final ArrayList<Integer> amounts = new ArrayList<Integer>();
                for (final PokerMoneyPot pot : pots)
                {
                    amounts.add(pot.getAmount());
                }
                
                for (int i = pots.size(); i < m_game.getPokerTable().getNbMaxSeats(); i++)
                {
                    amounts.add(0);
                }
                send(new GameBetTurnEndedCommand(amounts, r));
            }
            
            @Override
            public void playerHoleCardsChanged(PokerPlayerInfo p)
            {
                final GameCard[] holeCards = p.getCurrentHand(p.getCurrentTablePosition() == m_player.getCurrentTablePosition());
                send(new GameHoleCardsChangedCommand(p.getCurrentTablePosition(), holeCards[0].getId(), holeCards[1].getId(), p.isPlaying()));
            }
            
            @Override
            public void gameEnded()
            {
                send(new GameEndedCommand());
            }
            
            @Override
            public void playerWonPot(PokerPlayerInfo p, PokerMoneyPot pot, int wonAmount)
            {
                send(new GamePlayerWonPotCommand(p.getCurrentTablePosition(), pot.getId(), wonAmount, p.getCurrentSafeMoneyAmount()));
            }
            
            @Override
            public void playerActionTaken(PokerPlayerInfo p, TypePokerGameAction reason, int playedAmount)
            {
                send(new GamePlayerTurnEndedCommand(p.getCurrentTablePosition(), p.getCurrentBetMoneyAmount(), p.getCurrentSafeMoneyAmount(), m_game.getPokerTable().getTotalPotAmount(), reason, playedAmount, p.isPlaying()));
            }
            
            @Override
            public void playerMoneyChanged(PokerPlayerInfo p)
            {
                send(new GamePlayerMoneyChangedCommand(p.getCurrentTablePosition(), p.getCurrentSafeMoneyAmount()));
            }
            
            @Override
            public void everythingEnded()
            {
                send(new GameTableClosedCommand());
            }
            
            @Override
            public void playerActionNeeded(PokerPlayerInfo p)
            {
                send(new GamePlayerTurnBeganCommand(p.getCurrentTablePosition()));
            }
            
            @Override
            public void gameBlindsNeeded()
            {
                send(new GameStartedCommand(m_game.getPokerTable().getCurrentDealerNoSeat(), m_game.getPokerTable().getCurrentSmallBlindNoSeat(), m_game.getPokerTable().getCurrentBigBlindNoSeat()));
                // TODO: RICK: eventuellement le client devrait par lui meme r�pondre a cette question
                if (m_player.getCurrentTablePosition() == m_game.getPokerTable().getCurrentSmallBlindNoSeat())
                {
                    m_game.playMoney(m_player, m_game.getPokerTable().getSmallBlindAmount());
                }
                else if (m_player.getCurrentTablePosition() == m_game.getPokerTable().getCurrentBigBlindNoSeat())
                {
                    m_game.playMoney(m_player, m_game.getPokerTable().getBigBlindAmount());
                }
            }
            
            @Override
            public void gameBettingRoundStarted()
            {
                final GameCard[] cards = new GameCard[5];
                m_game.getPokerTable().getCurrentBoardCards().toArray(cards);
                for (int i = 0; i < 5; ++i)
                {
                    if (cards[i] == null)
                    {
                        cards[i] = GameCard.NO_CARD;
                    }
                }
                send(new GameBetTurnStartedCommand(cards[0].getId(), cards[1].getId(), cards[2].getId(), cards[3].getId(), cards[4].getId(), m_game.getCurrentGameRound()));
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
        });
    }
    
    private void initializeCommandObserver()
    {
        m_commandObserver.subscribe(new GameServerSideAdapter()
        {
            @Override
            public void disconnectCommandReceived(GamePlayMoneyCommand command)
            {
                m_isConnected = false;
                m_game.leaveGame(m_player);
            }
            
            @Override
            public void commandReceived(String command)
            {
                System.out.println("<TcpPokerClient :" + m_player.getPlayerName() + "> RECV [" + command + "]");
            }
            
            @Override
            public void playMoneyCommandReceived(GamePlayMoneyCommand command)
            {
                m_game.playMoney(m_player, command.getPlayed());
            }
        });
    }
}
