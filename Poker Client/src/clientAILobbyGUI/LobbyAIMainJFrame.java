package clientAILobbyGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.StringTokenizer;

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
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import pokerAI.IPokerAgentListener;
import pokerLogic.TypePokerGame;
import protocolLobby.LobbyConnectCommand;
import protocolLobby.LobbyCreateTableCommand;
import protocolLobby.LobbyDisconnectCommand;
import protocolLobby.LobbyJoinTableCommand;
import protocolLobby.LobbyListTableCommand;
import protocolLogic.IBluffinCommand;
import utilGUI.AutoListModel;
import utility.Constants;
import utility.IClosingListener;
import clientGame.ClientPokerPlayerInfo;
import clientGame.ClientPokerTableInfo;
import clientGame.PokerClient;
import clientGameGUI.TableGUI;
import clientLobbyGUI.LobbyAddTableJDialog;
import clientOldAndUglyLobbyGUI.OldAndUglyClientLobby;

public class LobbyAIMainJFrame extends JFrame implements IClosingListener<PokerClient>
{
    private Socket m_connection = null; // @jve:decl-index=0:
    private PrintWriter m_toServer = null;
    private BufferedReader m_fromServer = null;
    
    private String m_playerName;
    private List<TupleAISummary> m_AIs;
    private String m_serverAddress;
    private int m_serverPort;
    
    // List of PokerClient (one for each table the player joined)
    private final AutoListModel<PokerClient> m_clients = new AutoListModel<PokerClient>();
    
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
    private JButton jOldStyleButton = null;
    
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
            jMainToolBar.add(getJOldStyleButton());
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
                    
                    final LobbyAddTableJDialog form = new LobbyAddTableJDialog(LobbyAIMainJFrame.this, m_playerName);
                    form.setVisible(true);
                    if (form.isOK())
                    {
                        final int noPort = createTable(form.getTableName(), form.getGameType(), form.getBigBlind(), form.getNbPlayer());
                        
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
                    if (e.getClickCount() == 2)
                    {
                        eventJoinTable();
                    }
                }
            });
        }
        return jMainTable;
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
                        getJOldStyleButton().setEnabled(true);
                    }
                    else
                    {
                        // Eh bien on connecte
                        final LobbyAIConnectJDialog form = new LobbyAIConnectJDialog(LobbyAIMainJFrame.this);
                        form.setVisible(true);
                        if (form.isOK())
                        {
                            m_playerName = form.getPlayerName();
                            m_AIs = form.getAIs();
                            m_serverAddress = form.getServerAddress();
                            m_serverPort = form.getServerPort();
                            connect(m_serverAddress, m_serverPort);
                            // Authentify the user.
                            send(new LobbyConnectCommand(m_playerName));
                            
                            if (Boolean.valueOf(receive()))
                            {
                                jStatusLabel.setText("Connected as " + form.getPlayerName() + " to " + form.getServerAddress() + ":" + form.getServerPort());
                                getJRefreshButton().setEnabled(true);
                                getJAddTableButton().setEnabled(true);
                                getJOldStyleButton().setEnabled(false);
                                getJConnectButton().setText("Disconnect");
                                setTitle(m_playerName + " - " + jTitleLabel.getText());
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
    public void connect(String p_host, int p_noPort)
    {
        try
        {
            m_connection = new Socket(p_host, p_noPort);
            m_toServer = new PrintWriter(m_connection.getOutputStream(), true); // Auto-flush
            m_fromServer = new BufferedReader(new InputStreamReader(m_connection.getInputStream()));
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * This method initializes jOldStyleButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getJOldStyleButton()
    {
        if (jOldStyleButton == null)
        {
            jOldStyleButton = new JButton();
            jOldStyleButton.setText("OldStyle");
            jOldStyleButton.setBackground(Color.orange);
            jOldStyleButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    final OldAndUglyClientLobby oldLobby = new OldAndUglyClientLobby();
                    oldLobby.getJFrame().setVisible(true);
                    LobbyAIMainJFrame.this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                    setVisible(false);
                }
            });
        }
        return jOldStyleButton;
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
            jContentPane.add(jTitleLabel, BorderLayout.NORTH);
            jContentPane.add(jStatusLabel, BorderLayout.SOUTH);
            jContentPane.add(getJMainPanel(), BorderLayout.CENTER);
        }
        return jContentPane;
    }
    
    @Override
    public void closing(PokerClient e)
    {
        m_clients.remove(e);
        
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
        
        final StringTokenizer token = new StringTokenizer(receive(), Constants.DELIMITER);
        
        // Parse results.
        while (token.hasMoreTokens())
        {
            final Integer noPort = Integer.parseInt(token.nextToken());
            final String tableName = token.nextToken();
            final TypePokerGame gameType = TypePokerGame.valueOf(token.nextToken());
            final int bigBlind = Integer.parseInt(token.nextToken());
            final int nbPlayers = Integer.parseInt(token.nextToken());
            final int nbSeats = Integer.parseInt(token.nextToken());
            
            final Object[] row = new Object[5];
            row[0] = noPort;
            row[1] = tableName;
            row[2] = gameType;
            row[3] = bigBlind;
            row[4] = nbPlayers + "/" + nbSeats;
            // Add the table infos to the JTable of available tables.
            model.addRow(row);
        }
        getJJoinTableButton().setEnabled(model.getRowCount() != 0);
        // Select the first available table in the JTable.
        getJMainTable().getSelectedRow();
        final DefaultListSelectionModel selection = new DefaultListSelectionModel();
        selection.setSelectionInterval(0, 0);
        
        if ((model.getRowCount() > 0) && (getJMainTable().getSelectedRow() == -1))
        {
            getJMainTable().setSelectionModel(selection);
            getJJoinTableButton().setEnabled(true);
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
    
    public boolean send(IBluffinCommand p_msg)
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
    public int createTable(String p_tableName, TypePokerGame p_gameType, int p_bigBlind, int p_maxPlayers)
    {
        // Send query.
        send(new LobbyCreateTableCommand(p_tableName, p_gameType, p_bigBlind, p_maxPlayers, m_playerName));
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
            final ClientPokerPlayerInfo localPlayer = new ClientPokerPlayerInfo(noSeat, m_playerName, 0);
            final ClientPokerTableInfo table = new ClientPokerTableInfo();
            table.m_name = p_tableName;
            table.m_bigBlindAmount = p_bigBlindAmount;
            table.m_smallBlindAmount = p_bigBlindAmount / 2;
            
            final AutoListModel<IPokerAgentListener> observers = new AutoListModel<IPokerAgentListener>();
            final TableGUI gui = new TableGUI();
            observers.add(gui);
            
            // Start a the new PokerClient.
            final PokerClient client = new PokerClient(gui, observers, localPlayer, tableSocket, table, fromTable);
            client.addClosingListener(this);
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
    
    private PokerClient findClient()
    {
        if (getJMainTable().getSelectionModel().isSelectionEmpty())
        {
            return null;
        }
        
        final int index = getJMainTable().getSelectedRow();
        final int noPort = (Integer) getJMainTable().getModel().getValueAt(index, 0);
        
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
                refreshTables();
            }
        }
    }
    
} // @jve:decl-index=0:visual-constraint="10,10"
