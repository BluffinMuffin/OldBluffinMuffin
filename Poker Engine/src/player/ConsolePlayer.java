package player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import utility.TypePlayerAction;
import backend.Action;

/**
 * @author HOCUS
 *         This player is a local player that work on the console.
 *         When an action is needed, a choice is displayed in the console.
 */
public class ConsolePlayer extends AbstractPlayer
{
    
    /**
     * Create a new player named "Anonymous Player" with no money
     */
    public ConsolePlayer()
    {
        super();
    }
    
    /**
     * Create a new player with no money
     * 
     * @param p_name
     *            The name of the player
     */
    public ConsolePlayer(String p_name)
    {
        super(p_name);
    }
    
    /**
     * Create a new player
     * 
     * @param p_name
     *            The name of the player
     * @param p_money
     *            The starting chips of the player
     */
    public ConsolePlayer(String p_name, int p_money)
    {
        super(p_name, p_money);
    }
    
    @Override
    protected Action getActionFromUser(boolean p_canCheck, boolean p_canFold, boolean p_canCall, int p_callOf, boolean p_canRaise, int p_minimumRaise, int p_maximumRaise)
    {
        Action action = null;
        while (action == null)
        {
            System.out.println(m_name + " What do you want to do? (Money=" + m_money + ", bet=" + m_currentBet + ")");
            if (p_canCheck)
            {
                System.out.println("1. Check");
            }
            if (p_canFold)
            {
                System.out.println("2. Fold");
            }
            if (p_canCall)
            {
                System.out.println("3. Call");
            }
            if (p_canRaise)
            {
                System.out.println("4. Raise [" + p_minimumRaise + ", " + p_maximumRaise + "]");
            }
            
            final BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
            String input = "";
            try
            {
                input = is.readLine();
            }
            catch (final IOException e)
            {
                input = "";
            }
            
            if (input.equals("1"))
            {
                if (p_canCheck)
                {
                    action = new Action(TypePlayerAction.CHECK, 0);
                }
            }
            else if (input.equals("2"))
            {
                if (p_canFold)
                {
                    action = new Action(TypePlayerAction.FOLD, 0);
                }
            }
            else if (input.equals("3"))
            {
                if (p_canCall)
                {
                    action = new Action(TypePlayerAction.CALL, p_callOf);
                }
            }
            else if (input.equals("4"))
            {
                if (p_canRaise)
                {
                    int raise = -1;
                    System.out.println("Raise to");
                    try
                    {
                        input = is.readLine();
                        raise = Integer.parseInt(input);
                    }
                    catch (final IOException e)
                    {
                        System.out.println("Error on the reading.");
                        raise = -1;
                    }
                    catch (final NumberFormatException e)
                    {
                        System.out.println("Not a valid number.");
                        raise = -1;
                    }
                    
                    if ((raise >= p_minimumRaise) && (raise <= p_maximumRaise))
                    {
                        action = new Action(TypePlayerAction.RAISE, raise);
                    }
                    else
                    {
                        System.out.println("Invalid amount");
                    }
                }
            }
            else
            {
                System.out.println("Invalid input");
            }
        }
        
        return action;
    }
}
