package pokerClientAILobbyGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;

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

import newPokerClientAI.FactoryPokerAI;
import newPokerClientAI.PokerAISVM;
import newPokerClientAI.TypePokerAI;
import pokerClientSide.ClientSidePokerTcpServer;
import pokerGameGUI.GameTableViewerJFrame;
import pokerLobbyGUI.LobbyAddTableJDialog;
import pokerLobbyGUI.LobbyNameUsedJDialog;
import pokerLogic.OldTypePokerGame;
import protocolLobby.LobbyConnectCommand;
import protocolLobby.LobbyCreateTableCommand;
import protocolLobby.LobbyDisconnectCommand;
import protocolLobby.LobbyJoinTableCommand;
import protocolLobby.LobbyListTableCommand;
import protocolLobbyTools.SummaryTableInfo;
import protocolTools.IPokerCommand;
import utility.Constants;
import clientStats.StatsAgent;

public class LobbyAIMainJFrame extends JFrame
{
    private Socket m_connection = null; // @jve:decl-index=0:
    private PrintWriter m_toServer = null;
    private BufferedReader m_fromServer = null; // @jve:decl-index=0:
    
    private String m_playerName;
    private List<TupleAISummary> m_AIs = new ArrayList<TupleAISummary>(); // @jve:decl-index=0:
    private String m_serverAddress;
    private int m_serverPort;
    private TypePokerAI m_agentType;
    private boolean m_viewer;
    
    // List of PokerClient (one for each table the player joined)
    private final List<ClientSidePokerTcpServer> m_clients = new ArrayList<ClientSidePokerTcpServer>();
    
    // List of default observers that will be attached the player.
    // private final AutoListModel<TypeObserver> m_generalObservers = new AutoListModel<TypeObserver>(); // @jve:decl-index=0:
    private final List<LobbyAIMainJFrame> m_agents = new ArrayList<LobbyAIMainJFrame>(); // @jve:decl-index=0:
    private final TreeMap<Integer, SummaryTableInfo> m_tables = new TreeMap<Integer, SummaryTableInfo>();
    
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
     * This method initializes jLeaveTableButton
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
                    if (!getJMainTable().getSelectionModel().isSelectionEmpty())
                    {
                        
                        final int index = getJMainTable().getSelectedRow();
                        final int noPort = (Integer) getJMainTable().getModel().getValueAt(index, 0);
                        final SummaryTableInfo info = m_tables.get(noPort);
                        for (int i = 0; i < m_agents.size(); ++i)
                        {
                            final ClientSidePokerTcpServer client = m_agents.get(i).findClient(info.m_noPort);
                            if (client != null)
                            {
                                client.disconnect();
                            }
                        }
                        refreshTables();
                    }
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
                final LobbyAIMainJFrame thisClass = new LobbyAIMainJFrame();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }
    
    /**
     * This is the default constructor
     */
    public LobbyAIMainJFrame()
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
        this.setSize(new Dimension(462, 242));
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
                    
                    final LobbyAddTableJDialog form = new LobbyAddTableJDialog(LobbyAIMainJFrame.this, m_playerName, m_AIs.size());
                    form.setVisible(true);
                    if (form.isOK())
                    {
                        final int noPort = createTable(form.getTableName(), form.getBigBlind(), form.getNbPlayer());
                        
                        if (noPort != -1)
                        {
                            for (int i = 0; i < m_agents.size(); ++i)
                            {
                                m_agents.get(i).joinTable(noPort, form.getTableName(), form.getBigBlind());
                            }
                            refreshTables();
                        }
                        else
                        {
                            System.out.println("Cannot create table: '" + form.getTableName() + "'");
                        }
                    }
                }
            });
        }
        return jAddTableButton;
    }
    
    protected void setPlayerName(String mAIName)
    {
        m_playerName = mAIName;
        
    }
    
    protected void setAgentType(TypePokerAI mAIType)
    {
        m_agentType = mAIType;
        
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
                        allowJoinAndLeave();
                    }
                    else if (e.getClickCount() == 2)
                    {
                        if (canJoin())
                        {
                            eventJoinTable();
                        }
                    }
                }
            });
        }
        return jMainTable;
    }
    
    public void disconnect()
    {
        // Alors on disconnect
        send(new LobbyDisconnectCommand());
        
        // Close the socket
        if (m_connection != null)
        {
            try
            {
                m_connection.close();
            }
            catch (final IOException e1)
            {
                e1.printStackTrace();
            }
        }
        m_connection = null;
        m_toServer = null;
        m_fromServer = null;
        
        final DefaultTableModel model = (DefaultTableModel) getJMainTable().getModel();
        model.setRowCount(0);
        // Disconnect all clients (PokerClient).
        while (m_clients.size() != 0)
        {
            m_clients.get(0).disconnect();
        }
        jStatusLabel.setText("Not Connected");
        setTitle(jTitleLabel.getText());
        getJRefreshButton().setEnabled(false);
        getJAddTableButton().setEnabled(false);
        getJJoinTableButton().setEnabled(false);
        getJConnectButton().setText("Connect");
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
                    if (isConnected())
                    {
                        disconnect();
                        
                        for (int i = 0; i < m_agents.size(); ++i)
                        {
                            m_agents.get(i).disconnect();
                        }
                        m_agents.clear();
                    }
                    else
                    {
                        // Eh bien on connecte
                        final LobbyAIConnectJDialog form = new LobbyAIConnectJDialog(LobbyAIMainJFrame.this, getAIs());
                        form.setVisible(true);
                        if (form.isOK())
                        {
                            m_playerName = form.getPlayerName();
                            m_AIs = form.getAIs();
                            m_serverAddress = form.getServerAddress();
                            m_serverPort = form.getServerPort();
                            if (connect(m_serverAddress, m_serverPort))
                            {
                                // Authentify the user.
                                send(new LobbyConnectCommand(m_playerName));
                                boolean ok = Boolean.valueOf(receive());
                                while (!ok)
                                {
                                    m_playerName = "0" + m_playerName;
                                    send(new LobbyConnectCommand(m_playerName));
                                    ok = Boolean.valueOf(receive());
                                }
                                jStatusLabel.setText("Connected as " + m_playerName + " to " + m_serverAddress + ":" + m_serverPort);
                                getJRefreshButton().setEnabled(true);
                                getJAddTableButton().setEnabled(true);
                                getJConnectButton().setText("Disconnect");
                                setTitle(m_playerName + " - " + jTitleLabel.getText());
                                
                                for (final TupleAISummary tuple : getAIs())
                                {
                                    final LobbyAIMainJFrame client = new LobbyAIMainJFrame();
                                    client.setAgentType(tuple.m_AIType);
                                    client.setPlayerName(tuple.m_AIName);
                                    client.asViewer(tuple.m_viewer);
                                    if (client.connect(getAddress(), getPort()))
                                    {
                                        client.send(new LobbyConnectCommand(tuple.m_AIName));
                                        boolean isOk = Boolean.valueOf(client.receive());
                                        while (!isOk)
                                        {
                                            final LobbyNameUsedJDialog form2 = new LobbyNameUsedJDialog(LobbyAIMainJFrame.this, client.getPlayerName());
                                            form2.setVisible(true);
                                            client.setPlayerName(form2.getPlayerName());
                                            client.send(new LobbyConnectCommand(client.getPlayerName()));
                                            isOk = Boolean.valueOf(client.receive());
                                        }
                                        m_agents.add(client);
                                    }
                                }
                                refreshTables();
                            }
                            else
                            {
                                jStatusLabel.setText("Not connected, Authentification Failed!!!");
                                System.out.println("Authentification Failed!!!");
                            }
                            
                        }
                    }
                }
            });
        }
        return jConnectButton;
    }
    
    /**
     * Connect the user to a ServerLobby.
     * 
     * @param p_host
     *            - Hostname where the ServerLobby is listening.
     * @param p_noPort
     *            - Port number the ServerLobby is listening to.
     */
    public boolean connect(String p_host, int p_noPort)
    {
        try
        {
            m_connection = new Socket(p_host, p_noPort);
            m_toServer = new PrintWriter(m_connection.getOutputStream(), true); // Auto-flush
            m_fromServer = new BufferedReader(new InputStreamReader(m_connection.getInputStream()));
        }
        catch (final Exception e)
        {
            System.err.println("Error on connect: " + e.getMessage());
            return false;
        }
        return true;
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
            jTitleLabel.setText("Poker Client For AIs 2.0");
            jTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jTitleLabel.setFont(new Font("Dialog", Font.BOLD, 18));
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.setPreferredSize(new Dimension(442, 202));
            jContentPane.add(jTitleLabel, BorderLayout.NORTH);
            jContentPane.add(jStatusLabel, BorderLayout.SOUTH);
            jContentPane.add(getJMainPanel(), BorderLayout.CENTER);
        }
        return jContentPane;
    }
    
    public void allowJoinAndLeave()
    {
        getJJoinTableButton().setEnabled(canJoin());
        getJLeaveTableButton().setEnabled(canLeave());
    }
    
    public boolean canJoin()
    {
        if (getJMainTable().getSelectionModel().isSelectionEmpty())
        {
            return false;
        }
        
        final int index = getJMainTable().getSelectedRow();
        final int noPort = (Integer) getJMainTable().getModel().getValueAt(index, 0);
        final SummaryTableInfo info = m_tables.get(noPort);
        if (info.m_nbSeats - info.m_nbPlayers < m_AIs.size())
        {
            return false;
        }
        for (int i = 0; i < m_agents.size(); ++i)
        {
            if (m_agents.get(i).findClient(info.m_noPort) != null)
            {
                return false;
            }
        }
        return true;
    }
    
    public boolean canLeave()
    {
        if (getJMainTable().getSelectionModel().isSelectionEmpty())
        {
            return false;
        }
        
        final int index = getJMainTable().getSelectedRow();
        final int noPort = (Integer) getJMainTable().getModel().getValueAt(index, 0);
        final SummaryTableInfo info = m_tables.get(noPort);
        for (int i = 0; i < m_agents.size(); ++i)
        {
            if (m_agents.get(i).findClient(info.m_noPort) != null)
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Refresh the list of available tables on the ServerLobby.
     */
    public void refreshTables()
    {
        final DefaultTableModel model = (DefaultTableModel) getJMainTable().getModel();
        model.setRowCount(0);
        
        // Ask the server for all available tables.
        send(new LobbyListTableCommand());
        m_tables.clear();
        final StringTokenizer token = new StringTokenizer(receive(), Constants.DELIMITER);
        
        // Parse results.
        while (token.hasMoreTokens())
        {
            final SummaryTableInfo info = new SummaryTableInfo(token);
            
            final Object[] row = new Object[5];
            row[0] = info.m_noPort;
            row[1] = info.m_tableName;
            row[2] = info.m_gameType;
            row[3] = info.m_bigBlind;
            row[4] = info.m_nbPlayers + "/" + info.m_nbSeats;
            // Add the table infos to the JTable of available tables.
            model.addRow(row);
            m_tables.put(info.m_noPort, info);
        }
        // Select the first available table in the JTable.
        getJMainTable().getSelectedRow();
        final DefaultListSelectionModel selection = new DefaultListSelectionModel();
        selection.setSelectionInterval(0, 0);
        
        if ((model.getRowCount() > 0) && (getJMainTable().getSelectedRow() == -1))
        {
            getJMainTable().setSelectionModel(selection);
            allowJoinAndLeave();
        }
        else
        {
            getJJoinTableButton().setEnabled(false);
        }
    }
    
    /**
     * Send a message to a ServerLobby.
     * 
     * @param p_msg
     *            - Message to send to the ServerLobby.
     */
    public boolean sendMessage(String p_msg)
    {
        if (!isConnected())
        {
            return false;
        }
        
        // Output the message to the console. (Logs)
        System.out.println(m_playerName + " SENT [" + p_msg + "]");
        m_toServer.println(p_msg);
        
        return true;
    }
    
    public boolean send(IPokerCommand p_msg)
    {
        return sendMessage(p_msg.encodeCommand());
    }
    
    /**
     * Tell if the user is connected to a ServerLobby.
     * 
     * @return
     *         If the user is connected to a ServerLobby..
     */
    public boolean isConnected()
    {
        return (m_connection != null) && m_connection.isConnected() && !m_connection.isClosed();
    }
    
    /**
     * Receive a message from a ServerLobby.
     * 
     * @return
     *         The message received from the server.
     */
    public String receive()
    {
        if (!isConnected())
        {
            return null;
        }
        
        String msg = null;
        try
        {
            msg = m_fromServer.readLine();
            // Output the message to the console. (Logs)
            System.out.println(m_playerName + " RECV [" + msg + "]");
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
        
        return msg;
    }
    
    /**
     * Create a new table.
     * 
     * @param p_tableName
     *            - Name of the new table.
     * @param p_gameType
     *            - Game type of the new table.
     * @param p_bigBlind
     *            - Amount of the big blind for the new table.
     * @param p_maxPlayers
     *            - Maximum number of players that the new table can contain.
     * 
     * @return
     *         <b>true</b> if the user correctly joined the table. <br>
     *         <b>false</b> if no seat is free, someone with the same name
     *         has already joined this table, or the table does not exist.
     */
    public int createTable(String p_tableName, int p_bigBlind, int p_maxPlayers)
    {
        // Send query.
        send(new LobbyCreateTableCommand(p_tableName, OldTypePokerGame.NO_LIMIT, p_bigBlind, p_maxPlayers, m_playerName));
        // Wait for response.
        final StringTokenizer token = new StringTokenizer(receive(), Constants.DELIMITER);
        
        return Integer.parseInt(token.nextToken());
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
        Socket tableSocket = null;
        PrintWriter toTable = null;
        BufferedReader fromTable = null;
        try
        {
            // Connect with the TableManager on the specified port number.
            System.out.println("Trying connection with the table manager...");
            tableSocket = new Socket(m_connection.getInetAddress(), p_noPort);
            toTable = new PrintWriter(tableSocket.getOutputStream(), true); // Auto-flush
            // enabled.
            fromTable = new BufferedReader(new InputStreamReader(tableSocket.getInputStream()));
            
            // Authenticate the user.
            toTable.println(new LobbyConnectCommand(m_playerName).encodeCommand());
            if (!Boolean.parseBoolean(fromTable.readLine()))
            {
                System.out.println("Authentification failed on the table: " + p_tableName);
                return false;
            }
            
            // Build query.
            final LobbyJoinTableCommand command = new LobbyJoinTableCommand(m_playerName, p_tableName);
            
            // Send query.
            toTable.println(command.encodeCommand());
            
            // Wait for response.
            final StringTokenizer token = new StringTokenizer(fromTable.readLine(), Constants.DELIMITER);
            final int noSeat = Integer.parseInt(token.nextToken());
            
            if (noSeat == -1)
            {
                System.out.println("Cannot sit at this table: " + p_tableName);
                return false;
            }
            
            // Add a tab associated to the newly created table in advanced
            // settings.
            
            final ClientSidePokerTcpServer client = new ClientSidePokerTcpServer(tableSocket, fromTable, noSeat, m_playerName);
            if (m_agentType == TypePokerAI.SVM)
            {
                final StatsAgent statsAgent = new StatsAgent();
                // TODO: RICK: SVM WANNA DIE
                // statsAgent.setPokerObserver(client.getPokerObserver());
                // client.attach(statsAgent);
                
                new PokerAISVM(client, noSeat, statsAgent, m_playerName);
            }
            else
            {
                FactoryPokerAI.create(m_agentType, client, noSeat);
            }
            
            if (m_viewer)
            {
                final GameTableViewerJFrame viewer = new GameTableViewerJFrame();
                viewer.setPokerObserver(client.getGameObserver());
                viewer.setGame(client, noSeat);
                viewer.start();
            }
            
            // Start a the new PokerClient.
            client.start();
            
            m_clients.add(client);
            
            return true;
            
        }
        catch (final IOException e)
        {
            System.out.println(p_noPort + " not open.");
        }
        
        return false;
    }
    
    public ClientSidePokerTcpServer findClient(int noPort)
    {
        int i = 0;
        while ((i != m_clients.size()) && (m_clients.get(i).getNoPort() != noPort))
        {
            ++i;
        }
        
        if (i == m_clients.size())
        {
            return null;
        }
        
        return m_clients.get(i);
    }
    
    private void eventJoinTable()
    {
        if (!canJoin())
        {
            return;
        }
        
        final int index = getJMainTable().getSelectedRow();
        final int noPort = (Integer) getJMainTable().getModel().getValueAt(index, 0);
        
        final String tableName = (String) getJMainTable().getModel().getValueAt(index, 1);
        final int bigBlind = (Integer) getJMainTable().getModel().getValueAt(index, 3);
        
        for (int i = 0; i < m_agents.size(); ++i)
        {
            if (!m_agents.get(i).joinTable(noPort, tableName, bigBlind))
            {
                System.out.println(m_agents.get(i).getPlayerName() + " cannot play on '" + tableName + "' !!!");
            }
        }
        refreshTables();
    }
    
    public String getPlayerName()
    {
        return m_playerName;
    }
    
    public List<TupleAISummary> getAIs()
    {
        return m_AIs;
    }
    
    public String getAddress()
    {
        return m_serverAddress;
    }
    
    public int getPort()
    {
        return m_serverPort;
    }
    
    public void asViewer(boolean viewer)
    {
        m_viewer = viewer;
    }
} // @jve:decl-index=0:visual-constraint="10,10"
