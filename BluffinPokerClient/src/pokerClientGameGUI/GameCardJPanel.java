package pokerClientGameGUI;

import gameLogic.GameCard;

import java.awt.FlowLayout;
import java.util.TreeMap;

import javax.swing.JPanel;


public class GameCardJPanel extends JPanel
{
    private static final String PATH_IMAGE_CARD = "images/cards/";
    private static final String DEFAULT_CARD = "default.png";
    
    private String m_current = null;
    private final TreeMap<String, GameCardJLabel> m_labels = new TreeMap<String, GameCardJLabel>();
    
    private static final long serialVersionUID = 1L;
    
    public GameCardJPanel()
    {
        super(true);
        GameCardJLabel label = new GameCardJLabel(GameCardJPanel.PATH_IMAGE_CARD + GameCardJPanel.DEFAULT_CARD);
        m_labels.put("-2", label);
        this.add(label);
        for (int i = 0; i < 52; ++i)
        {
            final String code = GameCard.getInstance(i).toString();
            label = new GameCardJLabel(GameCardJPanel.PATH_IMAGE_CARD + code + ".png");
            m_labels.put(code, label);
            this.add(label);
        }
        this.getSize(m_labels.get("-2").getSize());
        this.setOpaque(false);
        final FlowLayout flowLayout1 = new FlowLayout();
        flowLayout1.setHgap(0);
        flowLayout1.setVgap(0);
        setLayout(flowLayout1);
    }
    
    public void setNoCard()
    {
        hideCurrent();
    }
    
    public void setCard(GameCard c)
    {
        hideCurrent();
        if (!c.isNoCard())
        {
            m_current = c.toString();
            m_labels.get(m_current).setVisible(true);
        }
    }
    
    private void hideCurrent()
    {
        if (m_current != null)
        {
            m_labels.get(m_current).setVisible(false);
            m_current = null;
        }
    }
}
