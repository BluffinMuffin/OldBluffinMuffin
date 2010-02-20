package newPokerLobbyServer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import utilGUI.ConsoleJPanel;

public class LobbyServerMainJFrame extends JFrame
{
    
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JPanel jTopPanel = null;
    private JLabel jStatusLabel = null;
    private ConsoleJPanel consoleJPanel = null;
    private JLabel jTitleLabel = null;
    private JToolBar jMainToolBar = null;
    private JPanel jRightPanel = null;
    private JLabel jTitleRightLabel = null;
    private JList jPlayersList = null;
    
    /**
     * This method initializes jRightPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJRightPanel()
    {
        if (jRightPanel == null)
        {
            jTitleRightLabel = new JLabel();
            jTitleRightLabel.setText("Players");
            jTitleRightLabel.setFont(new Font("Dialog", Font.BOLD, 14));
            jTitleRightLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jRightPanel = new JPanel();
            jRightPanel.setLayout(new BorderLayout());
            jRightPanel.setPreferredSize(new Dimension(150, 0));
            jRightPanel.add(jTitleRightLabel, BorderLayout.NORTH);
            jRightPanel.add(getJPlayersList(), BorderLayout.CENTER);
        }
        return jRightPanel;
    }
    
    /**
     * This method initializes jPlayersList
     * 
     * @return javax.swing.JList
     */
    private JList getJPlayersList()
    {
        if (jPlayersList == null)
        {
            jPlayersList = new JList();
        }
        return jPlayersList;
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
                final LobbyServerMainJFrame thisClass = new LobbyServerMainJFrame();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }
    
    /**
     * This is the default constructor
     */
    public LobbyServerMainJFrame()
    {
        super();
        initialize();
        pack();
        setLocationRelativeTo(null);
    }
    
    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize()
    {
        this.setSize(657, 241);
        this.setContentPane(getJContentPane());
        this.setTitle("JFrame");
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
            jStatusLabel = new JLabel();
            jStatusLabel.setText("Status");
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.setPreferredSize(new Dimension(657, 241));
            jContentPane.add(getJTopPanel(), BorderLayout.NORTH);
            jContentPane.add(jStatusLabel, BorderLayout.SOUTH);
            jContentPane.add(getConsoleJPanel(), BorderLayout.CENTER);
            jContentPane.add(getJRightPanel(), BorderLayout.EAST);
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
            jTitleLabel.setText("Poker Server Lobby 2.0");
            jTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jTitleLabel.setFont(new Font("Dialog", Font.BOLD, 18));
            jTopPanel = new JPanel();
            jTopPanel.setLayout(new BorderLayout());
            jTopPanel.add(jTitleLabel, BorderLayout.NORTH);
            jTopPanel.add(getJMainToolBar(), BorderLayout.CENTER);
        }
        return jTopPanel;
    }
    
    /**
     * This method initializes consoleJPanel
     * 
     * @return utilGUI.ConsoleJPanel
     */
    private ConsoleJPanel getConsoleJPanel()
    {
        if (consoleJPanel == null)
        {
            consoleJPanel = new ConsoleJPanel();
        }
        return consoleJPanel;
    }
    
    /**
     * This method initializes jMainToolBar
     * 
     * @return javax.swing.JToolBar
     */
    private JToolBar getJMainToolBar()
    {
        if (jMainToolBar == null)
        {
            jMainToolBar = new JToolBar();
            jMainToolBar.setPreferredSize(new Dimension(18, 20));
        }
        return jMainToolBar;
    }
    
} // @jve:decl-index=0:visual-constraint="10,10"
