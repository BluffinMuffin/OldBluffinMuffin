package clientAILobbyGUI;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;

import clientAI.TypeAgent;

public class LobbyAIConnectJDialog extends JDialog
{
    final DefaultListModel model = new DefaultListModel();
    private int m_EditWho;
    private String m_serverAddress;
    private int m_serverPort;
    private boolean m_OK;
    
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JButton jConnectButton = null;
    private JLabel jAdressLabel = null;
    private JComboBox jAddressComboBox = null;
    private JLabel jPortLabel = null;
    private JSpinner jPortSpinner = null;
    private JList jAIList = null;
    private JLabel jAILabel = null;
    private JButton jAddButton = null;
    private JLabel jAINameLabel = null;
    private JTextField jAINameTextField = null;
    private JLabel jAITypeLabel = null;
    private JComboBox jAITypeComboBox = null;
    private JCheckBox jAddViewerCheckBox = null;
    private JButton jSaveButton = null;
    private JButton jRemoveButton = null;
    private JCheckBox jAddViewer2CheckBox = null;
    
    /**
     * @param owner
     */
    public LobbyAIConnectJDialog(Frame owner)
    {
        
        super(owner);
        pack();
        setLocationRelativeTo(owner);
        initialize();
    }
    
    /**
     * @param owner
     */
    public LobbyAIConnectJDialog(Frame owner, List<TupleAISummary> liste)
    {
        this(owner);
        for (int i = 0; i < liste.size(); ++i)
        {
            model.add(i, liste.get(i));
        }
        if (model.size() > 0)
        {
            getJConnectButton().setEnabled(true);
        }
    }
    
    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize()
    {
        m_OK = false;
        this.setSize(411, 380);
        this.setModal(true);
        this.setTitle("Connection");
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
            jAITypeLabel = new JLabel();
            jAITypeLabel.setBounds(new Rectangle(180, 120, 100, 16));
            jAITypeLabel.setText("AI Type:");
            jAINameLabel = new JLabel();
            jAINameLabel.setBounds(new Rectangle(180, 165, 100, 16));
            jAINameLabel.setText("AI Name:");
            jAILabel = new JLabel();
            jAILabel.setBounds(new Rectangle(10, 100, 37, 16));
            jAILabel.setText("AIs:");
            jPortLabel = new JLabel();
            jPortLabel.setBounds(new Rectangle(10, 55, 100, 16));
            jPortLabel.setText("Server Port:");
            jAdressLabel = new JLabel();
            jAdressLabel.setBounds(new Rectangle(10, 10, 100, 16));
            jAdressLabel.setText("Server Address:");
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.add(getJConnectButton(), null);
            jContentPane.add(jAdressLabel, null);
            jContentPane.add(getJAddressComboBox(), null);
            jContentPane.add(jPortLabel, null);
            jContentPane.add(getJPortSpinner(), null);
            jContentPane.add(getJAIList(), null);
            jContentPane.add(jAILabel, null);
            jContentPane.add(getJAddButton(), null);
            jContentPane.add(jAINameLabel, null);
            jContentPane.add(getJAINameTextField(), null);
            jContentPane.add(jAITypeLabel, null);
            jContentPane.add(getJAITypeComboBox(), null);
            jContentPane.add(getJAddViewerCheckBox(), null);
            jContentPane.add(getJSaveButton(), null);
            jContentPane.add(getJRemoveButton(), null);
            jContentPane.add(getJAddViewer2CheckBox(), null);
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
            jConnectButton.setBounds(new Rectangle(164, 300, 81, 26));
            jConnectButton.setSelected(true);
            jConnectButton.setEnabled(false);
            jConnectButton.setName("jConnectButton");
            jConnectButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    m_serverAddress = getJAddressComboBox().getSelectedItem().toString();
                    m_serverPort = (Integer) getJPortSpinner().getValue();
                    m_OK = true;
                    LobbyAIConnectJDialog.this.setVisible(false);
                }
            });
        }
        return jConnectButton;
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
            jAddressComboBox.setLocation(new Point(10, 30));
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
    private JSpinner getJPortSpinner()
    {
        if (jPortSpinner == null)
        {
            jPortSpinner = new JSpinner();
            jPortSpinner.setModel(new SpinnerNumberModel(4242, 1, 65535, 1));
            jPortSpinner.setBounds(new Rectangle(10, 75, 75, 20));
            jPortSpinner.setEditor(new JSpinner.NumberEditor(jPortSpinner, "#"));
        }
        return jPortSpinner;
    }
    
    /**
     * This method initializes jAIList
     * 
     * @return javax.swing.JList
     */
    private JList getJAIList()
    {
        if (jAIList == null)
        {
            jAIList = new JList(model);
            jAIList.setSize(new Dimension(165, 167));
            jAIList.setLocation(new Point(10, 120));
            jAIList.addListSelectionListener(new javax.swing.event.ListSelectionListener()
            {
                public void valueChanged(javax.swing.event.ListSelectionEvent e)
                {
                    if (getJAIList().getSelectedIndex() >= 0)
                    {
                        m_EditWho = getJAIList().getSelectedIndex();
                        getJSaveButton().setEnabled(true);
                        getJRemoveButton().setEnabled(true);
                        getJAITypeComboBox().setEnabled(true);
                        getJAITypeComboBox().setSelectedItem(((TupleAISummary) model.get(m_EditWho)).m_AIType);
                        getJAINameTextField().setEnabled(true);
                        getJAINameTextField().setText(((TupleAISummary) model.get(m_EditWho)).m_AIName);
                        getJAddViewerCheckBox().setEnabled(true);
                        getJAddViewerCheckBox().setSelected(((TupleAISummary) model.get(m_EditWho)).m_viewer);
                    }
                }
            });
        }
        return jAIList;
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
            jAddButton.setText("Add");
            jAddButton.setSize(new Dimension(60, 20));
            jAddButton.setLocation(new Point(114, 97));
            jAddButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    m_EditWho = -1;
                    getJAddButton().setEnabled(false);
                    getJRemoveButton().setEnabled(true);
                    getJAIList().clearSelection();
                    getJAIList().setEnabled(false);
                    getJAITypeComboBox().setEnabled(true);
                    getJAITypeComboBox().setSelectedIndex(0);
                    getJAINameTextField().setEnabled(true);
                    getJAINameTextField().setText("");
                    getJAddViewerCheckBox().setEnabled(true);
                    getJAddViewerCheckBox().setSelected(false);
                }
            });
        }
        return jAddButton;
    }
    
    /**
     * This method initializes jAINameTextField
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getJAINameTextField()
    {
        if (jAINameTextField == null)
        {
            jAINameTextField = new JTextField();
            jAINameTextField.setBounds(new Rectangle(180, 185, 200, 20));
            jAINameTextField.setEnabled(false);
            jAINameTextField.addCaretListener(new javax.swing.event.CaretListener()
            {
                public void caretUpdate(javax.swing.event.CaretEvent e)
                {
                    if (getJAINameTextField().getText().isEmpty() || alreadyContain(getJAINameTextField().getText()))
                    {
                        getJSaveButton().setEnabled(false);
                    }
                    else
                    {
                        getJSaveButton().setEnabled(true);
                    }
                }
                
                private boolean alreadyContain(String text)
                {
                    for (int i = 0; i < model.getSize(); ++i)
                    {
                        final TupleAISummary bob = (TupleAISummary) model.get(i);
                        if (i != getEdithWho() && text.equalsIgnoreCase(bob.m_AIName))
                        {
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
        return jAINameTextField;
    }
    
    /**
     * This method initializes jAITypeComboBox
     * 
     * @return javax.swing.JComboBox
     */
    private JComboBox getJAITypeComboBox()
    {
        if (jAITypeComboBox == null)
        {
            jAITypeComboBox = new JComboBox();
            jAITypeComboBox.setBounds(new Rectangle(180, 140, 200, 20));
            jAITypeComboBox.setModel(new DefaultComboBoxModel(TypeAgent.values()));
            jAITypeComboBox.setSelectedItem(TypeAgent.AI_BASIC);
            jAITypeComboBox.setEnabled(false);
            jAITypeComboBox.setSelectedIndex(-1);
        }
        return jAITypeComboBox;
    }
    
    /**
     * This method initializes jAddViewerCheckBox
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getJAddViewerCheckBox()
    {
        if (jAddViewerCheckBox == null)
        {
            jAddViewerCheckBox = new JCheckBox();
            jAddViewerCheckBox.setText("Attach Viewer");
            jAddViewerCheckBox.setSize(new Dimension(106, 20));
            jAddViewerCheckBox.setEnabled(false);
            jAddViewerCheckBox.setLocation(new Point(180, 210));
            jAddViewerCheckBox.addItemListener(new java.awt.event.ItemListener()
            {
                public void itemStateChanged(java.awt.event.ItemEvent e)
                {
                    getJAddViewer2CheckBox().setSelected(false);
                    getJAddViewer2CheckBox().setEnabled(getJAddViewerCheckBox().isSelected());
                    
                }
            });
        }
        return jAddViewerCheckBox;
    }
    
    /**
     * This method initializes jSaveButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getJSaveButton()
    {
        if (jSaveButton == null)
        {
            jSaveButton = new JButton();
            jSaveButton.setBounds(new Rectangle(180, 250, 85, 20));
            jSaveButton.setEnabled(false);
            jSaveButton.setText("Save");
            jSaveButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    
                    if (m_EditWho >= 0)
                    {
                        model.set(m_EditWho, new TupleAISummary(getJAINameTextField().getText(), (TypeAgent) getJAITypeComboBox().getSelectedItem(), getJAddViewerCheckBox().isSelected(), getJAddViewer2CheckBox().isSelected()));
                    }
                    else
                    {
                        model.add(model.getSize(), new TupleAISummary(getJAINameTextField().getText(), (TypeAgent) getJAITypeComboBox().getSelectedItem(), getJAddViewerCheckBox().isSelected(), getJAddViewer2CheckBox().isSelected()));
                    }
                    getJAddButton().setEnabled(true);
                    getJSaveButton().setEnabled(false);
                    getJRemoveButton().setEnabled(false);
                    getJAIList().setEnabled(true);
                    getJAIList().setEnabled(true);
                    getJAIList().clearSelection();
                    getJAITypeComboBox().setSelectedIndex(-1);
                    getJAITypeComboBox().setEnabled(false);
                    getJAINameTextField().setText("");
                    getJAINameTextField().setEnabled(false);
                    getJAddViewerCheckBox().setSelected(false);
                    getJAddViewerCheckBox().setEnabled(false);
                    
                    m_EditWho = -1;
                    getJConnectButton().setEnabled(true);
                    if (model.getSize() == 9)
                    {
                        getJAddButton().setEnabled(false);
                    }
                }
            });
        }
        return jSaveButton;
    }
    
    /**
     * This method initializes jRemoveButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getJRemoveButton()
    {
        if (jRemoveButton == null)
        {
            jRemoveButton = new JButton();
            jRemoveButton.setBounds(new Rectangle(295, 250, 85, 20));
            jRemoveButton.setEnabled(false);
            jRemoveButton.setText("Remove");
            jRemoveButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    if (m_EditWho >= 0)
                    {
                        model.remove(m_EditWho);
                    }
                    
                    getJAddButton().setEnabled(true);
                    getJSaveButton().setEnabled(false);
                    getJRemoveButton().setEnabled(false);
                    getJAIList().setEnabled(true);
                    getJAIList().clearSelection();
                    getJAITypeComboBox().setSelectedIndex(-1);
                    getJAITypeComboBox().setEnabled(false);
                    getJAINameTextField().setText("");
                    getJAINameTextField().setEnabled(false);
                    getJAddViewerCheckBox().setSelected(false);
                    getJAddViewerCheckBox().setEnabled(false);
                    
                    m_EditWho = -1;
                    getJAddButton().setEnabled(true);
                    if (model.getSize() == 0)
                    {
                        getJConnectButton().setEnabled(false);
                    }
                }
            });
        }
        return jRemoveButton;
    }
    
    public boolean isOK()
    {
        return m_OK;
    }
    
    public String getPlayerName()
    {
        return model.getSize() + "AIs";
    }
    
    public List<TupleAISummary> getAIs()
    {
        final ArrayList<TupleAISummary> liste = new ArrayList<TupleAISummary>();
        for (int i = 0; i < model.getSize(); ++i)
        {
            liste.add((TupleAISummary) model.get(i));
        }
        return liste;
    }
    
    public String getServerAddress()
    {
        return m_serverAddress;
    }
    
    public int getServerPort()
    {
        return m_serverPort;
    }
    
    public int getEdithWho()
    {
        return m_EditWho;
    }
    
    /**
     * This method initializes jAddViewer2CheckBox
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getJAddViewer2CheckBox()
    {
        if (jAddViewer2CheckBox == null)
        {
            jAddViewer2CheckBox = new JCheckBox();
            jAddViewer2CheckBox.setBounds(new Rectangle(300, 210, 50, 20));
            jAddViewer2CheckBox.setText("2.0");
            jAddViewer2CheckBox.setEnabled(false);
        }
        return jAddViewer2CheckBox;
    }
    
} // @jve:decl-index=0:visual-constraint="10,10"
