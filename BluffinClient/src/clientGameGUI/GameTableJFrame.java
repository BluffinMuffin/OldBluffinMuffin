package clientGameGUI;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import pokerLogic.PokerPlayerAction;
import pokerLogic.TypePlayerAction;
import clientGameTools.IClientPokerActionner;

public class GameTableJFrame extends GameTableViewerJFrame implements IClientPokerActionner
{
    
    PokerPlayerAction m_playerAction = new PokerPlayerAction(TypePlayerAction.NOTHING);
    private int m_minRaise;
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JButton jFoldButton = null;
    private JButton jCheckButton = null;
    private JButton jCallButton = null;
    private JButton jRaiseButton = null;
    
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
                    actionTaken(new PokerPlayerAction(TypePlayerAction.CALL));
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
                    actionTaken(new PokerPlayerAction(TypePlayerAction.RAISE, m_minRaise));
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
        }
        m_minRaise = p_minRaiseAmount;
    }
    
}
