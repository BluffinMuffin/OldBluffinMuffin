package gui.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import basePoker.Card;

import utility.Bundle;

/**
 * @author Hocus
 *         This class represents the HUD (Heads Up Display) associated to each
 *         player.
 *         It contains informations about the player. (Money, cards, buttons,
 *         currently playing, ...)
 *         It is ready to enabled the remaining time of a player.
 */
public class HudPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    
    // private static final String PATH_IMAGE_BUTTON = "images/buttons/";
    private static final double SCALE_BUTTON_DEALER = 85.0 / 100.0;
    private static final double SCALE_BUTTON_SMALL_BLIND = 85.0 / 100.0;
    private static final double SCALE_BUTTON_BIG_BLIND = 85.0 / 100.0;
    
    // private static final int TIME_REMAINING_MAX = 15;
    // private static final int TIME_REMAINING_MIN = 0;
    
    private String m_playerName = "Player Name";
    
    private JLabel jLabelMoney;
    private CardPanel cardPanel2;
    private CardPanel cardPanel1;
    private ImagePanel imagePanelButtonDealer;
    private ImagePanel imagePanelButtonSmallBlind;
    private ImagePanel imagePanelButtonBigBlind;
    
    // private JProgressBar jProgressBarTimeRemaining;
    
    public HudPanel()
    {
        initComponents();
        setIsWinner(false);
        setIsDealer(true);
        setIsSmallBlind(true);
        setIsBigBlind(true);
        setPlayerName("Player Name");
    }
    
    private JPanel getCardPanel1()
    {
        if (cardPanel1 == null)
        {
            cardPanel1 = CardPanel.getInstance(Card.getInstance().get(Card.NO_CARD));
            cardPanel1.setLayout(new GroupLayout());
        }
        return cardPanel1;
    }
    
    // private JProgressBar getJProgressBarTimeRemaining() {
    // if (jProgressBarTimeRemaining == null) {
    // jProgressBarTimeRemaining = new JProgressBar();
    // jProgressBarTimeRemaining.setMaximum(TIME_REMAINING_MAX);
    // jProgressBarTimeRemaining.setMaximum(TIME_REMAINING_MIN);
    // jProgressBarTimeRemaining.setValue(TIME_REMAINING_MAX);
    // }
    // return jProgressBarTimeRemaining;
    // }
    
    private JPanel getCardPanel2()
    {
        if (cardPanel2 == null)
        {
            cardPanel2 = CardPanel.getInstance(Card.getInstance().get(Card.NO_CARD));
            cardPanel2.setLayout(new GroupLayout());
        }
        return cardPanel2;
    }
    
    private JPanel getImagePanelButtonBigBlind()
    {
        if (imagePanelButtonBigBlind == null)
        {
            imagePanelButtonBigBlind = new ImagePanel(ClassLoader.getSystemResource(Bundle.getIntance().get("viewer.image.buttonBigBlind")));
            imagePanelButtonBigBlind.setScale(HudPanel.SCALE_BUTTON_BIG_BLIND);
        }
        return imagePanelButtonBigBlind;
    }
    
    private JPanel getImagePanelButtonDealer()
    {
        if (imagePanelButtonDealer == null)
        {
            imagePanelButtonDealer = new ImagePanel(ClassLoader.getSystemResource(Bundle.getIntance().get("viewer.image.buttonDealer")));
            imagePanelButtonDealer.setScale(HudPanel.SCALE_BUTTON_DEALER);
        }
        return imagePanelButtonDealer;
    }
    
    private JPanel getImagePanelButtonSmallBlind()
    {
        if (imagePanelButtonSmallBlind == null)
        {
            imagePanelButtonSmallBlind = new ImagePanel(ClassLoader.getSystemResource(Bundle.getIntance().get("viewer.image.buttonSmallBlind")));
            imagePanelButtonSmallBlind.setScale(HudPanel.SCALE_BUTTON_SMALL_BLIND);
        }
        return imagePanelButtonSmallBlind;
    }
    
    private JLabel getJLabelCash()
    {
        if (jLabelMoney == null)
        {
            jLabelMoney = new JLabel();
            jLabelMoney.setHorizontalAlignment(SwingConstants.TRAILING);
            jLabelMoney.setForeground(Color.GREEN);
            jLabelMoney.setText("$$$.$$");
            jLabelMoney.setFont(new Font("Arial", Font.PLAIN, 14));
        }
        return jLabelMoney;
    }
    
    private void initComponents()
    {
        setBackground(Color.darkGray);
        setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(102, 51, 0), 2, true), "Player Name", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), Color.orange));
        setLayout(new GroupLayout());
        add(getCardPanel2(), new Constraints(new Leading(22, 43, 43, 43), new Leading(0, 57, 57, 57)));
        add(getCardPanel1(), new Constraints(new Leading(5, 43, 43, 43), new Leading(0, 57, 57, 57)));
        // add(getJProgressBarTimeRemaining(), new Constraints(new Leading(5,
        // 98, 98, 98), new Leading(0, 12, 12, 12)));
        add(getJLabelCash(), new Constraints(new Leading(0, 98, 12, 12), new Leading(63, 12, 12, 12)));
        add(getImagePanelButtonDealer(), new Constraints(new Leading(71, 33, 12, 12), new Leading(-5, 33, 12, 12)));
        add(getImagePanelButtonSmallBlind(), new Constraints(new Leading(77, 33, 12, 12), new Leading(28, 33, 10, 10)));
        add(getImagePanelButtonBigBlind(), new Constraints(new Leading(77, 33, 12, 12), new Leading(28, 33, 10, 10)));
        setSize(320, 223);
    }
    
    public void setCards(Card p_card1, Card p_card2)
    {
        cardPanel1.changeCard(p_card1);
        cardPanel2.changeCard(p_card2);
    }
    
    public void setIsBigBlind(boolean p_isBigBlind)
    {
        imagePanelButtonBigBlind.setVisible(p_isBigBlind);
    }
    
    public void setIsDealer(boolean p_isDealer)
    {
        imagePanelButtonDealer.setVisible(p_isDealer);
    }
    
    public void setIsPlaying(boolean p_isPlaying)
    {
        if (p_isPlaying)
        {
            this.setBackground(Color.RED);
            getJLabelCash().setForeground(Color.BLACK);
            jLabelMoney.setFont(new Font("Arial", Font.BOLD, 14));
            setPlayerName(m_playerName, Color.BLACK);
        }
        else
        {
            this.setBackground(Color.DARK_GRAY);
            getJLabelCash().setForeground(Color.GREEN);
            jLabelMoney.setFont(new Font("Arial", Font.PLAIN, 14));
            setPlayerName(m_playerName, Color.ORANGE);
        }
    }
    
    public void setIsSmallBlind(boolean p_isSmallBlind)
    {
        imagePanelButtonSmallBlind.setVisible(p_isSmallBlind);
    }
    
    public void setIsWinner(boolean p_isWinner)
    {
        if (p_isWinner)
        {
            this.setBackground(Color.WHITE);
            getJLabelCash().setForeground(Color.BLACK);
            jLabelMoney.setFont(new Font("Arial", Font.BOLD, 14));
            setPlayerName(m_playerName, Color.BLACK);
        }
        else
        {
            this.setBackground(Color.DARK_GRAY);
            getJLabelCash().setForeground(Color.GREEN);
            jLabelMoney.setFont(new Font("Arial", Font.PLAIN, 14));
            setPlayerName(m_playerName, Color.ORANGE);
        }
    }
    
    public void setMoney(String p_money)
    {
        jLabelMoney.setText(p_money);
    }
    
    public void setPlayerName(String p_playerName)
    {
        setPlayerName(p_playerName, Color.ORANGE);
    }
    
    public void setPlayerName(String p_playerName, Color p_color)
    {
        m_playerName = p_playerName;
        setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(102, 51, 0), 2, true), m_playerName, TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), p_color));
    }
    
    public void setTimeRemaining(Integer p_timeRemaining)
    {
        // jProgressBarTimeRemaining.setValue(p_timeRemaining);
    }
    
}
