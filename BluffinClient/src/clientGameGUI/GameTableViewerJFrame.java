package clientGameGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import pokerLogic.Card;
import pokerLogic.PokerPlayerAction;
import pokerLogic.PokerPlayerInfo;
import pokerLogic.PokerTableInfo;
import pokerLogic.TypePlayerAction;
import pokerLogic.TypePokerRound;
import clientGameTools.ClientPokerAdapter;
import clientGameTools.ClientPokerObserver;

public class GameTableViewerJFrame extends GameTableAbstractJFrame
{
    private final PlayerHudJPanel[] huds = new PlayerHudJPanel[10];
    private boolean m_gotoCaret = true;
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
    private JPanel jBottomPanel = null;
    private JLabel jTitleLabel = null;
    private JScrollPane jLogScrollPane = null;
    private JTextArea jLogTextArea = null; // @jve:decl-index=0:visual-constraint="1045,149"
    private JToolBar jBottomToolBar = null;
    private JToggleButton jLockToggleButton = null;
    private JLabel jDummyLabel = null;
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
        this.setTitle("Poker Table 2.0 (Viewer only)");
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
            jDummyLabel = new JLabel();
            jDummyLabel.setBounds(new Rectangle(0, 50, 874, 556));
            jDummyLabel.setIcon(new ImageIcon("images/table.png"));
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.setMinimumSize(new Dimension(1024, 768));
            jContentPane.setMaximumSize(new Dimension(1024, 768));
            jContentPane.setPreferredSize(new Dimension(1024, 768));
            jContentPane.add(getJTopPanel(), null);
            jContentPane.add(getJRightPanel(), null);
            jContentPane.add(getJBottomPanel(), null);
            jContentPane.add(getJTablePanel(), null);
            jContentPane.add(jDummyLabel, null);
        }
        return jContentPane;
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
            jTitleLabel.setText("Poker Table 2.0 (Viewer only)");
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
    private JPanel getJRightPanel()
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
    private JPanel getJBottomPanel()
    {
        if (jBottomPanel == null)
        {
            jBottomPanel = new JPanel();
            jBottomPanel.setLayout(new BorderLayout());
            jBottomPanel.setSize(new Dimension(1017, 132));
            jBottomPanel.setMaximumSize(new Dimension(874, 132));
            jBottomPanel.setMinimumSize(new Dimension(874, 132));
            jBottomPanel.setPreferredSize(new Dimension(874, 132));
            jBottomPanel.setBackground(Color.black);
            jBottomPanel.setLocation(new Point(0, 606));
            jBottomPanel.add(getJLogScrollPane(), BorderLayout.CENTER);
            jBottomPanel.add(getJBottomToolBar(), BorderLayout.NORTH);
        }
        return jBottomPanel;
    }
    
    /**
     * This method initializes jLogScrollPane
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJLogScrollPane()
    {
        if (jLogScrollPane == null)
        {
            jLogScrollPane = new JScrollPane(getJLogTextArea());
        }
        return jLogScrollPane;
    }
    
    /**
     * This method initializes jLogTextArea
     * 
     * @return javax.swing.JTextArea
     */
    private JTextArea getJLogTextArea()
    {
        if (jLogTextArea == null)
        {
            jLogTextArea = new JTextArea();
            jLogTextArea.setBackground(SystemColor.controlDkShadow);
            jLogTextArea.setEditable(false);
            jLogTextArea.setForeground(Color.white);
            jLogTextArea.setWrapStyleWord(true);
            jLogTextArea.setFont(new Font("Dialog", Font.BOLD, 12));
        }
        return jLogTextArea;
    }
    
    /**
     * This method initializes jBottomToolBar
     * 
     * @return javax.swing.JToolBar
     */
    private JToolBar getJBottomToolBar()
    {
        if (jBottomToolBar == null)
        {
            jBottomToolBar = new JToolBar();
            jBottomToolBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            jBottomToolBar.setPreferredSize(new Dimension(18, 20));
            jBottomToolBar.setFloatable(false);
            jBottomToolBar.add(getJLockToggleButton());
        }
        return jBottomToolBar;
    }
    
    /**
     * This method initializes jLockToggleButton
     * 
     * @return javax.swing.JToggleButton
     */
    private JToggleButton getJLockToggleButton()
    {
        if (jLockToggleButton == null)
        {
            jLockToggleButton = new JToggleButton();
            jLockToggleButton.setText("Lock");
            jLockToggleButton.addItemListener(new java.awt.event.ItemListener()
            {
                public void itemStateChanged(java.awt.event.ItemEvent e)
                {
                    m_gotoCaret = !jLockToggleButton.isSelected();
                }
            });
        }
        return jLockToggleButton;
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
            jTablePanel = new JPanel();
            jTablePanel.setLayout(null);
            jTablePanel.setBounds(new Rectangle(0, 50, 874, 556));
            jTablePanel.setOpaque(false);
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
        getJLogTextArea().append(line + "\n");
        if (m_gotoCaret)
        {
            getJLogTextArea().setCaretPosition(getJLogTextArea().getText().length());
        }
    }
    
    @Override
    public void setTable(PokerTableInfo pTable)
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
                // TODO Auto-generated method stub
                super.boardChanged(boardCardIndices);
            }
            
            @Override
            public void gameEnded()
            {
                m_last_NiceHud = -1;
                for (int i = 0; i < m_table.getPlayers().size(); ++i)
                {
                    final PlayerHudJPanel php = huds[m_table.getPlayer(i).m_noSeat];
                    if (m_table.getPlayer(i).m_money == 0)
                    {
                        php.setBackground(Color.gray);
                        php.setHeaderColor(Color.gray);
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
            public void gameStarted(PokerPlayerInfo oldDealer, PokerPlayerInfo oldSmallBlind, PokerPlayerInfo oldBigBlind)
            {
                if (oldDealer != null)
                {
                    huds[oldDealer.m_noSeat].setNotDealer();
                }
                if (oldSmallBlind != null)
                {
                    huds[oldSmallBlind.m_noSeat].setNoBlind();
                }
                if (oldBigBlind != null)
                {
                    huds[oldBigBlind.m_noSeat].setNoBlind();
                }
                huds[m_table.m_noSeatDealer].setDealer();
                huds[m_table.m_noSeatSmallBlind].setSmallBlind();
                huds[m_table.m_noSeatBigBlind].setBigBlind();
            }
            
            @Override
            public void playerCardChanged(PokerPlayerInfo player)
            {
                final PlayerHudJPanel php = huds[player.m_noSeat];
                php.setPlayerCards(player.getHand()[0], player.getHand()[1]);
            }
            
            @Override
            public void playerJoined(PokerPlayerInfo player)
            {
                final PlayerHudJPanel php = huds[player.m_noSeat];
                intallPlayer(php, player);
            }
            
            private void intallPlayer(PlayerHudJPanel php, PokerPlayerInfo player)
            {
                php.setPlayerName(player.getName());
                php.setPlayerInfo("");// TODO: Human or BOT
                php.setPlayerAction(TypePlayerAction.NOTHING);
                php.setPlayerCards(Card.getInstance().get(Card.HIDDEN_CARD), Card.getInstance().get(Card.HIDDEN_CARD));
                php.setPlayerMoney(player.m_money);
                php.setBackground(Color.white);
                php.setHeaderColor(Color.white);
                php.setVisible(true);
            }
            
            @Override
            public void playerLeft(PokerPlayerInfo player)
            {
                final PlayerHudJPanel php = huds[player.m_noSeat];
                php.setVisible(false);
            }
            
            @Override
            public void playerMoneyChanged(PokerPlayerInfo player, int oldMoneyAmount)
            {
            }
            
            @Override
            public void playerTurnBegan(PokerPlayerInfo player)
            {
                if (m_last_NiceHud >= 0)
                {
                    final PlayerHudJPanel php = huds[m_last_NiceHud];
                    php.setHeaderColor(Color.white);
                }
                if (player != null)
                {
                    final PlayerHudJPanel php = huds[player.m_noSeat];
                    if (player == m_table.m_localPlayer)
                    {
                        php.setHeaderColor(Color.green);
                    }
                    else
                    {
                        php.setHeaderColor(new Color(102, 255, 102));
                    }
                    m_last_NiceHud = player.m_noSeat;
                }
            }
            
            @Override
            public void playerTurnEnded(PokerPlayerInfo player, TypePlayerAction action, int actionAmount)
            {
                // TODO Auto-generated method stub
                super.playerTurnEnded(player, action, actionAmount);
                
                final PlayerHudJPanel php = huds[player.m_noSeat];
                php.setPlayerMoney(player.m_money);
                php.setPlayerAction(action);
            }
            
            @Override
            public void potWon(PokerPlayerInfo player, int potAmountWon, int potIndex)
            {
                final PlayerHudJPanel php = huds[player.m_noSeat];
                php.setHeaderColor(Color.cyan);
            }
            
            @Override
            public void tableClosed()
            {
                // TODO Auto-generated method stub
                super.tableClosed();
            }
            
            @Override
            public void tableInfos()
            {
                for (int i = 0; i < m_table.getPlayers().size(); ++i)
                {
                    final PlayerHudJPanel php = huds[m_table.getPlayer(i).m_noSeat];
                    intallPlayer(php, m_table.getPlayer(i));
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
                    sb.append(m_table.getBoard()[i].getCode());
                }
                writeLine(sb.toString());
            }
            
            @Override
            public void gameEnded()
            {
                writeLine("==> End of the Game");
            }
            
            @Override
            public void gameStarted(PokerPlayerInfo oldDealer, PokerPlayerInfo oldSmallBlind, PokerPlayerInfo oldBigBlind)
            {
                writeLine("==> Game started");
                writeLine("==> " + m_table.m_dealer.getName() + " is the Dealer");
                writeLine("==> " + m_table.m_smallBlind.getName() + " is the SmallBlind");
                writeLine("==> " + m_table.m_bigBlind.getName() + " is the BigBlind");
            }
            
            @Override
            public void playerCardChanged(PokerPlayerInfo player)
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
            public void playerJoined(PokerPlayerInfo player)
            {
                writeLine(player.getName() + " joined the table");
            }
            
            @Override
            public void playerLeft(PokerPlayerInfo player)
            {
                writeLine(player.getName() + " left the table");
            }
            
            @Override
            public void playerMoneyChanged(PokerPlayerInfo player, int oldMoneyAmount)
            {
                writeLine(player.getName() + " money changed");
            }
            
            @Override
            public void playerTurnBegan(PokerPlayerInfo player)
            {
                // writeLine("Player turn began");
            }
            
            @Override
            public void playerTurnEnded(PokerPlayerInfo player, TypePlayerAction action, int actionAmount)
            {
                final PokerPlayerAction ppa = new PokerPlayerAction(action, actionAmount);
                writeLine(player.getName() + " " + ppa);
            }
            
            @Override
            public void potWon(PokerPlayerInfo player, int potAmountWon, int potIndex)
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
} // @jve:decl-index=0:visual-constraint="10,10"
