package db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import temp.TypeSimplifiedAction;

import miscUtil.HeadsUpException;

import basePoker.Card;
import basePoker.TypePlayerAction;
import basePoker.TypePokerGame;

public class TupleHandHistories
{
    public class PhaseEvents
    {
        public TuplePlayer m_player;
        public TypePlayerAction m_action;
        public int m_actionAmount = -1;
        
        public PhaseEvents(TuplePlayer p_player, TypePlayerAction p_action)
        {
            m_player = p_player;
            m_action = p_action;
        }
        
        public PhaseEvents(TuplePlayer p_player, TypePlayerAction p_action, int p_actionAmount)
        {
            m_player = p_player;
            m_action = p_action;
            m_actionAmount = p_actionAmount;
        }
    }
    
    public class TuplePlayer
    {
        public String m_name;
        public int m_noSeat;
        public int m_money;
        public boolean m_isFolded = false;
        public int m_initialMoney;
        public Card m_card1 = Card.getInstance().get(Card.HIDDEN_CARD);
        public Card m_card2 = Card.getInstance().get(Card.HIDDEN_CARD);
        public boolean m_isHero = false;
        public boolean m_isSittingOut = false;
        public int m_betAmount = 0;
        public TypeSimplifiedAction m_lastActionsPreflop = null;
        public TypeSimplifiedAction m_lastActionsFlop = null;
        public TypeSimplifiedAction m_lastActionsTurn = null;
        public TypeSimplifiedAction m_lastActionsRiver = null;
    }
    
    public class Winner
    {
        public TuplePlayer m_player;
        public int m_winAmount;
    }
    
    private final static Pattern GENERIC_DATE = Pattern.compile("([0-9]{4}/[0-9]{2}/[0-9]{2})");
    private final static Pattern GENERIC_TIME = Pattern.compile("([0-9][0-9]?:[0-9]{2}:[0-9]{2})");
    private final static Pattern GENERIC_BLINDS_AMOUNT = Pattern.compile("\\$(([0-9]+)(\\.[0-9]+)?)/\\$(([0-9]+)(\\.[0-9]+)?)");
    private final static Pattern GENERIC_DEALT = Pattern.compile("\\[([0-9TJQKA][csdh]) ([0-9TJQKA][csdh])\\]");
    private final static Pattern GENERIC_EVENTS = Pattern.compile("(calls \\$(([0-9]+)(\\.[0-9]+)?)|bets \\$(([0-9]+)(\\.[0-9]+)?)|folds|checks|raises .*to \\$(([0-9]+)(\\.[0-9]+)?)|Uncalled .*\\$(([0-9]+)(\\.[0-9]+)?))");
    private final static Pattern GENERIC_FLOP_CARDS = Pattern.compile("\\[([0-9TJQKA][csdh]) ([0-9TJQKA][csdh]) ([0-9TJQKA][csdh])\\]");
    private final static Pattern GENERIC_TURN_CARD = Pattern.compile("\\[([0-9TJQKA][csdh])\\]");
    
    private final static Pattern GENERIC_RIVER_CARD = TupleHandHistories.GENERIC_TURN_CARD;
    
    // private final static Pattern GENERIC_SEAT = Pattern.compile("[Ss]eat .*#([0-9]+)");
    
    private final static Pattern GENERIC_PLAYER_INFOS = Pattern.compile("Seat ([0-9]+): (.+) \\(\\$([0-9]+(\\.[0-9]+)?).*\\)");
    
    // private final static Pattern POKERSTARS_DEALER_SEAT = Pattern.compile();
    
    private final static Pattern GENERIC_HEADS_UP_PLAYER_INFOS = Pattern.compile("Seat ([0-9]+): (.+) (\\(big blind\\)|\\(small blind\\))");
    
    private final static Pattern GENERIC_WINNER = Pattern.compile("(won|collected) \\(\\$([0-9]+(\\.[0-9]+)?).*\\)");
    
    private final static Pattern GENERIC_SEAT = Pattern.compile("(The button is in seat #([0-9]+)|Seat #([0-9]+) is the button)");
    
    private final static SimpleDateFormat GENERIC_TIME_FORMAT = new SimpleDateFormat("yyyy/MM/dd - HH:mm:ss");
    
    // private String parseHeadsUPSeatsLines(StringTokenizer p_token) throws Exception
    // {
    // //Seat lines
    // Matcher m;
    // String line = p_token.nextToken();
    //
    // while (p_token.hasMoreTokens())
    // {
    // if (line.startsWith("Seat"))
    // {
    // m = GENERIC_HEADS_UP_PLAYER_INFOS.matcher(line);
    // m.find();
    //				
    // TuplePlayer player = new TuplePlayer();
    // player.m_noSeat = Integer.parseInt(m.group(1)) -1;
    // player.m_name = m.group(2);
    // player.m_money = parseMoney(m.group(3));
    //				
    // m_players.add(player);
    // }
    //			
    // line = p_token.nextToken();
    // }
    //	
    // if (m_players.size() == 0)
    // throw new HeadsUpException();
    //		
    // String regexPlayerNames = "(";
    // for (TuplePlayer player : m_players)
    // regexPlayerNames += Matcher.quoteReplacement(player.m_name).replaceAll("(\\||\\(|\\)|\\[|\\]|\\+|\\?|\\.|\\*|\\{|\\}|\\^)", "\\\\$1") + "|";
    //		
    // regexPlayerNames = regexPlayerNames.replaceAll("\\|$", ")");
    // m_PlayerPattern = Pattern.compile(regexPlayerNames);
    //		
    // return line;
    // }
    
    private Pattern m_PlayerPattern = null;
    
    public String m_text;
    
    public TypePokerGame m_type;
    
    public Calendar m_startedTime;
    
    public int m_bigBlind;
    
    public int m_smallBlind;
    
    public int m_noSeatDealer = -1;
    
    public int m_noSeatBigBlind = -1;
    
    public int m_noSeatSmallBlind = -1;
    
    public ArrayList<TuplePlayer> m_players = new ArrayList<TuplePlayer>();
    
    public ArrayList<Card> m_flop = new ArrayList<Card>();
    
    public Card m_turn = null;
    
    public Card m_river = null;
    
    public ArrayList<PhaseEvents> m_preflopEvents = new ArrayList<PhaseEvents>();
    
    public ArrayList<PhaseEvents> m_flopEvents = new ArrayList<PhaseEvents>();
    
    public ArrayList<PhaseEvents> m_turnEvents = new ArrayList<PhaseEvents>();
    
    public ArrayList<PhaseEvents> m_riverEvents = new ArrayList<PhaseEvents>();
    
    public ArrayList<Winner> m_winners = new ArrayList<Winner>();
    
    public TupleHandHistories(String p_text) throws Exception
    {
        m_text = p_text;
        parse();
    }
    
    private ArrayList<PhaseEvents> getActions(TuplePlayer p_player)
    {
        final ArrayList<PhaseEvents> events = new ArrayList<PhaseEvents>();
        
        for (final PhaseEvents event : m_preflopEvents)
        {
            if (p_player == event.m_player)
            {
                events.add(event);
            }
        }
        
        for (final PhaseEvents event : m_flopEvents)
        {
            if (p_player == event.m_player)
            {
                events.add(event);
            }
        }
        
        for (final PhaseEvents event : m_turnEvents)
        {
            if (p_player == event.m_player)
            {
                events.add(event);
            }
        }
        
        for (final PhaseEvents event : m_riverEvents)
        {
            if (p_player == event.m_player)
            {
                events.add(event);
            }
        }
        
        return events;
    }
    
    public TuplePlayer getHero()
    {
        for (final TuplePlayer player : m_players)
        {
            if (player.m_isHero)
            {
                return player;
            }
        }
        
        return null;
    }
    
    public TuplePlayer getPlayer(int p_noSeat)
    {
        for (final TuplePlayer player : m_players)
        {
            if (player.m_noSeat == p_noSeat)
            {
                return player;
            }
        }
        
        return null;
    }
    
    public TuplePlayer getPlayer(String p_name)
    {
        for (final TuplePlayer player : m_players)
        {
            if (player.m_name.equals(p_name))
            {
                return player;
            }
        }
        
        return null;
    }
    
    private void parse() throws Exception
    {
        final StringTokenizer token = new StringTokenizer(m_text, "\n");
        
        // if (m_text.contains("#4718288226"))
        // System.out.println("The ONE!!!");
        
        // try
        {
            if (m_text.startsWith("Full Tilt Poker") || m_text.startsWith("FullTiltPoker"))
            {
                parseFullTilt(token);
            }
            else if (m_text.startsWith("PokerStars"))
            {
                parsePokerStars(token);
            }
            else
            {
                throw new Exception("*** Unknowned Site ***");
            }
        }
        // catch (HeadsUpException e)
        // {
        // //Players list is empty, try looking for them
        // // in the summary.
        // String line = token.nextToken();
        // while (token.hasMoreTokens() && !line.contains("*** SUMMARY ***"))
        // line = token.nextToken();
        //
        // try
        // {
        // parseHeadsUPSeatsLines(token);
        // parse(); //Try reparse the whole Log knowing players.
        // }
        // catch (NoSuchElementException e1)
        // {
        //				
        // }
        // }
    }
    
    private String parseEvents(StringTokenizer p_token, ArrayList<PhaseEvents> p_events)
    {
        String line = p_token.nextToken();
        while (!line.startsWith("*** "))
        {
            Matcher m = m_PlayerPattern.matcher(line);
            TuplePlayer player = null;
            
            if (m.find())
            {
                player = getPlayer(m.group(1)); // Player name
                
                m = TupleHandHistories.GENERIC_EVENTS.matcher(line);
                if (m.find())
                {
                    if (m.group(1).startsWith("calls"))
                    {
                        p_events.add(new PhaseEvents(player, TypePlayerAction.CALL, parseMoney(m.group(2))));
                    }
                    else if (m.group(1).startsWith("bets"))
                    {
                        p_events.add(new PhaseEvents(player, TypePlayerAction.RAISE, parseMoney(m.group(5))));
                    }
                    else if (m.group(1).startsWith("folds"))
                    {
                        p_events.add(new PhaseEvents(player, TypePlayerAction.FOLD));
                    }
                    else if (m.group(1).startsWith("checks"))
                    {
                        p_events.add(new PhaseEvents(player, TypePlayerAction.CHECK));
                    }
                    else if (m.group(1).startsWith("raises"))
                    {
                        p_events.add(new PhaseEvents(player, TypePlayerAction.RAISE, parseMoney(m.group(8))));
                    }
                    else if (m.group(1).startsWith("Uncalled"))
                    {
                        p_events.add(new PhaseEvents(player, TypePlayerAction.UNCALLED, parseMoney(m.group(11))));
                    }
                    else
                    {
                        System.err.println("*** Unknowned Action: " + line);
                    }
                }
            }
            
            line = p_token.nextToken();
        }
        
        return line;
    }
    
    private String parseFirstLine(StringTokenizer p_token)
    {
        // First line
        final String line = p_token.nextToken("\n");
        if (line.contains("No Limit") && line.contains("Hold'em"))
        {
            m_type = TypePokerGame.NO_LIMIT;
        }
        else
        {
            System.err.println("*** Unknowned Hold'em Game Type *** : " + line);
        }
        
        Matcher m = TupleHandHistories.GENERIC_BLINDS_AMOUNT.matcher(line);
        m.find();
        m_smallBlind = parseMoney(m.group(1));
        m_bigBlind = parseMoney(m.group(4));
        
        m = TupleHandHistories.GENERIC_DATE.matcher(line);
        m.find();
        final String date = m.group(0);
        
        m = TupleHandHistories.GENERIC_TIME.matcher(line);
        m.find();
        final String time = m.group(0);
        
        m_startedTime = new GregorianCalendar();
        try
        {
            m_startedTime.setTime(TupleHandHistories.GENERIC_TIME_FORMAT.parse(date + " - " + time));
        }
        catch (final ParseException e)
        {
            e.printStackTrace();
        }
        
        return line;
    }
    
    private String parseFlop(StringTokenizer p_token, String p_line) throws Exception
    {
        String line = p_line;
        Matcher m;
        
        if (line.contains("*** FLOP ***"))
        {
            // *** FLOP ***
            m = TupleHandHistories.GENERIC_FLOP_CARDS.matcher(line);
            m.find();
            m_flop.add(Card.getInstance().get(m.group(1)));
            m_flop.add(Card.getInstance().get(m.group(2)));
            m_flop.add(Card.getInstance().get(m.group(3)));
            line = parseEvents(p_token, m_flopEvents);
        }
        
        return line;
    }
    
    private void parseFullTilt(StringTokenizer p_token) throws Exception
    {
        String line;
        
        line = parseFirstLine(p_token);
        line = parseSeatsLines(p_token);
        line = parseHoleCards(p_token);
        line = parseFlop(p_token, line);
        line = parseTurn(p_token, line);
        line = parseRiver(p_token, line);
        line = parseShowDown(p_token, line);
        line = parseSummary(p_token, line);
    }
    
    private String parseHoleCards(StringTokenizer p_token) throws Exception
    {
        String line;
        Matcher m;
        
        // *** HOLE CARDS ***
        line = p_token.nextToken();
        if (line.startsWith("Dealt"))
        {
            m = m_PlayerPattern.matcher(line);
            
            if (m.find())
            {
                final TuplePlayer player = getPlayer(m.group(1)); // Player name
                
                m = TupleHandHistories.GENERIC_DEALT.matcher(line);
                m.find();
                
                player.m_isHero = true;
                player.m_card1 = Card.getInstance().get(m.group(1));
                player.m_card2 = Card.getInstance().get(m.group(2));
                
                if ((player.m_card1 == null) || (player.m_card2 == null))
                {
                    System.out.println(m.group(1));
                    System.out.println(m.group(2));
                    System.out.println("BUG");
                }
            }
        }
        else
        {
            System.err.println("Expected: Dealt: " + line);
            throw new Exception();
        }
        
        // Preflop events
        line = parseEvents(p_token, m_preflopEvents);
        
        return line;
    }
    
    private int parseMoney(String p_text)
    {
        if (p_text.contains("."))
        {
            if (p_text.substring(p_text.indexOf(".") + 1).length() == 2)
            {
                return Integer.parseInt(p_text.replaceAll("(\\.|,)", ""));
            }
            else
            {
                return Integer.parseInt(p_text.replaceAll("(\\.|,)", "")) * 10;
            }
        }
        
        return Integer.parseInt(p_text) * 100;
    }
    
    private void parsePokerStars(StringTokenizer p_token) throws Exception
    {
        String line;
        
        line = parseFirstLine(p_token);
        line = parseSeatsLines(p_token);
        line = parseHoleCards(p_token);
        line = parseFlop(p_token, line);
        line = parseTurn(p_token, line);
        line = parseRiver(p_token, line);
        line = parseShowDown(p_token, line);
        line = parseSummary(p_token, line);
    }
    
    private String parseRiver(StringTokenizer p_token, String p_line) throws Exception
    {
        String line = p_line;
        Matcher m;
        
        if (line.contains("*** RIVER ***"))
        {
            // *** RIVER ***
            m = TupleHandHistories.GENERIC_RIVER_CARD.matcher(line);
            m.find();
            m_river = Card.getInstance().get(m.group(1));
            line = parseEvents(p_token, m_riverEvents);
        }
        
        return line;
    }
    
    private String parseSeatsLines(StringTokenizer p_token) throws Exception
    {
        // Seat lines
        Matcher m;
        String line = p_token.nextToken();
        final ArrayList<String> lastLines = new ArrayList<String>();
        while (!line.contains("*** HOLE CARDS ***"))
        {
            if (line.startsWith("Seat"))
            {
                m = TupleHandHistories.GENERIC_PLAYER_INFOS.matcher(line);
                m.find();
                
                final TuplePlayer player = new TuplePlayer();
                player.m_noSeat = Integer.parseInt(m.group(1)) - 1;
                player.m_name = m.group(2);
                player.m_money = parseMoney(m.group(3));
                player.m_initialMoney = player.m_money;
                
                m_players.add(player);
            }
            if (line.contains("button"))
            {
                m = TupleHandHistories.GENERIC_SEAT.matcher(line);
                if (m.find())
                {
                    if ((m.group(2) != null) && !m.group(2).isEmpty())
                    {
                        m_noSeatDealer = Integer.parseInt(m.group(2)) - 1;
                    }
                    else
                    {
                        m_noSeatDealer = Integer.parseInt(m.group(3)) - 1;
                    }
                }
            }
            else
            {
                lastLines.add(line);
            }
            
            line = p_token.nextToken();
        }
        
        if (m_players.size() == 0)
        {
            throw new HeadsUpException();
        }
        
        String regexPlayerNames = "(";
        for (final TuplePlayer player : m_players)
        {
            regexPlayerNames += Matcher.quoteReplacement(player.m_name).replaceAll("(\\||\\(|\\)|\\[|\\]|\\+|\\?|\\.|\\*|\\{|\\}|\\^)", "\\\\$1") + "|";
        }
        
        regexPlayerNames = regexPlayerNames.replaceAll("\\|$", ")");
        m_PlayerPattern = Pattern.compile(regexPlayerNames);
        
        for (final String last : lastLines)
        {
            if (last.contains("big blind"))
            {
                m = m_PlayerPattern.matcher(last);
                if (m.find())
                {
                    final TuplePlayer player = getPlayer(m.group(1));
                    m_preflopEvents.add(new PhaseEvents(player, TypePlayerAction.BIG_BLIND));
                }
            }
            
            if (last.contains("small blind"))
            {
                m = m_PlayerPattern.matcher(last);
                if (m.find())
                {
                    final TuplePlayer player = getPlayer(m.group(1));
                    m_preflopEvents.add(new PhaseEvents(player, TypePlayerAction.SMALL_BLIND));
                }
            }
        }
        
        return line;
    }
    
    private String parseShowDown(StringTokenizer p_token, String p_line) throws Exception
    {
        String line = p_line;
        
        if (line.contains("*** SHOW DOWN ***"))
        {
            // *** SHOWDOWN ***
            line = p_token.nextToken();
            while (!line.startsWith("*** "))
            {
                line = p_token.nextToken();
            }
        }
        
        return line;
    }
    
    private String parseSummary(StringTokenizer p_token, String p_line) throws Exception
    {
        String line = p_line;
        Matcher m;
        
        if (line.contains("*** SUMMARY ***"))
        {
            // *** SUMMARY ***
            
            while (p_token.hasMoreTokens())
            {
                line = p_token.nextToken();
                m = TupleHandHistories.GENERIC_WINNER.matcher(line);
                if (m.find())
                {
                    final Winner winner = new Winner();
                    winner.m_winAmount = parseMoney(m.group(2));
                    
                    m = m_PlayerPattern.matcher(line);
                    m.find();
                    winner.m_player = getPlayer(m.group(1));
                    m_winners.add(winner);
                }
                
                // if (line.contains("button"))
                // {
                // m = m_PlayerPattern.matcher(line);
                // m.find();
                // m_noSeatDealer = getPlayer(m.group(1)).m_noSeat;
                // }
                
                if (line.contains("small blind"))
                {
                    m = Pattern.compile(m_PlayerPattern.pattern() + " (\\(button\\) | \\(big blind\\) )*\\(small blind\\)").matcher(line);
                    if (m.find())
                    {
                        m_noSeatSmallBlind = getPlayer(m.group(1)).m_noSeat;
                    }
                    else
                    {
                        m_noSeatSmallBlind = -1;
                    }
                }
                
                if (line.contains("big blind"))
                {
                    m = Pattern.compile(m_PlayerPattern.pattern() + " (\\(button\\) | \\(small blind\\) )*\\(big blind\\)").matcher(line);
                    if (m.find())
                    {
                        m_noSeatBigBlind = getPlayer(m.group(1)).m_noSeat;
                    }
                    else
                    {
                        m_noSeatBigBlind = -1;
                    }
                }
            }
        }
        else
        {
            System.out.println("*** Expected SUMMARY: " + line);
        }
        
        final ArrayList<Integer> indexesToRemove = new ArrayList<Integer>();
        
        for (final TuplePlayer player : m_players)
        {
            if (getActions(player).size() == 0)
            {
                player.m_isSittingOut = true;
                indexesToRemove.add(m_players.indexOf(player));
            }
        }
        
        for (int i = indexesToRemove.size() - 1; i >= 0; --i)
        {
            m_players.remove((int) indexesToRemove.get(i));
        }
        
        return line;
    }
    
    private String parseTurn(StringTokenizer p_token, String p_line) throws Exception
    {
        String line = p_line;
        Matcher m;
        
        if (line.contains("*** TURN ***"))
        {
            // *** TURN ***
            m = TupleHandHistories.GENERIC_TURN_CARD.matcher(line);
            m.find();
            m_turn = Card.getInstance().get(m.group(1));
            line = parseEvents(p_token, m_turnEvents);
        }
        
        return line;
    }
    
    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        
        sb.append(new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(m_startedTime.getTime()));
        sb.append("|");
        sb.append(m_type);
        sb.append("|");
        sb.append(m_smallBlind);
        sb.append("/");
        sb.append(m_bigBlind);
        sb.append("|H:");
        sb.append(getHero().m_name);
        sb.append("[");
        sb.append(getHero().m_card1);
        sb.append(" ");
        sb.append(getHero().m_card2);
        sb.append("]");
        sb.append("|D:");
        sb.append(getPlayer(m_noSeatDealer).m_name);
        sb.append("|");
        
        for (final TuplePlayer player : m_players)
        {
            sb.append("[");
            sb.append(player.m_noSeat);
            sb.append(">");
            sb.append(player.m_name);
            sb.append("(");
            sb.append(player.m_money);
            sb.append(")");
            sb.append("]");
        }
        sb.append("|");
        if (m_flop.size() > 0)
        {
            sb.append("[");
            sb.append(m_flop.get(0));
            sb.append(" ");
            sb.append(m_flop.get(1));
            sb.append(" ");
            sb.append(m_flop.get(2));
            sb.append("]");
        }
        if (m_turn != null)
        {
            sb.append("[");
            sb.append(m_turn);
            sb.append("]");
        }
        if (m_river != null)
        {
            sb.append("[");
            sb.append(m_river);
            sb.append("]");
        }
        
        sb.append("|");
        
        sb.append("P[");
        for (final PhaseEvents event : m_preflopEvents)
        {
            sb.append(event.m_player.m_name);
            sb.append(">");
            sb.append(event.m_action);
            sb.append("(");
            sb.append(event.m_actionAmount);
            sb.append(");");
        }
        sb.append("]");
        sb.append("|");
        
        sb.append("F[");
        for (final PhaseEvents event : m_flopEvents)
        {
            sb.append(event.m_player.m_name);
            sb.append(">");
            sb.append(event.m_action);
            sb.append("(");
            sb.append(event.m_actionAmount);
            sb.append(");");
        }
        sb.append("]");
        sb.append("|");
        
        sb.append("T[");
        for (final PhaseEvents event : m_turnEvents)
        {
            sb.append(event.m_player.m_name);
            sb.append(">");
            sb.append(event.m_action);
            sb.append("(");
            sb.append(event.m_actionAmount);
            sb.append(");");
        }
        sb.append("]");
        sb.append("|");
        
        sb.append("R[");
        for (final PhaseEvents event : m_riverEvents)
        {
            sb.append(event.m_player.m_name);
            sb.append(">");
            sb.append(event.m_action);
            sb.append("(");
            sb.append(event.m_actionAmount);
            sb.append(");");
        }
        sb.append("]");
        // sb.append("|");
        
        return sb.toString();
    }
}
