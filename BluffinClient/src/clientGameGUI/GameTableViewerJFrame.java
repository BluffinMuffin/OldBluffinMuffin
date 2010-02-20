package clientGameGUI;

import gameLogic.GameCard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import pokerLogic.OldPokerPlayerInfo;
import pokerLogic.OldPokerTableInfo;
import pokerLogic.PokerPlayerAction;
import pokerLogic.TypePlayerAction;
import pokerLogic.TypePokerRound;
import utilGUI.ConsoleJPanel;
import clientGameTools.ClientPokerAdapter;
import clientGameTools.ClientPokerObserver;

public class GameTableViewerJFrame extends GameTableAbstractJFrame
{
    private final PlayerHudJPanel[] huds = new PlayerHudJPanel[10];
    private final JLabel[] bets = new JLabel[10];
    private final GameCardJPanel[] board = new GameCardJPanel[5];
    private int m_last_NiceHud = -1;
    
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
    
    @Override
    public void setTable(OldPokerTableInfo pTable)
    {
        super.setTable(pTable);
        writeLine("******** LOGGED AS " + m_table.m_localPlayer.getName() + " ********");
    }
    
    @Override
    public void setPokerObserver(ClientPokerObserver observer)
    {
        super.setPokerObserver(observer);
        initializePokerObserverForConsole();
        initializePokerObserverForGUI();
    }
    
    private void initializePokerObserverForGUI()
    {
        m_pokerObserver.subscribe(new ClientPokerAdapter()
        {
            
            @Override
            public void betTurnEnded(ArrayList<Integer> potIndices, TypePokerRound round)
            {
                // TODO Auto-generated method stub
                super.betTurnEnded(potIndices, round);
            }
            
            @Override
            public void boardChanged(ArrayList<Integer> boardCardIndices)
            {
                for (final Integer i : boardCardIndices)
                {
                    board[i].setCard(m_table.getBoard()[i]);
                }
            }
            
            @Override
            public void gameEnded()
            {
                m_last_NiceHud = -1;
                for (int i = 0; i < m_table.getPlayers().size(); ++i)
                {
                    final PlayerHudJPanel php = huds[m_table.getPlayer(i).getNoSeat()];
                    final JLabel bet = bets[m_table.getPlayer(i).getNoSeat()];
                    bet.setText("");
                    php.setPlayerMoney(m_table.getPlayer(i).getMoney());
                    php.setNotDealer();
                    php.setNoBlind();
                    if (m_table.getPlayer(i).getMoney() == 0)
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
            public void gameStarted(OldPokerPlayerInfo oldDealer, OldPokerPlayerInfo oldSmallBlind, OldPokerPlayerInfo oldBigBlind)
            {
                changePotAmount(0);
                huds[m_table.m_noSeatDealer].setDealer();
                huds[m_table.m_noSeatSmallBlind].setSmallBlind();
                huds[m_table.m_noSeatBigBlind].setBigBlind();
                for (int i = 0; i < 5; ++i)
                {
                    board[i].setCard(GameCard.HIDDEN_CARD);
                }
            }
            
            @Override
            public void playerCardChanged(OldPokerPlayerInfo player)
            {
                final PlayerHudJPanel php = huds[player.getNoSeat()];
                php.setPlayerCards(player.getHand()[0], player.getHand()[1]);
            }
            
            @Override
            public void playerJoined(OldPokerPlayerInfo player)
            {
                final PlayerHudJPanel php = huds[player.getNoSeat()];
                installPlayer(php, player);
            }
            
            private void installPlayer(PlayerHudJPanel php, OldPokerPlayerInfo player)
            {
                php.setPlayerName(player.getName());
                php.setPlayerInfo("");// TODO: Human or BOT
                php.setPlayerAction(TypePlayerAction.NOTHING);
                php.setPlayerCards(GameCard.HIDDEN_CARD, GameCard.HIDDEN_CARD);
                php.setPlayerMoney(player.getMoney());
                php.setBackground(Color.white);
                php.setHeaderColor(Color.white);
                php.setVisible(true);
            }
            
            @Override
            public void playerLeft(OldPokerPlayerInfo player)
            {
                final PlayerHudJPanel php = huds[player.getNoSeat()];
                php.setVisible(false);
            }
            
            @Override
            public void playerMoneyChanged(OldPokerPlayerInfo player, int oldMoneyAmount)
            {
                final PlayerHudJPanel php = huds[player.getNoSeat()];
                php.setPlayerMoney(player.getMoney());
            }
            
            @Override
            public void playerTurnBegan(OldPokerPlayerInfo oldPlayer)
            {
                final OldPokerPlayerInfo player = m_table.m_currentPlayer;
                if (m_last_NiceHud >= 0)
                {
                    final PlayerHudJPanel php = huds[m_last_NiceHud];
                    php.setHeaderColor(Color.white);
                }
                if (player != null)
                {
                    final PlayerHudJPanel php = huds[player.getNoSeat()];
                    if (player == m_table.m_localPlayer)
                    {
                        php.setHeaderColor(Color.green);
                    }
                    else
                    {
                        php.setHeaderColor(new Color(175, 200, 75));
                    }
                    m_last_NiceHud = player.getNoSeat();
                }
            }
            
            @Override
            public void playerTurnEnded(OldPokerPlayerInfo player, TypePlayerAction action, int actionAmount)
            {
                
                final PlayerHudJPanel php = huds[player.getNoSeat()];
                php.setPlayerMoney(player.getMoney());
                php.setPlayerAction(action);
                changePotAmount(m_table.m_totalPotAmount);
                if (action == TypePlayerAction.FOLD)
                {
                    php.setPlayerCards(GameCard.NO_CARD, GameCard.NO_CARD);
                }
                if (player.getBet() > 0)
                {
                    final JLabel bet = bets[player.getNoSeat()];
                    bet.setText("$" + player.getBet());
                }
            }
            
            @Override
            public void potWon(OldPokerPlayerInfo player, int potAmountWon, int potIndex)
            {
                final PlayerHudJPanel php = huds[player.getNoSeat()];
                php.setPlayerMoney(player.getMoney());
                php.setHeaderColor(Color.cyan);
            }
            
            @Override
            public void tableInfos()
            {
                for (int i = 0; i < m_table.getPlayers().size(); ++i)
                {
                    final PlayerHudJPanel php = huds[m_table.getPlayer(i).getNoSeat()];
                    installPlayer(php, m_table.getPlayer(i));
                }
            }
        });
    }
    
    private void initializePokerObserverForConsole()
    {
        m_pokerObserver.subscribe(new ClientPokerAdapter()
        {
            
            @Override
            public void betTurnEnded(ArrayList<Integer> potIndices, TypePokerRound round)
            {
                writeLine("==> End of " + round.name());
            }
            
            @Override
            public void boardChanged(ArrayList<Integer> boardCardIndices)
            {
                final StringBuilder sb = new StringBuilder();
                sb.append("==> Board Card dealt:");
                for (final Integer i : boardCardIndices)
                {
                    sb.append(" ");
                    sb.append(m_table.getBoard()[i].toString());
                }
                writeLine(sb.toString());
            }
            
            @Override
            public void gameEnded()
            {
                writeLine("==> End of the Game");
            }
            
            @Override
            public void gameStarted(OldPokerPlayerInfo oldDealer, OldPokerPlayerInfo oldSmallBlind, OldPokerPlayerInfo oldBigBlind)
            {
                writeLine("==> Game started");
                writeLine("==> " + m_table.m_dealer.getName() + " is the Dealer");
                writeLine("==> " + m_table.m_smallBlind.getName() + " is the SmallBlind");
                writeLine("==> " + m_table.m_bigBlind.getName() + " is the BigBlind");
            }
            
            @Override
            public void playerCardChanged(OldPokerPlayerInfo player)
            {
                if (player == m_table.m_localPlayer)
                {
                    final StringBuilder sb = new StringBuilder();
                    sb.append("==> Hole Card dealt:");
                    
                    sb.append(" ");
                    sb.append(player.getHand()[0]);
                    sb.append(" ");
                    sb.append(player.getHand()[1]);
                    
                    writeLine(sb.toString());
                }
            }
            
            @Override
            public void playerJoined(OldPokerPlayerInfo player)
            {
                writeLine(player.getName() + " joined the table");
            }
            
            @Override
            public void playerLeft(OldPokerPlayerInfo player)
            {
                writeLine(player.getName() + " left the table");
            }
            
            @Override
            public void playerMoneyChanged(OldPokerPlayerInfo player, int oldMoneyAmount)
            {
                writeLine(player.getName() + " money changed");
            }
            
            @Override
            public void playerTurnBegan(OldPokerPlayerInfo player)
            {
                // writeLine("Player turn began");
            }
            
            @Override
            public void playerTurnEnded(OldPokerPlayerInfo player, TypePlayerAction action, int actionAmount)
            {
                final PokerPlayerAction ppa = new PokerPlayerAction(action, actionAmount);
                writeLine(player.getName() + " " + ppa);
            }
            
            @Override
            public void potWon(OldPokerPlayerInfo player, int potAmountWon, int potIndex)
            {
                writeLine(player.getName() + " won pot");
            }
            
            @Override
            public void tableClosed()
            {
                writeLine("==> Table closed");
            }
            
            @Override
            public void tableInfos()
            {
                writeLine("==> Table info received");
            }
            
            @Override
            public void waitingForPlayers()
            {
                // writeLine("Waiting for players");
            }
        });
    }
    
    protected void changeSubTitle(String title)
    {
        this.setTitle("Poker Table 2.0 " + title);
    }
    
    protected void changePotAmount(int amount)
    {
        jTotalPotLabel.setText("$" + amount);
    }
} // @jve:decl-index=0:visual-constraint="10,10"
