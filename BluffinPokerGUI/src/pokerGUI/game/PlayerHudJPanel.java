package pokerGUI.game;

import game.Card;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import poker.game.TypeAction;


public class PlayerHudJPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JPanel jTopPanel = null;
    private JLabel jNameLabel = null;
    private JLabel jInfoLabel = null;
    private JLabel jStatusLabel = null;
    private JLabel jActionLabel = null;
    private JPanel jCenterPanel = null;
    private GameCardJPanel cardPanel1 = null;
    private GameCardJPanel cardPanel2 = null;
    private JLabel jDealerLabel = null;
    private JLabel jBlindLabel = null;
    
    /**
     * This is the default constructor
     */
    public PlayerHudJPanel()
    {
        super();
        initialize();
    }
    
    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize()
    {
        jStatusLabel = new JLabel();
        jStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        jStatusLabel.setBounds(new Rectangle(0, 109, 125, 14));
        jStatusLabel.setText("$557");
        this.setLayout(null);
        this.setSize(125, 125);
        this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED), BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
        this.setBackground(Color.white);
        this.add(jStatusLabel, null);
        this.add(getJTopPanel(), null);
        this.add(getJCenterPanel(), null);
    }
    
    /**
     * This method initializes jTopPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJTopPanel()
    {
        if (jTopPanel == null)
        {
            jActionLabel = new JLabel();
            jActionLabel.setFont(new Font("Dialog", Font.BOLD, 10));
            jActionLabel.setHorizontalAlignment(SwingConstants.LEFT);
            jActionLabel.setText("Last Action: CHECK");
            jActionLabel.setPreferredSize(new Dimension(115, 14));
            jInfoLabel = new JLabel();
            jInfoLabel.setPreferredSize(new Dimension(115, 14));
            jInfoLabel.setText("Player Info");
            jInfoLabel.setFont(new Font("Dialog", Font.ITALIC, 12));
            jInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jNameLabel = new JLabel();
            jNameLabel.setPreferredSize(new Dimension(115, 14));
            jNameLabel.setText("Player Name");
            jNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            final FlowLayout flowLayout = new FlowLayout();
            flowLayout.setHgap(0);
            flowLayout.setVgap(1);
            jTopPanel = new JPanel();
            jTopPanel.setSize(new Dimension(118, 48));
            jTopPanel.setBackground(Color.white);
            jTopPanel.setOpaque(true);
            jTopPanel.setLocation(new Point(4, 3));
            jTopPanel.setLayout(flowLayout);
            jTopPanel.setPreferredSize(new Dimension(1, 48));
            jTopPanel.add(jNameLabel, null);
            jTopPanel.add(jInfoLabel, null);
            jTopPanel.add(jActionLabel, null);
        }
        return jTopPanel;
    }
    
    /**
     * This method initializes jCenterPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJCenterPanel()
    {
        if (jCenterPanel == null)
        {
            jBlindLabel = new JLabel();
            jBlindLabel.setBounds(new Rectangle(93, 33, 30, 30));
            jDealerLabel = new JLabel();
            jDealerLabel.setBounds(new Rectangle(93, 3, 30, 30));
            jCenterPanel = new JPanel();
            jCenterPanel.setLayout(null);
            jCenterPanel.setSize(new Dimension(125, 61));
            jCenterPanel.setLocation(new Point(0, 48));
            jCenterPanel.setBackground(Color.white);
            jCenterPanel.setOpaque(false);
            jCenterPanel.add(getCardPanel1(), null);
            jCenterPanel.add(getCardPanel2(), null);
            jCenterPanel.add(jDealerLabel, null);
            jCenterPanel.add(jBlindLabel, null);
        }
        return jCenterPanel;
    }
    
    /**
     * This method initializes cardPanel1
     * 
     * @return clientGameGUI.CardPanel
     */
    private GameCardJPanel getCardPanel1()
    {
        if (cardPanel1 == null)
        {
            cardPanel1 = new GameCardJPanel();
            cardPanel1.setBounds(new Rectangle(5, 3, 40, 56));
        }
        return cardPanel1;
    }
    
    /**
     * This method initializes cardPanel11
     * 
     * @return clientGameGUI.CardPanel
     */
    private GameCardJPanel getCardPanel2()
    {
        if (cardPanel2 == null)
        {
            cardPanel2 = new GameCardJPanel();
            cardPanel2.setBounds(new Rectangle(50, 3, 40, 56));
        }
        return cardPanel2;
    }
    
    public void setPlayerName(String name)
    {
        jNameLabel.setText(name);
    }
    
    public void setPlayerInfo(String info)
    {
        jInfoLabel.setText(info);
    }
    
    public void setPlayerAction(TypeAction action)
    {
        final StringBuilder sb = new StringBuilder("Last Action: ");
        switch (action)
        {
            case CALLED:
            case FOLDED:
            case RAISED:
                sb.append(action.name());
                break;
        }
        
        jActionLabel.setText(sb.toString());
    }
    
    public void setPlayerAction(TypeAction action, int amnt)
    {
        final StringBuilder sb = new StringBuilder("Last Action: ");
        switch (action)
        {
            case CALLED:
                if (amnt == 0)
                {
                    sb.append("CHECK");
                }
                else
                {
                    sb.append("CALL");
                }
                break;
            case FOLDED:
                sb.append("FOLD");
                break;
            case RAISED:
                sb.append("RAISE");
                break;
        }
        
        jActionLabel.setText(sb.toString());
    }
    
    public void setPlayerCards(Card c1, Card c2)
    {
        getCardPanel1().setCard(c1);
        getCardPanel2().setCard(c2);
    }
    
    public void setPlayerMoney(int money)
    {
        jStatusLabel.setText("$" + money);
    }
    
    public void setDealer()
    {
        jDealerLabel.setIcon(new ImageIcon("images/buttons/dealer.png"));
    }
    
    public void setNotDealer()
    {
        jDealerLabel.setIcon(null);
    }
    
    public void setBigBlind()
    {
        jBlindLabel.setIcon(new ImageIcon("images/buttons/big_blind.png"));
    }
    
    public void setSmallBlind()
    {
        jBlindLabel.setIcon(new ImageIcon("images/buttons/small_blind.png"));
    }
    
    public void setNoBlind()
    {
        jBlindLabel.setIcon(null);
    }
    
    public void setHeaderColor(Color c)
    {
        getJTopPanel().setBackground(c);
    }
}
