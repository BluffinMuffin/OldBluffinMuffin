package clientGameGUI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import pokerLogic.PokerTableInfo;
import utility.IClosingListener;
import clientGame.ClientPokerTableInfo;
import clientGameTools.ClientPokerObserver;
import clientGameTools.IClientPoker;

public abstract class GameTableAbstractJFrame extends JFrame implements IClientPoker
{
    private static final long serialVersionUID = 1L;
    protected ClientPokerObserver m_pokerObserver;
    protected ClientPokerTableInfo m_table;
    // private final ArrayList<JLabel> m_pots = new ArrayList<JLabel>();
    // private final ArrayList<CardPanel> m_boardCards = new ArrayList<CardPanel>();
    private final List<IClosingListener<IClientPoker>> m_closingListeners = Collections.synchronizedList(new ArrayList<IClosingListener<IClientPoker>>());
    
    @Override
    public void addClosingListener(IClosingListener<IClientPoker> pListener)
    {
        m_closingListeners.add(pListener);
        
    }
    
    @Override
    public void removeClosingListener(IClosingListener<IClientPoker> pListener)
    {
        m_closingListeners.remove(pListener);
        
    }
    
    @Override
    public void setTable(PokerTableInfo pTable)
    {
        m_table = (ClientPokerTableInfo) pTable;
        
    }
    
    @Override
    public void start()
    {
        SwingUtilities.invokeLater(this);
        
    }
    
    @Override
    public void stop()
    {
        setVisible(false);
        
    }
    
    @Override
    public void run()
    {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e)
            {
                synchronized (m_closingListeners)
                {
                    for (final IClosingListener<IClientPoker> listener : m_closingListeners)
                    {
                        listener.closing(GameTableAbstractJFrame.this);
                    }
                }
            }
        });
        setTitle(getTitle() + " - " + m_table.m_name);
        getContentPane().setPreferredSize(getSize());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
    }
    
    public void setPokerObserver(ClientPokerObserver observer)
    {
        m_pokerObserver = observer;
    }
}
