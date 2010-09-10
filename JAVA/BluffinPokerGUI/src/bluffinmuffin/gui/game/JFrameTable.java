package bluffinmuffin.gui.game;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import bluffinmuffin.poker.game.IPokerGame;
import bluffinmuffin.poker.game.PlayerInfo;
import bluffinmuffin.poker.game.TableInfo;
import bluffinmuffin.poker.game.observer.PokerGameAdapter;


public class JFrameTable extends JFrameTableViewer
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
    public JFrameTable()
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
                    final TableInfo table = m_game.getTable();
                    final PlayerInfo p = table.getPlayer(m_currentTablePosition);
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
                    final TableInfo table = m_game.getTable();
                    final PlayerInfo p = table.getPlayer(m_currentTablePosition);
                    m_game.playMoney(p, table.getCallAmnt(p));
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
                    final TableInfo table = m_game.getTable();
                    final PlayerInfo p = table.getPlayer(m_currentTablePosition);
                    m_game.playMoney(p, (Integer) getJRaiseSpinner().getValue() - p.getMoneyBetAmnt());
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
    public void setGame(IPokerGame game, int seatViewed)
    {
        super.setGame(game, seatViewed);
        initializePokerObserver();
    }
    
    private void initializePokerObserver()
    {
        m_game.attach(new PokerGameAdapter()
        {
            
            @Override
            public void playerActionNeeded(PlayerInfo p, PlayerInfo last)
            {
                if (p.getNoSeat() == m_currentTablePosition)
                {
                    getJFoldButton().setEnabled(true);
                    setCallButtonName();
                    getJCallButton().setEnabled(true);
                    final TableInfo table = m_game.getTable();
                    if (table.getHigherBet() < p.getMoneyAmnt())
                    {
                        final int min = table.getMinRaiseAmnt(p) + p.getMoneyBetAmnt();
                        getJRaiseButton().setEnabled(true);
                        getJRaiseSpinner().setEnabled(true);
                        getJRaiseSpinner().setModel(new SpinnerNumberModel(min, min, p.getMoneyAmnt(), min));
                    }
                }
            }
            
        });
    }
    
    public void setCallButtonName()
    {
        final TableInfo table = m_game.getTable();
        final PlayerInfo p = table.getPlayer(m_currentTablePosition);
        String s;
        if (table.canCheck(p))
        {
            s = "CHECK";
        }
        else if (table.getHigherBet() >= p.getMoneyAmnt())
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
