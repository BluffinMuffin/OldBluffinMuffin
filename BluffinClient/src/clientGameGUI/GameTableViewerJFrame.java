package clientGameGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import pokerLogic.PokerPlayerAction;
import pokerLogic.PokerPlayerInfo;
import pokerLogic.PokerTableInfo;
import pokerLogic.TypePlayerAction;
import pokerLogic.TypePokerRound;
import utilGUI.JBackgroundPanel;
import clientGameTools.ClientPokerAdapter;
import clientGameTools.ClientPokerObserver;

public class GameTableViewerJFrame extends GameTableAbstractJFrame
{
    private boolean m_gotoCaret = true;
    
    /**
     * This method initializes jCenterPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJCenterPanel()
    {
        if (jCenterPanel == null)
        {
            jCenterPanel = new JBackgroundPanel(new ImageIcon("images/table.png"));
            jCenterPanel.setLocation(new Point(0, 50));
            jCenterPanel.setSize(new Dimension(465, 222));
            // jCenterPanel.add(getJButton());
        }
        return jCenterPanel;
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
     * This method initializes jHud1Panel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJHud1Panel()
    {
        if (jHud1Panel == null)
        {
            jHud1Panel = new JPanel();
            jHud1Panel.setLayout(new GridBagLayout());
            jHud1Panel.setBounds(new Rectangle(18, 117, 141, 111));
        }
        return jHud1Panel;
    }
    
    /**
     * This method initializes jHud1Panel1
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJHud1Panel1()
    {
        if (jHud1Panel1 == null)
        {
            jHud1Panel1 = new JPanel();
            jHud1Panel1.setLayout(new GridBagLayout());
            jHud1Panel1.setLocation(new Point(16, 250));
            jHud1Panel1.setSize(new Dimension(141, 111));
        }
        return jHud1Panel1;
    }
    
    /**
     * This method initializes jHud1Panel11
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJHud1Panel11()
    {
        if (jHud1Panel11 == null)
        {
            jHud1Panel11 = new JPanel();
            jHud1Panel11.setLayout(new GridBagLayout());
            jHud1Panel11.setLocation(new Point(343, 30));
            jHud1Panel11.setSize(new Dimension(141, 111));
        }
        return jHud1Panel11;
    }
    
    /**
     * This method initializes jHud1Panel12
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJHud1Panel12()
    {
        if (jHud1Panel12 == null)
        {
            jHud1Panel12 = new JPanel();
            jHud1Panel12.setLayout(new GridBagLayout());
            jHud1Panel12.setLocation(new Point(170, 35));
            jHud1Panel12.setSize(new Dimension(141, 111));
        }
        return jHud1Panel12;
    }
    
    /**
     * This method initializes jHud1Panel13
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJHud1Panel13()
    {
        if (jHud1Panel13 == null)
        {
            jHud1Panel13 = new JPanel();
            jHud1Panel13.setLayout(new GridBagLayout());
            jHud1Panel13.setLocation(new Point(503, 30));
            jHud1Panel13.setSize(new Dimension(141, 111));
        }
        return jHud1Panel13;
    }
    
    /**
     * This method initializes jHud1Panel14
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJHud1Panel14()
    {
        if (jHud1Panel14 == null)
        {
            jHud1Panel14 = new JPanel();
            jHud1Panel14.setLayout(new GridBagLayout());
            jHud1Panel14.setLocation(new Point(659, 123));
            jHud1Panel14.setSize(new Dimension(141, 111));
        }
        return jHud1Panel14;
    }
    
    /**
     * This method initializes jHud1Panel15
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJHud1Panel15()
    {
        if (jHud1Panel15 == null)
        {
            jHud1Panel15 = new JPanel();
            jHud1Panel15.setLayout(new GridBagLayout());
            jHud1Panel15.setLocation(new Point(334, 352));
            jHud1Panel15.setSize(new Dimension(141, 111));
        }
        return jHud1Panel15;
    }
    
    /**
     * This method initializes jHud1Panel16
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJHud1Panel16()
    {
        if (jHud1Panel16 == null)
        {
            jHud1Panel16 = new JPanel();
            jHud1Panel16.setLayout(new GridBagLayout());
            jHud1Panel16.setLocation(new Point(662, 270));
            jHud1Panel16.setSize(new Dimension(141, 111));
        }
        return jHud1Panel16;
    }
    
    /**
     * This method initializes jHud1Panel17
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJHud1Panel17()
    {
        if (jHud1Panel17 == null)
        {
            jHud1Panel17 = new JPanel();
            jHud1Panel17.setLayout(new GridBagLayout());
            jHud1Panel17.setLocation(new Point(165, 351));
            jHud1Panel17.setSize(new Dimension(141, 111));
        }
        return jHud1Panel17;
    }
    
    /**
     * This method initializes jHud1Panel18
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJHud1Panel18()
    {
        if (jHud1Panel18 == null)
        {
            jHud1Panel18 = new JPanel();
            jHud1Panel18.setLayout(new GridBagLayout());
            jHud1Panel18.setLocation(new Point(496, 351));
            jHud1Panel18.setSize(new Dimension(141, 111));
        }
        return jHud1Panel18;
    }
    
    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButton()
    {
        if (jButton == null)
        {
            jButton = new JButton();
        }
        return jButton;
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
                final GameTableViewerJFrame thisClass = new GameTableViewerJFrame();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }
    
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JBackgroundPanel jCenterPanel = null;
    private JPanel jTopPanel = null;
    private JPanel jRightPanel = null;
    private JPanel jBottomPanel = null;
    private JLabel jTitleLabel = null;
    private JScrollPane jLogScrollPane = null;
    private JTextArea jLogTextArea = null; // @jve:decl-index=0:visual-constraint="1045,149"
    private JToolBar jBottomToolBar = null;
    private JToggleButton jLockToggleButton = null;
    private JPanel jHud1Panel = null;
    private JPanel jHud1Panel1 = null;
    private JPanel jHud1Panel11 = null;
    private JPanel jHud1Panel12 = null;
    private JPanel jHud1Panel13 = null;
    private JPanel jHud1Panel14 = null;
    private JPanel jHud1Panel15 = null;
    private JPanel jHud1Panel16 = null;
    private JPanel jHud1Panel17 = null;
    private JPanel jHud1Panel18 = null;
    private JButton jButton = null;
    
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
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.setMinimumSize(new Dimension(1024, 768));
            jContentPane.setMaximumSize(new Dimension(1024, 768));
            jContentPane.setPreferredSize(new Dimension(1024, 768));
            jContentPane.add(getJCenterPanel(), null);
            jContentPane.add(getJTopPanel(), null);
            jContentPane.add(getJRightPanel(), null);
            jContentPane.add(getJBottomPanel(), null);
        }
        return jContentPane;
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
                // TODO Auto-generated method stub
                super.gameEnded();
            }
            
            @Override
            public void gameStarted(PokerPlayerInfo oldDealer, PokerPlayerInfo oldSmallBlind, PokerPlayerInfo oldBigBlind)
            {
                // TODO Auto-generated method stub
                super.gameStarted(oldDealer, oldSmallBlind, oldBigBlind);
            }
            
            @Override
            public void playerCardChanged(PokerPlayerInfo player)
            {
                // TODO Auto-generated method stub
                super.playerCardChanged(player);
            }
            
            @Override
            public void playerJoined(PokerPlayerInfo player)
            {
                // TODO Auto-generated method stub
                super.playerJoined(player);
            }
            
            @Override
            public void playerLeft(PokerPlayerInfo player)
            {
                // TODO Auto-generated method stub
                super.playerLeft(player);
            }
            
            @Override
            public void playerMoneyChanged(PokerPlayerInfo player, int oldMoneyAmount)
            {
                // TODO Auto-generated method stub
                super.playerMoneyChanged(player, oldMoneyAmount);
            }
            
            @Override
            public void playerTurnBegan(PokerPlayerInfo player)
            {
                // TODO Auto-generated method stub
                super.playerTurnBegan(player);
            }
            
            @Override
            public void playerTurnEnded(PokerPlayerInfo player, TypePlayerAction action, int actionAmount)
            {
                // TODO Auto-generated method stub
                super.playerTurnEnded(player, action, actionAmount);
            }
            
            @Override
            public void potWon(PokerPlayerInfo player, int potAmountWon, int potIndex)
            {
                // TODO Auto-generated method stub
                super.potWon(player, potAmountWon, potIndex);
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
                // TODO Auto-generated method stub
                super.tableInfos();
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
