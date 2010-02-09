package clientGameGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.SystemColor;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import pokerLogic.PokerPlayerAction;
import pokerLogic.PokerPlayerInfo;
import pokerLogic.PokerTableInfo;
import pokerLogic.TypePlayerAction;
import pokerLogic.TypePokerRound;
import utilGUI.JBackgroundPanel;
import utility.IClosingListener;
import clientGame.ClientPokerTableInfo;
import clientGameTools.ClientPokerAdapter;
import clientGameTools.ClientPokerObserver;
import clientGameTools.IClientPoker;

public class GameTableJFrame extends JFrame implements IClientPoker
{
    protected ClientPokerObserver m_pokerObserver;
    protected ClientPokerTableInfo m_table = null;
    
    /**
     * This method initializes jCenterPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJCenterPanel()
    {
        if (jCenterPanel == null)
        {
            jCenterPanel = new JBackgroundPanel();
            jCenterPanel.setBackground(Color.black);
            jCenterPanel.setBackground(new ImageIcon("images/table.png"));
            jCenterPanel.setLayout(new GridBagLayout());
            jCenterPanel.setSize(new Dimension(874, 556));
            jCenterPanel.setLocation(new Point(0, 50));
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
            jRightPanel.setLayout(null);
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
     * @param args
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                final GameTableJFrame thisClass = new GameTableJFrame();
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
    
    /**
     * This is the default constructor
     */
    public GameTableJFrame()
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
        pack();
        setLocationRelativeTo(null);
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
        // getJLogTextArea().setCaretPosition(getJLogTextArea().getText().length());
    }
    
    public void setPokerObserver(ClientPokerObserver observer)
    {
        m_pokerObserver = observer;
        initializePokerObserverForConsole();
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
    
    @Override
    public void addClosingListener(IClosingListener<IClientPoker> pListener)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void removeClosingListener(IClosingListener<IClientPoker> pListener)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void setTable(PokerTableInfo pTable)
    {
        m_table = (ClientPokerTableInfo) pTable;
        writeLine("******** LOGGED AS " + m_table.m_localPlayer.getName() + " ********");
    }
    
    @Override
    public void start()
    {
        this.setVisible(true);
    }
    
    @Override
    public void stop()
    {
        this.setVisible(false);
        
    }
    
    @Override
    public void run()
    {
        // TODO Auto-generated method stub
        
    }
} // @jve:decl-index=0:visual-constraint="10,10"
