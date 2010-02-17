package pokerExtractor;

import gameLogic.GameCard;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.LinkedBlockingQueue;

import pokerExtractor.TupleHandHistories.PhaseEvents;
import pokerExtractor.TupleHandHistories.TuplePlayer;
import pokerExtractor.TupleHandHistories.Winner;
import pokerLogic.PokerPlayerInfo;
import pokerLogic.TypePlayerAction;
import pokerLogic.TypePokerRound;
import pokerStats.MonteCarlo;
import pokerStats.StatsInfos;
import protocolGame.GameBetTurnEndedCommand;
import protocolGame.GameBoardChangedCommand;
import protocolGame.GameEndedCommand;
import protocolGame.GamePlayerMoneyChangedCommand;
import protocolGame.GamePlayerTurnEndedCommand;
import protocolGame.GameStartedCommand;
import protocolGame.GameTableInfoCommand;
import protocolGameTools.SummarySeatInfo;
import protocolTools.IBluffinCommand;
import utility.Tool;
import clientGame.ClientPokerPlayerInfo;
import clientGame.ClientPokerTableInfo;
import clientGameTools.TypeSimplifiedAction;
import clientStats.StatsAgent;

public class SimulationServer
{
    TypePokerRound m_state = TypePokerRound.PREFLOP;
    PokerClientLocal m_client;
    StatsAgent m_statsAgent = new StatsAgent();
    LinkedBlockingQueue<String> m_fromClient = new LinkedBlockingQueue<String>(1);
    LinkedBlockingQueue<String> m_toClient = new LinkedBlockingQueue<String>(1);
    ClientPokerPlayerInfo m_clientHero = new ClientPokerPlayerInfo(0);
    TuplePlayer m_hero = null;
    ClientPokerTableInfo m_table = new ClientPokerTableInfo();
    TupleHandHistories m_currentInfos = null;
    // BufferedWriter m_output= null;
    ArrayList<String> m_vectors = new ArrayList<String>();
    
    private int m_nbRemainingPlayers = 0;
    
    private final static String SEPARATOR = " ";
    
    private final static int NB_OTHER_OPPONENTS_INFOS = 9 + 2 + 28;
    
    private final static int NB_STATS_PREFLOP = 28;
    
    private final static int NB_STATS_POSTFLOP = 9;
    
    private final static int NB_STATS = SimulationServer.NB_STATS_PREFLOP + SimulationServer.NB_STATS_POSTFLOP;
    
    private final static int NB_MC_ITERATIONS = 10000;
    
    private final static int NB_PLAYER_INFOS = SimulationServer.NB_OTHER_OPPONENTS_INFOS + SimulationServer.NB_STATS;
    
    public static double round(double value, int decimalPlace)
    {
        double power_of_ten = 1;
        // floating point arithmetic can be very tricky.
        // that's why I introduce a "fudge factor"
        double fudge_factor = 0.05;
        while (decimalPlace-- > 0)
        {
            power_of_ten *= 10.0d;
            fudge_factor /= 10.0d;
        }
        return Math.round((value + fudge_factor) * power_of_ten) / power_of_ten;
    }
    
    private Integer m_cptID = 0;
    
    public SimulationServer()
    {
        // observers.add(new Viewer());
        m_client = new PokerClientLocal(null, m_clientHero, m_table, m_toClient, m_fromClient);
        m_statsAgent.setPokerObserver(m_client.getPokerObserver());
        m_client.start();
    }
    
    private StatsInfos calculateHandValues()
    {
        final GameCard[] holeCards = new GameCard[] { m_hero.m_card1, m_hero.m_card2 };
        final GameCard[] boardCcards = new GameCard[5];
        
        if (m_state == TypePokerRound.PREFLOP)
        {
            boardCcards[0] = GameCard.NO_CARD;
            boardCcards[1] = GameCard.NO_CARD;
            boardCcards[2] = GameCard.NO_CARD;
            boardCcards[3] = GameCard.NO_CARD;
            boardCcards[4] = GameCard.NO_CARD;
        }
        else if (m_state == TypePokerRound.FLOP)
        {
            boardCcards[0] = m_currentInfos.m_flop.get(0);
            boardCcards[1] = m_currentInfos.m_flop.get(1);
            boardCcards[2] = m_currentInfos.m_flop.get(2);
            boardCcards[3] = GameCard.NO_CARD;
            boardCcards[4] = GameCard.NO_CARD;
        }
        else if (m_state == TypePokerRound.TURN)
        {
            boardCcards[0] = m_currentInfos.m_flop.get(0);
            boardCcards[1] = m_currentInfos.m_flop.get(1);
            boardCcards[2] = m_currentInfos.m_flop.get(2);
            boardCcards[3] = m_currentInfos.m_turn;
            boardCcards[4] = GameCard.NO_CARD;
        }
        else if (m_state == TypePokerRound.RIVER)
        {
            boardCcards[0] = m_currentInfos.m_flop.get(0);
            boardCcards[1] = m_currentInfos.m_flop.get(1);
            boardCcards[2] = m_currentInfos.m_flop.get(2);
            boardCcards[3] = m_currentInfos.m_turn;
            boardCcards[4] = m_currentInfos.m_river;
        }
        
        return MonteCarlo.CalculateWinRatio(holeCards, boardCcards, m_nbRemainingPlayers, SimulationServer.NB_MC_ITERATIONS);
    }
    
    public void clearVectors()
    {
        m_vectors.clear();
    }
    
    private String format(Object p_object)
    {
        String text = "";
        
        if (p_object == null)
        {
            // text = m_cptID.toString() + ":" + NOT_AVAILABLE + SEPARATOR;
        }
        else if (p_object.getClass().isEnum())
        {
            text = m_cptID.toString() + ":" + ((Enum<?>) p_object).ordinal() + ' ';
        }
        else if (p_object instanceof Double)
        {
            final double value = SimulationServer.round(((Double) p_object).doubleValue(), 3);
            if ((int) value == value)
            {
                if (value != 0.0)
                {
                    text = m_cptID.toString() + ":" + (int) value + ' ';
                }
            }
            else if (value != 0.0)
            {
                text = m_cptID.toString() + ":" + String.format(Locale.US, "%.3f", value) + ' ';
            }
        }
        else if (p_object instanceof Integer)
        {
            final int value = ((Integer) p_object).intValue();
            if (value != 0)
            {
                text = m_cptID.toString() + ":" + value + ' ';
            }
        }
        else
        {
            text = m_cptID.toString() + ":" + p_object.toString() + ' ';
        }
        
        m_cptID++;
        
        return text;
    }
    
    private String formatEnum(Enum<?> p_enum, Class<?> p_class)
    {
        final StringBuilder sb = new StringBuilder();
        
        if (p_enum != null)
        {
            for (final String bit : Tool.formatEnum(p_enum))
            {
                if (bit.equals("1"))
                {
                    sb.append(m_cptID);
                    sb.append(":");
                    sb.append(bit);
                    sb.append(SimulationServer.SEPARATOR);
                }
                // else if (bit.equals("0"))
                // {
                // sb.append(m_cptID);
                // sb.append(":");
                // sb.append(bit);
                // sb.append(SEPARATOR);
                // }
                
                m_cptID++;
            }
        }
        else
        {
            for (int i = 0; i != p_class.getFields().length; ++i)
            {
                // sb.append(m_cptID);
                // sb.append(":");
                // sb.append("0");
                // sb.append(SEPARATOR);
                m_cptID++;
            }
        }
        
        return sb.toString();
    }
    
    private String formatPosition(int p_position)
    {
        final StringBuilder sb = new StringBuilder();
        
        for (int i = 1; i != 10; ++i)
        {
            if (i == p_position)
            {
                sb.append(m_cptID);
                sb.append(":");
                sb.append(1);
                sb.append(SimulationServer.SEPARATOR);
            }
            // else
            // {
            // sb.append(m_cptID);
            // sb.append(":");
            // sb.append(0);
            // sb.append(SEPARATOR);
            // }
            
            m_cptID++;
        }
        
        return sb.toString();
    }
    
    private int getPosition(String p_name)
    {
        for (final PokerPlayerInfo player : m_client.m_table.getPlayers())
        {
            if (player.m_name.equals(p_name))
            {
                return player.m_relativePosition;
            }
        }
        
        return -1;
    }
    
    public StatsAgent getStats()
    {
        return m_statsAgent;
    }
    
    public ArrayList<String> getVectors()
    {
        return m_vectors;
    }
    
    private void manageLastActionsFlop(PhaseEvents p_event)
    {
        if (p_event.m_action == TypePlayerAction.FOLD)
        {
            p_event.m_player.m_lastActionsFlop = TypeSimplifiedAction.FOLD;
        }
        else if (p_event.m_action == TypePlayerAction.CHECK)
        {
            p_event.m_player.m_lastActionsFlop = TypeSimplifiedAction.CHECK;
        }
        else
        {
            if (p_event.m_player.m_lastActionsFlop == null)
            {
                if (p_event.m_action == TypePlayerAction.CALL)
                {
                    p_event.m_player.m_lastActionsFlop = TypeSimplifiedAction.CALL;
                }
                else if (p_event.m_action == TypePlayerAction.RAISE)
                {
                    p_event.m_player.m_lastActionsFlop = TypeSimplifiedAction.RAISE;
                }
            }
            else if (p_event.m_player.m_lastActionsFlop == TypeSimplifiedAction.CALL)
            {
                if (p_event.m_action == TypePlayerAction.RAISE)
                {
                    p_event.m_player.m_lastActionsFlop = TypeSimplifiedAction.CALL_RAISE;
                }
            }
            else if (p_event.m_player.m_lastActionsFlop == TypeSimplifiedAction.RAISE)
            {
                if (p_event.m_action == TypePlayerAction.CALL)
                {
                    p_event.m_player.m_lastActionsFlop = TypeSimplifiedAction.RAISE_CALL;
                }
                else if (p_event.m_action == TypePlayerAction.RAISE)
                {
                    p_event.m_player.m_lastActionsFlop = TypeSimplifiedAction.RAISE_RAISE;
                }
            }
        }
    }
    
    private void manageLastActionsPreflop(PhaseEvents p_event)
    {
        if (p_event.m_action == TypePlayerAction.FOLD)
        {
            p_event.m_player.m_lastActionsPreflop = TypeSimplifiedAction.FOLD;
        }
        else if (p_event.m_action == TypePlayerAction.CHECK)
        {
            p_event.m_player.m_lastActionsPreflop = TypeSimplifiedAction.CHECK;
        }
        else
        {
            if (p_event.m_player.m_lastActionsPreflop == null)
            {
                if (p_event.m_action == TypePlayerAction.CALL)
                {
                    p_event.m_player.m_lastActionsPreflop = TypeSimplifiedAction.CALL;
                }
                else if (p_event.m_action == TypePlayerAction.RAISE)
                {
                    p_event.m_player.m_lastActionsPreflop = TypeSimplifiedAction.RAISE;
                }
            }
            else if (p_event.m_player.m_lastActionsPreflop == TypeSimplifiedAction.CALL)
            {
                if (p_event.m_action == TypePlayerAction.RAISE)
                {
                    p_event.m_player.m_lastActionsPreflop = TypeSimplifiedAction.CALL_RAISE;
                }
            }
            else if (p_event.m_player.m_lastActionsPreflop == TypeSimplifiedAction.RAISE)
            {
                if (p_event.m_action == TypePlayerAction.CALL)
                {
                    p_event.m_player.m_lastActionsPreflop = TypeSimplifiedAction.RAISE_CALL;
                }
                else if (p_event.m_action == TypePlayerAction.RAISE)
                {
                    p_event.m_player.m_lastActionsPreflop = TypeSimplifiedAction.RAISE_RAISE;
                }
            }
        }
    }
    
    private void manageLastActionsRiver(PhaseEvents p_event)
    {
        if (p_event.m_action == TypePlayerAction.FOLD)
        {
            p_event.m_player.m_lastActionsRiver = TypeSimplifiedAction.FOLD;
        }
        else if (p_event.m_action == TypePlayerAction.CHECK)
        {
            p_event.m_player.m_lastActionsRiver = TypeSimplifiedAction.CHECK;
        }
        else
        {
            if (p_event.m_player.m_lastActionsRiver == null)
            {
                if (p_event.m_action == TypePlayerAction.CALL)
                {
                    p_event.m_player.m_lastActionsRiver = TypeSimplifiedAction.CALL;
                }
                else if (p_event.m_action == TypePlayerAction.RAISE)
                {
                    p_event.m_player.m_lastActionsRiver = TypeSimplifiedAction.RAISE;
                }
            }
            else if (p_event.m_player.m_lastActionsRiver == TypeSimplifiedAction.CALL)
            {
                if (p_event.m_action == TypePlayerAction.RAISE)
                {
                    p_event.m_player.m_lastActionsRiver = TypeSimplifiedAction.CALL_RAISE;
                }
            }
            else if (p_event.m_player.m_lastActionsRiver == TypeSimplifiedAction.RAISE)
            {
                if (p_event.m_action == TypePlayerAction.CALL)
                {
                    p_event.m_player.m_lastActionsRiver = TypeSimplifiedAction.RAISE_CALL;
                }
                else if (p_event.m_action == TypePlayerAction.RAISE)
                {
                    p_event.m_player.m_lastActionsRiver = TypeSimplifiedAction.RAISE_RAISE;
                }
            }
        }
    }
    
    private void manageLastActionsTurn(PhaseEvents p_event)
    {
        if (p_event.m_action == TypePlayerAction.FOLD)
        {
            p_event.m_player.m_lastActionsTurn = TypeSimplifiedAction.FOLD;
        }
        else if (p_event.m_action == TypePlayerAction.CHECK)
        {
            p_event.m_player.m_lastActionsTurn = TypeSimplifiedAction.CHECK;
        }
        else
        {
            if (p_event.m_player.m_lastActionsTurn == null)
            {
                if (p_event.m_action == TypePlayerAction.CALL)
                {
                    p_event.m_player.m_lastActionsTurn = TypeSimplifiedAction.CALL;
                }
                else if (p_event.m_action == TypePlayerAction.RAISE)
                {
                    p_event.m_player.m_lastActionsTurn = TypeSimplifiedAction.RAISE;
                }
            }
            else if (p_event.m_player.m_lastActionsTurn == TypeSimplifiedAction.CALL)
            {
                if (p_event.m_action == TypePlayerAction.RAISE)
                {
                    p_event.m_player.m_lastActionsTurn = TypeSimplifiedAction.CALL_RAISE;
                }
            }
            else if (p_event.m_player.m_lastActionsTurn == TypeSimplifiedAction.RAISE)
            {
                if (p_event.m_action == TypePlayerAction.CALL)
                {
                    p_event.m_player.m_lastActionsTurn = TypeSimplifiedAction.RAISE_CALL;
                }
                else if (p_event.m_action == TypePlayerAction.RAISE)
                {
                    p_event.m_player.m_lastActionsTurn = TypeSimplifiedAction.RAISE_RAISE;
                }
            }
        }
    }
    
    private void send(IBluffinCommand p_msg)
    {
        try
        {
            m_toClient.put(p_msg.encodeCommand());
        }
        catch (final InterruptedException e)
        {
            e.printStackTrace();
        }
        
        // try
        // {
        // Thread.sleep(1000);
        // }
        // catch (InterruptedException e)
        // {
        // e.printStackTrace();
        // }
    }
    
    public void simulate(TupleHandHistories p_infos)
    {
        m_clientHero.setNoSeat(p_infos.getHero().m_noSeat);
        m_hero = p_infos.getHero();
        m_currentInfos = p_infos;
        m_nbRemainingPlayers = m_currentInfos.m_players.size();
        
        // Send Table Infos.
        // @see [TABLE_INFOS;totalPotAmount;
        // nbPots;{potAmount};
        // {boardsCards};
        // nbPlayers;{noSeat;isEmpty;playerName;moneyAmount;card1;card2;
        // isDealer;isSmallBlind;isBigBlind;isPlaying;timeRemaining;betAmount}
        
        final ArrayList<Integer> amounts = new ArrayList<Integer>();
        for (int i = 0; i < p_infos.m_players.size(); ++i)
        {
            amounts.add(0);
        }
        final ArrayList<Integer> cards = new ArrayList<Integer>();
        for (int i = 0; i < 5; ++i)
        {
            cards.add(GameCard.NO_CARD_ID);
        }
        final ArrayList<SummarySeatInfo> seats = new ArrayList<SummarySeatInfo>();
        for (final TuplePlayer player : p_infos.m_players)
        {
            final ArrayList<Integer> holes = new ArrayList<Integer>();
            holes.add(player.m_card1.getId());
            holes.add(player.m_card2.getId());
            seats.add(new SummarySeatInfo(player.m_noSeat, false, player.m_name, player.m_money, holes, p_infos.m_noSeatDealer == player.m_noSeat, p_infos.m_noSeatSmallBlind == player.m_noSeat, p_infos.m_noSeatBigBlind == player.m_noSeat, false, 0, 0));
        }
        send(new GameTableInfoCommand(0, p_infos.m_players.size(), amounts, cards, p_infos.m_players.size(), seats));
        
        // Send GameStarted.
        // [GAME_STARTED;noSeatDealer;noSeatSmallBlind;noSeatBigBlind;]
        send(new GameStartedCommand(p_infos.m_noSeatDealer, p_infos.m_noSeatSmallBlind, p_infos.m_noSeatBigBlind));
        
        int totalPotAmount = 0;
        
        // Small blind posted
        
        final TuplePlayer smallBlindPlayer = p_infos.getPlayer(p_infos.m_noSeatSmallBlind);
        if (smallBlindPlayer != null)
        {
            smallBlindPlayer.m_betAmount = p_infos.m_smallBlind;
            smallBlindPlayer.m_money -= p_infos.m_smallBlind;
            totalPotAmount += p_infos.m_smallBlind;
            send(new GamePlayerTurnEndedCommand(smallBlindPlayer.m_noSeat, smallBlindPlayer.m_betAmount, smallBlindPlayer.m_money, totalPotAmount, TypePlayerAction.SMALL_BLIND, p_infos.m_smallBlind));
        }
        
        // Big blind posted
        final TuplePlayer bigBlindPlayer = p_infos.getPlayer(p_infos.m_noSeatBigBlind);
        if (bigBlindPlayer != null)
        {
            bigBlindPlayer.m_betAmount = p_infos.m_bigBlind;
            bigBlindPlayer.m_money -= p_infos.m_bigBlind;
            totalPotAmount += p_infos.m_bigBlind;
            send(new GamePlayerTurnEndedCommand(bigBlindPlayer.m_noSeat, bigBlindPlayer.m_betAmount, bigBlindPlayer.m_money, totalPotAmount, TypePlayerAction.BIG_BLIND, p_infos.m_bigBlind));
        }
        
        m_state = TypePokerRound.PREFLOP;
        // *** PREFLOP ***//
        totalPotAmount = simulatePhase(totalPotAmount, p_infos.m_preflopEvents, p_infos.m_bigBlind);
        
        // Send BetTurnEnded
        // [BETTING_TURN_ENDED;pot[0-nbSeats]Amount;TypePokerRound]
        send(new GameBetTurnEndedCommand(amounts, m_state));
        
        m_state = TypePokerRound.FLOP;
        if (p_infos.m_flopEvents.size() > 0)
        {
            // *** FLOP ***//
            // [BOARD_CHANGED;idCard1;idCard2;idCard3;idCard4;idCard5;]
            send(new GameBoardChangedCommand(p_infos.m_flop.get(0).getId(), p_infos.m_flop.get(1).getId(), p_infos.m_flop.get(2).getId(), GameCard.NO_CARD_ID, GameCard.NO_CARD_ID));
            
            totalPotAmount = simulatePhase(totalPotAmount, p_infos.m_flopEvents);
            
            // Send BetTurnEnded
            // [BETTING_TURN_ENDED;pot[0-nbSeats]Amount;TypePokerRound]
            send(new GameBetTurnEndedCommand(amounts, m_state));
        }
        
        m_state = TypePokerRound.TURN;
        if (p_infos.m_turnEvents.size() > 0)
        {
            // *** TURN ***//
            // [BOARD_CHANGED;idCard1;idCard2;idCard3;idCard4;idCard5;]
            send(new GameBoardChangedCommand(p_infos.m_flop.get(0).getId(), p_infos.m_flop.get(1).getId(), p_infos.m_flop.get(2).getId(), p_infos.m_flop.get(3).getId(), GameCard.NO_CARD_ID));
            
            totalPotAmount = simulatePhase(totalPotAmount, p_infos.m_turnEvents);
            
            // Send BetTurnEnded
            // [BETTING_TURN_ENDED;pot[0-nbSeats]Amount;TypePokerRound]
            send(new GameBetTurnEndedCommand(amounts, m_state));
        }
        
        m_state = TypePokerRound.RIVER;
        if (p_infos.m_riverEvents.size() > 0)
        {
            // *** RIVER ***//
            // [BOARD_CHANGED;idCard1;idCard2;idCard3;idCard4;idCard5;]
            send(new GameBoardChangedCommand(p_infos.m_flop.get(0).getId(), p_infos.m_flop.get(1).getId(), p_infos.m_flop.get(2).getId(), p_infos.m_flop.get(3).getId(), p_infos.m_flop.get(4).getId()));
            
            totalPotAmount = simulatePhase(totalPotAmount, p_infos.m_riverEvents);
            
            // Send BETTING_TURN_ENDED
            // [BETTING_TURN_ENDED;pot[0-nbSeats]Amount;TypePokerRound]
            send(new GameBetTurnEndedCommand(amounts, m_state));
        }
        
        for (final Winner winner : p_infos.m_winners)
        {
            winner.m_player.m_money += winner.m_winAmount;
            
            // Send PLAYER_MONEY_CHANGED
            // [PLAYER_MONEY_CHANGED;noSeat;moneyAmount;]
            send(new GamePlayerMoneyChangedCommand(winner.m_player.m_noSeat, winner.m_player.m_money));
        }
        
        // Send GAME_ENDED
        // [GAME_ENDED;]
        send(new GameEndedCommand());
    }
    
    private int simulatePhase(int p_totalPotAmount, ArrayList<PhaseEvents> p_events)
    {
        return simulatePhase(p_totalPotAmount, p_events, 0);
    }
    
    private int simulatePhase(int p_totalPotAmount, ArrayList<PhaseEvents> p_events, int p_currentBet)
    {
        int currentBet = p_currentBet;
        int totalPotAmount = p_totalPotAmount;
        
        for (final TuplePlayer player : m_currentInfos.m_players)
        {
            
            if ((m_state != TypePokerRound.PREFLOP) || ((player.m_noSeat != m_currentInfos.m_noSeatBigBlind) && (player.m_noSeat != m_currentInfos.m_noSeatSmallBlind)))
            {
                player.m_betAmount = 0;
            }
        }
        
        // Send PlayerTurnEnded
        // [PLAYER_TURN_ENDED;noSeat;betAmount;moneyAmount;totalPotAmount;action;actionAmount;]
        for (final PhaseEvents event : p_events)
        {
            if ((event.m_player == m_hero) && ((event.m_action == TypePlayerAction.RAISE) || (event.m_action == TypePlayerAction.CALL) || (event.m_action == TypePlayerAction.CHECK) || (event.m_action == TypePlayerAction.FOLD)))
            {
                write(event.m_action, totalPotAmount, currentBet);
            }
            
            if (event.m_action == TypePlayerAction.RAISE)
            {
                totalPotAmount -= event.m_player.m_betAmount;
                totalPotAmount += event.m_actionAmount;
                currentBet = event.m_actionAmount;
                event.m_player.m_money += event.m_player.m_betAmount;
                event.m_player.m_money -= event.m_actionAmount;
                event.m_player.m_betAmount = event.m_actionAmount;
            }
            else if (event.m_action == TypePlayerAction.CALL)
            {
                totalPotAmount += event.m_actionAmount;
                event.m_player.m_betAmount += event.m_actionAmount;
                event.m_player.m_money -= event.m_actionAmount;
            }
            else if (event.m_action == TypePlayerAction.UNCALLED)
            {
                totalPotAmount -= event.m_actionAmount;
                event.m_player.m_money += event.m_actionAmount;
                event.m_player.m_betAmount -= event.m_actionAmount;
            }
            else if (event.m_action == TypePlayerAction.FOLD)
            {
                event.m_player.m_isFolded = true;
                m_nbRemainingPlayers--;
            }
            send(new GamePlayerTurnEndedCommand(event.m_player.m_noSeat, event.m_player.m_betAmount, event.m_player.m_money, totalPotAmount, event.m_action, event.m_actionAmount));
            
            if (m_state == TypePokerRound.PREFLOP)
            {
                manageLastActionsPreflop(event);
            }
            else if (m_state == TypePokerRound.FLOP)
            {
                manageLastActionsFlop(event);
            }
            else if (m_state == TypePokerRound.TURN)
            {
                manageLastActionsTurn(event);
            }
            else if (m_state == TypePokerRound.RIVER)
            {
                manageLastActionsRiver(event);
            }
        }
        
        return totalPotAmount;
    }
    
    // heroDecision;heroHandValue;heroHandVariance;heroMoney;heroBet;
    // heroPosition;heroPreflopActions;heroFlopActions;heroTurnActions;heroRiverActions;
    // totalPot;handPhase;nbPlayers
    // {playerPosition;playerMoney;playerBet;playerPreflopActions;playerFlopActions;playerTurnActions;playerRiverActions;
    // playerPreflopStats[28];playerPostflopStats[9];}
    private void write(TypePlayerAction p_action, int p_totalPotAmount, int p_currentBet)
    {
        double highMoney = 0.0;
        for (final TuplePlayer player : m_currentInfos.m_players)
        {
            highMoney = Math.max(highMoney, player.m_money);
        }
        
        m_cptID = 1;
        final StatsInfos infos = calculateHandValues();
        
        final StringBuilder sb = new StringBuilder();
        sb.append(p_action.ordinal()); // Classes
        sb.append(SimulationServer.SEPARATOR);
        sb.append(format(infos.m_winRatio)); // 1
        sb.append(format(infos.m_standardDeviation)); // 2
        sb.append(format(m_hero.m_money / highMoney)); // 3
        sb.append(format((double) (p_currentBet - m_hero.m_betAmount) / (double) m_hero.m_initialMoney));// 4
        sb.append(format((double) (p_currentBet - m_hero.m_betAmount) / (double) p_totalPotAmount));// 5
        sb.append(formatPosition(getPosition(m_hero.m_name))); // 6-14
        
        sb.append(formatEnum(m_hero.m_lastActionsPreflop, TypeSimplifiedAction.class)); // 15-21
        sb.append(formatEnum(m_hero.m_lastActionsFlop, TypeSimplifiedAction.class)); // 22-28
        sb.append(formatEnum(m_hero.m_lastActionsTurn, TypeSimplifiedAction.class)); // 29-35
        sb.append(formatEnum(m_hero.m_lastActionsRiver, TypeSimplifiedAction.class)); // 36-42
        
        sb.append(format((double) p_totalPotAmount / (double) m_hero.m_money)); // 43
        sb.append(formatEnum(m_state, TypePokerRound.class)); // 44-47
        // sb.append(format(m_currentInfos.m_players.size())); //47
        
        for (final TuplePlayer player : m_currentInfos.m_players)
        {
            if (player == m_hero)
            {
                continue;
            }
            
            // if (isFolded(player, m_state)) continue;
            if (player.m_isFolded)
            {
                continue;
            }
            
            sb.append(formatPosition(getPosition(player.m_name))); // 48-56
            sb.append(format(player.m_money / highMoney)); // 57
            sb.append(format((double) player.m_betAmount / (double) player.m_initialMoney)); // 58
            sb.append(formatEnum(player.m_lastActionsPreflop, TypeSimplifiedAction.class)); // 59-65
            sb.append(formatEnum(player.m_lastActionsFlop, TypeSimplifiedAction.class)); // 66-72
            sb.append(formatEnum(player.m_lastActionsTurn, TypeSimplifiedAction.class)); // 73-79
            sb.append(formatEnum(player.m_lastActionsRiver, TypeSimplifiedAction.class)); // 80-86
            
            if (m_statsAgent.m_overallStats.get(player.m_name) == null)
            {
                for (int j = 0; j != SimulationServer.NB_STATS; ++j)
                {
                    sb.append(format(null)); // 87-123
                }
            }
            else
            {
                if (m_state == TypePokerRound.PREFLOP)
                {
                    // Stats preflop
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbVPIPTotal_PRF())); // 87
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProb4Bet_PRF())); // 88
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbFoldTo4Bet_PRF())); // 89
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbSBFoldVsSteal_PRF())); // 90
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbBBFoldVsSteal_PRF())); // 91
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbCOSteal_PRF())); // 92
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbDSteal_PRF())); // 93
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbSBSteal_PRF())); // 94
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbSB3BetVsSteal_PRF())); // 95
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbBB3BetVsSteal_PRF())); // 96
                    
                    // PreflopRaise by position
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbPFR_BB())); // 97
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbPFR_CO())); // 98
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbPFR_D())); // 99
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbPFR_EP())); // 100
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbPFR_MP())); // 101
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbPFR_SB())); // 102
                    
                    // 3bet% by position
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProb3Bet_BB())); // 103
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProb3Bet_CO())); // 104
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProb3Bet_D())); // 105
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProb3Bet_EP())); // 106
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProb3Bet_MP())); // 107
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProb3Bet_SB())); // 108
                    
                    // fold to 3bet% by position
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbFoldTo3Bet_BB())); // 109
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbFoldTo3Bet_CO())); // 110
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbFoldTo3Bet_D())); // 111
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbFoldTo3Bet_EP())); // 112
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbFoldTo3Bet_MP())); // 113
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbFoldTo3Bet_SB())); // 114
                    
                    // Stats postflop
                    for (int j = 0; j != SimulationServer.NB_STATS_POSTFLOP; ++j)
                    {
                        sb.append(format(null)); // 115-123
                    }
                }
                else
                {
                    // Stats preflop
                    for (int j = 0; j != SimulationServer.NB_STATS_PREFLOP; ++j)
                    {
                        sb.append(format(null)); // 87-114
                    }
                    
                    // Stats postflop
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbBetFlop())); // 115
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbBetTurn())); // 116
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbBetRiver())); // 117
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbCBetFlop())); // 118
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbCBetTurn())); // 119
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbFlopFoldVsCBet())); // 120
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbFlopRaiseVsCBet())); // 121
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbTurnFoldVsCBet())); // 122
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbWTSD())); // 123
                }
            }
        }
        
        for (int i = m_currentInfos.m_players.size() + 1; i != 10; ++i)
        {
            for (int j = 0; j != SimulationServer.NB_PLAYER_INFOS; ++j)
            {
                sb.append(format(null));// 48-123
            }
        }
        
        m_vectors.add(sb.toString());
    }
}
