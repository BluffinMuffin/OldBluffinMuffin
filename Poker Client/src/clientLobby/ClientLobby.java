package clientLobby;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import pokerAI.IPokerAgentActionner;
import pokerAI.IPokerAgentListener;
import pokerLogic.TypePokerGame;
import protocolLobby.LobbyCreateTableCommand;
import protocolLobby.LobbyDisconnectCommand;
import protocolLobby.LobbyListTableCommand;
import protocolLobby.TypeMessageTableManager;
import protocolLogic.BluffinAuthentificationCommand;
import utilGUI.AutoListModel;
import utilGUI.CurrencyIntegerEditor;
import utilGUI.JBackgroundPanel;
import utility.Bundle;
import utility.Constants;
import utility.IClosingListener;
import clientAI.FactoryAgent;
import clientAI.FactoryObserver;
import clientAI.PokerAI;
import clientAI.PokerSVM;
import clientAI.TypeAgent;
import clientAI.TypeObserver;
import clientGame.GUI;
import clientLogic.ClientPokerPlayerInfo;
import clientLogic.ClientPokerTableInfo;
import clientLogic.PokerClient;
import clientStats.StatsAgent;

/**
 * @author Hocus
 *         This class represents the entry point of the client application.
 *         A part of this class was generated using a visual editor
 *         (http://wiki.eclipse.org/VE).
 *         It is the lobby from where a user can join a table and play poker.
 *         First, a user need to connect with a poker server using the
 *         connection window.
 */
@SuppressWarnings("serial")
public class ClientLobby implements IClosingListener<PokerClient>
{
    /*
     * ################################
     * Auto Generated - Class Variables
     * ################################
     */

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                final ClientLobby application = new ClientLobby();
                application.getJFrame().setVisible(true);
            }
        });
    }
    
    private JFrame jFrame = null; // @jve:decl-index=0:visual-constraint="6,400"
    private JPanel jContentPane = null;
    private JMenuBar jJMenuBar = null;
    private JMenu fileMenu = null;
    private JMenu editMenu = null;
    private JMenu helpMenu = null;
    private JMenu helpSettings = null;
    private JMenuItem exitMenuItem = null;
    private JMenuItem aboutMenuItem = null;
    private JMenuItem jMenuItemAddTable = null;
    private JMenuItem jMenuItemRefresh = null;
    private JDialog jDialogAbout = null; // @jve:decl-index=0:visual-constraint="435,280"
    private JPanel aboutContentPane = null;
    private JLabel aboutVersionLabel = null;
    private JDialog jDialogAddTable = null; // @jve:decl-index=0:visual-constraint="434,0"
    private JPanel jContentAddTable = null;
    private JButton jButtonAdd = null;
    private JButton jButtonCancel = null;
    private JPanel jPanelAttributes = null;
    private JLabel jLabelName = null;
    private JTextField jTextFieldTableName = null;
    private JLabel jLabelNbPlayers = null;
    private JSlider jSliderNbPlayers = null;
    private JLabel jLabelGameType = null;
    private JComboBox jComboBoxGameType = null;
    private JSpinner jSpinnerBigBlind = null;
    private JLabel jLabelBigBlind = null;
    private JScrollPane jScrollPaneTables = null;
    private JTable jTablePokerTables = null;
    private JButton jButtonJoin = null;
    private JPanel jPanel1 = null;
    private JMenuItem jMenuItemConnect = null;
    private JDialog jDialogConnection = null; // @jve:decl-index=0:visual-constraint="779,2"
    private JPanel jContentConnection = null;
    private JPanel jPanelPlayerInfos = null;
    private JLabel jLabelUsername = null;
    private JTextField jTextFieldPlayerName = null;
    private JLabel jLabelServerHost = null;
    private JLabel jLabelServerPort = null;
    private JTextField jTextFieldServerPort = null;
    private JTextField jTextFieldServerHost = null;
    private JPanel jPanelServerInfos = null;
    private JButton jButtonConnect = null;
    private JButton jButtonConnectionCancel = null;
    private JMenuItem jMenuItemDisconnect = null;
    private JPanel jPanelAgentInfos = null;
    private JCheckBox jCheckBoxIsAgent = null;
    private JLabel jLabelIsAgent = null;
    private JLabel jLabelAgentType = null;
    private JComboBox jComboBoxAgentType = null;
    private JLabel jLabelAddViewer = null;
    private JCheckBox jCheckBoxAddViewer = null;
    private JButton jButtonAdvanced = null;
    private JDialog jDialogAdvanced = null; // @jve:decl-index=0:visual-constraint="10,55"
    private JPanel jContentPaneAdvanced = null;
    private JPanel jPanelObseverInfos = null;
    private JLabel jLabelObserverType = null;
    private JLabel jLabelAddedObservers = null;
    private JComboBox jComboBoxObserverType = null;
    private JList jListAddedObservers = null;
    private JButton jButtonAddObserver = null;
    private JButton jButtonRemoveObserver = null;
    private JButton jButtonAdvancedOK = null;
    
    private JTabbedPanePokerClient jTabbedPaneTables = null;
    private JMenuItem jMenuItemSitOut = null;
    private JMenuItem jMenuItemSitIn = null;
    private JBackgroundPanel jBackgroundPanelHandsStrength = null;
    
    private JMenuItem jMenuItemCardsStrength = null;
    private final ButtonGroup languageGroup = new ButtonGroup(); // @jve:decl-index=0:
    private JRadioButtonMenuItem jRadioButtonMenuItemEnglish = null;
    
    private JRadioButtonMenuItem jRadioButtonMenuItemFrench = null;
    private JMenu jMenuItemChangeLanguage = null;
    
    /*
     * ################################
     * Manually Added - Class Variables
     * ################################
     */

    private JFrame jFrameHandsStrength = null; // @jve:decl-index=0:visual-constraint="783,309"
    // Comunication tools with the LobbyServer
    private Socket m_connection = null; // @jve:decl-index=0:
    private PrintWriter m_toServer = null; // @jve:decl-index=0:
    
    private BufferedReader m_fromServer = null;
    // User infos
    private String m_playerName = null;
    private Boolean m_isAgent = null;
    private Boolean m_addViewer = null;
    
    private TypeAgent m_agentType = null;
    
    // List of PokerClient (one for each table the player joined)
    private final AutoListModel<PokerClient> m_clients = new AutoListModel<PokerClient>();
    
    // List of default observers that will be attached the player.
    private final AutoListModel<TypeObserver> m_generalObservers = new AutoListModel<TypeObserver>();
    
    // Last IPokerAgentActionner added.
    IPokerAgentActionner m_agent = null;
    
    // Internationalization tool
    private final Bundle m_bundle = Bundle.getIntance();
    
    /*
     * ######################################################
     * Getters and Setters
     * ######################################################
     */

    public ClientLobby()
    {
    }
    
    @Override
    public void closing(PokerClient e)
    {
        m_clients.remove(e);
    }
    
    /**
     * Connect the user to a ServerLobby.
     * 
     * @param p_host
     *            - Hostname where the ServerLobby is listening.
     * @param p_noPort
     *            - Port number the ServerLobby is listening to.
     */
    public void connect(String p_host, int p_noPort) throws UnknownHostException, IOException
    {
        m_connection = new Socket(p_host, p_noPort);
        m_toServer = new PrintWriter(m_connection.getOutputStream(), true); // Auto-flush
        // enabled.
        m_fromServer = new BufferedReader(new InputStreamReader(m_connection.getInputStream()));
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
        send(new LobbyCreateTableCommand(p_tableName, p_gameType, p_bigBlind, p_maxPlayers, m_playerName).encodeCommand());
        // Wait for response.
        final StringTokenizer token = new StringTokenizer(receive(), Constants.DELIMITER);
        
        return Integer.parseInt(token.nextToken());
    }
    
    /*
     * ######################################################
     * Public Methods
     * ######################################################
     */

    /**
     * Disconnect the user of a ServerLobby and
     * force the user to sit out from all joined tables.
     */
    public void disconnect()
    {
        System.out.println("Disconnecting...");
        try
        {
            // Tell ServerLobby that the user is leaving.
            send(new LobbyDisconnectCommand().encodeCommand());
            
            // Close the socket
            if (m_connection != null)
            {
                m_connection.close();
            }
            
            m_connection = null;
            m_toServer = null;
            m_fromServer = null;
            m_generalObservers.clear();
            
            // Stop the agent.
            if (m_agent != null)
            {
                m_agent.stop();
            }
            
            m_agent = null;
            
            // Remove all tabs in Advanced Settings excepting GeneralTab.
            for (int i = 0; i != getJTabbedPaneTables().getTabCount(); ++i)
            {
                final Component c = getJTabbedPaneTables().getTabComponentAt(i);
                if (c instanceof JPanelObserver)
                {
                    ((JPanelObserver) c).clear();
                    getJTabbedPaneTables().removeTabAt(i);
                    --i;
                }
            }
            
            // Clear list of available tables.
            final DefaultTableModel model = (DefaultTableModel) getJTablePokerTables().getModel();
            model.setRowCount(0);
            
            // Disconnect all clients (PokerClient).
            while (m_clients.size() != 0)
            {
                m_clients.get(0).disconnect();
            }
            
            // Change Lobby title name for the default.
            getJFrame().setTitle(m_bundle.get("lobby.title"));
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private PokerClient findClient()
    {
        if (getJTablePokerTables().getSelectionModel().isSelectionEmpty())
        {
            return null;
        }
        
        final int index = getJTablePokerTables().getSelectedRow();
        final int noPort = (Integer) getJTablePokerTables().getModel().getValueAt(index, 0);
        
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
    
    /**
     * This method initializes aboutVersionLabel
     * 
     * @return javax.swing.JLabel
     */
    private JLabel getAboutVersionLabel()
    {
        if (aboutVersionLabel == null)
        {
            aboutVersionLabel = new JLabel()
            {
                @Override
                public String getText()
                {
                    final StringBuilder sb = new StringBuilder();
                    sb.append("<html><h2><center>");
                    sb.append(m_bundle.get("about.team"));
                    sb.append("<br />");
                    sb.append(m_bundle.get("about.date"));
                    sb.append("<br />");
                    sb.append(m_bundle.get("about.location"));
                    sb.append("<br />");
                    sb.append(m_bundle.get("about.version"));
                    sb.append("</h2></center></html>");
                    return sb.toString();
                }
            };
            aboutVersionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        return aboutVersionLabel;
    }
    
    /**
     * This method initializes aboutContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getContentAbout()
    {
        if (aboutContentPane == null)
        {
            aboutContentPane = new JPanel();
            aboutContentPane.setLayout(new BorderLayout());
            aboutContentPane.add(getAboutVersionLabel(), BorderLayout.CENTER);
        }
        return aboutContentPane;
    }
    
    private JBackgroundPanel getJBackgroundPanelHandsStrength()
    {
        if (jBackgroundPanelHandsStrength == null)
        {
            jBackgroundPanelHandsStrength = new JBackgroundPanel();
            jBackgroundPanelHandsStrength.setBackground(new ImageIcon(m_bundle.get("handsStrength.imagePath")));
        }
        return jBackgroundPanelHandsStrength;
    }
    
    /**
     * This method initializes jButtonCancel
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButtonAddCancel()
    {
        if (jButtonCancel == null)
        {
            jButtonCancel = new JButton()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("addTable.buttonCancel");
                }
            };
            jButtonCancel.setName("jButtonCancel");
            jButtonCancel.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    getJDialogAddTable().setVisible(false);
                }
            });
        }
        return jButtonCancel;
    }
    
    /**
     * This method initializes jButtonAddObserver
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButtonAddObserver()
    {
        if (jButtonAddObserver == null)
        {
            jButtonAddObserver = new JButton()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("advanced.buttonAdd");
                }
            };
            jButtonAddObserver.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    m_generalObservers.add((TypeObserver) getJComboBoxObserverType().getSelectedItem());
                }
            });
        }
        return jButtonAddObserver;
    }
    
    /**
     * This method initializes jButtonAdd
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButtonAddTable()
    {
        if (jButtonAdd == null)
        {
            jButtonAdd = new JButton()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("addTable.buttonAdd");
                }
            };
            jButtonAdd.setName("jButtonAdd");
            jButtonAdd.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    final int noPort = createTable(getJTextFieldTableName().getText(), (TypePokerGame) getJComboBoxGameType().getSelectedItem(), (Integer) getJSpinnerBigBlind().getValue(), getJSliderNbPlayers().getValue());
                    
                    if (noPort != -1)
                    {
                        getJDialogAddTable().setVisible(false);
                        refreshTables();
                        joinTable(noPort, getJTextFieldTableName().getText(), (Integer) getJSpinnerBigBlind().getValue());
                    }
                    else
                    {
                        System.out.println("Cannot create table: '" + getJTextFieldTableName().getText() + "'");
                    }
                }
            });
        }
        return jButtonAdd;
    }
    
    /**
     * This method initializes jButtonAdvanced
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButtonAdvanced()
    {
        if (jButtonAdvanced == null)
        {
            jButtonAdvanced = new JButton()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("lobby.buttonAdvanced");
                }
            };
            jButtonAdvanced.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    getJDialogAdvanced().setVisible(true);
                }
            });
        }
        return jButtonAdvanced;
    }
    
    /**
     * This method initializes jButtonAdvancedOK
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButtonAdvancedOK()
    {
        if (jButtonAdvancedOK == null)
        {
            jButtonAdvancedOK = new JButton()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("advanced.buttonOK");
                }
            };
            jButtonAdvancedOK.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    jDialogAdvanced.setVisible(false);
                }
            });
        }
        return jButtonAdvancedOK;
    }
    
    /**
     * This method initializes jButtonConnect
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButtonConnect()
    {
        if (jButtonConnect == null)
        {
            jButtonConnect = new JButton()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("connect.buttonConnect");
                }
            };
            jButtonConnect.setSelected(true);
            jButtonConnect.setEnabled(true);
            jButtonConnect.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    try
                    {
                        // Connect to the ServerLobby.
                        connect(getJTextFieldServerHost().getText(), Integer.parseInt(getJTextFieldServerPort().getText()));
                        m_playerName = getJTextFieldPlayerName().getText();
                        m_isAgent = getJCheckBoxIsAgent().isSelected();
                        m_agentType = (TypeAgent) getJComboBoxAgentType().getSelectedItem();
                        m_addViewer = getJCheckBoxAddViewer().isSelected();
                        
                        if (m_addViewer)
                        {
                            m_generalObservers.add(TypeObserver.VIEWER);
                        }
                        
                        // Authentify the user.
                        send(new BluffinAuthentificationCommand(m_playerName).encodeCommand());
                        
                        if (Boolean.valueOf(receive()))
                        {
                            getJDialogConnection().setVisible(false);
                            getJFrame().setTitle(m_bundle.get("lobby.title") + " - " + m_playerName);
                        }
                        else
                        {
                            System.out.println("Authentification Failed!!!");
                        }
                    }
                    catch (final NumberFormatException e1)
                    {
                        e1.printStackTrace();
                    }
                    catch (final UnknownHostException e1)
                    {
                        System.out.println("*** Unknown Host");
                        e1.printStackTrace();
                    }
                    catch (final IOException e1)
                    {
                        e1.printStackTrace();
                    }
                }
            });
        }
        return jButtonConnect;
    }
    
    /**
     * This method initializes jButtonConnectionCancel
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButtonConnectionCancel()
    {
        if (jButtonConnectionCancel == null)
        {
            jButtonConnectionCancel = new JButton()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("connect.buttonCancel");
                }
            };
            jButtonConnectionCancel.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    getJDialogConnection().setVisible(false);
                }
            });
        }
        return jButtonConnectionCancel;
    }
    
    /**
     * This method initializes jButtonJoin
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButtonJoin()
    {
        if (jButtonJoin == null)
        {
            jButtonJoin = new JButton()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("lobby.buttonJoin");
                }
            };
            jButtonJoin.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    sitIN();
                }
            });
        }
        return jButtonJoin;
    }
    
    /**
     * This method initializes jButtonRemoveObserver
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButtonRemoveObserver()
    {
        if (jButtonRemoveObserver == null)
        {
            jButtonRemoveObserver = new JButton()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("advanced.buttonRemove");
                }
            };
            jButtonRemoveObserver.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    m_generalObservers.remove(getJListAddedObservers().getSelectedValue());
                }
            });
        }
        return jButtonRemoveObserver;
    }
    
    /**
     * This method initializes jCheckBoxAddViewer
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getJCheckBoxAddViewer()
    {
        if (jCheckBoxAddViewer == null)
        {
            jCheckBoxAddViewer = new JCheckBox();
            jCheckBoxAddViewer.setEnabled(false);
        }
        return jCheckBoxAddViewer;
    }
    
    /**
     * This method initializes jCheckBoxIsAgent
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getJCheckBoxIsAgent()
    {
        if (jCheckBoxIsAgent == null)
        {
            jCheckBoxIsAgent = new JCheckBox();
            jCheckBoxIsAgent.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    for (final Component component : getJPanelAgentInfos().getComponents())
                    {
                        component.setEnabled(getJCheckBoxIsAgent().isSelected());
                    }
                    
                    getJPanelAgentInfos().setEnabled(getJCheckBoxIsAgent().isSelected());
                    
                    if (getJCheckBoxIsAgent().isSelected())
                    {
                        getJTextFieldPlayerName().setText(m_bundle.get("connect.playerInfos.playerName.defaultAgent"));
                    }
                    else
                    {
                        getJTextFieldPlayerName().setText(m_bundle.get("connect.playerInfos.playerName.defaultPlayer"));
                    }
                }
            });
        }
        return jCheckBoxIsAgent;
    }
    
    /**
     * This method initializes jComboBoxAgentType
     * 
     * @return javax.swing.JComboBox
     */
    private JComboBox getJComboBoxAgentType()
    {
        if (jComboBoxAgentType == null)
        {
            jComboBoxAgentType = new JComboBox();
            jComboBoxAgentType.setEnabled(false);
            jComboBoxAgentType.setModel(new DefaultComboBoxModel(TypeAgent.values()));
            jComboBoxAgentType.setSelectedIndex(0);
        }
        
        return jComboBoxAgentType;
    }
    
    /**
     * This method initializes jComboBoxGameType
     * 
     * @return javax.swing.JComboBox
     */
    private JComboBox getJComboBoxGameType()
    {
        if (jComboBoxGameType == null)
        {
            jComboBoxGameType = new JComboBox();
            jComboBoxGameType.setModel(new DefaultComboBoxModel(TypePokerGame.values()));
            jComboBoxGameType.setSelectedItem(TypePokerGame.NO_LIMIT);
        }
        return jComboBoxGameType;
    }
    
    /**
     * This method initializes jComboBoxObserverType
     * 
     * @return javax.swing.JComboBox
     */
    private JComboBox getJComboBoxObserverType()
    {
        if (jComboBoxObserverType == null)
        {
            jComboBoxObserverType = new JComboBox();
            jComboBoxObserverType.setModel(new DefaultComboBoxModel(TypeObserver.values()));
        }
        return jComboBoxObserverType;
    }
    
    /**
     * This method initializes jContentPane1
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentAddTable()
    {
        if (jContentAddTable == null)
        {
            final GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.anchor = GridBagConstraints.SOUTHEAST;
            gridBagConstraints.gridheight = 0;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.weightx = 0.0;
            gridBagConstraints.weighty = 0.0;
            gridBagConstraints.insets = new Insets(5, 5, 5, 5);
            final GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.anchor = GridBagConstraints.SOUTHEAST;
            gridBagConstraints1.insets = new Insets(0, 3, 5, 2);
            gridBagConstraints1.gridheight = 0;
            gridBagConstraints1.gridwidth = 1;
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.gridy = 1;
            gridBagConstraints1.ipadx = 0;
            gridBagConstraints1.ipady = 0;
            gridBagConstraints1.weightx = 1.0;
            gridBagConstraints1.weighty = 0.0;
            gridBagConstraints1.fill = GridBagConstraints.NONE;
            final GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.gridx = 0;
            gridBagConstraints7.ipadx = 0;
            gridBagConstraints7.ipady = 0;
            gridBagConstraints7.gridwidth = 3;
            gridBagConstraints7.weightx = 1.0;
            gridBagConstraints7.weighty = 2.0;
            gridBagConstraints7.insets = new Insets(5, 5, 0, 5);
            gridBagConstraints7.fill = GridBagConstraints.BOTH;
            gridBagConstraints7.gridheight = 1;
            gridBagConstraints7.gridy = 0;
            jContentAddTable = new JPanel();
            jContentAddTable.setLayout(new GridBagLayout());
            jContentAddTable.add(getJPanelAttributes(), gridBagConstraints7);
            jContentAddTable.add(getJButtonAddTable(), gridBagConstraints1);
            jContentAddTable.add(getJButtonAddCancel(), gridBagConstraints);
        }
        return jContentAddTable;
    }
    
    /**
     * This method initializes jContentPane2
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentConnection()
    {
        if (jContentConnection == null)
        {
            final GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
            gridBagConstraints18.gridx = 1;
            gridBagConstraints18.gridwidth = 3;
            gridBagConstraints18.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints18.weighty = 1.0;
            gridBagConstraints18.gridy = 1;
            final GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
            gridBagConstraints17.fill = GridBagConstraints.BOTH;
            gridBagConstraints17.weighty = 1.0;
            gridBagConstraints17.weightx = 1.0;
            final GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
            gridBagConstraints16.gridx = 2;
            gridBagConstraints16.anchor = GridBagConstraints.SOUTHEAST;
            gridBagConstraints16.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints16.gridy = 3;
            final GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
            gridBagConstraints15.gridx = 1;
            gridBagConstraints15.weightx = 1.0;
            gridBagConstraints15.anchor = GridBagConstraints.SOUTHEAST;
            gridBagConstraints15.insets = new Insets(5, 5, 5, 0);
            gridBagConstraints15.gridy = 3;
            final GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
            gridBagConstraints10.gridx = 1;
            gridBagConstraints10.gridwidth = 3;
            gridBagConstraints10.weighty = 1.0;
            gridBagConstraints10.anchor = GridBagConstraints.WEST;
            gridBagConstraints10.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints10.insets = new Insets(0, 0, 0, 0);
            gridBagConstraints10.gridy = 2;
            final GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
            gridBagConstraints9.gridx = 0;
            gridBagConstraints9.ipadx = 0;
            gridBagConstraints9.ipady = 0;
            gridBagConstraints9.anchor = GridBagConstraints.WEST;
            gridBagConstraints9.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints9.weightx = 0.0;
            gridBagConstraints9.weighty = 1.0;
            gridBagConstraints9.gridwidth = 3;
            gridBagConstraints9.gridy = 0;
            jContentConnection = new JPanel();
            jContentConnection.setLayout(new GridBagLayout());
            jContentConnection.add(getJPanelPlayerInfos(), gridBagConstraints9);
            jContentConnection.add(getJPanelAgentInfos(), gridBagConstraints18);
            jContentConnection.add(getJPanelServerInfos(), gridBagConstraints10);
            jContentConnection.add(getJButtonConnect(), gridBagConstraints15);
            jContentConnection.add(getJButtonConnectionCancel(), gridBagConstraints16);
            
        }
        return jContentConnection;
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
            final BorderLayout borderLayout = new BorderLayout();
            borderLayout.setHgap(0);
            borderLayout.setVgap(0);
            jContentPane = new JPanel();
            jContentPane.setLayout(borderLayout);
            jContentPane.add(getJScrollPaneTables(), BorderLayout.CENTER);
            jContentPane.add(getJPanel1(), BorderLayout.SOUTH);
        }
        return jContentPane;
    }
    
    /**
     * This method initializes jContentPaneAdvanced
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPaneAdvanced()
    {
        if (jContentPaneAdvanced == null)
        {
            final GridBagConstraints gridBagConstraints38 = new GridBagConstraints();
            gridBagConstraints38.fill = GridBagConstraints.BOTH;
            gridBagConstraints38.weighty = 1.0;
            gridBagConstraints38.weightx = 1.0;
            final GridBagConstraints gridBagConstraints37 = new GridBagConstraints();
            gridBagConstraints37.gridx = 0;
            gridBagConstraints37.ipadx = 0;
            gridBagConstraints37.anchor = GridBagConstraints.EAST;
            gridBagConstraints37.fill = GridBagConstraints.NONE;
            gridBagConstraints37.gridwidth = 1;
            gridBagConstraints37.gridheight = 1;
            gridBagConstraints37.insets = new Insets(5, 0, 5, 5);
            gridBagConstraints37.weighty = 0.0;
            gridBagConstraints37.gridy = 1;
            jContentPaneAdvanced = new JPanel();
            jContentPaneAdvanced.setLayout(new GridBagLayout());
            jContentPaneAdvanced.add(getJTabbedPaneTables(), gridBagConstraints38);
            jContentPaneAdvanced.add(getJButtonAdvancedOK(), gridBagConstraints37);
        }
        return jContentPaneAdvanced;
    }
    
    /**
     * This method initializes aboutDialog
     * 
     * @return javax.swing.JDialog
     */
    private JDialog getJDialogAbout()
    {
        if (jDialogAbout == null)
        {
            jDialogAbout = new JDialog(getJFrame(), true)
            {
                @Override
                public String getTitle()
                {
                    return m_bundle.get("about.title");
                }
            };
            jDialogAbout.setSize(new Dimension(327, 230));
            jDialogAbout.setContentPane(getContentAbout());
        }
        return jDialogAbout;
    }
    
    /**
     * This method initializes jDialogAddTable
     * 
     * @return javax.swing.JDialog
     */
    private JDialog getJDialogAddTable()
    {
        if (jDialogAddTable == null)
        {
            jDialogAddTable = new JDialog(getJFrame())
            {
                @Override
                public String getTitle()
                {
                    return m_bundle.get("addTable.title") + ":";
                }
            };
            jDialogAddTable.setSize(new Dimension(270, 261));
            jDialogAddTable.setModal(true);
            jDialogAddTable.setName("Add Table");
            jDialogAddTable.setResizable(false);
            jDialogAddTable.getRootPane().setDefaultButton(getJButtonAddTable());
            jDialogAddTable.setContentPane(getJContentAddTable());
        }
        return jDialogAddTable;
    }
    
    /**
     * This method initializes jDialogAdvanced
     * 
     * @return javax.swing.JDialog
     */
    private JDialog getJDialogAdvanced()
    {
        if (jDialogAdvanced == null)
        {
            jDialogAdvanced = new JDialog()
            {
                @Override
                public String getTitle()
                {
                    super.getTitle();
                    return m_bundle.get("advanced.title");
                }
            };
            jDialogAdvanced.setSize(new Dimension(382, 317));
            jDialogAdvanced.setContentPane(getJContentPaneAdvanced());
            jDialogAdvanced.getRootPane().setDefaultButton(getJButtonAdvancedOK());
            
        }
        return jDialogAdvanced;
    }
    
    /**
     * This method initializes jDialogConnection
     * 
     * @return javax.swing.JDialog
     */
    private JDialog getJDialogConnection()
    {
        if (jDialogConnection == null)
        {
            jDialogConnection = new JDialog(getJFrame())
            {
                @Override
                public String getTitle()
                {
                    return m_bundle.get("connect.title");
                }
            };
            jDialogConnection.setSize(new Dimension(245, 295));
            jDialogConnection.setModal(true);
            jDialogConnection.setContentPane(getJContentConnection());
            jDialogConnection.setResizable(false);
            jDialogConnection.getRootPane().setDefaultButton(getJButtonConnect());
        }
        return jDialogConnection;
    }
    
    /**
     * This method initializes jFrame
     * 
     * @return javax.swing.JFrame
     */
    private JFrame getJFrame()
    {
        if (jFrame == null)
        {
            jFrame = new JFrame();
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.addWindowListener(new java.awt.event.WindowAdapter()
            {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e)
                {
                    disconnect();
                    ClientLobby.this.getJFrame().setVisible(false);
                }
            });
            jFrame.setTitle(m_bundle.get("lobby.title"));
            jFrame.setJMenuBar(getJJMenuBar());
            jFrame.setSize(417, 345);
            jFrame.setContentPane(getJContentPane());
            jFrame.getRootPane().setDefaultButton(getJButtonJoin());
        }
        return jFrame;
    }
    
    /**
     * This method initializes jFrameCardsStrength
     * 
     * @return javax.swing.JFrame
     */
    private JFrame getJFrameHandsStrength()
    {
        if (jFrameHandsStrength == null)
        {
            jFrameHandsStrength = new JFrame()
            {
                @Override
                public String getTitle()
                {
                    return m_bundle.get("handsStrength.title");
                }
            };
            jFrameHandsStrength.setSize(new Dimension(235, 670));
            jFrameHandsStrength.setResizable(false);
            jFrameHandsStrength.add(getJBackgroundPanelHandsStrength(), BorderLayout.CENTER);
        }
        return jFrameHandsStrength;
    }
    
    /**
     * This method initializes jJMenuBar
     * 
     * @return javax.swing.JMenuBar
     */
    private JMenuBar getJJMenuBar()
    {
        if (jJMenuBar == null)
        {
            jJMenuBar = new JMenuBar();
            jJMenuBar.add(getJMenuFile());
            jJMenuBar.add(getJMenuEdit());
            jJMenuBar.add(getJMenuSettings());
            jJMenuBar.add(getJMenuHelp());
        }
        return jJMenuBar;
    }
    
    /**
     * This method initializes jListAddedObservers
     * 
     * @return javax.swing.JList
     */
    private JList getJListAddedObservers()
    {
        if (jListAddedObservers == null)
        {
            jListAddedObservers = new JList(m_generalObservers);
            jListAddedObservers.setVisibleRowCount(3);
        }
        return jListAddedObservers;
    }
    
    /**
     * This method initializes jMenu
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getJMenuEdit()
    {
        if (editMenu == null)
        {
            editMenu = new JMenu()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("lobby.menuEdit");
                }
            };
            editMenu.add(getJMenuItemRefresh());
            editMenu.add(getJMenuItemAddTable());
            editMenu.add(getJMenuItemSitIn());
            editMenu.add(getJMenuItemSitOut());
            editMenu.addMenuListener(new javax.swing.event.MenuListener()
            {
                public void menuCanceled(javax.swing.event.MenuEvent e)
                {
                }
                
                public void menuDeselected(javax.swing.event.MenuEvent e)
                {
                }
                
                public void menuSelected(javax.swing.event.MenuEvent e)
                {
                    getJMenuItemAddTable().setEnabled(isConnected());
                    getJMenuItemRefresh().setEnabled(isConnected());
                    getJMenuItemSitIn().setEnabled(!getJTablePokerTables().getSelectionModel().isSelectionEmpty() && (findClient() == null));
                    getJMenuItemSitOut().setEnabled(!getJTablePokerTables().getSelectionModel().isSelectionEmpty() && (findClient() != null));
                }
            });
        }
        return editMenu;
    }
    
    /**
     * This method initializes jMenu
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getJMenuFile()
    {
        if (fileMenu == null)
        {
            fileMenu = new JMenu()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("lobby.menuFile");
                }
            };
            fileMenu.add(getJMenuItemConnect());
            fileMenu.add(getJMenuItemDisconnect());
            fileMenu.add(getJMenuItemExit());
            fileMenu.addMenuListener(new javax.swing.event.MenuListener()
            {
                public void menuCanceled(javax.swing.event.MenuEvent e)
                {
                }
                
                public void menuDeselected(javax.swing.event.MenuEvent e)
                {
                }
                
                public void menuSelected(javax.swing.event.MenuEvent e)
                {
                    getJMenuItemConnect().setEnabled(!isConnected());
                    getJMenuItemDisconnect().setEnabled(isConnected());
                }
            });
        }
        return fileMenu;
    }
    
    /**
     * This method initializes jMenu
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getJMenuHelp()
    {
        if (helpMenu == null)
        {
            helpMenu = new JMenu()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("lobby.menuHelp");
                }
            };
            helpMenu.add(getJMenuItemAbout());
            helpMenu.add(getJMenuItemHandsStrength());
        }
        return helpMenu;
    }
    
    /**
     * This method initializes jMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItemAbout()
    {
        if (aboutMenuItem == null)
        {
            aboutMenuItem = new JMenuItem()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("lobby.menuHelp.about");
                }
            };
            aboutMenuItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    final JDialog aboutDialog = getJDialogAbout();
                    aboutDialog.pack();
                    final Point loc = getJFrame().getLocation();
                    loc.translate(20, 20);
                    aboutDialog.setLocation(loc);
                    aboutDialog.setVisible(true);
                }
            });
        }
        return aboutMenuItem;
    }
    
    /**
     * This method initializes jMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItemAddTable()
    {
        if (jMenuItemAddTable == null)
        {
            jMenuItemAddTable = new JMenuItem()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("lobby.menuEdit.addTable");
                }
            };
            jMenuItemAddTable.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK, true));
            jMenuItemAddTable.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    getJDialogAddTable().setVisible(true);
                }
            });
        }
        return jMenuItemAddTable;
    }
    
    /**
     * This method initializes jMenuItemCardsStrength
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenu getJMenuItemChangeLanguage()
    {
        if (jMenuItemChangeLanguage == null)
        {
            jMenuItemChangeLanguage = new JMenu()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("lobby.menuSettings.changeLanguage");
                }
            };
            jMenuItemChangeLanguage.add(getJRadioButtonMenuItemEnglish());
            jMenuItemChangeLanguage.add(getJRadioButtonMenuItemFrench());
            jMenuItemChangeLanguage.setVisible(false);
            
        }
        return jMenuItemChangeLanguage;
    }
    
    /**
     * This method initializes jMenuItemConnect
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItemConnect()
    {
        if (jMenuItemConnect == null)
        {
            jMenuItemConnect = new JMenuItem()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("lobby.menuFile.connect");
                }
            };
            jMenuItemConnect.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    if (isConnected())
                    {
                        disconnect();
                    }
                    
                    getJDialogConnection().setVisible(true);
                    
                    if (isConnected())
                    {
                        refreshTables();
                    }
                }
            });
        }
        return jMenuItemConnect;
    }
    
    /**
     * This method initializes jMenuItemDisconnect
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItemDisconnect()
    {
        if (jMenuItemDisconnect == null)
        {
            jMenuItemDisconnect = new JMenuItem()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("lobby.menuFile.disconnect");
                }
            };
            jMenuItemDisconnect.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    disconnect();
                }
            });
        }
        return jMenuItemDisconnect;
    }
    
    /**
     * This method initializes jMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItemExit()
    {
        if (exitMenuItem == null)
        {
            exitMenuItem = new JMenuItem()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("lobby.menuFile.exit");
                }
            };
            exitMenuItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    disconnect();
                    ClientLobby.this.getJFrame().setVisible(false);
                }
            });
        }
        return exitMenuItem;
    }
    
    /**
     * This method initializes jMenuItemCardsStrength
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItemHandsStrength()
    {
        if (jMenuItemCardsStrength == null)
        {
            jMenuItemCardsStrength = new JMenuItem()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("lobby.menuHelp.handsStrength");
                }
            };
            jMenuItemCardsStrength.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    getJFrameHandsStrength().setVisible(true);
                }
            });
        }
        return jMenuItemCardsStrength;
    }
    
    /**
     * This method initializes jMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItemRefresh()
    {
        if (jMenuItemRefresh == null)
        {
            jMenuItemRefresh = new JMenuItem()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("lobby.menuEdit.refresh");
                }
            };
            jMenuItemRefresh.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0, true));
            jMenuItemRefresh.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    refreshTables();
                }
            });
        }
        return jMenuItemRefresh;
    }
    
    /**
     * This method initializes jMenuItemSitIn
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItemSitIn()
    {
        if (jMenuItemSitIn == null)
        {
            jMenuItemSitIn = new JMenuItem()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("lobby.menuEdit.sitIn");
                }
            };
            jMenuItemSitIn.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    sitIN();
                }
            });
        }
        return jMenuItemSitIn;
    }
    
    /**
     * This method initializes jMenuItemSitOut
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItemSitOut()
    {
        if (jMenuItemSitOut == null)
        {
            jMenuItemSitOut = new JMenuItem()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("lobby.menuEdit.sitOut");
                }
            };
            jMenuItemSitOut.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    sitOut();
                }
            });
        }
        return jMenuItemSitOut;
    }
    
    /**
     * This method initializes jMenu
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getJMenuSettings()
    {
        if (helpSettings == null)
        {
            helpSettings = new JMenu()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("lobby.menuSettings");
                }
            };
            helpSettings.add(getJMenuItemChangeLanguage());
            helpSettings.setVisible(false);
        }
        return helpSettings;
    }
    
    /**
     * This method initializes jPanel1
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel1()
    {
        if (jPanel1 == null)
        {
            final GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
            gridBagConstraints28.gridx = 0;
            gridBagConstraints28.anchor = GridBagConstraints.WEST;
            gridBagConstraints28.insets = new Insets(0, 5, 0, 0);
            gridBagConstraints28.gridy = 0;
            final GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            gridBagConstraints8.anchor = GridBagConstraints.SOUTHEAST;
            gridBagConstraints8.weightx = 1.0;
            gridBagConstraints8.insets = new Insets(5, 0, 5, 5);
            gridBagConstraints8.gridx = 1;
            gridBagConstraints8.gridwidth = 1;
            jPanel1 = new JPanel();
            jPanel1.setLayout(new GridBagLayout());
            jPanel1.add(getJButtonJoin(), gridBagConstraints8);
            jPanel1.add(getJButtonAdvanced(), gridBagConstraints28);
        }
        return jPanel1;
    }
    
    /**
     * This method initializes jPanelAgentInfos
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanelAgentInfos()
    {
        if (jPanelAgentInfos == null)
        {
            final GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
            gridBagConstraints27.gridx = 1;
            gridBagConstraints27.anchor = GridBagConstraints.WEST;
            gridBagConstraints27.insets = new Insets(3, 1, 3, 5);
            gridBagConstraints27.fill = GridBagConstraints.NONE;
            gridBagConstraints27.gridy = 1;
            final GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
            gridBagConstraints26.gridx = 0;
            gridBagConstraints26.insets = new Insets(3, 5, 3, 5);
            gridBagConstraints26.anchor = GridBagConstraints.WEST;
            gridBagConstraints26.gridy = 1;
            jLabelAddViewer = new JLabel()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("connect.agentInfos.label.addViewer") + ":";
                }
            };
            jLabelAddViewer.setEnabled(false);
            final GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
            gridBagConstraints25.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints25.gridy = 0;
            gridBagConstraints25.weightx = 1.0;
            gridBagConstraints25.insets = new Insets(0, 5, 0, 5);
            gridBagConstraints25.anchor = GridBagConstraints.WEST;
            gridBagConstraints25.gridx = 1;
            final GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
            gridBagConstraints24.anchor = GridBagConstraints.WEST;
            gridBagConstraints24.insets = new Insets(0, 5, 3, 5);
            jLabelAgentType = new JLabel()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("connect.agentInfos.label.agentType") + ":";
                }
            };
            jLabelAgentType.setEnabled(false);
            jPanelAgentInfos = new JPanel();
            jPanelAgentInfos.setLayout(new GridBagLayout());
            jPanelAgentInfos.setBorder(BorderFactory.createTitledBorder(null, m_bundle.get("connect.agentInfos.title"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
            jPanelAgentInfos.setEnabled(false);
            jPanelAgentInfos.add(jLabelAgentType, gridBagConstraints24);
            jPanelAgentInfos.add(getJComboBoxAgentType(), gridBagConstraints25);
            jPanelAgentInfos.add(jLabelAddViewer, gridBagConstraints26);
            jPanelAgentInfos.add(getJCheckBoxAddViewer(), gridBagConstraints27);
        }
        return jPanelAgentInfos;
    }
    
    /**
     * This method initializes jPanelAttributes
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanelAttributes()
    {
        if (jPanelAttributes == null)
        {
            // GridBagConstraints gridBagConstraints6 = new
            // GridBagConstraints();
            // gridBagConstraints6.gridx = 0;
            // gridBagConstraints6.gridy = 5;
            final GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
            gridBagConstraints51.gridx = 0;
            gridBagConstraints51.anchor = GridBagConstraints.WEST;
            gridBagConstraints51.insets = new Insets(0, 10, 0, 0);
            gridBagConstraints51.gridy = 2;
            jLabelBigBlind = new JLabel()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("addTable.tableInfos.label.bigBlind") + ":";
                }
            };
            final GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
            gridBagConstraints41.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints41.gridy = 2;
            gridBagConstraints41.weightx = 0.0;
            gridBagConstraints41.weighty = 1.0;
            gridBagConstraints41.gridwidth = 2;
            gridBagConstraints41.anchor = GridBagConstraints.WEST;
            gridBagConstraints41.insets = new Insets(0, 8, 0, 20);
            gridBagConstraints41.ipadx = 0;
            gridBagConstraints41.gridx = 5;
            final GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
            gridBagConstraints31.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints31.gridy = 1;
            gridBagConstraints31.weightx = 0.0;
            gridBagConstraints31.gridwidth = 2;
            gridBagConstraints31.insets = new Insets(0, 8, 0, 20);
            gridBagConstraints31.anchor = GridBagConstraints.WEST;
            gridBagConstraints31.weighty = 1.0;
            gridBagConstraints31.gridx = 5;
            final GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
            gridBagConstraints21.gridx = 0;
            gridBagConstraints21.anchor = GridBagConstraints.WEST;
            gridBagConstraints21.insets = new Insets(0, 10, 0, 0);
            gridBagConstraints21.gridy = 1;
            jLabelGameType = new JLabel()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("addTable.tableInfos.label.gameType") + ":";
                }
            };
            final GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints5.gridy = 3;
            gridBagConstraints5.weightx = 0.0;
            gridBagConstraints5.gridwidth = 3;
            gridBagConstraints5.gridheight = 1;
            gridBagConstraints5.anchor = GridBagConstraints.NORTHWEST;
            gridBagConstraints5.weighty = 1.0;
            gridBagConstraints5.insets = new Insets(5, 1, 0, 20);
            gridBagConstraints5.gridx = 5;
            final GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.gridx = 0;
            gridBagConstraints4.anchor = GridBagConstraints.NORTHWEST;
            gridBagConstraints4.gridwidth = 1;
            gridBagConstraints4.gridheight = 1;
            gridBagConstraints4.weightx = 0.0;
            gridBagConstraints4.insets = new Insets(5, 10, 0, 0);
            gridBagConstraints4.gridy = 3;
            jLabelNbPlayers = new JLabel()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("addTable.tableInfos.label.nbPlayers") + ":";
                }
            };
            jLabelNbPlayers.setComponentOrientation(ComponentOrientation.UNKNOWN);
            jLabelNbPlayers.setHorizontalTextPosition(SwingConstants.TRAILING);
            final GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints3.gridwidth = 3;
            gridBagConstraints3.gridx = 4;
            gridBagConstraints3.gridy = 0;
            gridBagConstraints3.weightx = 0.0;
            gridBagConstraints3.gridheight = 1;
            gridBagConstraints3.anchor = GridBagConstraints.WEST;
            gridBagConstraints3.weighty = 1.0;
            gridBagConstraints3.insets = new Insets(0, 8, 0, 20);
            final GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.insets = new Insets(0, 10, 0, 0);
            gridBagConstraints2.gridy = 0;
            gridBagConstraints2.gridwidth = 1;
            gridBagConstraints2.gridheight = 1;
            gridBagConstraints2.anchor = GridBagConstraints.WEST;
            gridBagConstraints2.ipadx = 0;
            gridBagConstraints2.gridx = 0;
            jLabelName = new JLabel()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("addTable.tableInfos.label.name") + ":";
                }
            };
            jPanelAttributes = new JPanel();
            jPanelAttributes.setLayout(new GridBagLayout());
            jPanelAttributes.setName("jPanelAttributes");
            jPanelAttributes.setBorder(BorderFactory.createTitledBorder(null, m_bundle.get("addTable.tableInfos.title"), TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
            jPanelAttributes.add(jLabelName, gridBagConstraints2);
            jPanelAttributes.add(getJTextFieldTableName(), gridBagConstraints3);
            jPanelAttributes.add(jLabelNbPlayers, gridBagConstraints4);
            jPanelAttributes.add(getJSliderNbPlayers(), gridBagConstraints5);
            jPanelAttributes.add(jLabelGameType, gridBagConstraints21);
            jPanelAttributes.add(getJComboBoxGameType(), gridBagConstraints31);
            jPanelAttributes.add(getJSpinnerBigBlind(), gridBagConstraints41);
            jPanelAttributes.add(jLabelBigBlind, gridBagConstraints51);
            // jPanelAttributes.add(getJPanel(), gridBagConstraints6);
        }
        return jPanelAttributes;
    }
    
    /**
     * This method initializes jPanelObseverInfos
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanelObseverInfos()
    {
        if (jPanelObseverInfos == null)
        {
            final GridBagConstraints gridBagConstraints35 = new GridBagConstraints();
            gridBagConstraints35.gridx = 2;
            gridBagConstraints35.gridheight = 1;
            gridBagConstraints35.weighty = 1.0;
            gridBagConstraints35.anchor = GridBagConstraints.NORTH;
            gridBagConstraints35.insets = new Insets(5, 0, 0, 0);
            gridBagConstraints35.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints35.gridy = 1;
            final GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
            gridBagConstraints34.gridx = 2;
            gridBagConstraints34.gridheight = 1;
            gridBagConstraints34.insets = new Insets(0, 0, 0, 0);
            gridBagConstraints34.weighty = 0.0;
            gridBagConstraints34.anchor = GridBagConstraints.CENTER;
            gridBagConstraints34.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints34.weightx = 0.0;
            gridBagConstraints34.gridy = 0;
            final GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
            gridBagConstraints33.fill = GridBagConstraints.BOTH;
            gridBagConstraints33.gridy = 1;
            gridBagConstraints33.weightx = 0.1;
            gridBagConstraints33.weighty = 0.1;
            gridBagConstraints33.insets = new Insets(5, 10, 10, 10);
            gridBagConstraints33.gridx = 1;
            final GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
            
            gridBagConstraints32.gridy = 0;
            gridBagConstraints32.weightx = 0.8;
            gridBagConstraints32.insets = new Insets(5, 10, 5, 10);
            gridBagConstraints32.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints32.gridx = 1;
            final GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
            gridBagConstraints30.gridx = 0;
            gridBagConstraints30.anchor = GridBagConstraints.NORTHWEST;
            gridBagConstraints30.insets = new Insets(5, 0, 0, 0);
            gridBagConstraints30.gridy = 1;
            jLabelAddedObservers = new JLabel()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("advanced.observerInfos.label.addedObservers") + ":";
                }
            };
            final GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
            gridBagConstraints29.gridx = 0;
            gridBagConstraints29.anchor = GridBagConstraints.WEST;
            gridBagConstraints29.gridy = 0;
            jLabelObserverType = new JLabel()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("advanced.observerInfos.label.observerType") + ":";
                }
            };
            jPanelObseverInfos = new JPanel();
            jPanelObseverInfos.setLayout(new GridBagLayout());
            jPanelObseverInfos.setBorder(BorderFactory.createTitledBorder(null, m_bundle.get("advanced.observerInfos.title"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
            jPanelObseverInfos.add(jLabelObserverType, gridBagConstraints29);
            jPanelObseverInfos.add(jLabelAddedObservers, gridBagConstraints30);
            jPanelObseverInfos.add(getJComboBoxObserverType(), gridBagConstraints32);
            jPanelObseverInfos.add(getJListAddedObservers(), gridBagConstraints33);
            jPanelObseverInfos.add(getJButtonAddObserver(), gridBagConstraints34);
            jPanelObseverInfos.add(getJButtonRemoveObserver(), gridBagConstraints35);
        }
        return jPanelObseverInfos;
    }
    
    /**
     * This method initializes jPanelPlayerInfos
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanelPlayerInfos()
    {
        if (jPanelPlayerInfos == null)
        {
            final GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
            gridBagConstraints23.insets = new Insets(3, 5, 3, 5);
            gridBagConstraints23.gridy = 1;
            gridBagConstraints23.anchor = GridBagConstraints.WEST;
            gridBagConstraints23.gridx = 0;
            final GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
            gridBagConstraints22.insets = new Insets(3, 1, 3, 5);
            gridBagConstraints22.gridy = 1;
            gridBagConstraints22.anchor = GridBagConstraints.WEST;
            gridBagConstraints22.weightx = 0.0;
            gridBagConstraints22.gridx = 1;
            final GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
            gridBagConstraints20.fill = GridBagConstraints.BOTH;
            gridBagConstraints20.gridx = 1;
            gridBagConstraints20.gridy = 0;
            gridBagConstraints20.weightx = 1.0;
            gridBagConstraints20.anchor = GridBagConstraints.WEST;
            gridBagConstraints20.insets = new Insets(0, 5, 0, 5);
            final GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
            gridBagConstraints19.insets = new Insets(3, 5, 3, 5);
            gridBagConstraints19.gridy = 0;
            gridBagConstraints19.anchor = GridBagConstraints.WEST;
            gridBagConstraints19.gridx = 0;
            jLabelIsAgent = new JLabel()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("connect.playerInfos.label.isAgent") + ":";
                }
            };
            jLabelUsername = new JLabel()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("connect.playerInfos.label.playerName") + ":";
                }
            };
            jLabelUsername.setName("jLabelUsername");
            jPanelPlayerInfos = new JPanel();
            jPanelPlayerInfos.setLayout(new GridBagLayout());
            jPanelPlayerInfos.setBorder(BorderFactory.createTitledBorder(null, m_bundle.get("connect.playerInfos.title"), TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
            jPanelPlayerInfos.add(jLabelUsername, gridBagConstraints19);
            jPanelPlayerInfos.add(getJTextFieldPlayerName(), gridBagConstraints20);
            jPanelPlayerInfos.add(getJCheckBoxIsAgent(), gridBagConstraints22);
            jPanelPlayerInfos.add(jLabelIsAgent, gridBagConstraints23);
        }
        return jPanelPlayerInfos;
    }
    
    /**
     * This method initializes jPanelServerInfos
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanelServerInfos()
    {
        if (jPanelServerInfos == null)
        {
            final GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.gridx = 0;
            gridBagConstraints11.insets = new Insets(0, 5, 3, 5);
            gridBagConstraints11.anchor = GridBagConstraints.WEST;
            gridBagConstraints11.gridy = 0;
            final GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
            gridBagConstraints12.gridx = 0;
            gridBagConstraints12.insets = new Insets(3, 5, 3, 5);
            gridBagConstraints12.anchor = GridBagConstraints.WEST;
            gridBagConstraints12.gridy = 1;
            final GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
            gridBagConstraints13.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints13.gridy = 1;
            gridBagConstraints13.weightx = 1.0;
            gridBagConstraints13.anchor = GridBagConstraints.WEST;
            gridBagConstraints13.insets = new Insets(3, 5, 3, 5);
            gridBagConstraints13.gridx = 2;
            final GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
            gridBagConstraints14.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints14.gridy = -1;
            gridBagConstraints14.weightx = 1.0;
            gridBagConstraints14.insets = new Insets(0, 5, 3, 5);
            gridBagConstraints14.ipadx = 0;
            gridBagConstraints14.anchor = GridBagConstraints.WEST;
            gridBagConstraints14.gridx = 2;
            jLabelServerPort = new JLabel()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("connect.serverInfos.label.serverPort") + ":";
                }
            };
            jLabelServerHost = new JLabel()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("connect.serverInfos.label.serverHost") + ":";
                }
            };
            jPanelServerInfos = new JPanel();
            jPanelServerInfos.setLayout(new GridBagLayout());
            jPanelServerInfos.setBorder(BorderFactory.createTitledBorder(null, m_bundle.get("connect.serverInfos.title"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
            jPanelServerInfos.add(getJTextFieldServerHost(), gridBagConstraints14);
            jPanelServerInfos.add(getJTextFieldServerPort(), gridBagConstraints13);
            jPanelServerInfos.add(jLabelServerPort, gridBagConstraints12);
            jPanelServerInfos.add(jLabelServerHost, gridBagConstraints11);
        }
        return jPanelServerInfos;
    }
    
    /**
     * This method initializes jCheckBoxMenuItemEnglish
     * 
     * @return javax.swing.JCheckBoxMenuItem
     */
    private JRadioButtonMenuItem getJRadioButtonMenuItemEnglish()
    {
        if (jRadioButtonMenuItemEnglish == null)
        {
            jRadioButtonMenuItemEnglish = new JRadioButtonMenuItem()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("lobby.menuSettings.changeLanguage.english");
                }
            };
            jRadioButtonMenuItemEnglish.setSelected(true);
            languageGroup.add(jRadioButtonMenuItemEnglish);
            jRadioButtonMenuItemEnglish.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    m_bundle.setLocale(Locale.ENGLISH);
                    ClientLobby.this.updateUI();
                }
            });
        }
        return jRadioButtonMenuItemEnglish;
    }
    
    /**
     * This method initializes jRadioButtonMenuItemFrench
     * 
     * @return javax.swing.JRadioButtonMenuItem
     */
    private JRadioButtonMenuItem getJRadioButtonMenuItemFrench()
    {
        if (jRadioButtonMenuItemFrench == null)
        {
            jRadioButtonMenuItemFrench = new JRadioButtonMenuItem()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("lobby.menuSettings.changeLanguage.french");
                }
            };
            jRadioButtonMenuItemFrench.setSelected(false);
            languageGroup.add(jRadioButtonMenuItemFrench);
            jRadioButtonMenuItemFrench.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    m_bundle.setLocale(Locale.FRENCH);
                    ClientLobby.this.updateUI();
                }
            });
        }
        return jRadioButtonMenuItemFrench;
    }
    
    /**
     * This method initializes jScrollPaneTables
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPaneTables()
    {
        if (jScrollPaneTables == null)
        {
            jScrollPaneTables = new JScrollPane();
            jScrollPaneTables.setViewportView(getJTablePokerTables());
            jScrollPaneTables.setFont(new Font("Tahoma", Font.BOLD, 12));
        }
        return jScrollPaneTables;
    }
    
    /**
     * This method initializes jSliderNbPlayers
     * 
     * @return javax.swing.JSlider
     */
    private JSlider getJSliderNbPlayers()
    {
        if (jSliderNbPlayers == null)
        {
            jSliderNbPlayers = new JSlider();
            jSliderNbPlayers.setMaximum(9);
            jSliderNbPlayers.setMajorTickSpacing(1);
            jSliderNbPlayers.setPaintTicks(true);
            jSliderNbPlayers.setMinimum(2);
            jSliderNbPlayers.setSnapToTicks(true);
            jSliderNbPlayers.setPaintLabels(true);
            jSliderNbPlayers.setMinorTickSpacing(0);
        }
        return jSliderNbPlayers;
    }
    
    /**
     * This method initializes jListBigBlind
     * 
     * @return javax.swing.JList
     */
    private JSpinner getJSpinnerBigBlind()
    {
        if (jSpinnerBigBlind == null)
        {
            jSpinnerBigBlind = new JSpinner();
            jSpinnerBigBlind.setModel(new SpinnerNumberModel(10, 0, 100000000, 1));
            jSpinnerBigBlind.setName("jSpinnerBigBlind");
            jSpinnerBigBlind.setEditor(new CurrencyIntegerEditor(jSpinnerBigBlind));
        }
        return jSpinnerBigBlind;
    }
    
    /**
     * This method initializes jTabbedPaneTables
     * 
     * @return javax.swing.JTabbedPane
     */
    private JTabbedPanePokerClient getJTabbedPaneTables()
    {
        if (jTabbedPaneTables == null)
        {
            jTabbedPaneTables = new JTabbedPanePokerClient();
            m_clients.addListListener(jTabbedPaneTables);
            jTabbedPaneTables.addTab(m_bundle.get("advanced.generalTab.name"), getJPanelObseverInfos());
        }
        return jTabbedPaneTables;
    }
    
    /**
     * This method initializes jTablePokerTables
     * 
     * @return javax.swing.JTable
     */
    private JTable getJTablePokerTables()
    {
        if (jTablePokerTables == null)
        {
            final String[] columnsName = new String[] { m_bundle.get("lobby.jTable.header.ID"), m_bundle.get("lobby.jTable.header.name"), m_bundle.get("lobby.jTable.header.gameType"), m_bundle.get("lobby.jTable.header.bigBlind"), m_bundle.get("lobby.jTable.header.nbPlayers"), };
            
            final DefaultTableModel defaultTableModel = new DefaultTableModel(columnsName, 0)
            {
                @Override
                public boolean isCellEditable(int row, int column)
                {
                    return false;
                }
            };
            
            jTablePokerTables = new JTable();
            jTablePokerTables.setAutoCreateColumnsFromModel(true);
            jTablePokerTables.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            jTablePokerTables.setShowHorizontalLines(true);
            jTablePokerTables.setShowVerticalLines(true);
            jTablePokerTables.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
            jTablePokerTables.setModel(defaultTableModel);
            jTablePokerTables.setShowGrid(true);
        }
        return jTablePokerTables;
    }
    
    /**
     * This method initializes jTextFieldPlayerName
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getJTextFieldPlayerName()
    {
        if (jTextFieldPlayerName == null)
        {
            jTextFieldPlayerName = new JTextField();
            jTextFieldPlayerName.setColumns(13);
            jTextFieldPlayerName.setText(m_bundle.get("connect.playerInfos.playerName.defaultPlayer"));
            jTextFieldPlayerName.setName("jTextFieldPlayerName");
        }
        return jTextFieldPlayerName;
    }
    
    /**
     * This method initializes jTextFieldServerHost
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getJTextFieldServerHost()
    {
        if (jTextFieldServerHost == null)
        {
            jTextFieldServerHost = new JTextField();
            jTextFieldServerHost.setText(String.valueOf(Constants.DEFAULT_HOST));
        }
        return jTextFieldServerHost;
    }
    
    /**
     * This method initializes jTextFieldServerPort
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getJTextFieldServerPort()
    {
        if (jTextFieldServerPort == null)
        {
            jTextFieldServerPort = new JTextField();
            jTextFieldServerPort.setText(String.valueOf(Constants.DEFAULT_NO_PORT));
            jTextFieldServerPort.setColumns(4);
        }
        return jTextFieldServerPort;
    }
    
    /**
     * This method initializes jTextFieldTableName
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getJTextFieldTableName()
    {
        if (jTextFieldTableName == null)
        {
            jTextFieldTableName = new JTextField();
            jTextFieldTableName.setColumns(10);
            jTextFieldTableName.setText(m_bundle.get("addTable.tableInfos.tableName.default"));
        }
        return jTextFieldTableName;
    }
    
    /**
     * Get the port number of a specific table.
     * 
     * @param p_table
     *            - Name of the table.
     * @return
     *         The port number associated to the table name <br/>
     *         or <b>null</b> if not found.
     */
    @SuppressWarnings("unused")
    public Integer getNoPort(String p_table)
    {
        // Ask the server for all available tables.
        send(new LobbyListTableCommand().encodeCommand());
        
        final StringTokenizer token = new StringTokenizer(receive(), Constants.DELIMITER);
        
        // Parse results until the right table name matches.
        while (token.hasMoreTokens())
        {
            final ClientPokerTableInfo table = new ClientPokerTableInfo();
            final Integer noPort = Integer.parseInt(token.nextToken());
            final String tableName = token.nextToken();
            final TypePokerGame gameType = TypePokerGame.valueOf(token.nextToken());
            final int bigBlind = Integer.parseInt(token.nextToken());
            final int nbPlayers = Integer.parseInt(token.nextToken());
            final int nbSeats = Integer.parseInt(token.nextToken());
            
            if (tableName.equalsIgnoreCase(p_table))
            {
                return noPort;
            }
        }
        
        return null;
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
            toTable.println(new BluffinAuthentificationCommand(m_playerName).encodeCommand());
            if (!Boolean.parseBoolean(fromTable.readLine()))
            {
                System.out.println("Authentification failed on the table: " + p_tableName);
                return false;
            }
            
            // Build query.
            final StringBuilder sb = new StringBuilder();
            sb.append(TypeMessageTableManager.JOIN_TABLE);
            sb.append(Constants.DELIMITER);
            sb.append(p_tableName);
            sb.append(Constants.DELIMITER);
            sb.append(m_playerName);
            sb.append(Constants.DELIMITER);
            
            // Send query.
            toTable.println(sb.toString());
            
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
            for (final TypeObserver observer : m_generalObservers)
            {
                observers.add(FactoryObserver.create(observer));
            }
            
            if (m_isAgent)
            {
                if (m_agentType == TypeAgent.AI_SVM)
                {
                    final StatsAgent statsAgent = new StatsAgent();
                    final PokerAI pokerAgent = new PokerSVM(statsAgent, m_playerName);
                    m_agent = pokerAgent;
                    observers.add(statsAgent);
                    observers.add(pokerAgent);
                }
                else
                {
                    final PokerAI pokerAgent = FactoryAgent.create(m_agentType);
                    m_agent = pokerAgent;
                    observers.add(pokerAgent);
                }
            }
            else
            {
                // final StatsAgent statsAgent = new StatsAgent();
                // final PokerSVM pokerSVM = new PokerSVM(statsAgent, m_playerName);
                // final GUI gui = new GUIAdvisor(statsAgent, pokerSVM);
                // m_agent = gui;
                // observers.add(statsAgent);
                // observers.add(pokerSVM);
                // observers.add(gui);
                final GUI gui = new GUI();
                m_agent = gui;
                observers.add(gui);
            }
            
            // Start a the new PokerClient.
            final PokerClient client = new PokerClient(m_agent, observers, localPlayer, tableSocket, table, fromTable);
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
     * Refresh the list of available tables on the ServerLobby.
     */
    public void refreshTables()
    {
        final DefaultTableModel model = (DefaultTableModel) getJTablePokerTables().getModel();
        model.setRowCount(0);
        
        // Ask the server for all available tables.
        send(new LobbyListTableCommand().encodeCommand());
        
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
        
        // Select the first available table in the JTable.
        getJTablePokerTables().getSelectedRow();
        final DefaultListSelectionModel selection = new DefaultListSelectionModel();
        selection.setSelectionInterval(0, 0);
        
        if ((model.getRowCount() > 0) && (getJTablePokerTables().getSelectedRow() == -1))
        {
            getJTablePokerTables().setSelectionModel(selection);
        }
    }
    
    /**
     * Send a message to a ServerLobby.
     * 
     * @param p_msg
     *            - Message to send to the ServerLobby.
     */
    public boolean send(String p_msg)
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
    
    /**
     * Set if a viewer will be attached to the player.
     * 
     * @param p_addViewer
     *            - Attach a viewer to the player.
     */
    public void setAddViewer(Boolean p_addViewer)
    {
        m_addViewer = p_addViewer;
        
        // Update the General tab in advanced settings.
        if (m_addViewer)
        {
            m_generalObservers.add(TypeObserver.VIEWER);
        }
    }
    
    /**
     * Set the type of the agent.
     * 
     * @param p_agentType
     *            - New agent type.
     */
    public void setAgentType(TypeAgent p_agentType)
    {
        m_agentType = p_agentType;
    }
    
    /**
     * Set if the player is an agent or not.
     * 
     * @param p_isAgent
     *            - Is the player agent.
     */
    public void setIsAgent(Boolean p_isAgent)
    {
        m_isAgent = p_isAgent;
    }
    
    /**
     * Set the player name.
     * 
     * @param p_name
     *            - New player name.
     */
    public void setPlayerName(String p_name)
    {
        m_playerName = p_name;
    }
    
    private void sitIN()
    {
        if (getJTablePokerTables().getSelectionModel().isSelectionEmpty())
        {
            return;
        }
        
        final int index = getJTablePokerTables().getSelectedRow();
        final int noPort = (Integer) getJTablePokerTables().getModel().getValueAt(index, 0);
        final String tableName = (String) getJTablePokerTables().getModel().getValueAt(index, 1);
        
        if (findClient() != null)
        {
            System.out.println("You are already sitting on the table: " + tableName);
        }
        else
        {
            final int bigBlind = (Integer) getJTablePokerTables().getModel().getValueAt(index, 3);
            if (!joinTable(noPort, tableName, bigBlind))
            {
                System.out.println("Table '" + tableName + "' does not exist anymore.");
                refreshTables();
            }
        }
    }
    
    private void sitOut()
    {
        if (getJTablePokerTables().getSelectionModel().isSelectionEmpty())
        {
            return;
        }
        
        final int index = getJTablePokerTables().getSelectedRow();
        final String tableName = (String) getJTablePokerTables().getModel().getValueAt(index, 1);
        
        final PokerClient client = findClient();
        
        if (client == null)
        {
            System.out.println("You are not sitting on the table: " + tableName);
        }
        else
        {
            client.disconnect();
            m_clients.remove(client);
        }
    }
    
    private void updateUI()
    {
        getJDialogAdvanced().repaint();
        getJDialogAbout().repaint();
        getJDialogAddTable().repaint();
        getJDialogConnection().repaint();
        getJDialogAdvanced().setTitle(m_bundle.get("advanced.title"));
        getJDialogAbout().setTitle(m_bundle.get("about.title"));
        getJDialogAddTable().setTitle(m_bundle.get("addTable.title"));
        getJDialogConnection().setTitle(m_bundle.get("connect.title"));
    }
    
}
