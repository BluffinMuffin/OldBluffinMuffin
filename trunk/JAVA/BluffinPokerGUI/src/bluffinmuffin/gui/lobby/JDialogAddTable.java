package bluffinmuffin.gui.lobby;

import java.awt.Dimension;
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

import bluffinmuffin.poker.entities.type.GameBetLimitType;

public class JDialogAddTable extends JDialog
{
    private final String m_playerName;
    
    private boolean m_OK;
    private String m_tableName;
    private int m_bigBlind;
    private int m_nbPlayer;
    private int m_WaitingTimeAfterPlayerAction;
    private int m_WaitingTimeAfterBoardDealed;
    private int m_WaitingTimeAfterPotWon;
    private int m_startingMoney;
    private GameBetLimitType m_Limit;
    
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JLabel jTableNameLabel = null;
    private JTextField jTableNameTextField = null;
    private JLabel jGameLimitLabel = null;
    private JComboBox jGameLimitComboBox = null;
    private JLabel jBigBlindLabel = null;
    private JSpinner jBigBlindSpinner = null;
    private JLabel jNbPlayersLabel = null;
    private JSlider jNbPlayersSlider = null;
    private JButton jAddButton = null;
    
    private JLabel jWTAPlayerActionLabel = null;
    private JSpinner jWTAPlayerActionSpinner = null;
    
    private JLabel jWTABoardDealedLabel = null;
    private JSpinner jWTABoardDealedSpinner = null;
    
    private JLabel jWTAPotWonLabel = null;
    private JSpinner jWTAPotWonSpinner = null;
    
    private JLabel jStartingMoneyLabel = null;
    private JSpinner jStartingMoneySpinner = null;
    
    /**
     * @param owner
     */
    public JDialogAddTable(Frame owner, String playerName, int nbPlayers)
    {
        super(owner);
        m_playerName = playerName;
        initialize();
        pack();
        setLocationRelativeTo(owner);
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
        this.setSize(254, 481);
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
            jStartingMoneyLabel = new JLabel();
            jStartingMoneyLabel.setBounds(new Rectangle(10, 350, 210, 16));
            jStartingMoneyLabel.setText("Starting Money:");
            jWTAPotWonLabel = new JLabel();
            jWTAPotWonLabel.setBounds(new Rectangle(10, 305, 210, 16));
            jWTAPotWonLabel.setText("Waiting time after pot won: (ms)");
            jWTABoardDealedLabel = new JLabel();
            jWTABoardDealedLabel.setBounds(new Rectangle(10, 260, 227, 16));
            jWTABoardDealedLabel.setText("Waiting time after board dealed: (ms)");
            jWTAPlayerActionLabel = new JLabel();
            jWTAPlayerActionLabel.setBounds(new Rectangle(10, 215, 227, 16));
            jWTAPlayerActionLabel.setText("Waiting time after player action: (ms)");
            jNbPlayersLabel = new JLabel();
            jNbPlayersLabel.setBounds(new Rectangle(10, 145, 100, 16));
            jNbPlayersLabel.setText("Nb of players:");
            jBigBlindLabel = new JLabel();
            jBigBlindLabel.setBounds(new Rectangle(10, 100, 100, 16));
            jBigBlindLabel.setText("Big Blind:");
            jGameLimitLabel = new JLabel();
            jGameLimitLabel.setBounds(new Rectangle(10, 55, 100, 16));
            jGameLimitLabel.setText("Game Limit:");
            jTableNameLabel = new JLabel();
            jTableNameLabel.setBounds(new Rectangle(10, 10, 100, 16));
            jTableNameLabel.setText("Table Name:");
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.setPreferredSize(new Dimension(244, 441));
            jContentPane.add(jTableNameLabel, null);
            jContentPane.add(getJTableNameTextField(), null);
            jContentPane.add(jGameLimitLabel, null);
            jContentPane.add(getJGameLimitComboBox(), null);
            jContentPane.add(jBigBlindLabel, null);
            jContentPane.add(getJBigBlindSpinner(), null);
            jContentPane.add(jNbPlayersLabel, null);
            jContentPane.add(getJNbPlayersSlider(), null);
            jContentPane.add(getJAddButton(), null);
            jContentPane.add(jWTAPlayerActionLabel, null);
            jContentPane.add(jWTABoardDealedLabel, null);
            jContentPane.add(jWTAPotWonLabel, null);
            jContentPane.add(getJWTAPlayerActionSpinner(), null);
            jContentPane.add(getJWTABoardDealedSpinner(), null);
            jContentPane.add(getJWTAPotWonSpinner(), null);
            jContentPane.add(jStartingMoneyLabel, null);
            jContentPane.add(getJStartingMoneySpinner(), null);
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
    private JComboBox getJGameLimitComboBox()
    {
        if (jGameLimitComboBox == null)
        {
            jGameLimitComboBox = new JComboBox();
            jGameLimitComboBox.setBounds(new Rectangle(10, 75, 200, 20));
            jGameLimitComboBox.setModel(new DefaultComboBoxModel(GameBetLimitType.values()));
            jGameLimitComboBox.setSelectedItem(GameBetLimitType.NO_LIMIT);
        }
        return jGameLimitComboBox;
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
    
    private JSpinner getJWTAPlayerActionSpinner()
    {
        if (jWTAPlayerActionSpinner == null)
        {
            jWTAPlayerActionSpinner = new JSpinner();
            jWTAPlayerActionSpinner.setModel(new SpinnerNumberModel(500, 0, 60000, 500));
            jWTAPlayerActionSpinner.setBounds(new Rectangle(10, 235, 75, 20));
            jWTAPlayerActionSpinner.setEditor(new JSpinner.NumberEditor(jWTAPlayerActionSpinner, "#"));
        }
        return jWTAPlayerActionSpinner;
    }
    
    private JSpinner getJWTABoardDealedSpinner()
    {
        if (jWTABoardDealedSpinner == null)
        {
            jWTABoardDealedSpinner = new JSpinner();
            jWTABoardDealedSpinner.setModel(new SpinnerNumberModel(500, 0, 60000, 500));
            jWTABoardDealedSpinner.setBounds(new Rectangle(10, 280, 75, 20));
            jWTABoardDealedSpinner.setEditor(new JSpinner.NumberEditor(jWTABoardDealedSpinner, "#"));
        }
        return jWTABoardDealedSpinner;
    }
    
    private JSpinner getJWTAPotWonSpinner()
    {
        if (jWTAPotWonSpinner == null)
        {
            jWTAPotWonSpinner = new JSpinner();
            jWTAPotWonSpinner.setModel(new SpinnerNumberModel(2500, 0, 60000, 500));
            jWTAPotWonSpinner.setBounds(new Rectangle(10, 325, 75, 20));
            jWTAPotWonSpinner.setEditor(new JSpinner.NumberEditor(jWTAPotWonSpinner, "#"));
        }
        return jWTAPotWonSpinner;
    }
    
    private JSpinner getJStartingMoneySpinner()
    {
        if (jStartingMoneySpinner == null)
        {
            jStartingMoneySpinner = new JSpinner();
            jStartingMoneySpinner.setModel(new SpinnerNumberModel(1500, 500, 1000000, 100));
            jStartingMoneySpinner.setBounds(new Rectangle(10, 370, 75, 20));
            jStartingMoneySpinner.setEditor(new JSpinner.NumberEditor(jStartingMoneySpinner, "#"));
        }
        return jStartingMoneySpinner;
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
            jNbPlayersSlider.setMaximum(10);
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
            jAddButton.setBounds(new Rectangle(79, 404, 56, 26));
            jAddButton.setText("Add");
            jAddButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    m_tableName = getJTableNameTextField().getText();
                    m_bigBlind = (Integer) getJBigBlindSpinner().getValue();
                    m_nbPlayer = getJNbPlayersSlider().getValue();
                    m_WaitingTimeAfterPlayerAction = (Integer) getJWTAPlayerActionSpinner().getValue();
                    m_WaitingTimeAfterBoardDealed = (Integer) getJWTABoardDealedSpinner().getValue();
                    m_WaitingTimeAfterPotWon = (Integer) getJWTAPotWonSpinner().getValue();
                    m_startingMoney = (Integer) getJStartingMoneySpinner().getValue();
                    m_Limit = (GameBetLimitType) getJGameLimitComboBox().getSelectedItem();
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
    
    public int getBigBlind()
    {
        return m_bigBlind;
    }
    
    public int getNbPlayer()
    {
        return m_nbPlayer;
    }
    
    public int getWaitingTimeAfterPlayerAction()
    {
        return m_WaitingTimeAfterPlayerAction;
    }
    
    public int getWaitingTimeAfterBoardDealed()
    {
        return m_WaitingTimeAfterBoardDealed;
    }
    
    public int getWaitingTimeAfterPotWon()
    {
        return m_WaitingTimeAfterPotWon;
    }
    
    public int getStartingMoney()
    {
        return m_startingMoney;
    }
    
    public GameBetLimitType getLimit()
    {
        return m_Limit;
    }
} // @jve:decl-index=0:visual-constraint="10,10"
