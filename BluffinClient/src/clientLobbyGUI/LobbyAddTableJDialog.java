package clientLobbyGUI;

import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;

import pokerLogic.TypePokerGame;

public class LobbyAddTableJDialog extends JDialog
{
    private final String m_playerName;
    
    private boolean m_OK;
    private String m_tableName;
    private TypePokerGame m_gameType;
    private int m_bigBlind;
    private int m_nbPlayer;
    
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JLabel jTableNameLabel = null;
    private JTextField jTableNameTextField = null;
    private JLabel jGameTypeLabel = null;
    private JComboBox jGameTypeComboBox = null;
    private JLabel jBigBlindLabel = null;
    private JSpinner jBigBlindSpinner = null;
    private JLabel jNbPlayersLabel = null;
    private JSlider jNbPlayersSlider = null;
    private JButton jAddButton = null;
    
    /**
     * @param owner
     */
    public LobbyAddTableJDialog(Frame owner, String playerName, int nbPlayers)
    {
        super(owner);
        pack();
        setLocationRelativeTo(owner);
        m_playerName = playerName;
        initialize();
        if (nbPlayers > 2)
        {
            getJNbPlayersSlider().setMinimum(nbPlayers);
        }
    }
    
    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize()
    {
        this.setSize(244, 301);
        this.setModal(true);
        this.setTitle("Add Table");
        this.setContentPane(getJContentPane());
        this.getRootPane().setDefaultButton(getJAddButton());
        m_OK = false;
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
            jNbPlayersLabel = new JLabel();
            jNbPlayersLabel.setBounds(new Rectangle(10, 145, 100, 16));
            jNbPlayersLabel.setText("Nb of players:");
            jBigBlindLabel = new JLabel();
            jBigBlindLabel.setBounds(new Rectangle(10, 100, 100, 16));
            jBigBlindLabel.setText("Big Blind:");
            jGameTypeLabel = new JLabel();
            jGameTypeLabel.setBounds(new Rectangle(10, 55, 100, 16));
            jGameTypeLabel.setText("Game Type:");
            jTableNameLabel = new JLabel();
            jTableNameLabel.setBounds(new Rectangle(10, 10, 100, 16));
            jTableNameLabel.setText("Table Name:");
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.add(jTableNameLabel, null);
            jContentPane.add(getJTableNameTextField(), null);
            jContentPane.add(jGameTypeLabel, null);
            jContentPane.add(getJGameTypeComboBox(), null);
            jContentPane.add(jBigBlindLabel, null);
            jContentPane.add(getJBigBlindSpinner(), null);
            jContentPane.add(jNbPlayersLabel, null);
            jContentPane.add(getJNbPlayersSlider(), null);
            jContentPane.add(getJAddButton(), null);
        }
        return jContentPane;
    }
    
    /**
     * This method initializes jTableNameTextField
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getJTableNameTextField()
    {
        if (jTableNameTextField == null)
        {
            jTableNameTextField = new JTextField();
            jTableNameTextField.setBounds(new Rectangle(10, 30, 200, 20));
            jTableNameTextField.setText(m_playerName + " Table");
        }
        return jTableNameTextField;
    }
    
    /**
     * This method initializes jGameTypeComboBox
     * 
     * @return javax.swing.JComboBox
     */
    private JComboBox getJGameTypeComboBox()
    {
        if (jGameTypeComboBox == null)
        {
            jGameTypeComboBox = new JComboBox();
            jGameTypeComboBox.setBounds(new Rectangle(10, 75, 200, 20));
            jGameTypeComboBox.setModel(new DefaultComboBoxModel(TypePokerGame.values()));
            jGameTypeComboBox.setSelectedItem(TypePokerGame.NO_LIMIT);
        }
        return jGameTypeComboBox;
    }
    
    /**
     * This method initializes jBigBlindTextField
     * 
     * @return javax.swing.JTextField
     */
    private JSpinner getJBigBlindSpinner()
    {
        if (jBigBlindSpinner == null)
        {
            jBigBlindSpinner = new JSpinner();
            jBigBlindSpinner.setModel(new SpinnerNumberModel(10, 10, 1000, 10));
            jBigBlindSpinner.setBounds(new Rectangle(10, 120, 75, 20));
            jBigBlindSpinner.setEditor(new JSpinner.NumberEditor(jBigBlindSpinner, "#"));
        }
        return jBigBlindSpinner;
    }
    
    /**
     * This method initializes jNbPlayersSlider
     * 
     * @return javax.swing.JSlider
     */
    private JSlider getJNbPlayersSlider()
    {
        if (jNbPlayersSlider == null)
        {
            jNbPlayersSlider = new JSlider();
            jNbPlayersSlider.setBounds(new Rectangle(10, 165, 200, 47));
            jNbPlayersSlider.setPaintTicks(true);
            jNbPlayersSlider.setMinimum(2);
            jNbPlayersSlider.setMaximum(9);
            jNbPlayersSlider.setMajorTickSpacing(1);
            jNbPlayersSlider.setMinorTickSpacing(1);
            jNbPlayersSlider.setPaintLabels(true);
            jNbPlayersSlider.setSnapToTicks(true);
        }
        return jNbPlayersSlider;
    }
    
    /**
     * This method initializes jAddButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getJAddButton()
    {
        if (jAddButton == null)
        {
            jAddButton = new JButton();
            jAddButton.setBounds(new Rectangle(79, 224, 56, 26));
            jAddButton.setText("Add");
            jAddButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    m_tableName = getJTableNameTextField().getText();
                    m_gameType = (TypePokerGame) getJGameTypeComboBox().getSelectedItem();
                    m_bigBlind = (Integer) getJBigBlindSpinner().getValue();
                    m_nbPlayer = getJNbPlayersSlider().getValue();
                    m_OK = true;
                    setVisible(false);
                }
            });
        }
        return jAddButton;
    }
    
    public boolean isOK()
    {
        return m_OK;
    }
    
    public String getTableName()
    {
        return m_tableName;
    }
    
    public TypePokerGame getGameType()
    {
        return m_gameType;
    }
    
    public int getBigBlind()
    {
        return m_bigBlind;
    }
    
    public int getNbPlayer()
    {
        return m_nbPlayer;
    }
} // @jve:decl-index=0:visual-constraint="10,10"
