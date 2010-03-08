package pokerServerSide;

import gameLogic.GameCard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import newPokerLogic.PokerGame;
import newPokerLogic.PokerMoneyPot;
import newPokerLogic.PokerPlayerInfo;
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

public class ServerSidePokerTcpClient implements Runnable
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
            public void playerHoleCardsChanged(PokerPlayerInfo p)
            {
                final GameCard[] holeCards = p.getCurrentHand(p.getCurrentTablePosition() == m_player.getCurrentTablePosition());
                send(new GameHoleCardsChangedCommand(p.getCurrentTablePosition(), holeCards[0].getId(), holeCards[1].getId()));
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
                // TODO: eventuellement, GamePlayerTurnEndedCommand devrait utiliser directement TypePokerGameAction
                switch (reason)
                {
                    case FOLDED:
                        send(new GamePlayerTurnEndedCommand(p.getCurrentTablePosition(), p.getCurrentBetMoneyAmount(), p.getCurrentSafeMoneyAmount(), m_game.getPokerTable().getTotalPotAmount(), TypePlayerAction.FOLD, playedAmount));
                        break;
                    case CALLED:
                        send(new GamePlayerTurnEndedCommand(p.getCurrentTablePosition(), p.getCurrentBetMoneyAmount(), p.getCurrentSafeMoneyAmount(), m_game.getPokerTable().getTotalPotAmount(), TypePlayerAction.CALL, playedAmount));
                        break;
                    case RAISED:
                        send(new GamePlayerTurnEndedCommand(p.getCurrentTablePosition(), p.getCurrentBetMoneyAmount(), p.getCurrentSafeMoneyAmount(), m_game.getPokerTable().getTotalPotAmount(), TypePlayerAction.RAISE, playedAmount));
                        break;
                    case SMALL_BLIND_POSTED:
                        send(new GamePlayerTurnEndedCommand(p.getCurrentTablePosition(), p.getCurrentBetMoneyAmount(), p.getCurrentSafeMoneyAmount(), m_game.getPokerTable().getTotalPotAmount(), TypePlayerAction.SMALL_BLIND, playedAmount));
                        break;
                    case BIG_BLIND_POSTED:
                        send(new GamePlayerTurnEndedCommand(p.getCurrentTablePosition(), p.getCurrentBetMoneyAmount(), p.getCurrentSafeMoneyAmount(), m_game.getPokerTable().getTotalPotAmount(), TypePlayerAction.BIG_BLIND, playedAmount));
                        break;
                }
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
                if (p.getCurrentTablePosition() == m_player.getCurrentTablePosition())
                {
                    final int minRaise = m_game.getPokerTable().getCurrentHigherBet() + m_game.getPokerTable().getBigBlindAmount();
                    final int maxRaise = p.getCurrentSafeMoneyAmount();
                    
                    send(new GameAskActionCommand(m_game.getPokerTable().getCurrentHigherBet() == p.getCurrentBetMoneyAmount(), m_game.getPokerTable().getCurrentHigherBet() > p.getCurrentBetMoneyAmount(), m_game.getPokerTable().getCurrentHigherBet() > p.getCurrentBetMoneyAmount(), m_game.getPokerTable().getCurrentHigherBet(), minRaise < maxRaise, minRaise, maxRaise));
                }
            }
            
            @Override
            public void gameBlindsNeeded()
            {
                send(new GameStartedCommand(m_game.getPokerTable().getCurrentDealerNoSeat(), m_game.getPokerTable().getCurrentSmallBlindNoSeat(), m_game.getPokerTable().getCurrentBigBlindNoSeat()));
                // TODO: eventuellement le client devrait par lui meme r�pondre a cette question
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
            public void gameBettingRoundStarted(TypePokerGameRound r)
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
                send(new GameBoardChangedCommand(cards[0].getId(), cards[1].getId(), cards[2].getId(), cards[3].getId(), cards[4].getId()));
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
}