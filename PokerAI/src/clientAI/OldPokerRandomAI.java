package clientAI;

import java.util.ArrayList;
import java.util.Random;

import pokerLogic.OldPokerPlayerAction;
import pokerLogic.OldTypePlayerAction;

public class OldPokerRandomAI extends OldPokerAI
{
    private Random rnd = new Random();
    
    public OldPokerRandomAI()
    {
    }
    
    @Override
    protected OldPokerPlayerAction analyze(ArrayList<OldTypePlayerAction> p_actionsAllowed, int p_minRaiseAmount, int p_maxRaiseAmount)
    {   
    	OldPokerPlayerAction action = null;
    	
        if (rnd.nextInt(3) == 0 && p_actionsAllowed.contains(OldTypePlayerAction.FOLD) )
        {
        	action = new OldPokerPlayerAction(OldTypePlayerAction.FOLD, 0);
        }
        else
        {
        	while(action == null){
        		action = chooseAction(p_actionsAllowed, p_minRaiseAmount, p_maxRaiseAmount);
        	}
        }
        
        return action;
    }
    
    private OldPokerPlayerAction chooseAction(ArrayList<OldTypePlayerAction> p_actionsAllowed, int p_minRaiseAmount, int p_maxRaiseAmount){
    	OldPokerPlayerAction action = null;
    	switch (rnd.nextInt(3)) {
		case 0:
			if(p_actionsAllowed.contains(OldTypePlayerAction.CHECK)){
				action = new OldPokerPlayerAction(OldTypePlayerAction.CHECK, 0);
			}
			break;
		case 1:
			if(p_actionsAllowed.contains(OldTypePlayerAction.CALL)){
				action = new OldPokerPlayerAction(OldTypePlayerAction.CALL, m_callAmount);
			}
			break;
		case 2:
			if(p_actionsAllowed.contains(OldTypePlayerAction.RAISE)){
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
				
				action = new OldPokerPlayerAction(OldTypePlayerAction.RAISE, bet);
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