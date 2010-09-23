package bluffinmuffin.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import bluffinmuffin.gui.game.JFrameTable;
import bluffinmuffin.gui.lobby.JDialogAddTable;
import bluffinmuffin.gui.lobby.JDialogNameUsed;
import bluffinmuffin.protocol.GameTCPClient;
import bluffinmuffin.protocol.LobbyTCPClient;
import bluffinmuffin.protocol.TupleTableInfo;

public class ClientLobby extends JFrame
{
    private LobbyTCPClient m_server;
    
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JLabel jTitleLabel = null;
    private JLabel jStatusLabel = null;
    private JPanel jMainPanel = null;
    private JToolBar jMainToolBar = null;
    private JButton jAddTableButton = null;
    private JButton jRefreshButton = null;
    private JButton jJoinTableButton = null;
    private JScrollPane jMainScrollPane = null;
    private JTable jMainTable = null;
    private JButton jConnectButton = null;
    private JButton jLeaveTableButton = null;
    
    /**
     * This method initializes jLeaveTableButton1
     * 
     * @return javax.swing.JButton
     */
    private JButton getJLeaveTableButton()
    {
        if (jLeaveTableButton == null)
        {
            jLeaveTableButton = new JButton();
            jLeaveTableButton.setEnabled(false);
            jLeaveTableButton.setText("Leave Table");
            jLeaveTableButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    final GameTCPClient client = findClient();
                    eventLeaveTable(client);
                }
            });
        }
        return jLeaveTableButton;
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
                final ClientLobby thisClass = new ClientLobby();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }
    
    /**
     * This is the default constructor
     */
    public ClientLobby()
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
        this.setTitle(jTitleLabel.getText());
        this.setSize(new Dimension(443, 169));
        pack();
        setLocationRelativeTo(null);
    }
    
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
            jMainToolBar.add(getJConnectButton());
            jMainToolBar.add(getJRefreshButton());
            jMainToolBar.add(getJAddTableButton());
            jMainToolBar.add(getJJoinTableButton());
            jMainToolBar.add(getJLeaveTableButton());
        }
        return jMainToolBar;
    }
    
    private void eventAddTable()
    {
        final JDialogAddTable form = new JDialogAddTable(ClientLobby.this, m_server.getPlayerName(), 1);
        form.setVisible(true);
        if (form.isOK())
        {
            final int noPort = m_server.createTable(form.getTableName(), form.getBigBlind(), form.getNbPlayer(), form.getWaitingTimeAfterPlayerAction(), form.getWaitingTimeAfterBoardDealed(), form.getWaitingTimeAfterPotWon(), form.getLimit(), form.getStartingMoney());
            
            if (noPort != -1)
            {
                joinTable(noPort, form.getTableName(), form.getBigBlind());
                refreshTables();
            }
            else
            {
                System.out.println("Cannot create table: '" + form.getTableName() + "'");
            }
        }
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
            jAddTableButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    
                    eventAddTable();
                }
            });
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
            jRefreshButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    refreshTables();
                }
            });
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
            jJoinTableButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    eventJoinTable();
                }
            });
        }
        return jJoinTableButton;
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
    
    public void allowJoinOrLeave()
    {
        final GameTCPClient client = findClient();
        if (client != null)
        {
            getJJoinTableButton().setEnabled(false);
            getJLeaveTableButton().setEnabled(true);
        }
        else
        {
            getJJoinTableButton().setEnabled(true);
            getJLeaveTableButton().setEnabled(false);
        }
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
            final String[] columnsName = new String[] { "ID", "Name", "Game Type", "Big Blind", "Nb. Players" };
            
            final DefaultTableModel defaultTableModel = new DefaultTableModel(columnsName, 0)
            {
                /**
                 * 
                 */
                private static final long serialVersionUID = 1L;
                
                @Override
                public boolean isCellEditable(int row, int column)
                {
                    return false;
                }
            };
            
            jMainTable = new JTable();
            jMainTable.setAutoCreateColumnsFromModel(true);
            jMainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            jMainTable.setShowGrid(true);
            jMainTable.setModel(defaultTableModel);
            jMainTable.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    if (e.getClickCount() == 1)
                    {
                        allowJoinOrLeave();
                    }
                    else if (e.getClickCount() == 2)
                    {
                        eventJoinTable();
                    }
                }
            });
        }
        return jMainTable;
    }
    
    private void eventConnect()
    {
        final JDialogConnect form = new JDialogConnect(ClientLobby.this);
        form.setVisible(true);
        if (form.isOK())
        {
            m_server = new LobbyTCPClient(form.getServerAddress(), form.getServerPort());
            
            if (m_server.connect())
            {
                m_server.start();
                // Authentify the user.
                boolean isOk = m_server.identify(form.getPlayerName());
                while (!isOk)
                {
                    final JDialogNameUsed form2 = new JDialogNameUsed(ClientLobby.this, m_server.getPlayerName());
                    form2.setVisible(true);
                    isOk = m_server.identify(form2.getPlayerName());
                }
                
                jStatusLabel.setText("Connected as " + m_server.getPlayerName() + " to " + m_server.getServerAddress() + ":" + m_server.getServerPort());
                getJRefreshButton().setEnabled(true);
                getJAddTableButton().setEnabled(true);
                getJConnectButton().setText("Disconnect");
                setTitle(m_server.getPlayerName() + " - " + jTitleLabel.getText());
                refreshTables();
                if (getJMainTable().getModel().getRowCount() == 0)
                {
                    eventAddTable();
                }
            }
            else
            {
                jStatusLabel.setText("Not connected, Authentification Failed!!!");
                System.out.println("Authentification Failed!!!");
            }
        }
    }
    
    /**
     * This method initializes jConnectButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getJConnectButton()
    {
        if (jConnectButton == null)
        {
            jConnectButton = new JButton();
            jConnectButton.setText("Connect");
            jConnectButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    if (m_server != null && m_server.isConnected())
                    {
                        
                        // Close the socket
                        if (m_server != null)
                        {
                            m_server.disconnect();
                        }
                        m_server = null;
                        
                        final DefaultTableModel model = (DefaultTableModel) getJMainTable().getModel();
                        model.setRowCount(0);
                        jStatusLabel.setText("Not Connected");
                        setTitle(jTitleLabel.getText());
                        getJRefreshButton().setEnabled(false);
                        getJAddTableButton().setEnabled(false);
                        getJJoinTableButton().setEnabled(false);
                        getJConnectButton().setText("Connect");
                    }
                    else
                    {
                        // Eh bien on connecte
                        eventConnect();
                    }
                }
            });
        }
        return jConnectButton;
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
            jTitleLabel.setText("Poker Client Lobby 2.0");
            jTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jTitleLabel.setFont(new Font("Dialog", Font.BOLD, 18));
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.setPreferredSize(new Dimension(423, 129));
            jContentPane.add(jTitleLabel, BorderLayout.NORTH);
            jContentPane.add(jStatusLabel, BorderLayout.SOUTH);
            jContentPane.add(getJMainPanel(), BorderLayout.CENTER);
        }
        return jContentPane;
    }
    
    /**
     * Refresh the list of available tables on the ServerLobby.
     */
    public void refreshTables()
    {
        final DefaultTableModel model = (DefaultTableModel) getJMainTable().getModel();
        model.setRowCount(0);
        
        for (final TupleTableInfo info : m_server.getListTables())
        {
            final Object[] row = new Object[5];
            row[0] = info.m_noPort;
            row[1] = info.m_tableName;
            row[2] = info.m_limit.name();
            row[3] = info.m_bigBlind;
            row[4] = info.m_nbPlayers + "/" + info.m_nbSeats;
            // Add the table infos to the JTable of available tables.
            model.addRow(row);
        }
        // Select the first available table in the JTable.
        getJMainTable().getSelectedRow();
        final DefaultListSelectionModel selection = new DefaultListSelectionModel();
        selection.setSelectionInterval(0, 0);
        
        if ((model.getRowCount() > 0) && (getJMainTable().getSelectedRow() == -1))
        {
            getJMainTable().setSelectionModel(selection);
            allowJoinOrLeave();
        }
        else
        {
            getJJoinTableButton().setEnabled(false);
            getJLeaveTableButton().setEnabled(false);
        }
    }
    
    /**
     * Join the table specified by is port number.
     * 
     * @param p_noPort
     *            - Port number associated to the table.
     * @param p_tableName
     *            - <b>Depricated</b>
     * @param p_bigBlindAmount
     *            - <b>Depricated</b>
     * 
     * @return
     *         <b>true</b> if the user correctly joined the table. <br>
     *         <b>false</b> if no seat is free, someone with the same name
     *         has already joined this table, or the table does not exist.
     */
    public boolean joinTable(int p_noPort, String p_tableName, int p_bigBlindAmount)
    {
        JFrameTable gui = null;
        gui = new JFrameTable();
        final GameTCPClient tcpGame = m_server.joinTable(p_noPort, p_tableName, gui);
        
        gui.addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent winEvt)
            {
                eventLeaveTable(tcpGame);
            }
        });
        
        return tcpGame != null;
    }
    
    private GameTCPClient findClient()
    {
        if (getJMainTable().getSelectionModel().isSelectionEmpty())
        {
            return null;
        }
        
        final int index = getJMainTable().getSelectedRow();
        final int noPort = (Integer) getJMainTable().getModel().getValueAt(index, 0);
        return m_server.findClient(noPort);
    }
    
    private void eventJoinTable()
    {
        if (getJMainTable().getSelectionModel().isSelectionEmpty())
        {
            return;
        }
        
        final int index = getJMainTable().getSelectedRow();
        final int noPort = (Integer) getJMainTable().getModel().getValueAt(index, 0);
        final String tableName = (String) getJMainTable().getModel().getValueAt(index, 1);
        
        if (findClient() != null)
        {
            System.out.println("You are already sitting on the table: " + tableName);
        }
        else
        {
            final int bigBlind = (Integer) getJMainTable().getModel().getValueAt(index, 3);
            if (!joinTable(noPort, tableName, bigBlind))
            {
                System.out.println("Table '" + tableName + "' does not exist anymore.");
            }
        }
        refreshTables();
    }
    
    private void eventLeaveTable(GameTCPClient client)
    {
        if (client != null)
        {
            client.disconnect();
            refreshTables();
        }
    }
    
} // @jve:decl-index=0:visual-constraint="10,10"
