package pokerGameGUI;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import newPokerLogic.PokerPlayerInfo;
import newPokerLogic.PokerTableInfo;
import newPokerLogicTools.PokerGameAdapter;
import newPokerLogicTools.PokerGameObserver;

public class GameTableJFrame extends GameTableViewerJFrame
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JButton jFoldButton = null;
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
                    final PokerTableInfo table = m_game.getPokerTable();
                    final PokerPlayerInfo p = table.getPlayer(m_currentTablePosition);
                    m_game.playMoney(p, -1);
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
                    final PokerTableInfo table = m_game.getPokerTable();
                    final PokerPlayerInfo p = table.getPlayer(m_currentTablePosition);
                    m_game.playMoney(p, table.getCurrentHigherBet() - p.getCurrentBetMoneyAmount());
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
                    final PokerTableInfo table = m_game.getPokerTable();
                    final PokerPlayerInfo p = table.getPlayer(m_currentTablePosition);
                    m_game.playMoney(p, (Integer) getJRaiseSpinner().getValue() - p.getCurrentBetMoneyAmount());
                }
            });
        }
        return jRaiseButton;
    }
    
    private void disableButtons()
    {
        getJFoldButton().setEnabled(false);
        getJCallButton().setEnabled(false);
        getJRaiseButton().setEnabled(false);
        getJRaiseSpinner().setEnabled(false);
    }
    
    @Override
    public void setPokerObserver(PokerGameObserver observer)
    {
        super.setPokerObserver(observer);
        initializePokerObserver();
    }
    
    private void initializePokerObserver()
    {
        m_pokerObserver.subscribe(new PokerGameAdapter()
        {
            
            @Override
            public void playerActionNeeded(PokerPlayerInfo p)
            {
                if (p.getCurrentTablePosition() == m_currentTablePosition)
                {
                    getJFoldButton().setEnabled(true);
                    setCallButtonName();
                    getJCallButton().setEnabled(true);
                    final PokerTableInfo table = m_game.getPokerTable();
                    final int totalAmnt = p.getCurrentBetMoneyAmount() + p.getCurrentSafeMoneyAmount();
                    if (table.getCurrentHigherBet() < totalAmnt)
                    {
                        final int min = table.getCurrentHigherBet() + table.getBigBlindAmount();
                        getJRaiseButton().setEnabled(true);
                        getJRaiseSpinner().setEnabled(true);
                        getJRaiseSpinner().setModel(new SpinnerNumberModel(min, min, totalAmnt, min));
                    }
                }
            }
            
        });
    }
    
    // TODO: Pas pentoute rapport ici: si tout le monde est all-in au preflop ca chie :p
    public void setCallButtonName()
    {
        final PokerTableInfo table = m_game.getPokerTable();
        final PokerPlayerInfo p = table.getPlayer(m_currentTablePosition);
        String s;
        if (table.getCurrentHigherBet() == p.getCurrentBetMoneyAmount())
        {
            s = "CHECK";
        }
        else if (table.getCurrentHigherBet() >= (p.getCurrentBetMoneyAmount() + p.getCurrentSafeMoneyAmount()))
        {
            s = "ALL-IN";
        }
        else
        {
            s = "CALL";
        }
        getJCallButton().setText(s);
    }
}
