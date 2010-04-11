package gui.game;

import game.Card;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import poker.game.TypeAction;

public class JPanelPlayerHud extends JPanel
{
    private boolean main = false;
    private boolean alive = false;
    private static final long serialVersionUID = 1L;
    private JPanel jTopPanel = null;
    private JLabel jNameLabel = null;
    private JLabel jStatusLabel = null;
    private JLabel jActionLabel = null;
    private JPanel jCenterPanel = null;
    private JPanelCard cardPanel1 = null;
    private JPanelCard cardPanel2 = null;
    private JLabel jDealerLabel = null;
    private JLabel jBlindLabel = null;
    
    /**
     * This is the default constructor
     */
    public JPanelPlayerHud()
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
        jStatusLabel.setBackground(Color.white);
        jStatusLabel.setOpaque(true);
        jStatusLabel.setSize(new Dimension(117, 12));
        jStatusLabel.setLocation(new Point(4, 109));
        jStatusLabel.setText("$557");
        this.setLayout(null);
        this.setSize(125, 125);
        this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED), BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
        this.setBackground(new Color(82, 98, 114));
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
            jActionLabel.setFont(new Font("Dialog", Font.ITALIC, 12));
            jActionLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jActionLabel.setText("CHECK");
            jActionLabel.setOpaque(true);
            jActionLabel.setBackground(Color.white);
            jActionLabel.setPreferredSize(new Dimension(117, 20));
            jNameLabel = new JLabel();
            jNameLabel.setPreferredSize(new Dimension(117, 26));
            jNameLabel.setText("Player Name");
            jNameLabel.setFont(new Font("Dialog", Font.BOLD, 14));
            jNameLabel.setOpaque(true);
            jNameLabel.setBackground(new Color(162, 178, 194));
            jNameLabel.setForeground(Color.black);
            jNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            final FlowLayout flowLayout = new FlowLayout();
            flowLayout.setHgap(0);
            flowLayout.setVgap(1);
            jTopPanel = new JPanel();
            jTopPanel.setBackground(new Color(82, 98, 114));
            jTopPanel.setOpaque(false);
            jTopPanel.setLocation(new Point(3, 3));
            jTopPanel.setSize(new Dimension(120, 48));
            jTopPanel.setLayout(flowLayout);
            jTopPanel.setPreferredSize(new Dimension(1, 33));
            jTopPanel.add(jNameLabel, null);
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
            jBlindLabel.setSize(new Dimension(30, 30));
            jBlindLabel.setLocation(new Point(87, 33));
            jDealerLabel = new JLabel();
            jDealerLabel.setSize(new Dimension(30, 30));
            jDealerLabel.setLocation(new Point(87, 3));
            jCenterPanel = new JPanel();
            jCenterPanel.setLayout(null);
            jCenterPanel.setSize(new Dimension(117, 61));
            jCenterPanel.setLocation(new Point(4, 48));
            jCenterPanel.setBackground(Color.white);
            jCenterPanel.setOpaque(true);
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
    private JPanelCard getCardPanel1()
    {
        if (cardPanel1 == null)
        {
            cardPanel1 = new JPanelCard();
            cardPanel1.setSize(new Dimension(40, 56));
            cardPanel1.setLocation(new Point(2, 3));
        }
        return cardPanel1;
    }
    
    /**
     * This method initializes cardPanel11
     * 
     * @return clientGameGUI.CardPanel
     */
    private JPanelCard getCardPanel2()
    {
        if (cardPanel2 == null)
        {
            cardPanel2 = new JPanelCard();
            cardPanel2.setSize(new Dimension(40, 56));
            cardPanel2.setLocation(new Point(45, 3));
        }
        return cardPanel2;
    }
    
    public void setPlayerName(String name)
    {
        jNameLabel.setText(name);
    }
    
    public void setPlayerAction(TypeAction action)
    {
        setPlayerAction(action, 0);
    }
    
    public void setPlayerAction(TypeAction action, int amnt)
    {
        final StringBuilder sb = new StringBuilder("");
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
    
    public void isPlaying()
    {
        if (alive)
        {
            jActionLabel.setBackground(new Color(132, 148, 214));
        }
    }
    
    public void isWinning()
    {
        if (alive)
        {
            jActionLabel.setBackground(new Color(42, 186, 229));
            jActionLabel.setText("WIN");
        }
    }
    
    public void isDoingNothing()
    {
        if (alive)
        {
            jActionLabel.setBackground(Color.white);
        }
    }
    
    public void isMainPlayer(boolean mainPlayer)
    {
        main = mainPlayer;
    }
    
    public void setDead()
    {
        alive = false;
        jNameLabel.setBackground(Color.gray);
        jActionLabel.setBackground(Color.gray);
        jStatusLabel.setBackground(Color.gray);
        getJCenterPanel().setBackground(Color.gray);
    }
    
    public void setAlive()
    {
        alive = true;
        if (main)
        {
            jNameLabel.setBackground(new Color(112, 128, 214));
            jNameLabel.setForeground(Color.white);
        }
        else
        {
            jNameLabel.setBackground(new Color(162, 178, 194));
            jNameLabel.setForeground(Color.black);
        }
        jActionLabel.setBackground(Color.white);
        jStatusLabel.setBackground(Color.white);
        getJCenterPanel().setBackground(Color.white);
    }
}
