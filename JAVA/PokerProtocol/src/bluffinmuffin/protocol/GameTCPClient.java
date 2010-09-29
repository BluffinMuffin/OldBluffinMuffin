package bluffinmuffin.protocol;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import bluffinmuffin.game.entities.Card;
import bluffinmuffin.poker.IPokerGame;
import bluffinmuffin.poker.entities.PlayerInfo;
import bluffinmuffin.poker.entities.PotInfo;
import bluffinmuffin.poker.entities.TableInfo;
import bluffinmuffin.poker.entities.type.GameRoundType;
import bluffinmuffin.poker.entities.type.PlayerActionType;
import bluffinmuffin.poker.observer.IPokerGameListener;
import bluffinmuffin.poker.observer.PokerGameObserver;
import bluffinmuffin.protocol.commands.AbstractCommand;
import bluffinmuffin.protocol.commands.DisconnectCommand;
import bluffinmuffin.protocol.commands.game.BetTurnEndedCommand;
import bluffinmuffin.protocol.commands.game.BetTurnStartedCommand;
import bluffinmuffin.protocol.commands.game.GameEndedCommand;
import bluffinmuffin.protocol.commands.game.GameStartedCommand;
import bluffinmuffin.protocol.commands.game.PlayerHoleCardsChangedCommand;
import bluffinmuffin.protocol.commands.game.PlayerJoinedCommand;
import bluffinmuffin.protocol.commands.game.PlayerLeftCommand;
import bluffinmuffin.protocol.commands.game.PlayerMoneyChangedCommand;
import bluffinmuffin.protocol.commands.game.PlayerPlayMoneyCommand;
import bluffinmuffin.protocol.commands.game.PlayerTurnBeganCommand;
import bluffinmuffin.protocol.commands.game.PlayerTurnEndedCommand;
import bluffinmuffin.protocol.commands.game.PlayerWonPotCommand;
import bluffinmuffin.protocol.commands.game.TableClosedCommand;
import bluffinmuffin.protocol.commands.game.TableInfoCommand;
import bluffinmuffin.protocol.commands.lobby.GameCommand;
import bluffinmuffin.protocol.observer.game.GameClientAdapter;
import bluffinmuffin.protocol.observer.game.GameClientObserver;

public class GameTCPClient implements IPokerGame
{
    private final PokerGameObserver m_gameObserver = new PokerGameObserver();
    private final TableInfo m_pokerTable = new TableInfo();
    private final GameClientObserver m_commandObserver = new GameClientObserver();
    private final int m_tablePosition;
    private final String m_playerName;
    
    // Communication with the server
    private boolean m_isConnected = true;
    private final BlockingQueue<String> m_incoming = new LinkedBlockingQueue<String>();
    private final int m_tableID;
    private final LobbyTCPClient m_comm;
    
    public GameTCPClient(LobbyTCPClient comm, int tableID, int tablePosition, String name)
    {
        m_comm = comm;
        m_tableID = tableID;
        m_tablePosition = tablePosition;
        m_playerName = name;
    }
    
    public boolean isConnected()
    {
        return m_isConnected;
    }
    
    public void start()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                initializeCommandObserver();
                while (isConnected())
                {
                    try
                    {
                        if (receive() == null)
                        {
                            break;
                        }
                    }
                    catch (final InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                
                disconnect();
            }
        }.start();
    }
    
    public void disconnect()
    {
        m_isConnected = false;
    }
    
    protected void sendMessage(String p_msg)
    {
        m_comm.send(new GameCommand(m_tableID, p_msg));
    }
    
    protected void send(AbstractCommand p_msg)
    {
        sendMessage(p_msg.encode());
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
    
    public int getNoSeat()
    {
        return m_tablePosition;
    }
    
    public GameClientObserver getCommandObserver()
    {
        return m_commandObserver;
    }
    
    @Override
    public TableInfo getTable()
    {
        return m_pokerTable;
    }
    
    @Override
    public boolean leaveGame(PlayerInfo player)
    {
        send(new DisconnectCommand());
        return true;
    }
    
    @Override
    public boolean playMoney(PlayerInfo player, int amount)
    {
        send(new PlayerPlayMoneyCommand(amount));
        return true;
    }
    
    private void initializeCommandObserver()
    {
        final GameClientAdapter adapter = new GameClientAdapter()
        {
            
            @Override
            public void commandReceived(String command)
            {
                System.out.println(m_playerName + " RECV -=" + command + "=-");
            }
            
            @Override
            public void betTurnEndedCommandReceived(BetTurnEndedCommand command)
            {
                final List<Integer> amounts = command.getPotsAmounts();
                m_pokerTable.getPots().clear();
                m_pokerTable.setTotalPotAmnt(0);
                m_pokerTable.setHigherBet(0);
                for (int i = 0; i < amounts.size() && amounts.get(i) > 0; ++i)
                {
                    m_pokerTable.getPots().add(new PotInfo(i, amounts.get(i)));
                    m_pokerTable.incTotalPotAmnt(amounts.get(i));
                }
                for (final PlayerInfo p : m_pokerTable.getPlayers())
                {
                    p.setMoneyBetAmnt(0);
                }
                m_gameObserver.gameBettingRoundEnded(command.getRound());
            }
            
            @Override
            public void betTurnStartedCommandReceived(BetTurnStartedCommand command)
            {
                final Card[] cards = new Card[5];
                for (int i = 0; i < 5; ++i)
                {
                    cards[i] = Card.getInstance(command.getCardsId().get(i));
                }
                m_pokerTable.setCards(cards[0], cards[1], cards[2], cards[3], cards[4]);
                m_pokerTable.setRound(command.getRound());
                if (m_pokerTable.getRound() == GameRoundType.PREFLOP)
                {
                    m_pokerTable.setNoSeatLastRaise(m_pokerTable.nextPlayingPlayer(m_pokerTable.getNoSeatBigBlind()).getNoSeat());
                }
                else
                {
                    m_pokerTable.setNoSeatLastRaise(m_pokerTable.nextPlayingPlayer(m_pokerTable.getNoSeatDealer()).getNoSeat());
                }
                m_gameObserver.gameBettingRoundStarted();
            }
            
            @Override
            public void gameEndedCommandReceived(GameEndedCommand command)
            {
                m_pokerTable.setTotalPotAmnt(0);
                m_gameObserver.gameEnded();
            }
            
            @Override
            public void gameStartedCommandReceived(GameStartedCommand command)
            {
                m_pokerTable.setNoSeatDealer(command.GetNoSeatD());
                m_pokerTable.setNoSeatSmallBlind(command.GetNoSeatSB());
                m_pokerTable.setNoSeatBigBlind(command.GetNoSeatBB());
                
                // TODO: RICK: This is nice but, si le player passe pas par tcp (direct, hooking, etc) il saura pas quoi faire lors des blinds.
                if (m_pokerTable.getNoSeatSmallBlind() == m_tablePosition)
                {
                    send(new PlayerPlayMoneyCommand(m_pokerTable.getSmallBlindAmnt()));
                }
                if (m_pokerTable.getNoSeatBigBlind() == m_tablePosition)
                {
                    send(new PlayerPlayMoneyCommand(m_pokerTable.getBigBlindAmnt()));
                }
                m_gameObserver.gameBlindsNeeded();
            }
            
            @Override
            public void holeCardsChangedCommandReceived(PlayerHoleCardsChangedCommand command)
            {
                final PlayerInfo p = m_pokerTable.getPlayer(command.getPlayerPos());
                if (p != null)
                {
                    if (command.isPlaying())
                    {
                        p.setPlaying();
                    }
                    else
                    {
                        p.setNotPlaying();
                    }
                    final List<Integer> ids = command.getCardsId();
                    final Card gc0 = Card.getInstance(ids.get(0));
                    final Card gc1 = Card.getInstance(ids.get(1));
                    p.setCards(gc0, gc1);
                    m_gameObserver.playerHoleCardsChanged(p);
                }
            }
            
            @Override
            public void playerJoinedCommandReceived(PlayerJoinedCommand command)
            {
                final PlayerInfo p = new PlayerInfo(command.getPlayerPos(), command.getPlayerName(), command.getPlayerMoney());
                if (p != null)
                {
                    m_pokerTable.forceJoinTable(p, command.getPlayerPos());
                    m_gameObserver.playerJoined(p);
                }
            }
            
            @Override
            public void playerLeftCommandReceived(PlayerLeftCommand command)
            {
                final PlayerInfo p = m_pokerTable.getPlayer(command.getPlayerPos());
                if (p != null)
                {
                    m_pokerTable.leaveTable(p);
                    m_gameObserver.playerLeaved(p);
                }
            }
            
            @Override
            public void playerMoneyChangedCommandReceived(PlayerMoneyChangedCommand command)
            {
                final PlayerInfo p = m_pokerTable.getPlayer(command.getPlayerPos());
                if (p != null)
                {
                    p.setMoneySafeAmnt(command.getPlayerMoney());
                    m_gameObserver.playerMoneyChanged(p);
                }
            }
            
            @Override
            public void playerTurnBeganCommandReceived(PlayerTurnBeganCommand command)
            {
                final PlayerInfo p = m_pokerTable.getPlayer(command.getPlayerPos());
                final PlayerInfo last = m_pokerTable.getPlayer(command.getLastPlayerNoSeat());
                if (p != null)
                {
                    m_pokerTable.setNoSeatCurrPlayer(command.getPlayerPos());
                    m_gameObserver.playerActionNeeded(p, last);
                }
            }
            
            @Override
            public void playerTurnEndedCommandReceived(PlayerTurnEndedCommand command)
            {
                if (m_pokerTable.getHigherBet() < command.getPlayerBet())
                {
                    m_pokerTable.setHigherBet(command.getPlayerBet());
                }
                m_pokerTable.setTotalPotAmnt(command.getTotalPot());
                final PlayerInfo p = m_pokerTable.getPlayer(command.getPlayerPos());
                if (p != null)
                {
                    if (command.getActionType() == PlayerActionType.RAISED)
                    {
                        m_pokerTable.setNoSeatLastRaise(p.getNoSeat());
                    }
                    final int a = command.getActionAmount();
                    p.setMoneyBetAmnt(command.getPlayerBet());
                    p.setMoneySafeAmnt(command.getPlayerMoney());
                    if (command.isPlaying())
                    {
                        p.setPlaying();
                    }
                    else
                    {
                        p.setNotPlaying();
                    }
                    m_gameObserver.playerActionTaken(p, command.getActionType(), a);
                }
            }
            
            @Override
            public void playerWonPotCommandReceived(PlayerWonPotCommand command)
            {
                final PlayerInfo p = m_pokerTable.getPlayer(command.getPlayerPos());
                if (p != null)
                {
                    p.setMoneySafeAmnt(command.getPlayerMoney());
                    m_gameObserver.playerWonPot(p, new PotInfo(command.getPotID(), command.getShared()), command.getShared());
                }
            }
            
            @Override
            public void tableClosedCommandReceived(TableClosedCommand command)
            {
                m_gameObserver.everythingEnded();
            }
            
            @Override
            public void tableInfoCommandReceived(TableInfoCommand command)
            {
                m_pokerTable.setTotalPotAmnt(command.getTotalPotAmount());
                m_pokerTable.setBetLimit(command.getLimit());
                final List<Integer> amounts = command.getPotsAmount();
                m_pokerTable.getPots().clear();
                for (int i = 0; i < amounts.size() && amounts.get(i) > 0; ++i)
                {
                    m_pokerTable.getPots().add(new PotInfo(i, amounts.get(i)));
                }
                
                final Card[] cards = new Card[5];
                for (int i = 0; i < 5; ++i)
                {
                    cards[i] = Card.getInstance(command.getBoardCardIDs().get(i));
                }
                m_pokerTable.setCards(cards[0], cards[1], cards[2], cards[3], cards[4]);
                
                for (final PlayerInfo p : m_pokerTable.getPlayers())
                {
                    m_pokerTable.leaveTable(p);
                }
                for (int i = 0; i < command.getNbPlayers(); ++i)
                {
                    final TuplePlayerInfo seat = command.getSeats().get(i);
                    if (seat.m_isEmpty)
                    {
                        continue;
                    }
                    final int noSeat = seat.m_noSeat;
                    final PlayerInfo p = new PlayerInfo(noSeat, seat.m_playerName, seat.m_money);
                    m_pokerTable.forceJoinTable(p, noSeat);
                    final List<Integer> ids = seat.m_holeCardIDs;
                    p.setCards(Card.getInstance(ids.get(0)), Card.getInstance(ids.get(1)));
                    if (seat.m_isPlaying)
                    {
                        p.setPlaying();
                    }
                    
                    if (seat.m_isDealer)
                    {
                        m_pokerTable.setNoSeatDealer(noSeat);
                    }
                    if (seat.m_isSmallBlind)
                    {
                        m_pokerTable.setNoSeatSmallBlind(noSeat);
                    }
                    if (seat.m_isBigBlind)
                    {
                        m_pokerTable.setNoSeatBigBlind(noSeat);
                    }
                    if (seat.m_isCurrentPlayer)
                    {
                        m_pokerTable.setNoSeatCurrPlayer(noSeat);
                    }
                    
                    p.setMoneyBetAmnt(seat.m_bet);
                    
                    m_gameObserver.playerHoleCardsChanged(p);
                    
                }
                m_gameObserver.gameGenerallyUpdated();
            }
        };
        m_commandObserver.subscribe(adapter);
    }
    
    public int getNoPort()
    {
        return m_tableID;
    }
    
    @Override
    public void attach(IPokerGameListener listener)
    {
        m_gameObserver.subscribe(listener);
    }
    
    @Override
    public void detach(IPokerGameListener listener)
    {
        m_gameObserver.unsubscribe(listener);
    }
    
    @Override
    public String encode()
    {
        String encode = "";
        
        // Assume que les game sont en real money
        encode += "1";
        
        // Assume que c'est tlt du Texas Hold'em
        encode += 0;
        
        // Assume que c'Est tlt des Ring game
        encode += 0;
        
        // Assume que c'Est tlt du NoLimit
        encode += 0;
        
        encode += m_pokerTable.getRound().ordinal();
        
        return encode;
    }
}
