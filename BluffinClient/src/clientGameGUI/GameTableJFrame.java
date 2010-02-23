package clientGameGUI;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import pokerLogic.PokerPlayerAction;
import pokerLogic.TypePlayerAction;
import clientGameTools.IClientPokerActionner;

public class GameTableJFrame extends GameTableViewerJFrame implements IClientPokerActionner
{
    private int m_minAmount;
    private final PokerPlayerAction m_playerAction = new PokerPlayerAction(TypePlayerAction.NOTHING);
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JButton jFoldButton = null;
    private JButton jCheckButton = null;
    private JButton jCallButton = null;
    private JButton jRaiseButton = null;
    private JSpinner jRaiseSpinner = null;
    
    /**
     * This method initializes
     * 
     */
    public GameTableJFrame()
    {
        super();
        initialize();
    }
    
    /**
     * This method initializes this
     * 
     */
    private void initialize()
    {
        final JPanel panel = super.getJRightPanel();
        panel.add(getJFoldButton());
        panel.add(getJCheckButton());
        panel.add(getJCallButton());
        panel.add(getJRaiseButton());
        panel.add(getJRaiseSpinner());
        changeSubTitle("");
    }
    
    /**
     * This method initializes jCheckButton
     * 
     * @return javax.swing.JButton
     */
    private JSpinner getJRaiseSpinner()
    {
        if (jRaiseSpinner == null)
        {
            jRaiseSpinner = new JSpinner();
            jRaiseSpinner.setEnabled(false);
            jRaiseSpinner.setPreferredSize(new Dimension(125, 25));
        }
        return jRaiseSpinner;
    }
    
    /**
     * This method initializes jCheckButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getJFoldButton()
    {
        if (jFoldButton == null)
        {
            jFoldButton = new JButton();
            jFoldButton.setText("FOLD");
            jFoldButton.setEnabled(false);
            jFoldButton.setPreferredSize(new Dimension(125, 25));
            jFoldButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    disableButtons();
                    actionTaken(new PokerPlayerAction(TypePlayerAction.FOLD));
                }
            });
        }
        return jFoldButton;
    }
    
    /**
     * This method initializes jCheckButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getJCheckButton()
    {
        if (jCheckButton == null)
        {
            jCheckButton = new JButton();
            jCheckButton.setText("CHECK");
            jCheckButton.setEnabled(false);
            jCheckButton.setPreferredSize(new Dimension(125, 25));
            jCheckButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    disableButtons();
                    actionTaken(new PokerPlayerAction(TypePlayerAction.CHECK));
                }
            });
        }
        return jCheckButton;
    }
    
    /**
     * This method initializes jCheckButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getJCallButton()
    {
        if (jCallButton == null)
        {
            jCallButton = new JButton();
            jCallButton.setText("CALL");
            jCallButton.setEnabled(false);
            jCallButton.setPreferredSize(new Dimension(125, 25));
            jCallButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    disableButtons();
                    actionTaken(new PokerPlayerAction(TypePlayerAction.CALL, m_minAmount));
                }
            });
        }
        return jCallButton;
    }
    
    /**
     * This method initializes jCheckButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getJRaiseButton()
    {
        if (jRaiseButton == null)
        {
            jRaiseButton = new JButton();
            jRaiseButton.setText("RAISE");
            jRaiseButton.setEnabled(false);
            jRaiseButton.setPreferredSize(new Dimension(125, 25));
            jRaiseButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    disableButtons();
                    actionTaken(new PokerPlayerAction(TypePlayerAction.RAISE, (Integer) getJRaiseSpinner().getValue()));
                }
            });
        }
        return jRaiseButton;
    }
    
    private void actionTaken(PokerPlayerAction p_actionTaken)
    {
        synchronized (m_playerAction)
        {
            m_playerAction.setType(p_actionTaken.getType());
            m_playerAction.setAmount(p_actionTaken.getAmount());
            m_playerAction.notify();
        }
    }
    
    private void disableButtons()
    {
        getJFoldButton().setEnabled(false);
        getJCheckButton().setEnabled(false);
        getJCallButton().setEnabled(false);
        getJRaiseButton().setEnabled(false);
        getJRaiseSpinner().setEnabled(false);
    }
    
    @Override
    public PokerPlayerAction getAction()
    {
        final PokerPlayerAction actionTaken = new PokerPlayerAction(TypePlayerAction.NOTHING);
        
        synchronized (m_playerAction)
        {
            try
            {
                if (m_playerAction.getType() == TypePlayerAction.NOTHING)
                {
                    m_playerAction.wait();
                }
                
                actionTaken.setType(m_playerAction.getType());
                actionTaken.setAmount(m_playerAction.getAmount());
                m_playerAction.setType(TypePlayerAction.NOTHING);
            }
            catch (final InterruptedException e)
            {
            }
        }
        
        return actionTaken;
    }
    
    @Override
    public void takeAction(ArrayList<TypePlayerAction> p_actionsAllowed, int p_callAmount, int p_minRaiseAmount, int p_maxRaiseAmount)
    {
        m_minAmount = p_callAmount;
        if (p_actionsAllowed.contains(TypePlayerAction.CALL))
        {
            getJCallButton().setEnabled(true);
        }
        
        if (p_actionsAllowed.contains(TypePlayerAction.CHECK))
        {
            getJCheckButton().setEnabled(true);
        }
        
        if (p_actionsAllowed.contains(TypePlayerAction.FOLD))
        {
            getJFoldButton().setEnabled(true);
        }
        
        if (p_actionsAllowed.contains(TypePlayerAction.RAISE))
        {
            getJRaiseButton().setEnabled(true);
            getJRaiseSpinner().setEnabled(true);
            getJRaiseSpinner().setModel(new SpinnerNumberModel(p_minRaiseAmount, p_minRaiseAmount, p_maxRaiseAmount, p_minRaiseAmount));
        }
    }
}
