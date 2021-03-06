package bluffinmuffin.gui.game;

import javax.swing.JFrame;

import bluffinmuffin.poker.IPokerGame;
import bluffinmuffin.poker.IPokerViewer;


public abstract class AbstractJFrameTable extends JFrame implements IPokerViewer
{
    private static final long serialVersionUID = 1L;
    protected IPokerGame m_game;
    protected int m_currentTablePosition;
    
    @Override
    public void setGame(IPokerGame game, int seatViewed)
    {
        m_game = game;
        m_currentTablePosition = seatViewed;
    }
    
    @Override
    public void start()
    {
        setTitle(getTitle());
        getContentPane().setPreferredSize(getSize());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
