package pokerClientLobbyGUI;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

public class TempLobbyNameUsedJDialog extends JDialog
{
    private String m_playerName;
    
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JButton jConnectButton = null;
    private JLabel jPlayerNameLabel = null;
    private JTextField jPlayerNameTextField = null;
    
    /**
     * @param owner
     */
    public TempLobbyNameUsedJDialog(Frame owner, String name)
    {
        
        super(owner);
        m_playerName = name;
        initialize();
        pack();
        setLocationRelativeTo(owner);
    }
    
    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize()
    {
        this.setSize(250, 131);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setModal(true);
        this.setTitle("'" + m_playerName + "' already used");
        this.setContentPane(getJContentPane());
        this.getRootPane().setDefaultButton(getJConnectButton());
    }
    
    @Override
    protected JRootPane createRootPane()
    {
        final KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        final JRootPane rootPane = super.createRootPane();
        rootPane.registerKeyboardAction(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                setVisible(false);
            }
        }, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
        return rootPane;
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
            jPlayerNameLabel = new JLabel();
            jPlayerNameLabel.setBounds(new Rectangle(10, 10, 100, 16));
            jPlayerNameLabel.setText("New Name:");
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.setPreferredSize(new Dimension(230, 91));
            jContentPane.add(getJConnectButton(), null);
            jContentPane.add(jPlayerNameLabel, null);
            jContentPane.add(getJPlayerNameTextField(), null);
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
            jConnectButton.setText("Modify");
            jConnectButton.setBounds(new Rectangle(60, 58, 98, 26));
            jConnectButton.setSelected(true);
            jConnectButton.setName("jConnectButton");
            jConnectButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    m_playerName = getJPlayerNameTextField().getText();
                    TempLobbyNameUsedJDialog.this.setVisible(false);
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
            jPlayerNameTextField.setText(m_playerName);
        }
        return jPlayerNameTextField;
    }
    
    public String getPlayerName()
    {
        return m_playerName;
    }
    
} // @jve:decl-index=0:visual-constraint="10,10"
