package clientLobbyGUI;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LobbyConnectJDialog extends JDialog
{
    private String m_playerName;
    private String m_serverAddress;
    private int m_serverPort;
    private boolean m_OK;
    
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JButton jConnectButton = null;
    private JLabel jPlayerNameLabel = null;
    private JTextField jPlayerNameTextField = null;
    private JLabel jAdressLabel = null;
    private JComboBox jAddressComboBox = null;
    private JLabel jPortLabel = null;
    private JTextField jPortTextField = null;
    
    /**
     * @param owner
     */
    public LobbyConnectJDialog(Frame owner)
    {
        
        super(owner);
        initialize();
    }
    
    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize()
    {
        m_OK = false;
        this.setSize(250, 220);
        this.setModal(true);
        this.setTitle("Connection");
        this.setContentPane(getJContentPane());
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
            jPortLabel = new JLabel();
            jPortLabel.setBounds(new Rectangle(10, 100, 92, 16));
            jPortLabel.setText("Server Port:");
            jAdressLabel = new JLabel();
            jAdressLabel.setBounds(new Rectangle(10, 55, 100, 16));
            jAdressLabel.setText("Server Address:");
            jPlayerNameLabel = new JLabel();
            jPlayerNameLabel.setBounds(new Rectangle(10, 10, 100, 16));
            jPlayerNameLabel.setText("Player Name:");
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.add(getJConnectButton(), null);
            jContentPane.add(jPlayerNameLabel, null);
            jContentPane.add(getJPlayerNameTextField(), null);
            jContentPane.add(jAdressLabel, null);
            jContentPane.add(getJAddressComboBox(), null);
            jContentPane.add(jPortLabel, null);
            jContentPane.add(getJPortTextField(), null);
        }
        return jContentPane;
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
            jConnectButton.setBounds(new Rectangle(66, 148, 98, 26));
            jConnectButton.setName("jConnectButton");
            jConnectButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    m_playerName = getJPlayerNameTextField().getText();
                    m_serverAddress = getJAddressComboBox().getSelectedItem().toString();
                    m_serverPort = Integer.parseInt(getJPortTextField().getText());
                    m_OK = true;
                    LobbyConnectJDialog.this.setVisible(false);
                }
            });
        }
        return jConnectButton;
    }
    
    /**
     * This method initializes jPlayerNameTextField
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getJPlayerNameTextField()
    {
        if (jPlayerNameTextField == null)
        {
            jPlayerNameTextField = new JTextField();
            jPlayerNameTextField.setBounds(new Rectangle(10, 30, 200, 20));
            jPlayerNameTextField.setText("Player");
        }
        return jPlayerNameTextField;
    }
    
    /**
     * This method initializes jAddressComboBox
     * 
     * @return javax.swing.JComboBox
     */
    private JComboBox getJAddressComboBox()
    {
        if (jAddressComboBox == null)
        {
            jAddressComboBox = new JComboBox();
            jAddressComboBox.setLocation(new Point(10, 75));
            jAddressComboBox.setEditable(true);
            jAddressComboBox.addItem("127.0.0.1");
            jAddressComboBox.addItem("SRV-PRJ-05.dmi.usherb.ca");
            jAddressComboBox.setSelectedIndex(0);
            jAddressComboBox.setSize(new Dimension(200, 20));
        }
        return jAddressComboBox;
    }
    
    /**
     * This method initializes jPortTextField
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getJPortTextField()
    {
        if (jPortTextField == null)
        {
            jPortTextField = new JTextField();
            jPortTextField.setBounds(new Rectangle(10, 120, 50, 20));
            jPortTextField.setText("4242");
        }
        return jPortTextField;
    }
    
    public boolean isOK()
    {
        return m_OK;
    }
    
    public String getPlayerName()
    {
        return m_playerName;
    }
    
    public String getServerAddress()
    {
        return m_serverAddress;
    }
    
    public int getServerPort()
    {
        return m_serverPort;
    }
    
} // @jve:decl-index=0:visual-constraint="10,10"
