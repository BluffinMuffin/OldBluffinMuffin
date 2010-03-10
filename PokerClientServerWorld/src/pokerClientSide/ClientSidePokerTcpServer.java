package pokerClientSide;

import gameLogic.GameCard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import newPokerLogic.IPokerGame;
import newPokerLogic.PokerMoneyPot;
import newPokerLogic.PokerPlayerInfo;
import newPokerLogic.PokerTableInfo;
import newPokerLogic.TypePokerGameAction;
import newPokerLogic.TypePokerGameRound;
import newPokerLogicTools.PokerGameObserver;
import pokerLogic.OldPokerPlayerAction;
import pokerLogic.OldTypePlayerAction;
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
import protocolGameTools.GameClientSideAdapter;
import protocolGameTools.GameClientSideObserver;
import protocolGameTools.SummarySeatInfo;
import protocolTools.IPokerCommand;

public class ClientSidePokerTcpServer implements IPokerGame
{
    private final PokerGameObserver m_gameObserver = new PokerGameObserver();
    private final PokerTableInfo m_pokerTable = new PokerTableInfo();
    private final GameClientSideObserver m_commandObserver = new GameClientSideObserver();
    private final int m_tablePosition;
    private final String m_playerName;
    
    // Communication with the server
    Socket m_socket = null;
    BufferedReader m_fromServer = null;
    PrintWriter m_toServer = null;
    
    public ClientSidePokerTcpServer(Socket socket, BufferedReader serverReader, int tablePosition, String name)
    {
        m_socket = socket;
        m_tablePosition = tablePosition;
        m_playerName = name;
        try
        {
            m_fromServer = serverReader;
            m_toServer = new PrintWriter(m_socket.getOutputStream(), true);
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public boolean isConnected()
    {
        return (m_socket != null) && m_socket.isConnected() && !m_socket.isClosed();
    }
    
    public void start()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                initializeCommandObserver();
                try
                {
                    while (isConnected())
                    {
                        receive();
                    }
                }
                catch (final IOException e)
                {
                    /* e.printStackTrace(); */
                }
                
                disconnect();
            }
        }.start();
    }
    
    public void disconnect()
    {
        try
        {
            if (isConnected())
            {
                m_socket.close();
                m_socket = null;
            }
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }
    
    protected void send(OldPokerPlayerAction p_action)
    {
        send(new GameSendActionCommand(p_action));
    }
    
    protected void sendMessage(String p_msg)
    {
        System.out.println(m_playerName + " SEND [" + p_msg + "]");
        m_toServer.println(p_msg);
    }
    
    protected void send(IPokerCommand p_msg)
    {
        sendMessage(p_msg.encodeCommand());
    }
    
    protected String receive() throws IOException
    {
        final String line = m_fromServer.readLine();
        System.out.println(m_playerName + " RECV something");
        m_commandObserver.receiveSomething(line);
        return line;
    }
    
    public PokerPlayerInfo getCurrentPlayer()
    {
        return m_pokerTable.getPlayer(m_tablePosition);
    }
    
    public GameClientSideObserver getCommandObserver()
    {
        return m_commandObserver;
    }
    
    @Override
    public PokerTableInfo getPokerTable()
    {
        return m_pokerTable;
    }
    
    @Override
    public PokerGameObserver getGameObserver()
    {
        return m_gameObserver;
    }
    
    @Override
    public boolean leaveGame(PokerPlayerInfo player)
    {
        send(new GameSendActionCommand(new OldPokerPlayerAction(OldTypePlayerAction.DISCONNECT)));
        return true;
    }
    
    @Override
    public boolean playMoney(PokerPlayerInfo player, int amount)
    {
        // TODO: en theorie, ca devrait etre pluss cool ! :p
        // La on patch le leack en envoyant un RAISE peu importe ce que ca devrait reelement etre
        send(new GameSendActionCommand(new OldPokerPlayerAction(OldTypePlayerAction.RAISE, amount)));
        return true;
    }
    
    private void initializeCommandObserver()
    {
        final GameClientSideAdapter adapter = new GameClientSideAdapter()
        {
            
            @Override
            public void commandReceived(String command)
            {
                System.out.println(m_playerName + " RECV -=" + command + "=-");
            }
            
            @Override
            public void betTurnEndedCommandReceived(GameBetTurnEndedCommand command)
            {
                final List<Integer> amounts = command.getPotsAmounts();
                m_pokerTable.getPots().clear();
                m_pokerTable.setTotalPotAmount(0);
                m_pokerTable.setCurrentHigherBet(0);
                for (int i = 0; i < amounts.size() && amounts.get(i) > 0; ++i)
                {
                    m_pokerTable.getPots().add(new PokerMoneyPot(i, amounts.get(i)));
                    m_pokerTable.incTotalPotAmount(amounts.get(i));
                }
                
                // TODO: eventuellement, GameBetTurnEndedCommand devrait utiliser directement TypePokerGameRound
                TypePokerGameRound round = TypePokerGameRound.PREFLOP;
                switch (command.getRound())
                {
                    case PREFLOP:
                        round = TypePokerGameRound.PREFLOP;
                        break;
                    case FLOP:
                        round = TypePokerGameRound.FLOP;
                        break;
                    case TURN:
                        round = TypePokerGameRound.TURN;
                        break;
                    case RIVER:
                        round = TypePokerGameRound.RIVER;
                        break;
                }
                
                for (final PokerPlayerInfo p : m_pokerTable.getPlayers())
                {
                    p.setCurrentBetMoneyAmount(0);
                    if (p.getCurrentSafeMoneyAmount() > 0)
                    {
                        p.setPlaying();
                    }
                    else
                    {
                        p.setFolded();
                    }
                }
                m_gameObserver.gameBettingRoundEnded(round);
            }
            
            @Override
            public void boardChangedCommandReceived(GameBoardChangedCommand command)
            {
                final GameCard[] cards = new GameCard[5];
                for (int i = 0; i < 5; ++i)
                {
                    cards[i] = GameCard.getInstance(command.getCardsId().get(i));
                }
                m_pokerTable.setBoardCards(cards[0], cards[1], cards[2], cards[3], cards[4]);
                // TODO: get rid of this !
                if (cards[0].getId() == GameCard.NO_CARD_ID)
                {
                    m_pokerTable.setCurrentGameRound(TypePokerGameRound.PREFLOP);
                }
                else if (cards[3].getId() == GameCard.NO_CARD_ID)
                {
                    m_pokerTable.setCurrentGameRound(TypePokerGameRound.FLOP);
                }
                else if (cards[4].getId() == GameCard.NO_CARD_ID)
                {
                    m_pokerTable.setCurrentGameRound(TypePokerGameRound.TURN);
                }
                else
                {
                    m_pokerTable.setCurrentGameRound(TypePokerGameRound.RIVER);
                }
                m_gameObserver.gameBettingRoundStarted();
            }
            
            @Override
            public void gameEndedCommandReceived(GameEndedCommand command)
            {
                m_gameObserver.gameEnded();
            }
            
            @Override
            public void gameStartedCommandReceived(GameStartedCommand command)
            {
                m_pokerTable.setCurrentDealerNoSeat(command.GetNoSeatD());
                m_pokerTable.setCurrentSmallBlindNoSeat(command.GetNoSeatSB());
                m_pokerTable.setCurrentBigBlindNoSeat(command.GetNoSeatBB());
                m_gameObserver.gameBlindsNeeded();
            }
            
            @Override
            public void holeCardsChangedCommandReceived(GameHoleCardsChangedCommand command)
            {
                final PokerPlayerInfo p = m_pokerTable.getPlayer(command.getPlayerPos());
                if (p != null)
                {
                    final List<Integer> ids = command.getCardsId();
                    p.setHand(GameCard.getInstance(ids.get(0)), GameCard.getInstance(ids.get(1)));
                    m_gameObserver.playerHoleCardsChanged(p);
                }
            }
            
            @Override
            public void playerJoinedCommandReceived(GamePlayerJoinedCommand command)
            {
                final PokerPlayerInfo p = new PokerPlayerInfo(command.getPlayerPos(), command.getPlayerName(), command.getPlayerMoney());
                if (p != null)
                {
                    m_pokerTable.forceJoinTable(p, command.getPlayerPos());
                    if (p.getCurrentSafeMoneyAmount() > 0)
                    {
                        p.setPlaying();
                    }
                    m_gameObserver.playerJoined(p);
                }
            }
            
            @Override
            public void playerLeftCommandReceived(GamePlayerLeftCommand command)
            {
                final PokerPlayerInfo p = m_pokerTable.getPlayer(command.getPlayerPos());
                if (p != null)
                {
                    m_pokerTable.leaveTable(p);
                    m_gameObserver.playerLeaved(p);
                }
            }
            
            @Override
            public void playerMoneyChangedCommandReceived(GamePlayerMoneyChangedCommand command)
            {
                final PokerPlayerInfo p = m_pokerTable.getPlayer(command.getPlayerPos());
                if (p != null)
                {
                    p.setCurrentSafeMoneyAmount(command.getPlayerMoney());
                    m_gameObserver.playerMoneyChanged(p);
                }
            }
            
            @Override
            public void playerTurnBeganCommandReceived(GamePlayerTurnBeganCommand command)
            {
                final PokerPlayerInfo p = m_pokerTable.getPlayer(command.getPlayerPos());
                if (p != null)
                {
                    m_pokerTable.setCurrentPlayerNoSeat(command.getPlayerPos());
                    m_gameObserver.playerActionNeeded(p);
                }
            }
            
            @Override
            public void playerTurnEndedCommandReceived(GamePlayerTurnEndedCommand command)
            {
                if (m_pokerTable.getCurrentHigherBet() < command.getPlayerBet())
                {
                    m_pokerTable.setCurrentHigherBet(command.getPlayerBet());
                }
                final PokerPlayerInfo p = m_pokerTable.getPlayer(command.getPlayerPos());
                if (p != null)
                {
                    final int a = command.getActionAmount();
                    TypePokerGameAction action = TypePokerGameAction.FOLDED;
                    p.setCurrentBetMoneyAmount(command.getPlayerBet());
                    p.setCurrentSafeMoneyAmount(command.getPlayerMoney());
                    
                    // TODO: eventuellement, GamePlayerTurnEndedCommand devrait utiliser directement TypePokerGameAction
                    switch (command.getActionType())
                    {
                        case FOLD:
                            action = TypePokerGameAction.FOLDED;
                            break;
                        case CALL:
                            action = TypePokerGameAction.CALLED;
                            m_pokerTable.incTotalPotAmount(a);
                            break;
                        case RAISE:
                            action = TypePokerGameAction.RAISED;
                            m_pokerTable.incTotalPotAmount(a);
                            break;
                        case SMALL_BLIND:
                            action = TypePokerGameAction.SMALL_BLIND_POSTED;
                            m_pokerTable.incTotalPotAmount(a);
                            break;
                        case BIG_BLIND:
                            action = TypePokerGameAction.BIG_BLIND_POSTED;
                            m_pokerTable.incTotalPotAmount(a);
                            break;
                    }
                    m_gameObserver.playerActionTaken(p, action, a);
                }
            }
            
            @Override
            public void playerWonPotCommandReceived(GamePlayerWonPotCommand command)
            {
                final PokerPlayerInfo p = m_pokerTable.getPlayer(command.getPlayerPos());
                if (p != null)
                {
                    p.setCurrentSafeMoneyAmount(command.getPlayerMoney());
                    m_gameObserver.playerWonPot(p, m_pokerTable.getPots().get(command.getPotID()), command.getShared());
                }
            }
            
            @Override
            public void tableClosedCommandReceived(GameTableClosedCommand command)
            {
                m_gameObserver.everythingEnded();
            }
            
            @Override
            public void tableInfoCommandReceived(GameTableInfoCommand command)
            {
                m_pokerTable.setTotalPotAmount(command.getTotalPotAmount());
                
                final List<Integer> amounts = command.getPotsAmount();
                m_pokerTable.getPots().clear();
                for (int i = 0; i < amounts.size() && amounts.get(i) > 0; ++i)
                {
                    m_pokerTable.getPots().add(new PokerMoneyPot(i, amounts.get(i)));
                }
                
                final GameCard[] cards = new GameCard[5];
                for (int i = 0; i < 5; ++i)
                {
                    cards[i] = GameCard.getInstance(command.getBoardCardIDs().get(i));
                }
                m_pokerTable.setBoardCards(cards[0], cards[1], cards[2], cards[3], cards[4]);
                
                for (final PokerPlayerInfo p : m_pokerTable.getPlayers())
                {
                    m_pokerTable.leaveTable(p);
                }
                for (int i = 0; i < command.getNbPlayers(); ++i)
                {
                    final SummarySeatInfo seat = command.getSeats().get(i);
                    if (seat.m_isEmpty)
                    {
                        continue;
                    }
                    final int noSeat = seat.m_noSeat;
                    final PokerPlayerInfo p = new PokerPlayerInfo(noSeat, seat.m_playerName, seat.m_money);
                    m_pokerTable.forceJoinTable(p, noSeat);
                    final List<Integer> ids = seat.m_holeCardIDs;
                    p.setHand(GameCard.getInstance(ids.get(0)), GameCard.getInstance(ids.get(1)));
                    if (p.getCurrentSafeMoneyAmount() > 0)
                    {
                        p.setPlaying();
                    }
                    
                    if (seat.m_isDealer)
                    {
                        m_pokerTable.setCurrentDealerNoSeat(noSeat);
                    }
                    if (seat.m_isSmallBlind)
                    {
                        m_pokerTable.setCurrentSmallBlindNoSeat(noSeat);
                    }
                    if (seat.m_isBigBlind)
                    {
                        m_pokerTable.setCurrentBigBlindNoSeat(noSeat);
                    }
                    if (seat.m_isCurrentPlayer)
                    {
                        m_pokerTable.setCurrentPlayerNoSeat(noSeat);
                    }
                    
                    p.setCurrentBetMoneyAmount(seat.m_bet);
                    
                    m_gameObserver.playerHoleCardsChanged(p);
                    
                }
                m_gameObserver.gameGenerallyUpdated();
            }
        };
        m_commandObserver.subscribe(adapter);
    }
    
    public int getNoPort()
    {
        return m_socket.getPort();
    }
}
