package tools;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import player.IPlayer;
import backend.HoldEmTable;
import backend.IHoldEmObserver;
import backend.Pot;
import basePoker.Card;
import basePoker.PokerPlayerAction;

/**
 * @author HOCUS
 *         This class can log all the events of a Hold'Em table in
 *         a file or in the console.
 *         To log in the console, use : new Logger(System.out)
 */
public class Logger implements IHoldEmObserver
{
    
    private final OutputStream m_output;
    
    /**
     * Create a new logger.
     * 
     * @param p_output
     */
    public Logger(OutputStream p_output)
    {
        m_output = p_output;
    }
    
    @Override
    public void bigBlindPosted(HoldEmTable p_table, IPlayer p_player, int p_bigBlind)
    {
        println(p_player.getName() + " post big blind " + p_bigBlind);
    }
    
    @Override
    public void endBettingTurn(HoldEmTable p_table)
    {
        println("Betting turn ended");
    }
    
    @Override
    public void flopDealt(HoldEmTable p_table, Card[] p_board)
    {
        println("Flop: " + Card.CardArrayToCode(p_board));
    }
    
    @Override
    public void gameEnded(HoldEmTable p_table)
    {
        println("Game endend");
    }
    
    @Override
    public void gameStarted(HoldEmTable p_table)
    {
        println("Game Start");
        
        final List<IPlayer> players = p_table.getPlayers();
        
        for (int i = 0; i < players.size(); i++)
        {
            if (players.get(i) != null)
            {
                println(players.get(i).getName() + " " + (players.get(i).isDealer() ? "D - " : "  - ") + players.get(i).getMoney());
            }
        }
        
    }
    
    @Override
    public void holeCardDeal(HoldEmTable p_table, IPlayer p_player)
    {
        /*
         * println(p_player.getName() + " receive "
         * + Card.CardArrayListToCode(p_player.getHand()));
         */
    }
    
    @Override
    public void playerEndTurn(HoldEmTable p_table, IPlayer p_player, PokerPlayerAction p_action)
    {
        println(p_player.getName() + " " + p_action.toString() + ". Bet = " + p_player.getBet());
    }
    
    @Override
    public void playerJoinedTable(HoldEmTable p_table, IPlayer p_player)
    {
        println(p_player.getName() + " Joined the table");
    }
    
    @Override
    public void playerLeftTable(HoldEmTable p_table, IPlayer p_player)
    {
        println(p_player.getName() + " Left the table");
    }
    
    @Override
    public void playerMoneyChanged(HoldEmTable p_table, IPlayer p_player)
    {
        println(p_player.getName() + " now have " + p_player.getMoney() + " on table");
    }
    
    @Override
    public void playerShowCard(HoldEmTable p_table, IPlayer p_player)
    {
        println(p_player.getName() + " show his card " + Card.CardArrayListToCode(p_player.getHand()));
    }
    
    @Override
    public void playerTurnStarted(HoldEmTable p_table, IPlayer p_player)
    {
        println(p_player.getName() + " Start his turn");
    }
    
    @Override
    public void potWon(HoldEmTable p_table, IPlayer p_player, Pot p_pot, int p_share)
    {
        println(p_player.getName() + " won " + p_share);
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
    
    @Override
    public void riverDeal(HoldEmTable p_table, Card[] p_board)
    {
        println("River: " + Card.CardArrayToCode(p_board));
    }
    
    @Override
    public void smallBlindPosted(HoldEmTable p_table, IPlayer p_player, int p_smallBlind)
    {
        println(p_player.getName() + " post small blind " + p_smallBlind);
    }
    
    @Override
    public void tableEnded(HoldEmTable p_table)
    {
        println("Table close");
    }
    
    @Override
    public void tableInfos(HoldEmTable pTable)
    {
        println("Table Infos sent");
    }
    
    @Override
    public void tableStarted(HoldEmTable p_table)
    {
        this.println("Table Started");
    }
    
    @Override
    public void turnDeal(HoldEmTable p_table, Card[] p_board)
    {
        println("Turn: " + Card.CardArrayToCode(p_board));
    }
    
    @Override
    public void waitingForPlayers(HoldEmTable p_table)
    {
        println("Waiting for Players");
    }
}
