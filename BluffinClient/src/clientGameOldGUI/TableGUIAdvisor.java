package clientGameOldGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.Leading;


import pokerLogic.Card;
import pokerLogic.PokerPlayerAction;
import pokerLogic.PokerPlayerInfo;
import pokerLogic.PokerTableInfo;
import pokerLogic.TypePlayerAction;
import pokerStats.MonteCarlo;
import pokerStats.StatsInfos;
import clientAI.PokerSVM;
import clientGameTools.ClientPokerAdapter;
import clientGameTools.ClientPokerObserver;
import clientStats.PlayerStats;
import clientStats.StatsAgent;

/**
 * @author Hocus
 *         This class is a windows displaying the vision of a player
 *         and allows interactions with the game using buttons.
 *         Moreover, several tools are available to the user. (Profiler, Stats,
 *         Advice, ...)
 *         A part of this class was generated using a visual editor
 *         (http://wiki.eclipse.org/VE).
 */
public class TableGUIAdvisor extends TableGUI
{
    class LoadSVMTask extends SwingWorker<Void, Void>
    {
        @Override
        public Void doInBackground()
        {
            while ((m_pokerSVM != null) && !m_pokerSVM.isReady())
            {
                try
                {
                    Thread.sleep(500);
                }
                catch (final InterruptedException ignore)
                {
                }
            }
            
            return null;
        }
        
        @Override
        public void done()
        {
            getJProgressBarLoadingSVM().setVisible(false);
            getJButtonAdvisor().setVisible(true);
        }
    }
    
    private static final long serialVersionUID = -526188455940605519L;
    
    private static final int NB_MC_ITERATIONS = 10000;
    
    private StatsAgent m_statsAgent = null;
    
    private PokerSVM m_pokerSVM = null;
    private JDialogPlayerStats jDialogPlayerStats = null;
    private TypePlayerAction m_tipAction = TypePlayerAction.NOTHING;
    private boolean m_advisorEnabled = false;
    
    private Double m_probWin = 0.0;
    private Double m_standardDeviationWin = 0.0;
    private JButton jButtonStats = null;
    private JButton jButtonAdvisor = null;
    private JLabel jLabelProbWin = null;
    
    private JLabel jLabelAdvise = null;
    
    private JProgressBar jProgressBarLoadingSVM = null;
    
    private TableGUIAdvisor()
    {
        super();
        initComponents();
        new LoadSVMTask().execute();
    }
    
    public TableGUIAdvisor(StatsAgent p_statsAgent, PokerSVM p_pokerSVM)
    {
        this();
        m_statsAgent = p_statsAgent;
        m_pokerSVM = p_pokerSVM;
    }
    
    private StatsInfos calculateHandValues()
    {
        final Card[] holeCards = m_table.m_localPlayer.getHand();
        final Card[] boardCards = m_table.m_boardCards.toArray(new Card[m_table.m_boardCards.size()]);
        
        return MonteCarlo.CalculateWinRatio(holeCards, boardCards, m_table.m_nbRemainingPlayers, TableGUIAdvisor.NB_MC_ITERATIONS);
    }
    
    @Override
    public PokerPlayerAction getAction()
    {
        updateAdvice();
        return super.getAction();
    }
    
    @SuppressWarnings("serial")
    private JButton getJButtonAdvisor()
    {
        if (jButtonAdvisor == null)
        {
            jButtonAdvisor = new JButton()
            {
                @Override
                public String getText()
                {
                    return ((m_advisorEnabled) ? m_bundle.get("advisor.buttonSwitchAdvisorOFF") : m_bundle.get("advisor.buttonSwitchAdvisorON"));
                }
            };
            
            jButtonAdvisor.setVisible((m_pokerSVM == null) || m_pokerSVM.isReady());
            jButtonAdvisor.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent event)
                {
                    jButtonAdvisorActionPerformed(event);
                }
            });
        }
        
        return jButtonAdvisor;
    }
    
    @SuppressWarnings("serial")
    private JButton getJButtonStats()
    {
        if (jButtonStats == null)
        {
            jButtonStats = new JButton()
            {
                @Override
                public String getText()
                {
                    return m_bundle.get("advisor.buttonStats");
                }
            };
            jButtonStats.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent event)
                {
                    jButtonStatsActionPerformed(event);
                }
            });
        }
        return jButtonStats;
    }
    
    public JDialogPlayerStats getjDialogPlayerStats()
    {
        if (jDialogPlayerStats == null)
        {
            jDialogPlayerStats = new JDialogPlayerStats(this, m_statsAgent);
        }
        
        return jDialogPlayerStats;
    }
    
    private JLabel getJLabelAdvise()
    {
        if (jLabelAdvise == null)
        {
            jLabelAdvise = new JLabel();
            jLabelAdvise.setForeground(Color.ORANGE);
            jLabelAdvise.setVisible(m_advisorEnabled);
            jLabelAdvise.setFont(new Font("Arial", Font.PLAIN, 14));
            jLabelAdvise.setHorizontalAlignment(SwingConstants.CENTER);
            jLabelAdvise.setBorder(new LineBorder(Color.LIGHT_GRAY));
            jLabelAdvise.setText(m_bundle.get("advisor.label.noAdvise"));
        }
        
        return jLabelAdvise;
    }
    
    private JLabel getJLabelProbWin()
    {
        if (jLabelProbWin == null)
        {
            jLabelProbWin = new JLabel();
            jLabelProbWin.setForeground(Color.WHITE);
            jLabelProbWin.setFont(new Font("Arial", Font.PLAIN, 15));
            jLabelProbWin.setHorizontalAlignment(SwingConstants.CENTER);
            jLabelProbWin.setBorder(new LineBorder(Color.DARK_GRAY));
            jLabelProbWin.setText("");
        }
        
        return jLabelProbWin;
    }
    
    private JProgressBar getJProgressBarLoadingSVM()
    {
        if (jProgressBarLoadingSVM == null)
        {
            jProgressBarLoadingSVM = new JProgressBar();
            jProgressBarLoadingSVM.setIndeterminate(true);
            jProgressBarLoadingSVM.setBorderPainted(true);
            jProgressBarLoadingSVM.setBorder(new LineBorder(Color.BLUE));
        }
        
        return jProgressBarLoadingSVM;
    }
    
    private void initComponents()
    {
        getJPanel1().add(getJProgressBarLoadingSVM(), new Constraints(new Leading(8, 160, 12, 12), new Leading(524, 26, 12, 12)));
        getJPanel1().add(getJButtonStats(), new Constraints(new Leading(8, 70, 12, 12), new Leading(492, 12, 12)));
        getJPanel1().add(getJButtonAdvisor(), new Constraints(new Leading(8, 160, 12, 12), new Leading(524, 12, 12)));
        getJPanel1().add(getJLabelProbWin(), new Constraints(new Leading(380, 120, 12, 12), new Leading(518, 12, 12)));
        getJPanel1().add(getJLabelAdvise(), new Constraints(new Leading(696, 171, 12, 12), new Leading(406, 50, 12, 12)));
    }
    
    private void jButtonAdvisorActionPerformed(ActionEvent event)
    {
        m_advisorEnabled = !m_advisorEnabled;
        getJLabelAdvise().setVisible(m_advisorEnabled);
    }
    
    private void jButtonStatsActionPerformed(ActionEvent event)
    {
        getjDialogPlayerStats().setVisible(true);
        getjDialogPlayerStats().refresh();
    }
    
    @Override
    public void setTable(PokerTableInfo p_table)
    {
        super.setTable(p_table);
        getjDialogPlayerStats().setTable(p_table);
    }
    
    @Override
    public void takeAction(ArrayList<TypePlayerAction> p_actionsAllowed, int p_callAmount, int p_minRaiseAmount, int p_maxRaiseAmount)
    {
        super.takeAction(p_actionsAllowed, p_callAmount, p_minRaiseAmount, p_maxRaiseAmount);
        
        if ((m_pokerSVM != null) && m_pokerSVM.isReady())
        {
            m_pokerSVM.takeAction(p_actionsAllowed, p_callAmount, p_minRaiseAmount, p_maxRaiseAmount);
        }
    }
    
    @Override
    public String toString()
    {
        return m_bundle.get("advisor.title");
    }
    
    /**
     * Update the advice the SVM agent give to the player.
     */
    private void updateAdvice()
    {
        if ((m_pokerSVM != null) && m_pokerSVM.isReady())
        {
            m_tipAction = m_pokerSVM.getAction().getType();
            getJLabelAdvise().setText((m_tipAction != TypePlayerAction.NOTHING) ? m_bundle.get("advisor.label.advise") + ": " + m_tipAction : m_bundle.get("advisor.label.noAdvise"));
        }
    }
    
    /**
     * Update probability of winning shown under the user's player.
     */
    private void updateProbWin()
    {
        final StatsInfos infos = calculateHandValues();
        m_probWin = infos.m_winRatio;
        m_standardDeviationWin = infos.m_standardDeviation;
        
        final StringBuilder sb = new StringBuilder();
        sb.append("<html><center><font color='white'>");
        sb.append(m_bundle.get("advisor.label.probWin"));
        sb.append(": </color><br /><font color='#00FF00'>");
        sb.append(String.format("%.1f", m_probWin * 100));
        sb.append("%");
        
        if (m_standardDeviationWin < 1)
        {
            sb.append(" &#177; ");
            sb.append(String.format("%.1f", m_standardDeviationWin * 100));
            sb.append("%");
        }
        
        sb.append("</color></center></html>");
        
        getJLabelProbWin().setText(sb.toString());
    }
    
    @Override
    public void setPokerObserver(ClientPokerObserver observer)
    {
        super.setPokerObserver(observer);
        
        initializePokerObserver();
    }
    
    private void initializePokerObserver()
    {
        m_pokerObserver.subscribe(new ClientPokerAdapter()
        {
            
            @Override
            public void boardChanged(ArrayList<Integer> boardCardIndices)
            {
                updateProbWin();
            }
            
            @Override
            public void gameEnded()
            {
                if (getjDialogPlayerStats().isVisible())
                {
                    getjDialogPlayerStats().refresh();
                }
                
                if (m_statsAgent != null)
                {
                    for (int i = 0; i < m_table.getNbSeats(); ++i)
                    {
                        final PokerPlayerInfo player = m_table.getPlayer(i);
                        final HudPanel hud = getPlayer(i).m_hud;
                        
                        if (player == null)
                        {
                            hud.setToolTipText("");
                        }
                        else
                        {
                            if (player.m_isPlaying)
                            {
                                final PlayerStats stats = m_statsAgent.m_overallStats.get(player.m_name);
                                final String tightLoose = ((stats.isTight()) ? m_bundle.get("advisor.tooltip.tight") : m_bundle.get("advisor.tooltip.loose"));
                                final String preflopPassiveAgressive = ((stats.getPassive_PRF() > 0.5) ? m_bundle.get("advisor.tooltip.passive") + "(" + String.format("%.1f", stats.getPassive_PRF() * 100) + "%)" : m_bundle.get("advisor.tooltip.aggressive") + "(" + String.format("%.1f", (1 - stats.getPassive_PRF()) * 100) + "%)");
                                final String postflopPassiveAgressive = ((stats.getPassive_PF() > 0.5) ? m_bundle.get("advisor.tooltip.passive") + "(" + String.format("%.1f", stats.getPassive_PF() * 100) + "%)" : m_bundle.get("advisor.tooltip.aggressive") + "(" + String.format("%.1f", (1 - stats.getPassive_PF()) * 100) + "%)");
                                hud.setToolTipText(tightLoose + " , " + preflopPassiveAgressive + " / " + postflopPassiveAgressive);
                            }
                        }
                    }
                }
            }
            
            @Override
            public void playerCardChanged(PokerPlayerInfo player)
            {
                if (m_table.m_localPlayer == player)
                {
                    updateProbWin();
                }
            }
        });
    }
}
