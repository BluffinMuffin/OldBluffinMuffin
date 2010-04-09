package protocol;

import game.Card;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import poker.game.IPokerGame;
import poker.game.MoneyPot;
import poker.game.PlayerInfo;
import poker.game.TableInfo;
import poker.game.observer.IPokerGameListener;
import poker.game.observer.PokerGameObserver;
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
import protocol.observer.game.GameClientAdapter;
import protocol.observer.game.GameClientObserver;

public class GameTCPClient implements IPokerGame {
    private final PokerGameObserver m_gameObserver = new PokerGameObserver();
    private final TableInfo m_pokerTable = new TableInfo();
    private final GameClientObserver m_commandObserver = new GameClientObserver();
    private final int m_tablePosition;
    private final String m_playerName;

    // Communication with the server
    Socket m_socket = null;
    BufferedReader m_fromServer = null;
    PrintWriter m_toServer = null;

    public GameTCPClient(Socket socket, BufferedReader serverReader, int tablePosition, String name) {
        m_socket = socket;
        m_tablePosition = tablePosition;
        m_playerName = name;
        try {
            m_fromServer = serverReader;
            m_toServer = new PrintWriter(m_socket.getOutputStream(), true);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return (m_socket != null) && m_socket.isConnected() && !m_socket.isClosed();
    }

    public void start() {
        new Thread() {
            @Override
            public void run() {
                initializeCommandObserver();
                try {
                    while (isConnected()) {
                        if (receive() == null) {
                            break;
                        }
                    }
                } catch (final IOException e) {
                    /* e.printStackTrace(); */
                }

                disconnect();
            }
        }.start();
    }

    public void disconnect() {
        try {
            if (isConnected()) {
                // Alors on disconnect
                send(new DisconnectCommand());
                m_fromServer.close();
                m_toServer.close();
                m_socket.close();
                m_socket = null;
                m_fromServer = null;
                m_toServer = null;
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    protected void sendMessage(String p_msg) {
        System.out.println(m_playerName + " SEND [" + p_msg + "]");
        m_toServer.println(p_msg);
    }

    protected void send(ICommand p_msg) {
        sendMessage(p_msg.encodeCommand());
    }

    protected String receive() throws IOException {
        final String line = m_fromServer.readLine();
        m_commandObserver.receiveSomething(line);
        return line;
    }

    public int getNoSeat() {
        return m_tablePosition;
    }

    public GameClientObserver getCommandObserver() {
        return m_commandObserver;
    }

    @Override
    public TableInfo getTable() {
        return m_pokerTable;
    }

    @Override
    public boolean leaveGame(PlayerInfo player) {
        send(new DisconnectCommand());
        return true;
    }

    @Override
    public boolean playMoney(PlayerInfo player, int amount) {
        send(new PlayerPlayMoneyCommand(amount));
        return true;
    }

    private void initializeCommandObserver() {
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
                    m_pokerTable.getPots().add(new MoneyPot(i, amounts.get(i)));
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
                if (p != null)
                {
                    m_pokerTable.setNoSeatCurrPlayer(command.getPlayerPos());
                    m_gameObserver.playerActionNeeded(p);
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
                    m_gameObserver.playerWonPot(p, new MoneyPot(command.getPotID(), command.getShared()), command.getShared());
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
                    m_pokerTable.getPots().add(new MoneyPot(i, amounts.get(i)));
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

    public int getNoPort() {
        if (m_socket == null) {
            return -1;
        }
        return m_socket.getPort();
    }

    @Override
    public void attach(IPokerGameListener listener) {
        m_gameObserver.subscribe(listener);
    }

    @Override
    public void detach(IPokerGameListener listener) {
        m_gameObserver.unsubscribe(listener);
    }

    @Override
    public String encode() {
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
