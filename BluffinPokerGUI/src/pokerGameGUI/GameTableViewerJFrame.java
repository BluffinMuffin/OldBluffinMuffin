package pokerGameGUI;

import gameLogic.GameCard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import newPokerLogic.IPokerGame;
import newPokerLogic.PokerMoneyPot;
import newPokerLogic.PokerPlayerInfo;
import newPokerLogic.PokerTableInfo;
import newPokerLogic.TypePokerGameAction;
import newPokerLogic.TypePokerGameRound;
import newPokerLogicTools.PokerGameAdapter;
import newPokerLogicTools.PokerGameObserver;
import pokerLogic.TypePlayerAction;
import utilGUI.ConsoleJPanel;

public class GameTableViewerJFrame extends GameTableAbstractJFrame
{
    private final PlayerHudJPanel[] huds = new PlayerHudJPanel[10];
    private final JLabel[] bets = new JLabel[10];
    private final GameCardJPanel[] board = new GameCardJPanel[5];
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                final GameTableViewerJFrame thisClass = new GameTableViewerJFrame();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }
    
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JPanel jTopPanel = null;
    private JPanel jRightPanel = null;
    private ConsoleJPanel jBottomConsolePanel = null;
    private JLabel jTitleLabel = null;
    private JLabel jBackgroundTableLabel = null;
    private JPanel jTablePanel = null;
    private PlayerHudJPanel jHudPanel = null;
    private PlayerHudJPanel jHudPanel11 = null;
    private PlayerHudJPanel jHudPanel12 = null;
    private PlayerHudJPanel jHudPanel13 = null;
    private PlayerHudJPanel jHudPanel14 = null;
    private PlayerHudJPanel jHudPanel15 = null;
    private PlayerHudJPanel jHudPanel16 = null;
    private PlayerHudJPanel jHudPanel17 = null;
    private PlayerHudJPanel jHudPanel18 = null;
    private PlayerHudJPanel jHudPanel19 = null;
    private GameCardJPanel gameCardJPanel1 = null;
    private GameCardJPanel gameCardJPanel2 = null;
    private GameCardJPanel gameCardJPanel3 = null;
    private GameCardJPanel gameCardJPanel4 = null;
    private GameCardJPanel gameCardJPanel5 = null;
    private JLabel jTotalPotTitleLabel = null;
    private JLabel jTotalPotLabel = null;
    private JLabel jBetLabel1 = null;
    private JLabel jBetLabel2 = null;
    private JLabel jBetLabel3 = null;
    private JLabel jBetLabel4 = null;
    private JLabel jBetLabel5 = null;
    private JLabel jBetLabel6 = null;
    private JLabel jBetLabel7 = null;
    private JLabel jBetLabel8 = null;
    private JLabel jBetLabel9 = null;
    private JLabel jBetLabel10 = null;
    
    /**
     * This is the default constructor
     */
    public GameTableViewerJFrame()
    {
        super();
        initialize();
    }
    
    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize()
    {
        this.setContentPane(getJContentPane());
        huds[0] = getJHudPanel12();
        huds[1] = getJHudPanel13();
        huds[2] = getJHudPanel14();
        huds[3] = getJHudPanel15();
        huds[4] = getJHudPanel16();
        huds[5] = getJHudPanel19();
        huds[6] = getJHudPanel18();
        huds[7] = getJHudPanel17();
        huds[8] = getJHudPanel();
        huds[9] = getJHudPanel11();
        bets[0] = jBetLabel1;
        bets[1] = jBetLabel2;
        bets[2] = jBetLabel3;
        bets[3] = jBetLabel4;
        bets[4] = jBetLabel5;
        bets[5] = jBetLabel6;
        bets[6] = jBetLabel7;
        bets[7] = jBetLabel8;
        bets[8] = jBetLabel9;
        bets[9] = jBetLabel10;
        board[0] = getGameCardJPanel1();
        board[1] = getGameCardJPanel2();
        board[2] = getGameCardJPanel3();
        board[3] = getGameCardJPanel4();
        board[4] = getGameCardJPanel5();
        changeSubTitle("(Viewer Only)");
        this.setResizable(false);
        this.setPreferredSize(new Dimension(1024, 768));
        this.setSize(new Dimension(1024, 768));
    }
    
    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane()
    {
        if (jContentPane == null)
        {
            jBackgroundTableLabel = new JLabel();
            jBackgroundTableLabel.setBounds(new Rectangle(0, 50, 874, 556));
            jBackgroundTableLabel.setIcon(new ImageIcon("images/table.png"));
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.setMinimumSize(new Dimension(1024, 768));
            jContentPane.setMaximumSize(new Dimension(1024, 768));
            jContentPane.setPreferredSize(new Dimension(1024, 768));
            jContentPane.add(getJTopPanel(), null);
            jContentPane.add(getJRightPanel(), null);
            jContentPane.add(getJBottomConsolePanel(), null);
            jContentPane.add(getJTablePanel(), null);
            jContentPane.add(jBackgroundTableLabel, null);
        }
        return jContentPane;
    }
    
    /**
     * This method initializes gameCardJPanel1
     * 
     * @return clientGameGUI.GameCardJPanel
     */
    private GameCardJPanel getGameCardJPanel1()
    {
        if (gameCardJPanel1 == null)
        {
            gameCardJPanel1 = new GameCardJPanel();
            gameCardJPanel1.setBounds(new Rectangle(269, 240, 40, 56));
        }
        return gameCardJPanel1;
    }
    
    /**
     * This method initializes gameCardJPanel2
     * 
     * @return clientGameGUI.GameCardJPanel
     */
    private GameCardJPanel getGameCardJPanel2()
    {
        if (gameCardJPanel2 == null)
        {
            gameCardJPanel2 = new GameCardJPanel();
            gameCardJPanel2.setBounds(new Rectangle(329, 240, 40, 56));
        }
        return gameCardJPanel2;
    }
    
    /**
     * This method initializes gameCardJPanel3
     * 
     * @return clientGameGUI.GameCardJPanel
     */
    private GameCardJPanel getGameCardJPanel3()
    {
        if (gameCardJPanel3 == null)
        {
            gameCardJPanel3 = new GameCardJPanel();
            gameCardJPanel3.setBounds(new Rectangle(389, 240, 40, 56));
        }
        return gameCardJPanel3;
    }
    
    /**
     * This method initializes gameCardJPanel4
     * 
     * @return clientGameGUI.GameCardJPanel
     */
    private GameCardJPanel getGameCardJPanel4()
    {
        if (gameCardJPanel4 == null)
        {
            gameCardJPanel4 = new GameCardJPanel();
            gameCardJPanel4.setBounds(new Rectangle(469, 240, 40, 56));
        }
        return gameCardJPanel4;
    }
    
    /**
     * This method initializes gameCardJPanel5
     * 
     * @return clientGameGUI.GameCardJPanel
     */
    private GameCardJPanel getGameCardJPanel5()
    {
        if (gameCardJPanel5 == null)
        {
            gameCardJPanel5 = new GameCardJPanel();
            gameCardJPanel5.setBounds(new Rectangle(549, 240, 40, 56));
        }
        return gameCardJPanel5;
    }
    
    /**
     * This method initializes jTopPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJTopPanel()
    {
        if (jTopPanel == null)
        {
            jTitleLabel = new JLabel();
            changeSubTitle("(Viewer Only)");
            jTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jTitleLabel.setFont(new Font("Dialog", Font.BOLD, 24));
            jTitleLabel.setForeground(Color.white);
            jTopPanel = new JPanel();
            jTopPanel.setLayout(new BorderLayout());
            jTopPanel.setSize(new Dimension(1017, 50));
            jTopPanel.setMaximumSize(new Dimension(1024, 50));
            jTopPanel.setMinimumSize(new Dimension(1024, 50));
            jTopPanel.setPreferredSize(new Dimension(1024, 50));
            jTopPanel.setBackground(Color.black);
            jTopPanel.setForeground(Color.white);
            jTopPanel.setLocation(new Point(0, 0));
            jTopPanel.add(jTitleLabel, BorderLayout.CENTER);
        }
        return jTopPanel;
    }
    
    /**
     * This method initializes jRightPanel
     * 
     * @return javax.swing.JPanel
     */
    protected JPanel getJRightPanel()
    {
        if (jRightPanel == null)
        {
            jRightPanel = new JPanel();
            jRightPanel.setLayout(new FlowLayout());
            jRightPanel.setSize(new Dimension(143, 556));
            jRightPanel.setBackground(Color.black);
            jRightPanel.setLocation(new Point(874, 50));
        }
        return jRightPanel;
    }
    
    /**
     * This method initializes jBottomPanel
     * 
     * @return javax.swing.JPanel
     */
    private ConsoleJPanel getJBottomConsolePanel()
    {
        if (jBottomConsolePanel == null)
        {
            jBottomConsolePanel = new ConsoleJPanel();
            jBottomConsolePanel.setSize(new Dimension(1017, 132));
            jBottomConsolePanel.setMaximumSize(new Dimension(1017, 132));
            jBottomConsolePanel.setMinimumSize(new Dimension(1017, 132));
            jBottomConsolePanel.setPreferredSize(new Dimension(1017, 132));
            jBottomConsolePanel.setBackground(Color.black);
            jBottomConsolePanel.setLocation(new Point(0, 606));
        }
        return jBottomConsolePanel;
    }
    
    /**
     * This method initializes jTablePanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJTablePanel()
    {
        if (jTablePanel == null)
        {
            jBetLabel10 = new JLabel();
            jBetLabel10.setBounds(new Rectangle(203, 203, 100, 16));
            jBetLabel10.setHorizontalAlignment(SwingConstants.CENTER);
            jBetLabel10.setText("");
            jBetLabel10.setForeground(Color.white);
            jBetLabel9 = new JLabel();
            jBetLabel9.setBounds(new Rectangle(203, 326, 100, 16));
            jBetLabel9.setHorizontalAlignment(SwingConstants.CENTER);
            jBetLabel9.setText("");
            jBetLabel9.setForeground(Color.white);
            jBetLabel8 = new JLabel();
            jBetLabel8.setBounds(new Rectangle(237, 374, 100, 16));
            jBetLabel8.setHorizontalAlignment(SwingConstants.CENTER);
            jBetLabel8.setText("");
            jBetLabel8.setForeground(Color.white);
            jBetLabel7 = new JLabel();
            jBetLabel7.setBounds(new Rectangle(382, 374, 100, 16));
            jBetLabel7.setHorizontalAlignment(SwingConstants.CENTER);
            jBetLabel7.setText("");
            jBetLabel7.setForeground(Color.white);
            jBetLabel6 = new JLabel();
            jBetLabel6.setBounds(new Rectangle(527, 374, 100, 16));
            jBetLabel6.setHorizontalAlignment(SwingConstants.CENTER);
            jBetLabel6.setText("");
            jBetLabel6.setForeground(Color.white);
            jBetLabel5 = new JLabel();
            jBetLabel5.setBounds(new Rectangle(566, 326, 100, 16));
            jBetLabel5.setHorizontalAlignment(SwingConstants.CENTER);
            jBetLabel5.setText("");
            jBetLabel5.setForeground(Color.white);
            jBetLabel4 = new JLabel();
            jBetLabel4.setBounds(new Rectangle(566, 203, 100, 16));
            jBetLabel4.setHorizontalAlignment(SwingConstants.CENTER);
            jBetLabel4.setText("");
            jBetLabel4.setForeground(Color.white);
            jBetLabel3 = new JLabel();
            jBetLabel3.setBounds(new Rectangle(527, 140, 100, 16));
            jBetLabel3.setHorizontalAlignment(SwingConstants.CENTER);
            jBetLabel3.setText("");
            jBetLabel3.setForeground(Color.white);
            jBetLabel2 = new JLabel();
            jBetLabel2.setBounds(new Rectangle(382, 140, 100, 16));
            jBetLabel2.setHorizontalAlignment(SwingConstants.CENTER);
            jBetLabel2.setText("");
            jBetLabel2.setForeground(Color.white);
            jBetLabel1 = new JLabel();
            jBetLabel1.setBounds(new Rectangle(237, 140, 100, 16));
            jBetLabel1.setHorizontalAlignment(SwingConstants.CENTER);
            jBetLabel1.setText("");
            jBetLabel1.setForeground(Color.white);
            jTotalPotLabel = new JLabel();
            jTotalPotLabel.setBounds(new Rectangle(25, 45, 100, 16));
            jTotalPotLabel.setText("");
            jTotalPotLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jTotalPotLabel.setForeground(Color.white);
            jTotalPotTitleLabel = new JLabel();
            jTotalPotTitleLabel.setBounds(new Rectangle(25, 25, 100, 16));
            jTotalPotTitleLabel.setForeground(Color.white);
            jTotalPotTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jTotalPotTitleLabel.setText("Total Pot:");
            jTablePanel = new JPanel();
            jTablePanel.setLayout(null);
            jTablePanel.setBounds(new Rectangle(0, 50, 874, 556));
            jTablePanel.setOpaque(false);
            jTablePanel.setBackground(Color.black);
            jTablePanel.add(getJHudPanel(), null);
            jTablePanel.add(getJHudPanel11(), null);
            jTablePanel.add(getJHudPanel12(), null);
            jTablePanel.add(getJHudPanel13(), null);
            jTablePanel.add(getJHudPanel14(), null);
            jTablePanel.add(getJHudPanel15(), null);
            jTablePanel.add(getJHudPanel16(), null);
            jTablePanel.add(getJHudPanel17(), null);
            jTablePanel.add(getJHudPanel18(), null);
            jTablePanel.add(getJHudPanel19(), null);
            jTablePanel.add(getGameCardJPanel1(), null);
            jTablePanel.add(getGameCardJPanel2(), null);
            jTablePanel.add(getGameCardJPanel3(), null);
            jTablePanel.add(getGameCardJPanel4(), null);
            jTablePanel.add(getGameCardJPanel5(), null);
            jTablePanel.add(jTotalPotTitleLabel, null);
            jTablePanel.add(jTotalPotLabel, null);
            jTablePanel.add(jBetLabel1, null);
            jTablePanel.add(jBetLabel2, null);
            jTablePanel.add(jBetLabel3, null);
            jTablePanel.add(jBetLabel4, null);
            jTablePanel.add(jBetLabel5, null);
            jTablePanel.add(jBetLabel6, null);
            jTablePanel.add(jBetLabel7, null);
            jTablePanel.add(jBetLabel8, null);
            jTablePanel.add(jBetLabel9, null);
            jTablePanel.add(jBetLabel10, null);
        }
        return jTablePanel;
    }
    
    /**
     * This method initializes jHudPanel
     * 
     * @return javax.swing.JPanel
     */
    private PlayerHudJPanel getJHudPanel()
    {
        if (jHudPanel == null)
        {
            jHudPanel = new PlayerHudJPanel();
            jHudPanel.setLayout(null);
            jHudPanel.setBounds(new Rectangle(75, 275, 125, 125));
            jHudPanel.setVisible(false);
        }
        return jHudPanel;
    }
    
    /**
     * This method initializes jHudPanel11
     * 
     * @return javax.swing.JPanel
     */
    private PlayerHudJPanel getJHudPanel11()
    {
        if (jHudPanel11 == null)
        {
            jHudPanel11 = new PlayerHudJPanel();
            jHudPanel11.setLayout(null);
            jHudPanel11.setBounds(new Rectangle(75, 130, 125, 125));
            jHudPanel11.setVisible(false);
        }
        return jHudPanel11;
    }
    
    /**
     * This method initializes jHudPanel12
     * 
     * @return javax.swing.JPanel
     */
    private PlayerHudJPanel getJHudPanel12()
    {
        if (jHudPanel12 == null)
        {
            jHudPanel12 = new PlayerHudJPanel();
            jHudPanel12.setLayout(null);
            jHudPanel12.setBounds(new Rectangle(225, 10, 125, 125));
            jHudPanel12.setVisible(false);
        }
        return jHudPanel12;
    }
    
    /**
     * This method initializes jHudPanel13
     * 
     * @return javax.swing.JPanel
     */
    private PlayerHudJPanel getJHudPanel13()
    {
        if (jHudPanel13 == null)
        {
            jHudPanel13 = new PlayerHudJPanel();
            jHudPanel13.setLayout(null);
            jHudPanel13.setBounds(new Rectangle(370, 10, 125, 125));
            jHudPanel13.setVisible(false);
        }
        return jHudPanel13;
    }
    
    /**
     * This method initializes jHudPanel14
     * 
     * @return javax.swing.JPanel
     */
    private PlayerHudJPanel getJHudPanel14()
    {
        if (jHudPanel14 == null)
        {
            jHudPanel14 = new PlayerHudJPanel();
            jHudPanel14.setLayout(null);
            jHudPanel14.setBounds(new Rectangle(515, 10, 125, 125));
            jHudPanel14.setVisible(false);
        }
        return jHudPanel14;
    }
    
    /**
     * This method initializes jHudPanel15
     * 
     * @return javax.swing.JPanel
     */
    private PlayerHudJPanel getJHudPanel15()
    {
        if (jHudPanel15 == null)
        {
            jHudPanel15 = new PlayerHudJPanel();
            jHudPanel15.setLayout(null);
            jHudPanel15.setBounds(new Rectangle(665, 130, 125, 125));
            jHudPanel15.setVisible(false);
        }
        return jHudPanel15;
    }
    
    /**
     * This method initializes jHudPanel16
     * 
     * @return javax.swing.JPanel
     */
    private PlayerHudJPanel getJHudPanel16()
    {
        if (jHudPanel16 == null)
        {
            jHudPanel16 = new PlayerHudJPanel();
            jHudPanel16.setLayout(null);
            jHudPanel16.setBounds(new Rectangle(665, 275, 125, 125));
            jHudPanel16.setVisible(false);
        }
        return jHudPanel16;
    }
    
    /**
     * This method initializes jHudPanel17
     * 
     * @return javax.swing.JPanel
     */
    private PlayerHudJPanel getJHudPanel17()
    {
        if (jHudPanel17 == null)
        {
            jHudPanel17 = new PlayerHudJPanel();
            jHudPanel17.setLayout(null);
            jHudPanel17.setBounds(new Rectangle(225, 400, 125, 125));
            jHudPanel17.setVisible(false);
        }
        return jHudPanel17;
    }
    
    /**
     * This method initializes jHudPanel18
     * 
     * @return javax.swing.JPanel
     */
    private PlayerHudJPanel getJHudPanel18()
    {
        if (jHudPanel18 == null)
        {
            jHudPanel18 = new PlayerHudJPanel();
            jHudPanel18.setLayout(null);
            jHudPanel18.setBounds(new Rectangle(370, 400, 125, 125));
            jHudPanel18.setVisible(false);
        }
        return jHudPanel18;
    }
    
    /**
     * This method initializes jHudPanel19
     * 
     * @return javax.swing.JPanel
     */
    private PlayerHudJPanel getJHudPanel19()
    {
        if (jHudPanel19 == null)
        {
            jHudPanel19 = new PlayerHudJPanel();
            jHudPanel19.setLayout(null);
            jHudPanel19.setBounds(new Rectangle(515, 400, 125, 125));
            jHudPanel19.setVisible(false);
        }
        return jHudPanel19;
    }
    
    public void writeLine(String line)
    {
        getJBottomConsolePanel().writeLine(line);
    }
    
    public void write(String txt)
    {
        getJBottomConsolePanel().write(txt);
    }
    
    @Override
    public void setGame(IPokerGame game, int seatViewed)
    {
        super.setGame(game, seatViewed);
        // writeLine("******** LOGGED AS " + m_table.m_localPlayer.getName() + " ********");
    }
    
    @Override
    public void setPokerObserver(PokerGameObserver observer)
    {
        super.setPokerObserver(observer);
        initializePokerObserverForConsole();
        initializePokerObserverForGUI();
    }
    
    protected void changeSubTitle(String title)
    {
        this.setTitle("Poker Table 2.0 " + title);
    }
    
    protected void changePotAmount(int amount)
    {
        jTotalPotLabel.setText("$" + amount);
    }
    
    private void initializePokerObserverForGUI()
    {
        m_pokerObserver.subscribe(new PokerGameAdapter()
        {
            
            @Override
            public void gameBettingRoundEnded(TypePokerGameRound r)
            {
                // TODO: update POTS
                final PokerTableInfo table = m_game.getPokerTable();
                
                for (int i = 0; i < table.getPlayers().size(); ++i)
                {
                    final JLabel bet = bets[table.getPlayer(i).getCurrentTablePosition()];
                    bet.setText("");
                }
            }
            
            @Override
            public void gameBettingRoundStarted(TypePokerGameRound r)
            {
                int i = 0;
                final GameCard[] cards = new GameCard[5];
                m_game.getPokerTable().getCurrentBoardCards().toArray(cards);
                
                for (; i < 5 && cards[i].getId() != GameCard.NO_CARD_ID; ++i)
                {
                    board[i].setCard(cards[i]);
                }
                for (; i < 5; ++i)
                {
                    board[i].setCard(GameCard.HIDDEN_CARD);
                }
            }
            
            @Override
            public void gameBlindsNeeded()
            {
                changePotAmount(0);
                final PokerTableInfo table = m_game.getPokerTable();
                huds[table.getCurrentDealerNoSeat()].setDealer();
                huds[table.getCurrentSmallBlindNoSeat()].setSmallBlind();
                huds[table.getCurrentBigBlindNoSeat()].setBigBlind();
                for (int i = 0; i < 5; ++i)
                {
                    board[i].setCard(GameCard.HIDDEN_CARD);
                }
            }
            
            @Override
            public void gameEnded()
            {
                final PokerTableInfo table = m_game.getPokerTable();
                for (int i = 0; i < table.getPlayers().size(); ++i)
                {
                    final PlayerHudJPanel php = huds[i];
                    final JLabel bet = bets[i];
                    bet.setText("");
                    php.setPlayerMoney(table.getPlayer(i).getCurrentSafeMoneyAmount());
                    php.setNotDealer();
                    php.setNoBlind();
                    if (table.getPlayer(i).getCurrentSafeMoneyAmount() == 0)
                    {
                        php.setBackground(Color.gray);
                        php.setHeaderColor(Color.gray);
                        php.setPlayerCards(GameCard.NO_CARD, GameCard.NO_CARD);
                    }
                    else
                    {
                        php.setBackground(Color.white);
                        php.setHeaderColor(Color.white);
                    }
                    php.setPlayerAction(TypePlayerAction.NOTHING);
                }
                super.gameEnded();
            }
            
            @Override
            public void gameGenerallyUpdated()
            {
                final PokerTableInfo table = m_game.getPokerTable();
                for (int i = 0; i < table.getPlayers().size(); ++i)
                {
                    final PlayerHudJPanel php = huds[i];
                    installPlayer(php, table.getPlayer(i));
                }
            }
            
            @Override
            public void playerActionNeeded(PokerPlayerInfo p)
            {
                final PlayerHudJPanel php = huds[p.getCurrentTablePosition()];
                if (p.getCurrentTablePosition() == m_currentTablePosition)
                {
                    php.setHeaderColor(Color.green);
                }
                else
                {
                    php.setHeaderColor(new Color(175, 200, 75));
                }
            }
            
            @Override
            public void playerActionTaken(PokerPlayerInfo p, TypePokerGameAction reason, int playedAmount)
            {
                final PokerTableInfo table = m_game.getPokerTable();
                final PlayerHudJPanel php = huds[p.getCurrentTablePosition()];
                php.setPlayerMoney(p.getCurrentSafeMoneyAmount());
                php.setPlayerAction(reason, playedAmount);
                changePotAmount(table.getTotalPotAmount());
                
                php.setHeaderColor(Color.white);
                if (reason == TypePokerGameAction.FOLDED)
                {
                    php.setPlayerCards(GameCard.NO_CARD, GameCard.NO_CARD);
                }
                if (p.getCurrentBetMoneyAmount() > 0)
                {
                    final JLabel bet = bets[p.getCurrentTablePosition()];
                    bet.setText("$" + p.getCurrentBetMoneyAmount());
                }
            }
            
            @Override
            public void playerHoleCardsChanged(PokerPlayerInfo p)
            {
                final PlayerHudJPanel php = huds[p.getCurrentTablePosition()];
                final GameCard[] cards = p.getCurrentHand(true);
                php.setPlayerCards(cards[0], cards[1]);
            }
            
            @Override
            public void playerJoined(PokerPlayerInfo p)
            {
                final PlayerHudJPanel php = huds[p.getCurrentTablePosition()];
                installPlayer(php, p);
            }
            
            @Override
            public void playerLeaved(PokerPlayerInfo p)
            {
                final PlayerHudJPanel php = huds[p.getCurrentTablePosition()];
                php.setVisible(false);
            }
            
            @Override
            public void playerMoneyChanged(PokerPlayerInfo p)
            {
                // TODO Auto-generated method stub
                super.playerMoneyChanged(p);
            }
            
            @Override
            public void playerWonPot(PokerPlayerInfo p, PokerMoneyPot pot, int wonAmount)
            {
                // TODO Auto-generated method stub
                super.playerWonPot(p, pot, wonAmount);
            }
            
            private void installPlayer(PlayerHudJPanel php, PokerPlayerInfo player)
            {
                php.setPlayerName(player.getPlayerName());
                php.setPlayerInfo("");// TODO: Human or BOT
                php.setPlayerAction(TypePlayerAction.NOTHING);
                php.setPlayerCards(GameCard.NO_CARD, GameCard.NO_CARD);
                php.setPlayerMoney(player.getCurrentSafeMoneyAmount());
                php.setBackground(Color.white);
                php.setHeaderColor(Color.white);
                php.setVisible(true);
            }
        });
    }
    
    private void initializePokerObserverForConsole()
    {
        m_pokerObserver.subscribe(new PokerGameAdapter()
        {
            
            @Override
            public void everythingEnded()
            {
                writeLine("==> Table closed");
            }
            
            @Override
            public void gameBettingRoundEnded(TypePokerGameRound r)
            {
                writeLine("==> End of " + r.name());
            }
            
            @Override
            public void gameBettingRoundStarted(TypePokerGameRound r)
            {
                writeLine("==> Beginning of " + r.name());
                if (r != TypePokerGameRound.PREFLOP)
                {
                    write("==> Current board cards:");
                    final GameCard[] cards = new GameCard[5];
                    m_game.getPokerTable().getCurrentBoardCards().toArray(cards);
                    for (int i = 0; i < 5 && cards[i].getId() != GameCard.NO_CARD_ID; ++i)
                    {
                        write(" " + cards[i].toString());
                    }
                }
                writeLine("");
            }
            
            @Override
            public void gameBlindsNeeded()
            {
                writeLine("==> Game started");
                final PokerTableInfo table = m_game.getPokerTable();
                final PokerPlayerInfo d = table.getPlayer(table.getCurrentDealerNoSeat());
                final PokerPlayerInfo sb = table.getPlayer(table.getCurrentSmallBlindNoSeat());
                final PokerPlayerInfo bb = table.getPlayer(table.getCurrentBigBlindNoSeat());
                writeLine("==> " + d.getPlayerName() + " is the Dealer");
                writeLine("==> " + sb.getPlayerName() + " is the SmallBlind");
                writeLine("==> " + bb.getPlayerName() + " is the BigBlind");
            }
            
            @Override
            public void gameEnded()
            {
                writeLine("==> End of the Game");
            }
            
            @Override
            public void gameGenerallyUpdated()
            {
                writeLine("==> Table info received");
            }
            
            @Override
            public void playerActionNeeded(PokerPlayerInfo p)
            {
                writeLine("Player turn began (" + p.getPlayerName() + ")");
            }
            
            @Override
            public void playerActionTaken(PokerPlayerInfo p, TypePokerGameAction reason, int playedAmount)
            {
                writeLine(p.getPlayerName() + " did [" + reason.name() + "]");
            }
            
            @Override
            public void playerHoleCardsChanged(PokerPlayerInfo p)
            {
                final GameCard[] cards = p.getCurrentHand(true);
                writeLine("==> Hole Card changed for " + p.getPlayerName() + ": " + cards[0].toString() + " " + cards[1].toString());
            }
            
            @Override
            public void playerJoined(PokerPlayerInfo p)
            {
                writeLine(p.getPlayerName() + " joined the table");
            }
            
            @Override
            public void playerLeaved(PokerPlayerInfo p)
            {
                writeLine(p.getPlayerName() + " left the table");
            }
            
            @Override
            public void playerMoneyChanged(PokerPlayerInfo p)
            {
                writeLine(p.getPlayerName() + " money changed to " + p.getCurrentSafeMoneyAmount());
            }
            
            @Override
            public void playerWonPot(PokerPlayerInfo p, PokerMoneyPot pot, int wonAmount)
            {
                writeLine(p.getPlayerName() + " won pot ($" + wonAmount + ")");
            }
        });
    }
} // @jve:decl-index=0:visual-constraint="10,10"
