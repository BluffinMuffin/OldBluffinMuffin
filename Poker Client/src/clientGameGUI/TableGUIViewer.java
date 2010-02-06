package clientGameGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import pokerAI.IPokerAgent;
import pokerAI.IPokerAgentListener;
import pokerLogic.Card;
import pokerLogic.PokerPlayerInfo;
import pokerLogic.PokerTableInfo;
import pokerLogic.TypePlayerAction;
import pokerLogic.TypePokerRound;
import utilGUI.JBackgroundPanel;
import utility.Bundle;
import utility.Constants;
import utility.IClosingListener;
import clientGame.ClientPokerTableInfo;

/**
 * @author Hocus
 *         This class is a windows displaying the vision of a player.
 *         No interaction can be done through this class.
 *         A part of this class was generated using a visual editor
 *         (http://wiki.eclipse.org/VE).
 */
public class TableGUIViewer extends JFrame implements IPokerAgentListener
{
    protected class GUIPlayer
    {
        public HudPanel m_hud;
        
        public JLabel m_bet;
        
        public GUIPlayer(HudPanel p_hud, JLabel p_bet)
        {
            m_hud = p_hud;
            m_bet = p_bet;
        }
    }
    
    private static final long serialVersionUID = -2593630757205925095L;
    private JPanel jPanel1;
    private JBackgroundPanel jBackgroundPanel0;
    private HudPanel hudPanel5;
    private HudPanel hudPanel6;
    private HudPanel hudPanel7;
    private HudPanel hudPanel8;
    private HudPanel hudPanelClient;
    private HudPanel hudPanel1;
    private HudPanel hudPanel2;
    private HudPanel hudPanel3;
    
    private HudPanel hudPanel4;
    private CardPanel cardPanelFlop1;
    private CardPanel cardPanelFlop2;
    private CardPanel cardPanelFlop3;
    private CardPanel cardPanelTurn;
    
    private CardPanel cardPanelRiver;
    private JLabel jLabelBet7;
    private JLabel jLabelBet8;
    private JLabel jLabelBet4;
    private JLabel jLabelBet3;
    private JLabel jLabelBet2;
    private JLabel jLabelBet1;
    private JLabel jLabelBet5;
    private JLabel jLabelBet6;
    
    private JLabel jLabelBetClient;
    private JLabel jLabelTotalPot;
    private JLabel jLabelSidePot1;
    private JLabel jLabelSidePot2;
    private JLabel jLabelSidePot3;
    private JLabel jLabelSidePot4;
    private JLabel jLabelSidePot0;
    private JLabel jLabelSidePot6;
    private JLabel jLabelSidePot7;
    private JLabel jLabelSidePot8;
    
    private JLabel jLabelSidePot5;
    
    private JLabel jLabel0;
    protected Bundle m_bundle = Bundle.getIntance();
    private final ArrayList<GUIPlayer> m_players = new ArrayList<GUIPlayer>();
    private final ArrayList<JLabel> m_pots = new ArrayList<JLabel>();
    private final ArrayList<CardPanel> m_boardCards = new ArrayList<CardPanel>();
    private final List<IClosingListener<IPokerAgent>> m_closingListeners = Collections.synchronizedList(new ArrayList<IClosingListener<IPokerAgent>>());
    
    protected ClientPokerTableInfo m_table = null;
    
    private final static Integer NB_PLAYERS = 9;
    
    public TableGUIViewer()
    {
        initComponents();
        m_players.add(new GUIPlayer(getHudPanelClient(), getJLabelBetClient()));
        m_players.add(new GUIPlayer(getHudPanel1(), getJLabelBet1()));
        m_players.add(new GUIPlayer(getHudPanel2(), getJLabelBet2()));
        m_players.add(new GUIPlayer(getHudPanel3(), getJLabelBet3()));
        m_players.add(new GUIPlayer(getHudPanel4(), getJLabelBet4()));
        m_players.add(new GUIPlayer(getHudPanel5(), getJLabelBet5()));
        m_players.add(new GUIPlayer(getHudPanel6(), getJLabelBet6()));
        m_players.add(new GUIPlayer(getHudPanel7(), getJLabelBet7()));
        m_players.add(new GUIPlayer(getHudPanel8(), getJLabelBet8()));
        
        m_pots.add(getJLabelSidePot0());
        m_pots.add(getJLabelSidePot1());
        m_pots.add(getJLabelSidePot2());
        m_pots.add(getJLabelSidePot3());
        m_pots.add(getJLabelSidePot4());
        m_pots.add(getJLabelSidePot5());
        m_pots.add(getJLabelSidePot6());
        m_pots.add(getJLabelSidePot7());
        m_pots.add(getJLabelSidePot8());
        
        m_boardCards.add(getCardPanelFlop1());
        m_boardCards.add(getCardPanelFlop2());
        m_boardCards.add(getCardPanelFlop3());
        m_boardCards.add(getCardPanelTurn());
        m_boardCards.add(getCardPanelRiver());
    }
    
    @Override
    public void addClosingListener(IClosingListener<IPokerAgent> p_listener)
    {
        m_closingListeners.add(p_listener);
    }
    
    public void betTurnEnded(ArrayList<Integer> p_potIndices, TypePokerRound p_gameState)
    {
        for (final Integer index : p_potIndices)
        {
            final JLabel pot = m_pots.get(index);
            final int potAmount = m_table.m_pots.get(index);
            pot.setVisible(potAmount > 0);
            pot.setText(format(potAmount));
        }
        
        for (final GUIPlayer player : m_players)
        {
            player.m_bet.setText(format(0));
        }
    }
    
    public void boardChanged(ArrayList<Integer> p_boardCardIndices)
    {
        for (final Integer index : p_boardCardIndices)
        {
            final CardPanel cardPanel = m_boardCards.get(index);
            final Card card = m_table.m_boardCards.get(index);
            cardPanel.changeCard(card);
        }
    }
    
    public void doAction(GUIPlayer p_player, TypePlayerAction p_action, int p_actionAmount)
    {
        switch (p_action)
        {
            case CALL:
                break;
            case CHECK:
                break;
            case FOLD:
                foldPlayer(p_player);
                break;
            case NOTHING:
                break;
            case RAISE:
                break;
            case UNKNOWN:
                break;
        }
    }
    
    private void foldPlayer(GUIPlayer p_player)
    {
        p_player.m_hud.setCards(Card.getInstance().get(-1), Card.getInstance().get(-1));
    }
    
    protected String format(Number p_nb)
    {
        try
        {
            return Constants.TOKEN_FORMATTER.valueToString(p_nb);
            // return m_formatter.valueToString(p_nb);
        }
        catch (final ParseException e)
        {
        }
        
        return "";
    }
    
    public void gameEnded()
    {
    }
    
    public void gameStarted(PokerPlayerInfo p_oldDealer, PokerPlayerInfo p_oldSmallBlind, PokerPlayerInfo p_oldBigBlind)
    {
        for (final CardPanel cardPanel : m_boardCards)
        {
            cardPanel.changeCard(Card.getInstance().get(-1));
        }
        
        getJLabelTotalPot().setText(format(0));
        
        for (final JLabel pot : m_pots)
        {
            pot.setText(format(0));
            pot.setVisible(false);
        }
        
        for (final GUIPlayer player : m_players)
        {
            player.m_bet.setText(format(0));
            player.m_hud.setIsPlaying(false);
            player.m_hud.setIsWinner(false);
            player.m_hud.setCards(Card.getInstance().get(-1), Card.getInstance().get(-1));
            player.m_hud.setIsDealer(false);
            player.m_hud.setIsSmallBlind(false);
            player.m_hud.setIsBigBlind(false);
        }
        
        getPlayer(m_table.m_dealer.m_noSeat).m_hud.setIsDealer(true);
        getPlayer(m_table.m_smallBlind.m_noSeat).m_hud.setIsSmallBlind(true);
        getPlayer(m_table.m_bigBlind.m_noSeat).m_hud.setIsBigBlind(true);
    }
    
    private CardPanel getCardPanelFlop1()
    {
        if (cardPanelFlop1 == null)
        {
            cardPanelFlop1 = CardPanel.getInstance(Card.getInstance().get(Card.NO_CARD));
        }
        return cardPanelFlop1;
    }
    
    private CardPanel getCardPanelFlop2()
    {
        if (cardPanelFlop2 == null)
        {
            cardPanelFlop2 = CardPanel.getInstance(Card.getInstance().get(Card.NO_CARD));
        }
        return cardPanelFlop2;
    }
    
    private CardPanel getCardPanelFlop3()
    {
        if (cardPanelFlop3 == null)
        {
            cardPanelFlop3 = CardPanel.getInstance(Card.getInstance().get(Card.NO_CARD));
        }
        return cardPanelFlop3;
    }
    
    private CardPanel getCardPanelRiver()
    {
        if (cardPanelRiver == null)
        {
            cardPanelRiver = CardPanel.getInstance(Card.getInstance().get(Card.NO_CARD));
        }
        return cardPanelRiver;
    }
    
    private CardPanel getCardPanelTurn()
    {
        if (cardPanelTurn == null)
        {
            cardPanelTurn = CardPanel.getInstance(Card.getInstance().get(Card.NO_CARD));
        }
        return cardPanelTurn;
    }
    
    private HudPanel getHudPanel1()
    {
        if (hudPanel1 == null)
        {
            hudPanel1 = new HudPanel();
        }
        return hudPanel1;
    }
    
    private HudPanel getHudPanel2()
    {
        if (hudPanel2 == null)
        {
            hudPanel2 = new HudPanel();
        }
        return hudPanel2;
    }
    
    private HudPanel getHudPanel3()
    {
        if (hudPanel3 == null)
        {
            hudPanel3 = new HudPanel();
        }
        return hudPanel3;
    }
    
    private HudPanel getHudPanel4()
    {
        if (hudPanel4 == null)
        {
            hudPanel4 = new HudPanel();
        }
        return hudPanel4;
    }
    
    private HudPanel getHudPanel5()
    {
        if (hudPanel5 == null)
        {
            hudPanel5 = new HudPanel();
        }
        return hudPanel5;
    }
    
    private HudPanel getHudPanel6()
    {
        if (hudPanel6 == null)
        {
            hudPanel6 = new HudPanel();
        }
        return hudPanel6;
    }
    
    private HudPanel getHudPanel7()
    {
        if (hudPanel7 == null)
        {
            hudPanel7 = new HudPanel();
        }
        return hudPanel7;
    }
    
    private HudPanel getHudPanel8()
    {
        if (hudPanel8 == null)
        {
            hudPanel8 = new HudPanel();
        }
        return hudPanel8;
    }
    
    private HudPanel getHudPanelClient()
    {
        if (hudPanelClient == null)
        {
            hudPanelClient = new HudPanel();
        }
        return hudPanelClient;
    }
    
    private JBackgroundPanel getJBackgroundPanel0()
    {
        if (jBackgroundPanel0 == null)
        {
            jBackgroundPanel0 = new JBackgroundPanel();
            jBackgroundPanel0.setBackground(new ImageIcon("images/table.png"));
            jBackgroundPanel0.add(getJPanel1());
        }
        return jBackgroundPanel0;
    }
    
    private JLabel getJLabelBet1()
    {
        if (jLabelBet1 == null)
        {
            jLabelBet1 = new JLabel();
            jLabelBet1.setText("2.50$");
            jLabelBet1.setFont(new Font("Arial", Font.BOLD, 15));
            jLabelBet1.setForeground(Color.WHITE);
        }
        return jLabelBet1;
    }
    
    private JLabel getJLabelBet2()
    {
        if (jLabelBet2 == null)
        {
            jLabelBet2 = new JLabel();
            jLabelBet2.setText("2.50$");
            jLabelBet2.setFont(new Font("Arial", Font.BOLD, 15));
            jLabelBet2.setForeground(Color.WHITE);
        }
        return jLabelBet2;
    }
    
    private JLabel getJLabelBet3()
    {
        if (jLabelBet3 == null)
        {
            jLabelBet3 = new JLabel();
            jLabelBet3.setText("2.50$");
            jLabelBet3.setFont(new Font("Arial", Font.BOLD, 15));
            jLabelBet3.setForeground(Color.WHITE);
        }
        return jLabelBet3;
    }
    
    private JLabel getJLabelBet4()
    {
        if (jLabelBet4 == null)
        {
            jLabelBet4 = new JLabel();
            jLabelBet4.setText("2.50$");
            jLabelBet4.setFont(new Font("Arial", Font.BOLD, 15));
            jLabelBet4.setForeground(Color.WHITE);
        }
        return jLabelBet4;
    }
    
    private JLabel getJLabelBet5()
    {
        if (jLabelBet5 == null)
        {
            jLabelBet5 = new JLabel();
            jLabelBet5.setText("2.50$");
            jLabelBet5.setFont(new Font("Arial", Font.BOLD, 15));
            jLabelBet5.setForeground(Color.WHITE);
        }
        return jLabelBet5;
    }
    
    private JLabel getJLabelBet6()
    {
        if (jLabelBet6 == null)
        {
            jLabelBet6 = new JLabel();
            jLabelBet6.setHorizontalAlignment(SwingConstants.TRAILING);
            jLabelBet6.setText("2.50$");
            jLabelBet6.setFont(new Font("Arial", Font.BOLD, 15));
            jLabelBet6.setForeground(Color.WHITE);
        }
        return jLabelBet6;
    }
    
    private JLabel getJLabelBet7()
    {
        if (jLabelBet7 == null)
        {
            jLabelBet7 = new JLabel();
            jLabelBet7.setHorizontalAlignment(SwingConstants.TRAILING);
            jLabelBet7.setText("2.50$");
            jLabelBet7.setFont(new Font("Arial", Font.BOLD, 15));
            jLabelBet7.setForeground(Color.WHITE);
        }
        return jLabelBet7;
    }
    
    private JLabel getJLabelBet8()
    {
        if (jLabelBet8 == null)
        {
            jLabelBet8 = new JLabel();
            jLabelBet8.setHorizontalAlignment(SwingConstants.TRAILING);
            jLabelBet8.setText("2.50$");
            jLabelBet8.setFont(new Font("Arial", Font.BOLD, 15));
            jLabelBet8.setForeground(Color.WHITE);
        }
        return jLabelBet8;
    }
    
    private JLabel getJLabelBetClient()
    {
        if (jLabelBetClient == null)
        {
            jLabelBetClient = new JLabel();
            jLabelBetClient.setHorizontalAlignment(SwingConstants.CENTER);
            jLabelBetClient.setText("2.50$");
            jLabelBetClient.setFont(new Font("Arial", Font.BOLD, 15));
            jLabelBetClient.setForeground(Color.WHITE);
        }
        return jLabelBetClient;
    }
    
    private JLabel getJLabelSidePot0()
    {
        if (jLabelSidePot0 == null)
        {
            jLabelSidePot0 = new JLabel();
            jLabelSidePot0.setHorizontalAlignment(SwingConstants.CENTER);
            jLabelSidePot0.setText("9990.00$");
            jLabelSidePot0.setFont(new Font("Arial", Font.BOLD, 15));
            jLabelSidePot0.setForeground(Color.WHITE);
        }
        return jLabelSidePot0;
    }
    
    private JLabel getJLabelSidePot1()
    {
        if (jLabelSidePot1 == null)
        {
            jLabelSidePot1 = new JLabel();
            jLabelSidePot1.setHorizontalAlignment(SwingConstants.CENTER);
            jLabelSidePot1.setText("100,000.00$");
            jLabelSidePot1.setFont(new Font("Arial", Font.BOLD, 15));
            jLabelSidePot1.setForeground(Color.WHITE);
        }
        return jLabelSidePot1;
    }
    
    private JLabel getJLabelSidePot2()
    {
        if (jLabelSidePot2 == null)
        {
            jLabelSidePot2 = new JLabel();
            jLabelSidePot2.setHorizontalAlignment(SwingConstants.CENTER);
            jLabelSidePot2.setText("100,000.00$");
            jLabelSidePot2.setFont(new Font("Arial", Font.BOLD, 15));
            jLabelSidePot2.setForeground(Color.WHITE);
        }
        return jLabelSidePot2;
    }
    
    private JLabel getJLabelSidePot3()
    {
        if (jLabelSidePot3 == null)
        {
            jLabelSidePot3 = new JLabel();
            jLabelSidePot3.setHorizontalAlignment(SwingConstants.CENTER);
            jLabelSidePot3.setText("100,000.00$");
            jLabelSidePot3.setFont(new Font("Arial", Font.BOLD, 15));
            jLabelSidePot3.setForeground(Color.WHITE);
            
        }
        return jLabelSidePot3;
    }
    
    private JLabel getJLabelSidePot4()
    {
        if (jLabelSidePot4 == null)
        {
            jLabelSidePot4 = new JLabel();
            jLabelSidePot4.setHorizontalAlignment(SwingConstants.CENTER);
            jLabelSidePot4.setText("1000.00$");
            jLabelSidePot4.setFont(new Font("Arial", Font.BOLD, 15));
            jLabelSidePot4.setForeground(Color.WHITE);
        }
        return jLabelSidePot4;
    }
    
    // private NumberFormatter m_formatter = new NumberFormatter(new
    // CurrencyIntegerFormat());
    
    private JLabel getJLabelSidePot5()
    {
        if (jLabelSidePot5 == null)
        {
            jLabelSidePot5 = new JLabel();
            jLabelSidePot5.setHorizontalAlignment(SwingConstants.CENTER);
            jLabelSidePot5.setText("1000.00$");
            jLabelSidePot5.setFont(new Font("Arial", Font.BOLD, 15));
            jLabelSidePot5.setForeground(Color.WHITE);
        }
        return jLabelSidePot5;
    }
    
    private JLabel getJLabelSidePot6()
    {
        if (jLabelSidePot6 == null)
        {
            jLabelSidePot6 = new JLabel();
            jLabelSidePot6.setHorizontalAlignment(SwingConstants.CENTER);
            jLabelSidePot6.setText("1000.00$");
            jLabelSidePot6.setFont(new Font("Arial", Font.BOLD, 15));
            jLabelSidePot6.setForeground(Color.WHITE);
        }
        return jLabelSidePot6;
    }
    
    private JLabel getJLabelSidePot7()
    {
        if (jLabelSidePot7 == null)
        {
            jLabelSidePot7 = new JLabel();
            jLabelSidePot7.setHorizontalAlignment(SwingConstants.CENTER);
            jLabelSidePot7.setText("1000.00$");
            jLabelSidePot7.setFont(new Font("Arial", Font.BOLD, 15));
            jLabelSidePot7.setForeground(Color.WHITE);
        }
        return jLabelSidePot7;
    }
    
    private JLabel getJLabelSidePot8()
    {
        if (jLabelSidePot8 == null)
        {
            jLabelSidePot8 = new JLabel();
            jLabelSidePot8.setHorizontalAlignment(SwingConstants.CENTER);
            jLabelSidePot8.setText("1000.00$");
            jLabelSidePot8.setFont(new Font("Arial", Font.BOLD, 15));
            jLabelSidePot8.setForeground(Color.WHITE);
        }
        return jLabelSidePot8;
    }
    
    private JLabel getJLabelTotalPot()
    {
        if (jLabelTotalPot == null)
        {
            jLabelTotalPot = new JLabel();
            jLabelTotalPot.setForeground(Color.ORANGE);
            jLabelTotalPot.setHorizontalAlignment(SwingConstants.CENTER);
            jLabelTotalPot.setText("1000.00$");
            jLabelTotalPot.setFont(new Font("Arial", Font.BOLD, 15));
        }
        return jLabelTotalPot;
    }
    
    @SuppressWarnings("serial")
    private JLabel getJLabelTotalPotTitle()
    {
        if (jLabel0 == null)
        {
            jLabel0 = new JLabel()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("viewer.label.totalPot") + ":";
                }
            };
            jLabel0.setForeground(Color.WHITE);
            jLabel0.setFont(new Font("Arial", Font.BOLD, 15));
        }
        return jLabel0;
    }
    
    protected JPanel getJPanel1()
    {
        if (jPanel1 == null)
        {
            jPanel1 = new JPanel();
            jPanel1.setLayout(new GroupLayout());
            jPanel1.add(getHudPanelClient(), new Constraints(new Leading(379, 122, 10, 10), new Leading(415, 105, 10, 10)));
            jPanel1.add(getHudPanel1(), new Constraints(new Leading(190, 122, 10, 10), new Leading(406, 105, 12, 12)));
            jPanel1.add(getHudPanel4(), new Constraints(new Leading(238, 122, 10, 10), new Leading(3, 105, 12, 12)));
            jPanel1.add(getHudPanel5(), new Constraints(new Leading(513, 122, 10, 10), new Leading(3, 105, 12, 12)));
            jPanel1.add(getHudPanel6(), new Constraints(new Leading(696, 122, 12, 12), new Leading(93, 105, 10, 10)));
            jPanel1.add(getHudPanel7(), new Constraints(new Leading(720, 117, 10, 10), new Leading(293, 105, 12, 12)));
            jPanel1.add(getHudPanel3(), new Constraints(new Leading(51, 122, 10, 10), new Leading(93, 105, 12, 12)));
            jPanel1.add(getHudPanel2(), new Constraints(new Leading(32, 122, 10, 10), new Leading(293, 105, 12, 12)));
            jPanel1.add(getHudPanel8(), new Constraints(new Leading(560, 122, 10, 10), new Leading(406, 105, 12, 12)));
            jPanel1.add(getJLabelBet8(), new Constraints(new Leading(523, 60, 60, 60), new Leading(364, 10, 10)));
            jPanel1.add(getJLabelBetClient(), new Constraints(new Leading(406, 60, 60, 60), new Leading(372, 10, 10)));
            jPanel1.add(getJLabelBet5(), new Constraints(new Leading(519, 60, 60, 60), new Leading(143, 10, 10)));
            jPanel1.add(getJLabelBet4(), new Constraints(new Leading(320, 60, 60, 60), new Leading(141, 10, 10)));
            jPanel1.add(getJLabelBet3(), new Constraints(new Leading(199, 60, 60, 60), new Leading(186, 10, 10)));
            jPanel1.add(getCardPanelFlop1(), new Constraints(new Leading(332, 16, 10, 10), new Leading(175, 17, 12, 12)));
            jPanel1.add(getCardPanelFlop2(), new Constraints(new Leading(374, 16, 10, 10), new Leading(175, 17, 12, 12)));
            jPanel1.add(getCardPanelFlop3(), new Constraints(new Leading(416, 16, 10, 10), new Leading(175, 17, 12, 12)));
            jPanel1.add(getCardPanelTurn(), new Constraints(new Leading(458, 16, 10, 10), new Leading(175, 17, 12, 12)));
            jPanel1.add(getCardPanelRiver(), new Constraints(new Leading(500, 16, 10, 10), new Leading(175, 17, 12, 12)));
            jPanel1.add(getJLabelSidePot1(), new Constraints(new Leading(265, 80, 10, 10), new Leading(246, 12, 12)));
            jPanel1.add(getJLabelSidePot2(), new Constraints(new Leading(352, 80, 12, 12), new Leading(246, 12, 12)));
            jPanel1.add(getJLabelSidePot3(), new Constraints(new Leading(439, 80, 10, 10), new Leading(246, 12, 12)));
            jPanel1.add(getJLabelSidePot4(), new Constraints(new Leading(525, 80, 10, 10), new Leading(246, 12, 12)));
            jPanel1.add(getJLabelSidePot6(), new Constraints(new Leading(485, 80, 12, 12), new Leading(274, 12, 12)));
            jPanel1.add(getJLabelSidePot5(), new Constraints(new Leading(313, 80, 12, 12), new Leading(274, 12, 12)));
            jPanel1.add(getJLabelSidePot7(), new Constraints(new Leading(352, 80, 12, 12), new Leading(302, 12, 12)));
            jPanel1.add(getJLabelSidePot8(), new Constraints(new Leading(439, 80, 12, 12), new Leading(302, 12, 12)));
            jPanel1.add(getJLabelBet7(), new Constraints(new Leading(601, 60, 10, 10), new Leading(305, 12, 12)));
            jPanel1.add(getJLabelBet1(), new Constraints(new Leading(298, 60, 10, 10), new Leading(359, 10, 10)));
            jPanel1.add(getJLabelBet2(), new Constraints(new Leading(193, 60, 10, 10), new Leading(302, 19, 12, 12)));
            jPanel1.add(getJLabelBet6(), new Constraints(new Leading(601, 60, 12, 12), new Leading(186, 12, 12)));
            jPanel1.add(getJLabelTotalPot(), new Constraints(new Leading(371, 130, 12, 12), new Leading(36, 10, 10)));
            jPanel1.add(getJLabelTotalPotTitle(), new Constraints(new Leading(372, 12, 12), new Leading(14, 12, 12)));
            jPanel1.add(getJLabelSidePot0(), new Constraints(new Leading(396, 80, 12, 12), new Leading(155, 12, 12)));
        }
        return jPanel1;
    }
    
    protected GUIPlayer getPlayer(int p_noSeat)
    {
        return m_players.get((p_noSeat + (TableGUIViewer.NB_PLAYERS - m_table.m_localPlayer.m_noSeat)) % TableGUIViewer.NB_PLAYERS);
    }
    
    private void initComponents()
    {
        setFont(new Font("Dialog", Font.PLAIN, 12));
        setResizable(false);
        setForeground(Color.black);
        add(getJBackgroundPanel0(), BorderLayout.CENTER);
        setSize(874, 556);
    }
    
    public void playerCardChanged(PokerPlayerInfo p_player)
    {
        getPlayer(p_player.m_noSeat).m_hud.setCards(p_player.getHand()[0], p_player.getHand()[1]);
    }
    
    public void playerJoined(PokerPlayerInfo p_player)
    {
        final GUIPlayer playerGUI = getPlayer(p_player.m_noSeat);
        playerGUI.m_bet.setText(format(p_player.m_betAmount));
        playerGUI.m_bet.setVisible(true);
        playerGUI.m_hud.setPlayerName(p_player.m_name);
        playerGUI.m_hud.setMoney(format(p_player.m_money));
        playerGUI.m_hud.setCards(p_player.getHand()[0], p_player.getHand()[1]);
        playerGUI.m_hud.setIsDealer(p_player.m_isDealer);
        playerGUI.m_hud.setIsSmallBlind(p_player.m_isSmallBlind);
        playerGUI.m_hud.setIsBigBlind(p_player.m_isBigBlind);
        playerGUI.m_hud.setTimeRemaining(p_player.m_timeRemaining);
        playerGUI.m_hud.setVisible(true);
    }
    
    public void playerLeft(PokerPlayerInfo p_player)
    {
        final GUIPlayer playerGUI = getPlayer(p_player.m_noSeat);
        playerGUI.m_hud.setVisible(false);
        playerGUI.m_bet.setVisible(false);
    }
    
    public void playerMoneyChanged(PokerPlayerInfo p_player, int p_oldMoneyAmount)
    {
        final GUIPlayer playerGUI = getPlayer(p_player.m_noSeat);
        playerGUI.m_hud.setMoney(format(p_player.m_money));
    }
    
    public void playerTurnBegan(PokerPlayerInfo p_oldCurrentPlayer)
    {
        getPlayer(m_table.m_currentPlayer.m_noSeat).m_hud.setIsPlaying(true);
    }
    
    public void playerTurnEnded(PokerPlayerInfo p_player, TypePlayerAction p_action, int p_actionAmount)
    {
        final GUIPlayer player = getPlayer(p_player.m_noSeat);
        player.m_bet.setText(format(p_player.m_betAmount));
        player.m_hud.setMoney(format(p_player.m_money));
        doAction(player, p_action, p_actionAmount);
        player.m_hud.setIsPlaying(false);
        getJLabelTotalPot().setText(format(m_table.m_totalPotAmount));
    }
    
    public void potWon(PokerPlayerInfo p_player, int p_potAmountWon, int p_potIndex)
    {
        m_pots.get(p_potIndex).setVisible(false);
        final GUIPlayer playerGUI = getPlayer(p_player.m_noSeat);
        playerGUI.m_hud.setMoney(format(p_player.m_money));
        playerGUI.m_hud.setIsWinner(true);
    }
    
    @Override
    public void removeClosingListener(IClosingListener<IPokerAgent> p_listener)
    {
        m_closingListeners.remove(p_listener);
    }
    
    public void run()
    {
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e)
            {
                synchronized (m_closingListeners)
                {
                    for (final IClosingListener<IPokerAgent> listener : m_closingListeners)
                    {
                        listener.closing(TableGUIViewer.this);
                    }
                }
            }
        });
        this.setTitle(this.toString() + " - " + m_table.m_name);
        this.getContentPane().setPreferredSize(this.getSize());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    public void setTable(PokerTableInfo p_table)
    {
        m_table = (ClientPokerTableInfo) p_table;
    }
    
    @Override
    public void start()
    {
        SwingUtilities.invokeLater(this);
    }
    
    @Override
    public void stop()
    {
        this.setVisible(false);
    }
    
    public void tableClosed()
    {
    }
    
    public void tableInfos()
    {
        getJLabelTotalPot().setText(format(m_table.m_totalPotAmount));
        
        for (final JLabel pot : m_pots)
        {
            pot.setText(format(0));
            pot.setVisible(false);
        }
        
        for (int i = 0; i != m_table.m_pots.size(); ++i)
        {
            final JLabel pot = m_pots.get(i);
            final int potAmount = m_table.m_pots.get(i);
            pot.setVisible(potAmount > 0);
            pot.setText(format(potAmount));
        }
        
        for (int i = 0; i != m_boardCards.size(); ++i)
        {
            m_boardCards.get(i).changeCard(m_table.m_boardCards.get(i));
        }
        
        for (final GUIPlayer playerGUI : m_players)
        {
            playerGUI.m_bet.setVisible(false);
            playerGUI.m_hud.setVisible(false);
        }
        
        for (final Integer i : m_table.getPlayerIds())
        {
            final PokerPlayerInfo player = m_table.getPlayer(i);
            final GUIPlayer playerGUI = getPlayer(i);
            
            playerGUI.m_bet.setText(format(player.m_betAmount));
            playerGUI.m_bet.setVisible(true);
            playerGUI.m_hud.setPlayerName(player.m_name);
            playerGUI.m_hud.setMoney(format(player.m_money));
            playerGUI.m_hud.setCards(player.getHand()[0], player.getHand()[1]);
            playerGUI.m_hud.setIsDealer(player.m_isDealer);
            playerGUI.m_hud.setIsSmallBlind(player.m_isSmallBlind);
            playerGUI.m_hud.setIsBigBlind(player.m_isBigBlind);
            playerGUI.m_hud.setTimeRemaining(player.m_timeRemaining);
            playerGUI.m_hud.setVisible(true);
        }
    }
    
    @Override
    public String toString()
    {
        return m_bundle.get("viewer.title");
    }
    
    public void waitingForPlayers()
    {
    }
}
