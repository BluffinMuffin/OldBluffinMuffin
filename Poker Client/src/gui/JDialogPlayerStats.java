package gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import miscUtil.Bundle;

import stats.PlayerStats;
import stats.StatsAgent;
import backend.ClientPokerPlayerInfo;
import backend.ClientPokerTableInfo;
import basePoker.PokerPlayerInfo;
import basePoker.PokerTableInfo;

/**
 * @author Hocus
 *         This class represents a JTable containing statistics about players
 *         sitting on the current table. The statistics are provided by a
 *         StatsAgent.
 *         A part of this class was generated using a visual editor
 *         (http://wiki.eclipse.org/VE).
 */
public class JDialogPlayerStats extends JFrame// JDialog
{
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JScrollPane jScrollPane = null;
    private JTable jTablePlayerStats = null;
    private StatsAgent m_statsAgent = null;
    private PokerTableInfo m_table = null;
    private final Bundle m_bundle = Bundle.getIntance();
    
    /**
     * Fetch all column names and tooltips depending
     * on the selected language.
     */
    private final String[][] columnNamesAndToolTips = { { m_bundle.get("stats.jTable.header.playerName"), m_bundle.get("stats.jTable.tooltip.playerName") }, // Player
            // Name
            { m_bundle.get("stats.jTable.header.nbHands"), m_bundle.get("stats.jTable.tooltip.nbHands") }, // Number of
            // hands
            { m_bundle.get("stats.jTable.header.3Bet_D"), m_bundle.get("stats.jTable.tooltip.3Bet_D") }, // 3Bet_D
            { m_bundle.get("stats.jTable.header.3Bet_SB"), m_bundle.get("stats.jTable.tooltip.3Bet_SB") }, // 3Bet_SB
            { m_bundle.get("stats.jTable.header.3Bet_BB"), m_bundle.get("stats.jTable.tooltip.3Bet_BB") }, // 3Bet_BB
            { m_bundle.get("stats.jTable.header.3Bet_EP"), m_bundle.get("stats.jTable.tooltip.3Bet_EP") }, // 3Bet_EP
            { m_bundle.get("stats.jTable.header.3Bet_MP"), m_bundle.get("stats.jTable.tooltip.3Bet_MP") }, // 3Bet_MP
            { m_bundle.get("stats.jTable.header.3Bet_CO"), m_bundle.get("stats.jTable.tooltip.3Bet_CO") }, // 3Bet_CO
            { m_bundle.get("stats.jTable.header.4Bet_PRF"), m_bundle.get("stats.jTable.tooltip.4Bet_PRF") }, // 4Bet_PRF
            { m_bundle.get("stats.jTable.header.BB_3Bet_vs_Steal_PRF"), m_bundle.get("stats.jTable.tooltip.BB_3Bet_vs_Steal_PRF") }, // BB_3Bet_vs_Steal_PRF
            { m_bundle.get("stats.jTable.header.BB_Fold_vs_Steal_PRF"), m_bundle.get("stats.jTable.tooltip.BB_Fold_vs_Steal_PRF") }, // BB_Fold_vs_Steal_PRF
            { m_bundle.get("stats.jTable.header.Flop_Bet"), m_bundle.get("stats.jTable.tooltip.Flop_Bet") }, // Flop_Bet
            { m_bundle.get("stats.jTable.header.Turn_Bet"), m_bundle.get("stats.jTable.tooltip.Turn_Bet") }, // Turn_Bet
            { m_bundle.get("stats.jTable.header.River_Bet"), m_bundle.get("stats.jTable.tooltip.River_Bet") }, // River_Bet
            { m_bundle.get("stats.jTable.header.Flop_CBet"), m_bundle.get("stats.jTable.tooltip.Flop_CBet") }, // Flop_CBet
            { m_bundle.get("stats.jTable.header.Turn_CBet"), m_bundle.get("stats.jTable.tooltip.Turn_CBet") }, // Turn_CBet
            { m_bundle.get("stats.jTable.header.D_Steal_PRF"), m_bundle.get("stats.jTable.tooltip.D_Steal_PRF") }, // D_Steal_PRF
            { m_bundle.get("stats.jTable.header.CO_Steal_PRF"), m_bundle.get("stats.jTable.tooltip.CO_Steal_PRF") }, // CO_Steal_PRF
            { m_bundle.get("stats.jTable.header.Flop_Fold_vs_CBet"), m_bundle.get("stats.jTable.tooltip.Flop_Fold_vs_CBet") }, // Flop_Fold_vs_CBet
            { m_bundle.get("stats.jTable.header.Flop_Raise_vs_CBet"), m_bundle.get("stats.jTable.tooltip.Flop_Raise_vs_CBet") }, // Flop_Raise_vs_CBet
            { m_bundle.get("stats.jTable.header.Fold_to_3Bet_D"), m_bundle.get("stats.jTable.tooltip.Fold_to_3Bet_D") }, // Fold_to_3Bet_D
            { m_bundle.get("stats.jTable.header.Fold_to_3Bet_SB"), m_bundle.get("stats.jTable.tooltip.Fold_to_3Bet_SB") }, // Fold_to_3Bet_SB
            { m_bundle.get("stats.jTable.header.Fold_to_3Bet_BB"), m_bundle.get("stats.jTable.tooltip.Fold_to_3Bet_BB") }, // Fold_to_3Bet_BB
            { m_bundle.get("stats.jTable.header.Fold_to_3Bet_EP"), m_bundle.get("stats.jTable.tooltip.Fold_to_3Bet_EP") }, // Fold_to_3Bet_EP
            { m_bundle.get("stats.jTable.header.Fold_to_3Bet_MP"), m_bundle.get("stats.jTable.tooltip.Fold_to_3Bet_MP") }, // Fold_to_3Bet_MP
            { m_bundle.get("stats.jTable.header.Fold_to_3Bet_CO"), m_bundle.get("stats.jTable.tooltip.Fold_to_3Bet_CO") }, // Fold_to_3Bet_CO
            { m_bundle.get("stats.jTable.header.Fold_to_4Bet_PRF"), m_bundle.get("stats.jTable.tooltip.Fold_to_4Bet_PRF") }, // Fold_to_4Bet_PRF
            { m_bundle.get("stats.jTable.header.PFR_D"), m_bundle.get("stats.jTable.tooltip.PFR_D") }, // PFR_D
            { m_bundle.get("stats.jTable.header.PFR_SB"), m_bundle.get("stats.jTable.tooltip.PFR_SB") }, // PFR_SB
            { m_bundle.get("stats.jTable.header.PFR_BB"), m_bundle.get("stats.jTable.tooltip.PFR_BB") }, // PFR_BB
            { m_bundle.get("stats.jTable.header.PFR_EP"), m_bundle.get("stats.jTable.tooltip.PFR_EP") }, // PFR_EP
            { m_bundle.get("stats.jTable.header.PFR_MP"), m_bundle.get("stats.jTable.tooltip.PFR_MP") }, // PFR_MP
            { m_bundle.get("stats.jTable.header.PFR_CO"), m_bundle.get("stats.jTable.tooltip.PFR_CO") }, // PFR_CO
            { m_bundle.get("stats.jTable.header.SB_Steal_PRF"), m_bundle.get("stats.jTable.tooltip.SB_Steal_PRF") }, // SB_Steal_PRF
            { m_bundle.get("stats.jTable.header.SB_3Bet_vs_Steal_PRF"), m_bundle.get("stats.jTable.tooltip.SB_3Bet_vs_Steal_PRF") }, // SB_3Bet_vs_Steal_PRF
            { m_bundle.get("stats.jTable.header.SB_3Fold_vs_Steal_PRF"), m_bundle.get("stats.jTable.tooltip.SB_3Fold_vs_Steal_PRF") }, // SB_3Fold_vs_Steal_PRF
            { m_bundle.get("stats.jTable.header.Turn_Fold_vs_CBet"), m_bundle.get("stats.jTable.tooltip.Turn_Fold_vs_CBet") }, // Turn_Fold_vs_CBet
            { m_bundle.get("stats.jTable.header.VPIP_Total_PRF"), m_bundle.get("stats.jTable.tooltip.VPIP_Total_PRF") }, // VPIP_Total_PRF
            { m_bundle.get("stats.jTable.header.WTSD"), m_bundle.get("stats.jTable.tooltip.WTSD") } // WTSD
    };
    
    /**
     * @param owner
     */
    public JDialogPlayerStats(Frame owner)
    {
        // super(owner);
        initialize();
    }
    
    public JDialogPlayerStats(Frame owner, StatsAgent p_statsAgent)
    {
        // super(owner);
        initialize();
        m_statsAgent = p_statsAgent;
    }
    
    /**
     * Get column names.
     * 
     * @return
     *         An array representing column's name.
     */
    private final String[] getColumnNames()
    {
        final String[] names = new String[columnNamesAndToolTips.length];
        for (int i = 0; i < names.length; i++)
        {
            names[i] = columnNamesAndToolTips[i][0];
        }
        
        return names;
    }
    
    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane()
    {
        if (jContentPane == null)
        {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
        }
        return jContentPane;
    }
    
    /**
     * This method initializes jScrollPane
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane()
    {
        if (jScrollPane == null)
        {
            jScrollPane = new JScrollPane();
            jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            jScrollPane.setViewportView(getJTablePlayerStats());
        }
        return jScrollPane;
    }
    
    /**
     * This method initializes jTablePlayerStats
     * 
     * @return javax.swing.JTable
     */
    @SuppressWarnings("serial")
    private JTable getJTablePlayerStats()
    {
        if (jTablePlayerStats == null)
        {
            final DefaultTableModel model = new DefaultTableModel(getColumnNames(), 0)
            {
                @Override
                public boolean isCellEditable(int row, int column)
                {
                    return false;
                }
            };
            
            jTablePlayerStats = new JTable()
            {
                // Implement table header tool tips.
                @Override
                protected JTableHeader createDefaultTableHeader()
                {
                    return new JTableHeader(columnModel)
                    {
                        @Override
                        public String getToolTipText(MouseEvent e)
                        {
                            final java.awt.Point p = e.getPoint();
                            final int index = columnModel.getColumnIndexAtX(p.x);
                            final int realIndex = columnModel.getColumn(index).getModelIndex();
                            return columnNamesAndToolTips[realIndex][1];
                        }
                    };
                }
            };
            jTablePlayerStats.setAutoCreateRowSorter(true);
            jTablePlayerStats.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTablePlayerStats.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            jTablePlayerStats.setModel(model);
        }
        return jTablePlayerStats;
    }
    
    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize()
    {
        this.setSize(578, 275);
        this.setContentPane(getJContentPane());
        this.setTitle("Players Stats");
    }
    
    /**
     * Refresh statistics of all players.
     * The new statistics are provided by a StatsAgent.
     */
    // "Players", "3Bet_D", "3Bet_SB", "3Bet_BB", "3Bet_EP", "3Bet_MP",
    // "3Bet_CO",
    // "4Bet_PRF", "BB_3Bet_vs_Steal_PRF", "BB_Fold_vs_Steal_PRF",
    // "Flop_Bet", "Turn_Bet", "River_Bet",
    // "Flop_CBet", "Turn_CBet", "D_Steal_PRF", "CO_Steal_PRF",
    // "Flop_Fold_vs_CBet", "Flop_Raise_vs_CBet",
    // "Fold_to_3Bet_SB", "Fold_to_3Bet_D", "Fold_to_3Bet_BB",
    // "Fold_to_3Bet_EP", "Fold_to_3Bet_MP", "Fold_to_3Bet_CO",
    // "Fold_to_4Bet_PRF",
    // "PFR_D", "PFR_SB", "PFR_BB", "PFR_EP", "PFR_MP", "PFR_CO",
    // "SB_Steal_PRF", "SB_3Bet_vs_Steal_PRF", "SB_3Fold_vs_Steal_PRF",
    // "Turn_Fold_vs_CBet", "VPIP_Total_PRF", "WTSD"
    public void refresh()
    {
        final DefaultTableModel model = (DefaultTableModel) jTablePlayerStats.getModel();
        model.setRowCount(0);
        
        if ((m_statsAgent == null) || (m_table == null))
        {
            return;
        }
        
        // Update each player
        for (final PokerPlayerInfo player : m_table.m_players.values())
        {
            final PlayerStats stats = m_statsAgent.m_overallStats.get(player.m_name);
            
            if (stats != null)
            {
                // Create a new row containing updated statistics.
                final Object[] row = new Object[] { stats.getName(), stats.getNbHands(), stats.getProb3Bet_D(), stats.getProb3Bet_SB(), stats.getProb3Bet_BB(), stats.getProb3Bet_EP(), stats.getProb3Bet_MP(), stats.getProb3Bet_CO(), stats.getProb4Bet_PRF(), stats.getProbBB3BetVsSteal_PRF(), stats.getProbBBFoldVsSteal_PRF(), stats.getProbBetFlop(), stats.getProbBetTurn(), stats.getProbBetRiver(), stats.getProbCBetFlop(), stats.getProbCBetTurn(), stats.getProbDSteal_PRF(), stats.getProbCOSteal_PRF(), stats.getProbFlopFoldVsCBet(), stats.getProbFlopRaiseVsCBet(), stats.getProbFoldTo3Bet_D(), stats.getProbFoldTo3Bet_SB(), stats.getProbFoldTo3Bet_BB(), stats.getProbFoldTo3Bet_EP(), stats.getProbFoldTo3Bet_MP(), stats.getProbFoldTo3Bet_CO(), stats.getProbFoldTo4Bet_PRF(), stats.getProbPFR_D(), stats.getProbPFR_SB(), stats.getProbPFR_BB(), stats.getProbPFR_EP(), stats.getProbPFR_MP(), stats.getProbPFR_CO(), stats.getProbSBSteal_PRF(), stats.getProbSB3BetVsSteal_PRF(), stats.getProbSBFoldVsSteal_PRF(), stats.getProbTurnFoldVsCBet(), stats.getProbVPIPTotal_PRF(), stats.getProbWTSD(), };
                
                model.addRow(row);
            }
        }
    }
    
    public void setTable(PokerTableInfo p_table)
    {
        m_table = p_table;
        this.setTitle(m_bundle.get("stats.title") + " - " + p_table.m_name);
    }
    
} // @jve:decl-index=0:visual-constraint="10,10"
