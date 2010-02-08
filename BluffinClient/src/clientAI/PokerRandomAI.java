package clientAI;

import java.util.ArrayList;
import java.util.Random;

import pokerLogic.PokerPlayerAction;
import pokerLogic.TypePlayerAction;

public class PokerRandomAI extends PokerAI
{
    private Random rnd = new Random();
    
    public PokerRandomAI()
    {
    }
    
    @Override
    protected PokerPlayerAction analyze(ArrayList<TypePlayerAction> p_actionsAllowed, int p_minRaiseAmount, int p_maxRaiseAmount)
    {   
    	PokerPlayerAction action = null;
    	
        if (rnd.nextInt(3) == 0 && p_actionsAllowed.contains(TypePlayerAction.FOLD) )
        {
        	action = new PokerPlayerAction(TypePlayerAction.FOLD, 0);
        }
        else
        {
        	while(action == null){
        		action = chooseAction(p_actionsAllowed, p_minRaiseAmount, p_maxRaiseAmount);
        	}
        }
        
        return action;
    }
    
    private PokerPlayerAction chooseAction(ArrayList<TypePlayerAction> p_actionsAllowed, int p_minRaiseAmount, int p_maxRaiseAmount){
    	PokerPlayerAction action = null;
    	switch (rnd.nextInt(3)) {
		case 0:
			if(p_actionsAllowed.contains(TypePlayerAction.CHECK)){
				action = new PokerPlayerAction(TypePlayerAction.CHECK, 0);
			}
			break;
		case 1:
			if(p_actionsAllowed.contains(TypePlayerAction.CALL)){
				action = new PokerPlayerAction(TypePlayerAction.CALL, m_callAmount);
			}
			break;
		case 2:
			if(p_actionsAllowed.contains(TypePlayerAction.RAISE)){
				int bet = m_minRaiseAmount;
				int choice = rnd.nextInt(100);
				int m = m_maxRaiseAmount - m_minRaiseAmount;
				
				if(choice < 50)
				{
				}
				else if(choice < 90){
					bet += rnd.nextInt(m / ( 5 + rnd.nextInt(10)) );
				}
				else
				{
					bet += rnd.nextInt(m / rnd.nextInt(5) );
				}
				
				action = new PokerPlayerAction(TypePlayerAction.RAISE, bet);
			}
			break;
		}
    	
    	return action;
    }
    
    
    @Override
    public String toString()
    {
        return "Poker Random AI";
    }
}
