package gui.game;

import game.Card;
import gui.JDialogHandStrength;
import gui.JPanelConsole;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import poker.game.IPokerGame;
import poker.game.MoneyPot;
import poker.game.PlayerInfo;
import poker.game.TableInfo;
import poker.game.TypeAction;
import poker.game.TypeRound;
import poker.game.observer.PokerGameAdapter;

public class JFrameTableViewer extends AbstractJFrameTable
{
    private final JPanelPlayerHud[] huds = new JPanelPlayerHud[10];
    private final JLabel[] bets = new JLabel[10];
    private final JPanelCard[] board = new JPanelCard[5];
    
    /**
     * This method initializes jHelpButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getJHelpButton()
    {
        if (jHelpButton == null)
        {
            jHelpButton = new JButton();
            jHelpButton.setText("HELP");
            jHelpButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    final JDialogHandStrength dialog = new JDialogHandStrength(JFrameTableViewer.this);
                    dialog.setVisible(true);
                }
            });
        }
        return jHelpButton;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                final JFrameTableViewer thisClass = new JFrameTableViewer();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }
    
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JPanel jTopPanel = null;
    private JPanel jRightPanel = null;
    private JPanelConsole jBottomConsolePanel = null;
    private JLabel jTitleLabel = null;
    private JLabel jBackgroundTableLabel = null;
    private JPanel jTablePanel = null;
    private JPanelPlayerHud jHudPanel = null;
    private JPanelPlayerHud jHudPanel11 = null;
    private JPanelPlayerHud jHudPanel12 = null;
    private JPanelPlayerHud jHudPanel13 = null;
    private JPanelPlayerHud jHudPanel14 = null;
    private JPanelPlayerHud jHudPanel15 = null;
    private JPanelPlayerHud jHudPanel16 = null;
    private JPanelPlayerHud jHudPanel17 = null;
    private JPanelPlayerHud jHudPanel18 = null;
    private JPanelPlayerHud jHudPanel19 = null;
    private JPanelCard gameCardJPanel1 = null;
    private JPanelCard gameCardJPanel2 = null;
    private JPanelCard gameCardJPanel3 = null;
    private JPanelCard gameCardJPanel4 = null;
    private JPanelCard gameCardJPanel5 = null;
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
    private JButton jHelpButton = null;
    
    /**
     * This is the default constructor
     */
    public JFrameTableViewer()
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
    private JPanelCard getGameCardJPanel1()
    {
        if (gameCardJPanel1 == null)
        {
            gameCardJPanel1 = new JPanelCard();
            gameCardJPanel1.setBounds(new Rectangle(269, 240, 40, 56));
        }
        return gameCardJPanel1;
    }
    
    /**
     * This method initializes gameCardJPanel2
     * 
     * @return clientGameGUI.GameCardJPanel
     */
    private JPanelCard getGameCardJPanel2()
    {
        if (gameCardJPanel2 == null)
        {
            gameCardJPanel2 = new JPanelCard();
            gameCardJPanel2.setBounds(new Rectangle(329, 240, 40, 56));
        }
        return gameCardJPanel2;
    }
    
    /**
     * This method initializes gameCardJPanel3
     * 
     * @return clientGameGUI.GameCardJPanel
     */
    private JPanelCard getGameCardJPanel3()
    {
        if (gameCardJPanel3 == null)
        {
            gameCardJPanel3 = new JPanelCard();
            gameCardJPanel3.setBounds(new Rectangle(389, 240, 40, 56));
        }
        return gameCardJPanel3;
    }
    
    /**
     * This method initializes gameCardJPanel4
     * 
     * @return clientGameGUI.GameCardJPanel
     */
    private JPanelCard getGameCardJPanel4()
    {
        if (gameCardJPanel4 == null)
        {
            gameCardJPanel4 = new JPanelCard();
            gameCardJPanel4.setBounds(new Rectangle(469, 240, 40, 56));
        }
        return gameCardJPanel4;
    }
    
    /**
     * This method initializes gameCardJPanel5
     * 
     * @return clientGameGUI.GameCardJPanel
     */
    private JPanelCard getGameCardJPanel5()
    {
        if (gameCardJPanel5 == null)
        {
            gameCardJPanel5 = new JPanelCard();
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
            jTopPanel.add(getJHelpButton(), BorderLayout.EAST);
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
    private JPanelConsole getJBottomConsolePanel()
    {
        if (jBottomConsolePanel == null)
        {
            jBottomConsolePanel = new JPanelConsole();
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
    private JPanelPlayerHud getJHudPanel()
    {
        if (jHudPanel == null)
        {
            jHudPanel = new JPanelPlayerHud();
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
    private JPanelPlayerHud getJHudPanel11()
    {
        if (jHudPanel11 == null)
        {
            jHudPanel11 = new JPanelPlayerHud();
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
    private JPanelPlayerHud getJHudPanel12()
    {
        if (jHudPanel12 == null)
        {
            jHudPanel12 = new JPanelPlayerHud();
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
    private JPanelPlayerHud getJHudPanel13()
    {
        if (jHudPanel13 == null)
        {
            jHudPanel13 = new JPanelPlayerHud();
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
    private JPanelPlayerHud getJHudPanel14()
    {
        if (jHudPanel14 == null)
        {
            jHudPanel14 = new JPanelPlayerHud();
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
    private JPanelPlayerHud getJHudPanel15()
    {
        if (jHudPanel15 == null)
        {
            jHudPanel15 = new JPanelPlayerHud();
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
    private JPanelPlayerHud getJHudPanel16()
    {
        if (jHudPanel16 == null)
        {
            jHudPanel16 = new JPanelPlayerHud();
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
    private JPanelPlayerHud getJHudPanel17()
    {
        if (jHudPanel17 == null)
        {
            jHudPanel17 = new JPanelPlayerHud();
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
    private JPanelPlayerHud getJHudPanel18()
    {
        if (jHudPanel18 == null)
        {
            jHudPanel18 = new JPanelPlayerHud();
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
    private JPanelPlayerHud getJHudPanel19()
    {
        if (jHudPanel19 == null)
        {
            jHudPanel19 = new JPanelPlayerHud();
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
        m_game.attach(new PokerGameAdapter()
        {
            
            @Override
            public void gameBettingRoundEnded(TypeRound r)
            {
                // TODO: RICK: update POTS
                final TableInfo table = m_game.getTable();
                
                for (int i = 0; i < table.getPlayers().size(); ++i)
                {
                    final JLabel bet = bets[table.getPlayer(i).getNoSeat()];
                    bet.setText("");
                }
            }
            
            @Override
            public void gameBettingRoundStarted()
            {
                int i = 0;
                for (; i < 5 && m_game.getTable().getCards().get(i).getId() != Card.NO_CARD_ID; ++i)
                {
                    board[i].setCard(m_game.getTable().getCards().get(i));
                }
                for (; i < 5; ++i)
                {
                    board[i].setCard(Card.HIDDEN_CARD);
                }
            }
            
            @Override
            public void gameBlindsNeeded()
            {
                changePotAmount(0);
                final TableInfo table = m_game.getTable();
                huds[table.getNoSeatDealer()].setDealer();
                huds[table.getNoSeatSmallBlind()].setSmallBlind();
                huds[table.getNoSeatBigBlind()].setBigBlind();
                for (int i = 0; i < 5; ++i)
                {
                    board[i].setCard(Card.HIDDEN_CARD);
                }
            }
            
            @Override
            public void gameEnded()
            {
                final TableInfo table = m_game.getTable();
                for (int i = 0; i < table.getPlayers().size(); ++i)
                {
                    final JPanelPlayerHud php = huds[i];
                    final JLabel bet = bets[i];
                    bet.setText("");
                    php.setPlayerMoney(table.getPlayer(i).getMoneySafeAmnt());
                    php.setNotDealer();
                    php.setNoBlind();
                    if (table.getPlayer(i).getMoneySafeAmnt() == 0)
                    {
                        php.setBackground(Color.gray);
                        php.setHeaderColor(Color.gray);
                        php.setPlayerCards(Card.NO_CARD, Card.NO_CARD);
                    }
                    else
                    {
                        php.setBackground(Color.white);
                        php.setHeaderColor(Color.white);
                    }
                    php.setPlayerAction(TypeAction.NOTHING);
                }
                super.gameEnded();
            }
            
            @Override
            public void gameGenerallyUpdated()
            {
                final TableInfo table = m_game.getTable();
                for (final PlayerInfo p : table.getPlayers())
                {
                    final JPanelPlayerHud php = huds[p.getNoSeat()];
                    installPlayer(php, p);
                }
            }
            
            @Override
            public void playerActionNeeded(PlayerInfo p)
            {
                final JPanelPlayerHud php = huds[p.getNoSeat()];
                if (p.getNoSeat() == m_currentTablePosition)
                {
                    php.setHeaderColor(Color.green);
                }
                else
                {
                    php.setHeaderColor(new Color(175, 200, 75));
                }
            }
            
            @Override
            public void playerActionTaken(PlayerInfo p, TypeAction reason, int playedAmount)
            {
                final TableInfo table = m_game.getTable();
                final JPanelPlayerHud php = huds[p.getNoSeat()];
                php.setPlayerMoney(p.getMoneySafeAmnt());
                php.setPlayerAction(reason, playedAmount);
                changePotAmount(table.getTotalPotAmnt());
                
                php.setHeaderColor(Color.white);
                if (reason == TypeAction.FOLDED)
                {
                    php.setPlayerCards(Card.NO_CARD, Card.NO_CARD);
                }
                if (p.getMoneyBetAmnt() > 0)
                {
                    final JLabel bet = bets[p.getNoSeat()];
                    bet.setText("$" + p.getMoneyBetAmnt());
                }
            }
            
            @Override
            public void playerHoleCardsChanged(PlayerInfo p)
            {
                final JPanelPlayerHud php = huds[p.getNoSeat()];
                final Card[] cards = p.getCards(true);
                php.setPlayerCards(cards[0], cards[1]);
            }
            
            @Override
            public void playerJoined(PlayerInfo p)
            {
                final JPanelPlayerHud php = huds[p.getNoSeat()];
                installPlayer(php, p);
            }
            
            @Override
            public void playerLeaved(PlayerInfo p)
            {
                final JPanelPlayerHud php = huds[p.getNoSeat()];
                php.setVisible(false);
            }
            
            @Override
            public void playerMoneyChanged(PlayerInfo p)
            {
                final JPanelPlayerHud php = huds[p.getNoSeat()];
                php.setPlayerMoney(p.getMoneySafeAmnt());
            }
            
            @Override
            public void playerWonPot(PlayerInfo p, MoneyPot pot, int wonAmount)
            {
                final JPanelPlayerHud php = huds[p.getNoSeat()];
                php.setPlayerMoney(p.getMoneySafeAmnt());
                php.setHeaderColor(Color.cyan);
            }
            
            private void installPlayer(JPanelPlayerHud php, PlayerInfo player)
            {
                php.setPlayerName(player.getName());
                php.setPlayerInfo("");// TODO: RICK: Human or BOT
                php.setPlayerAction(TypeAction.NOTHING);
                final Card[] cards = player.getCards(true);
                php.setPlayerCards(cards[0], cards[1]);
                php.setPlayerMoney(player.getMoneySafeAmnt());
                php.setBackground(Color.white);
                php.setHeaderColor(Color.white);
                php.setVisible(true);
            }
        });
    }
    
    private void initializePokerObserverForConsole()
    {
        m_game.attach(new PokerGameAdapter()
        {
            
            @Override
            public void everythingEnded()
            {
                writeLine("==> Table closed");
            }
            
            @Override
            public void gameBettingRoundEnded(TypeRound r)
            {
                writeLine("==> End of " + r.name());
            }
            
            @Override
            public void gameBettingRoundStarted()
            {
                final TableInfo table = m_game.getTable();
                final TypeRound r = table.getRound();
                writeLine("==> Beginning of " + r.name());
                if (r != TypeRound.PREFLOP)
                {
                    write("==> Current board cards:");
                    for (int i = 0; i < 5 && table.getCards().get(i).getId() != Card.NO_CARD_ID; ++i)
                    {
                        write(" " + table.getCards().get(i).toString());
                    }
                    writeLine("");
                }
            }
            
            @Override
            public void gameBlindsNeeded()
            {
                writeLine("==> Game started");
                final TableInfo table = m_game.getTable();
                final PlayerInfo d = table.getPlayer(table.getNoSeatDealer());
                final PlayerInfo sb = table.getPlayer(table.getNoSeatSmallBlind());
                final PlayerInfo bb = table.getPlayer(table.getNoSeatBigBlind());
                writeLine("==> " + d.getName() + " is the Dealer");
                writeLine("==> " + sb.getName() + " is the SmallBlind");
                writeLine("==> " + bb.getName() + " is the BigBlind");
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
            public void playerActionNeeded(PlayerInfo p)
            {
                writeLine("Player turn began (" + p.getName() + ")");
            }
            
            @Override
            public void playerActionTaken(PlayerInfo p, TypeAction reason, int playedAmount)
            {
                writeLine(p.getName() + " did [" + reason.name() + "]");
            }
            
            @Override
            public void playerHoleCardsChanged(PlayerInfo p)
            {
                final Card[] cards = p.getCards(true);
                writeLine("==> Hole Card changed for " + p.getName() + ": " + cards[0].toString() + " " + cards[1].toString());
            }
            
            @Override
            public void playerJoined(PlayerInfo p)
            {
                writeLine(p.getName() + " joined the table");
            }
            
            @Override
            public void playerLeaved(PlayerInfo p)
            {
                writeLine(p.getName() + " left the table");
            }
            
            @Override
            public void playerMoneyChanged(PlayerInfo p)
            {
                writeLine(p.getName() + " money changed to " + p.getMoneySafeAmnt());
            }
            
            @Override
            public void playerWonPot(PlayerInfo p, MoneyPot pot, int wonAmount)
            {
                writeLine(p.getName() + " won pot ($" + wonAmount + ")");
            }
        });
    }
} // @jve:decl-index=0:visual-constraint="10,10"
