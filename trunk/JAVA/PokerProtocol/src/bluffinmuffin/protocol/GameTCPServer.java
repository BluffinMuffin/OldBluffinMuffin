package bluffinmuffin.protocol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import bluffinmuffin.game.entities.Card;
import bluffinmuffin.poker.PokerGame;
import bluffinmuffin.poker.PokerGame.TypeState;
import bluffinmuffin.poker.entities.PlayerInfo;
import bluffinmuffin.poker.entities.PotInfo;
import bluffinmuffin.poker.entities.TableInfo;
import bluffinmuffin.poker.entities.type.GameRoundType;
import bluffinmuffin.poker.entities.type.PlayerActionType;
import bluffinmuffin.poker.observer.PokerGameAdapter;
import bluffinmuffin.protocol.commands2.AbstractCommand;
import bluffinmuffin.protocol.commands2.DisconnectCommand;
import bluffinmuffin.protocol.commands2.game.BetTurnEndedCommand;
import bluffinmuffin.protocol.commands2.game.BetTurnStartedCommand;
import bluffinmuffin.protocol.commands2.game.GameEndedCommand;
import bluffinmuffin.protocol.commands2.game.GameStartedCommand;
import bluffinmuffin.protocol.commands2.game.PlayerHoleCardsChangedCommand;
import bluffinmuffin.protocol.commands2.game.PlayerJoinedCommand;
import bluffinmuffin.protocol.commands2.game.PlayerLeftCommand;
import bluffinmuffin.protocol.commands2.game.PlayerMoneyChangedCommand;
import bluffinmuffin.protocol.commands2.game.PlayerPlayMoneyCommand;
import bluffinmuffin.protocol.commands2.game.PlayerTurnBeganCommand;
import bluffinmuffin.protocol.commands2.game.PlayerTurnEndedCommand;
import bluffinmuffin.protocol.commands2.game.PlayerWonPotCommand;
import bluffinmuffin.protocol.commands2.game.TableClosedCommand;
import bluffinmuffin.protocol.commands2.game.TableInfoCommand;
import bluffinmuffin.protocol.observer.game.GameServerAdapter;
import bluffinmuffin.protocol.observer.game.GameServerObserver;

public class GameTCPServer implements Runnable
{
    // Communication Things
    private boolean m_isConnected = true;
    private final BlockingQueue<String> m_incoming = new LinkedBlockingQueue<String>();
    private final BlockingQueue<String> m_outcoming = new LinkedBlockingQueue<String>();
    
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
    public GameTCPServer(PokerGame game, String name, int money)
    {
        m_game = game;
        m_player = new PlayerInfo(name, money);
        initializeCommandObserver();
    }
    
    public String getOutcoming() throws InterruptedException
    {
        return m_outcoming.take();
    }
    
    public void incoming(String s) throws InterruptedException
    {
        m_incoming.put(s);
    }
    
    protected String receive() throws InterruptedException
    {
        final String line = m_incoming.take();
        m_commandObserver.receiveSomething(line);
        return line;
    }
    
    public boolean canStartGame()
    {
        return m_player.canPlay();
    }
    
    /**
     * Send a message to the client
     * 
     * @param p_msg
     *            The message to send
     */
    protected void send(AbstractCommand command)
    {
        // System.out.println("Server SEND TO " + this.m_name + " [" + p_msg + "]");
        try
        {
            m_outcoming.put(command.encode());
        }
        catch (final InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    public PlayerInfo getPlayer()
    {
        return m_player;
    }
    
    public PokerGame getGame()
    {
        return m_game;
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
        return m_isConnected;
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
        catch (final InterruptedException e)
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
            public void gameBettingRoundEnded(GameRoundType r)
            {
                final List<PotInfo> pots = m_game.getTable().getPots();
                final ArrayList<Integer> amounts = new ArrayList<Integer>();
                for (final PotInfo pot : pots)
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
            public void playerWonPot(PlayerInfo p, PotInfo pot, int wonAmount)
            {
                send(new PlayerWonPotCommand(p.getNoSeat(), pot.getId(), wonAmount, p.getMoneySafeAmnt()));
            }
            
            @Override
            public void playerActionTaken(PlayerInfo p, PlayerActionType reason, int playedAmount)
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
                m_isConnected = false;
            }
            
            @Override
            public void playerActionNeeded(PlayerInfo p, PlayerInfo last)
            {
                send(new PlayerTurnBeganCommand(p.getNoSeat(), last.getNoSeat()));
            }
            
            @Override
            public void gameBlindsNeeded()
            {
                send(new GameStartedCommand(m_game.getTable().getNoSeatDealer(), m_game.getTable().getNoSeatSmallBlind(), m_game.getTable().getNoSeatBigBlind()));
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
                m_player.setZombie();
                final TableInfo t = m_game.getTable();
                if (m_game.getState() == TypeState.PLAYERS_WAITING)
                {
                    m_game.leaveGame(m_player);
                }
                else if (t.getNoSeatCurrPlayer() == m_player.getNoSeat())
                {
                    if (t.canCheck(m_player))
                    {
                        m_game.playMoney(m_player, 0);
                    }
                    else
                    {
                        m_game.playMoney(m_player, -1);
                    }
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
