package gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import utilGUI.AutoListModel;
import utilGUI.ListEvent;
import utilGUI.ListListener;

import backend.PokerClient;
import backend.agent.FactoryObserver;
import backend.agent.TypeObserver;
import basePokerAI.IPokerAgentListener;

/**
 * @author Hocus
 *         This class represents a JPanel that reacts to modification made to a
 *         AutoListModel<IPokerAgentListener>.
 *         It allows management of PokerClient observers.
 */
public class JPanelObserver extends JPanel implements ListListener<IPokerAgentListener>
{
    private static final long serialVersionUID = 1L;
    
    private PokerClient m_client = null;
    private AutoListModel<IPokerAgentListener> m_observers = null;
    
    private JLabel jLabelObserverType = null;
    private JLabel jLabelAddedObservers = null;
    private JComboBox jComboBoxObserverType = null;
    private JList jListAddedObservers = null;
    private JButton jButtonAddObserver = null;
    private JButton jButtonRemoveObserver = null;
    
    /**
     * This is the default constructor
     */
    public JPanelObserver(PokerClient p_client)
    {
        super();
        m_client = p_client;
        m_observers = p_client.getObservers();
        m_observers.addListListener(this);
        initialize();
    }
    
    public void clear()
    {
        m_observers.clear();
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof PokerClient)
        {
            return m_client.equals(obj);
        }
        
        return super.equals(obj);
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
            jButtonAddObserver = new JButton();
            jButtonAddObserver.setText("Add");
            jButtonAddObserver.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    final IPokerAgentListener observer = FactoryObserver.create((TypeObserver) getJComboBoxObserverType().getSelectedItem());
                    m_observers.add(observer);
                }
            });
        }
        return jButtonAddObserver;
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
            jButtonRemoveObserver = new JButton();
            jButtonRemoveObserver.setText("Remove");
            jButtonRemoveObserver.setEnabled(false);
            jButtonRemoveObserver.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    final IPokerAgentListener observer = (IPokerAgentListener) getJListAddedObservers().getSelectedValue();
                    
                    if (m_client.getAgent() != observer)
                    {
                        m_observers.remove(observer);
                    }
                }
            });
        }
        return jButtonRemoveObserver;
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
     * This method initializes jListAddedObservers
     * 
     * @return javax.swing.JList
     */
    private JList getJListAddedObservers()
    {
        if (jListAddedObservers == null)
        {
            jListAddedObservers = new JList(m_observers);
            jListAddedObservers.setVisibleRowCount(3);
            jListAddedObservers.addListSelectionListener(new javax.swing.event.ListSelectionListener()
            {
                public void valueChanged(javax.swing.event.ListSelectionEvent e)
                {
                    final IPokerAgentListener observer = (IPokerAgentListener) getJListAddedObservers().getSelectedValue();
                    getJButtonRemoveObserver().setEnabled(m_client.getAgent() != observer);
                }
            });
        }
        return jListAddedObservers;
    }
    
    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize()
    {
        this.setSize(300, 200);
        
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
        jLabelAddedObservers = new JLabel();
        jLabelAddedObservers.setText("Added observers:");
        final GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
        gridBagConstraints29.gridx = 0;
        gridBagConstraints29.anchor = GridBagConstraints.WEST;
        gridBagConstraints29.gridy = 0;
        jLabelObserverType = new JLabel();
        jLabelObserverType.setText("Observer type:");
        
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createTitledBorder(null, "Observer Infos", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
        this.add(jLabelObserverType, gridBagConstraints29);
        this.add(jLabelAddedObservers, gridBagConstraints30);
        this.add(getJComboBoxObserverType(), gridBagConstraints32);
        this.add(getJListAddedObservers(), gridBagConstraints33);
        this.add(getJButtonAddObserver(), gridBagConstraints34);
        this.add(getJButtonRemoveObserver(), gridBagConstraints35);
    }
    
    public boolean isConnected()
    {
        return m_client.isConnected();
    }
    
    @Override
    public void itemsAdded(ListEvent<IPokerAgentListener> e)
    {
    }
    
    @Override
    public void itemsChanged(ListEvent<IPokerAgentListener> e)
    {
    }
    
    @Override
    public void itemsRemoved(ListEvent<IPokerAgentListener> e)
    {
        if (m_observers.size() == 0)
        {
            this.getParent().remove(this);
        }
    }
}
