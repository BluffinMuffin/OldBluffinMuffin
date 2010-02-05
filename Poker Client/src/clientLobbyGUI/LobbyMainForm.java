package clientLobbyGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class LobbyMainForm extends JFrame
{
    
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JLabel jTitleLabel = null;
    private JLabel jStatusLabel = null;
    private JPanel jMainPanel = null;
    private JToolBar jMainToolBar = null;
    private JToggleButton jConnectToggleButton = null;
    private JButton jAddTableButton = null;
    private JButton jRefreshButton = null;
    private JButton jJoinTableButton = null;
    private JButton jAdvancedButton = null;
    private JScrollPane jMainScrollPane = null;
    private JTable jMainTable = null;
    
    /**
     * This method initializes jMainPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJMainPanel()
    {
        if (jMainPanel == null)
        {
            jMainPanel = new JPanel();
            jMainPanel.setLayout(new BorderLayout());
            jMainPanel.add(getJMainToolBar(), BorderLayout.NORTH);
            jMainPanel.add(getJMainScrollPane(), BorderLayout.CENTER);
        }
        return jMainPanel;
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
            jMainToolBar.setPreferredSize(new Dimension(18, 25));
            jMainToolBar.setFloatable(false);
            jMainToolBar.add(getJConnectToggleButton());
            jMainToolBar.add(getJRefreshButton());
            jMainToolBar.add(getJAddTableButton());
            jMainToolBar.add(getJJoinTableButton());
            jMainToolBar.add(getJAdvancedButton());
        }
        return jMainToolBar;
    }
    
    /**
     * This method initializes jConnectToggleButton
     * 
     * @return javax.swing.JToggleButton
     */
    private JToggleButton getJConnectToggleButton()
    {
        if (jConnectToggleButton == null)
        {
            jConnectToggleButton = new JToggleButton();
            jConnectToggleButton.setText("Connect");
            jConnectToggleButton.addItemListener(new java.awt.event.ItemListener()
            {
                public void itemStateChanged(java.awt.event.ItemEvent e)
                {
                    if (jConnectToggleButton.isSelected())
                    {
                        final LobbyConnectForm form = new LobbyConnectForm(LobbyMainForm.this);
                        form.setVisible(true);
                        jStatusLabel.setText("Connected");
                        getJRefreshButton().setEnabled(true);
                        getJAddTableButton().setEnabled(true);
                        getJAdvancedButton().setEnabled(true);
                    }
                    else
                    {
                        jStatusLabel.setText("Not Connected");
                        getJRefreshButton().setEnabled(false);
                        getJAddTableButton().setEnabled(false);
                        getJJoinTableButton().setEnabled(false);
                        getJAdvancedButton().setEnabled(false);
                    }
                }
            });
        }
        return jConnectToggleButton;
    }
    
    /**
     * This method initializes jAddTableButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getJAddTableButton()
    {
        if (jAddTableButton == null)
        {
            jAddTableButton = new JButton();
            jAddTableButton.setText("Add Table");
            jAddTableButton.setEnabled(false);
        }
        return jAddTableButton;
    }
    
    /**
     * This method initializes jRefreshButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getJRefreshButton()
    {
        if (jRefreshButton == null)
        {
            jRefreshButton = new JButton();
            jRefreshButton.setText("Refresh");
            jRefreshButton.setEnabled(false);
        }
        return jRefreshButton;
    }
    
    /**
     * This method initializes jJoinTableButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getJJoinTableButton()
    {
        if (jJoinTableButton == null)
        {
            jJoinTableButton = new JButton();
            jJoinTableButton.setText("Join Table");
            jJoinTableButton.setEnabled(false);
        }
        return jJoinTableButton;
    }
    
    /**
     * This method initializes jAdvancedButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getJAdvancedButton()
    {
        if (jAdvancedButton == null)
        {
            jAdvancedButton = new JButton();
            jAdvancedButton.setText("Advanced");
            jAdvancedButton.setEnabled(false);
        }
        return jAdvancedButton;
    }
    
    /**
     * This method initializes jMainScrollPane
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJMainScrollPane()
    {
        if (jMainScrollPane == null)
        {
            jMainScrollPane = new JScrollPane();
            jMainScrollPane.setViewportView(getJMainTable());
        }
        return jMainScrollPane;
    }
    
    /**
     * This method initializes jMainTable
     * 
     * @return javax.swing.JTable
     */
    private JTable getJMainTable()
    {
        if (jMainTable == null)
        {
            jMainTable = new JTable();
        }
        return jMainTable;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                final LobbyMainForm thisClass = new LobbyMainForm();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }
    
    /**
     * This is the default constructor
     */
    public LobbyMainForm()
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
        this.setSize(337, 259);
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
            jStatusLabel.setText("Not Connected");
            jTitleLabel = new JLabel();
            jTitleLabel.setText("Client Lobby 2.0");
            jTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jTitleLabel.setFont(new Font("Dialog", Font.BOLD, 18));
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(jTitleLabel, BorderLayout.NORTH);
            jContentPane.add(jStatusLabel, BorderLayout.SOUTH);
            jContentPane.add(getJMainPanel(), BorderLayout.CENTER);
        }
        return jContentPane;
    }
    
} // @jve:decl-index=0:visual-constraint="10,10"
