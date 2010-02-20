package tempServerGameTools;

import gameLogic.GameCard;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import pokerLogic.OldPokerPlayerInfo;
import pokerLogic.PokerPlayerAction;
import pokerLogic.Pot;
import tempServerGame.TempServerPokerPlayerInfo;
import tempServerGame.TempServerTableCommunicator;

/**
 * @author HOCUS
 *         This class can log all the events of a Hold'Em table in
 *         a file or in the console.
 *         To log in the console, use : new Logger(System.out)
 */
public class TempServerPokerLogger
{
    
    private final TempServerPokerObserver m_receiver;
    private final OutputStream m_output;
    
    /**
     * Create a new logger.
     * 
     * @param p_output
     */
    public TempServerPokerLogger(OutputStream p_output, TempServerPokerObserver receiver)
    {
        m_receiver = receiver;
        m_output = p_output;
        initializeReceiver();
    }
    
    /**
     * Print a message to the output
     * 
     * @param p_message
     *            The message to print
     */
    public void print(String p_message)
    {
        try
        {
            m_output.write(p_message.getBytes());
            m_output.flush();
        }
        catch (final IOException e)
        {
            System.out.println("Error on the logger");
            e.printStackTrace();
        }
    }
    
    /**
     * Print a message to the output adding a \n
     * 
     * @param p_message
     *            The message to print
     */
    public void println(String p_message)
    {
        try
        {
            m_output.write((p_message + "\n").getBytes());
            m_output.flush();
        }
        catch (final IOException e)
        {
            System.out.println("Error on the logger");
            e.printStackTrace();
        }
    }
    
    private void initializeReceiver()
    {
        m_receiver.subscribe(new TempServerPokerAdapter()
        {
            @Override
            public void bigBlindPosted(TempServerTableCommunicator p_table, TempServerPokerPlayerInfo p_player, int p_bigBlind)
            {
                println(p_player.getName() + " post big blind " + p_bigBlind);
            }
            
            @Override
            public void endBettingTurn(TempServerTableCommunicator p_table)
            {
                println("Betting turn ended");
            }
            
            @Override
            public void flopDealt(TempServerTableCommunicator p_table, GameCard[] p_board)
            {
                println("Flop: " + GameCard.CardArrayToCode(p_board));
            }
            
            @Override
            public void gameEnded(TempServerTableCommunicator p_table)
            {
                println("Game endend");
            }
            
            @Override
            public void gameStarted(TempServerTableCommunicator p_table)
            {
                println("Game Start");
                
                final List<OldPokerPlayerInfo> players = p_table.getPlayers();
                
                for (int i = 0; i < players.size(); i++)
                {
                    if (players.get(i) != null)
                    {
                        println(players.get(i).getName() + " " + (players.get(i).isDealer() ? "D - " : "  - ") + players.get(i).getMoney());
                    }
                }
                
            }
            
            @Override
            public void holeCardDeal(TempServerTableCommunicator p_table, TempServerPokerPlayerInfo p_player)
            {
                /*
                 * println(p_player.getName() + " receive "
                 * + Card.CardArrayListToCode(p_player.getHand()));
                 */
            }
            
            @Override
            public void playerEndTurn(TempServerTableCommunicator p_table, TempServerPokerPlayerInfo p_player, PokerPlayerAction p_action)
            {
                println(p_player.getName() + " " + p_action.toString() + ". Bet = " + p_player.getBet());
            }
            
            @Override
            public void playerJoinedTable(TempServerTableCommunicator p_table, TempServerPokerPlayerInfo p_player)
            {
                println(p_player.getName() + " Joined the table");
            }
            
            @Override
            public void playerLeftTable(TempServerTableCommunicator p_table, TempServerPokerPlayerInfo p_player)
            {
                println(p_player.getName() + " Left the table");
            }
            
            @Override
            public void playerMoneyChanged(TempServerTableCommunicator p_table, TempServerPokerPlayerInfo p_player)
            {
                println(p_player.getName() + " now have " + p_player.getMoney() + " on table");
            }
            
            @Override
            public void playerShowCard(TempServerTableCommunicator p_table, TempServerPokerPlayerInfo p_player)
            {
                println(p_player.getName() + " show his card " + GameCard.CardArrayToCode(p_player.getHand()));
            }
            
            @Override
            public void playerTurnStarted(TempServerTableCommunicator p_table, TempServerPokerPlayerInfo p_player)
            {
                println(p_player.getName() + " Start his turn");
            }
            
            @Override
            public void potWon(TempServerTableCommunicator p_table, TempServerPokerPlayerInfo p_player, Pot p_pot, int p_share)
            {
                println(p_player.getName() + " won " + p_share);
            }
            
            @Override
            public void riverDeal(TempServerTableCommunicator p_table, GameCard[] p_board)
            {
                println("River: " + GameCard.CardArrayToCode(p_board));
            }
            
            @Override
            public void smallBlindPosted(TempServerTableCommunicator p_table, TempServerPokerPlayerInfo p_player, int p_smallBlind)
            {
                println(p_player.getName() + " post small blind " + p_smallBlind);
            }
            
            @Override
            public void tableEnded(TempServerTableCommunicator p_table)
            {
                println("Table close");
            }
            
            @Override
            public void tableInfos(TempServerTableCommunicator pTable)
            {
                println("Table Infos sent");
            }
            
            @Override
            public void tableStarted(TempServerTableCommunicator p_table)
            {
                println("Table Started");
            }
            
            @Override
            public void turnDeal(TempServerTableCommunicator p_table, GameCard[] p_board)
            {
                println("Turn: " + GameCard.CardArrayToCode(p_board));
            }
            
            @Override
            public void waitingForPlayers(TempServerTableCommunicator p_table)
            {
                println("Waiting for Players");
            }
        });
    }
}
