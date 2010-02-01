package gui;

import gui.components.CurrencyIntegerEditor;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.Leading;

import utility.ClosingListener;
import utility.TypePlayerAction;
import backend.PlayerAction;
import backend.agent.IPokerAgent;
import backend.agent.IPokerAgentActionner;

/**
 * @author Hocus
 *         This class is a windows displaying the vision of a player,
 *         but allows interactions with the game using buttons.
 *         A part of this class was generated using a visual editor
 *         (http://wiki.eclipse.org/VE).
 */
public class GUI extends Viewer implements IPokerAgentActionner
{
    private static final long serialVersionUID = -526188625940605519L;
    
    private JButton jButtonCheck;
    private JButton jButtonCall;
    private JButton jButtonRaise;
    private JButton jButtonFold;
    private JSpinner jSpinnerRaise;
    
    private final List<ClosingListener<IPokerAgent>> m_closingListeners = Collections.synchronizedList(new ArrayList<ClosingListener<IPokerAgent>>());
    
    private final static int RAISE_STEP = 1;
    
    /**
     * Main entry of the class.
     * Note: This class is only created so that you can easily preview the
     * result at runtime.
     * It is not expected to be managed by the designer.
     * You can modify it as you like.
     */
    public static void main(String[] args)
    {
        // installLnF();
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                GUI frame;
                
                frame = new GUI();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setTitle("Hocus Pokus");
                frame.getContentPane().setPreferredSize(frame.getSize());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
    
    PlayerAction m_playerAction = new PlayerAction(TypePlayerAction.NOTHING);
    
    public GUI()
    {
        super();
        initComponents();
    }
    
    private void actionTaken(PlayerAction p_actionTaken)
    {
        synchronized (m_playerAction)
        {
            m_playerAction.m_typeAction = p_actionTaken.m_typeAction;
            m_playerAction.m_actionAmount = p_actionTaken.m_actionAmount;
            m_playerAction.notify();
        }
    }
    
    private void disableButtons()
    {
        getJButtonCall().setText(m_bundle.get("gui.buttonCall"));
        getJButtonCall().setEnabled(false);
        getJButtonCheck().setEnabled(false);
        getJButtonFold().setEnabled(false);
        getJButtonRaise().setEnabled(false);
        getJSpinnerRaise().setEnabled(false);
    }
    
    public void disconnect()
    {
        actionTaken(new PlayerAction(TypePlayerAction.DISCONNECT));
        
        synchronized (m_closingListeners)
        {
            for (final ClosingListener<IPokerAgent> listener : m_closingListeners)
            {
                listener.closing(GUI.this);
            }
        }
    }
    
    public PlayerAction getAction()
    {
        final PlayerAction actionTaken = new PlayerAction(TypePlayerAction.NOTHING);
        
        synchronized (m_playerAction)
        {
            try
            {
                if (m_playerAction.m_typeAction == TypePlayerAction.NOTHING)
                {
                    m_playerAction.wait();
                }
                
                actionTaken.m_typeAction = m_playerAction.m_typeAction;
                actionTaken.m_actionAmount = m_playerAction.m_actionAmount;
                m_playerAction.m_typeAction = TypePlayerAction.NOTHING;
            }
            catch (final InterruptedException e)
            {
            }
        }
        
        return actionTaken;
    }
    
    private JButton getJButtonCall()
    {
        if (jButtonCall == null)
        {
            jButtonCall = new JButton();
            jButtonCall.setMargin(new Insets(0, 0, 0, 0));
            jButtonCall.setText(m_bundle.get("gui.buttonCall"));
            jButtonCall.setEnabled(false);
            jButtonCall.addActionListener(new ActionListener()
            {
                
                public void actionPerformed(ActionEvent event)
                {
                    jButtonCallActionActionPerformed(event);
                }
            });
        }
        return jButtonCall;
    }
    
    private JButton getJButtonCheck()
    {
        if (jButtonCheck == null)
        {
            jButtonCheck = new JButton();
            jButtonCheck.setText(m_bundle.get("gui.buttonCheck"));
            jButtonCheck.setMargin(new Insets(0, 0, 0, 0));
            jButtonCheck.setEnabled(false);
            jButtonCheck.addActionListener(new ActionListener()
            {
                
                public void actionPerformed(ActionEvent event)
                {
                    jButtonCheckActionActionPerformed(event);
                }
            });
        }
        return jButtonCheck;
    }
    
    private JButton getJButtonFold()
    {
        if (jButtonFold == null)
        {
            jButtonFold = new JButton();
            jButtonFold.setText(m_bundle.get("gui.buttonFold"));
            jButtonFold.setEnabled(false);
            jButtonFold.setMargin(new Insets(2, 0, 2, 0));
            jButtonFold.addActionListener(new ActionListener()
            {
                
                public void actionPerformed(ActionEvent event)
                {
                    jButtonFoldActionActionPerformed(event);
                }
            });
        }
        return jButtonFold;
    }
    
    private JButton getJButtonRaise()
    {
        if (jButtonRaise == null)
        {
            jButtonRaise = new JButton();
            jButtonRaise.setText(m_bundle.get("gui.buttonRaise"));
            jButtonRaise.setMargin(new Insets(2, 0, 2, 0));
            jButtonRaise.setEnabled(false);
            jButtonRaise.addActionListener(new ActionListener()
            {
                
                public void actionPerformed(ActionEvent event)
                {
                    jButtonRaiseActionActionPerformed(event);
                }
            });
        }
        return jButtonRaise;
    }
    
    private JSpinner getJSpinnerRaise()
    {
        if (jSpinnerRaise == null)
        {
            jSpinnerRaise = new JSpinner();
            jSpinnerRaise.setEnabled(false);
            jSpinnerRaise.setModel(new SpinnerNumberModel(0, 0, 1000000, 1));
            jSpinnerRaise.setEditor(new CurrencyIntegerEditor(jSpinnerRaise));
        }
        return jSpinnerRaise;
    }
    
    private void initComponents()
    {
        getJPanel1().add(getJSpinnerRaise(), new Constraints(new Leading(786, 81, 12, 12), new Leading(466, 12, 12)));
        getJPanel1().add(getJButtonRaise(), new Constraints(new Leading(786, 80, 12, 12), new Leading(492, 12, 12)));
        getJPanel1().add(getJButtonFold(), new Constraints(new Leading(786, 80, 12, 12), new Leading(524, 12, 12)));
        getJPanel1().add(getJButtonCheck(), new Constraints(new Leading(696, 84, 12, 12), new Leading(492, 58, 58, 58)));
        getJPanel1().add(getJButtonCall(), new Constraints(new Leading(696, 84, 12, 12), new Leading(492, 58, 58, 58)));
    }
    
    private void jButtonCallActionActionPerformed(ActionEvent event)
    {
        disableButtons();
        actionTaken(new PlayerAction(TypePlayerAction.CALL));
    }
    
    private void jButtonCheckActionActionPerformed(ActionEvent event)
    {
        disableButtons();
        actionTaken(new PlayerAction(TypePlayerAction.CHECK));
    }
    
    private void jButtonFoldActionActionPerformed(ActionEvent event)
    {
        disableButtons();
        actionTaken(new PlayerAction(TypePlayerAction.FOLD));
    }
    
    private void jButtonRaiseActionActionPerformed(ActionEvent event)
    {
        disableButtons();
        actionTaken(new PlayerAction(TypePlayerAction.RAISE, (Integer) jSpinnerRaise.getValue()));
    }
    
    @Override
    public void run()
    {
        this.addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e)
            {
                disconnect();
                super.windowClosing(e);
            }
        });
        
        super.run();
    }
    
    public void takeAction(ArrayList<TypePlayerAction> p_actionsAllowed, int p_callAmount, int p_minRaiseAmount, int p_maxRaiseAmount)
    {
        JButton bigButton = null;
        
        getJButtonCheck().setEnabled(false);
        getJButtonCheck().setVisible(false);
        getJButtonCall().setEnabled(false);
        getJButtonCall().setVisible(false);
        
        if (p_actionsAllowed.contains(TypePlayerAction.CALL))
        {
            getJButtonCall().setText("<html><center>" + m_bundle.get("gui.buttonCall") + "<br>" + format(p_callAmount) + "</center></html>");
            bigButton = getJButtonCall();
        }
        
        if (p_actionsAllowed.contains(TypePlayerAction.CHECK))
        {
            bigButton = getJButtonCheck();
        }
        
        if (bigButton != null)
        {
            bigButton.setVisible(true);
            bigButton.setEnabled(true);
            this.getRootPane().setDefaultButton(bigButton);
        }
        
        getJButtonFold().setEnabled(p_actionsAllowed.contains(TypePlayerAction.FOLD));
        
        // Raise
        getJButtonRaise().setEnabled(p_actionsAllowed.contains(TypePlayerAction.RAISE));
        getJSpinnerRaise().setEnabled(p_actionsAllowed.contains(TypePlayerAction.RAISE));
        getJSpinnerRaise().setModel(new SpinnerNumberModel(p_minRaiseAmount, p_minRaiseAmount, p_maxRaiseAmount, GUI.RAISE_STEP));
        getJSpinnerRaise().setEditor(new CurrencyIntegerEditor(jSpinnerRaise));
    }
    
    @Override
    public String toString()
    {
        return m_bundle.get("gui.title");
    }
}
