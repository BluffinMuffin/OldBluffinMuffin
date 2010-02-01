package statsEric;

import java.util.ArrayList;
import java.util.EnumSet;

import utility.ClosingListener;
import utility.TypeGameState;
import utility.TypePlayerAction;
import backend.Player;
import backend.Table;
import backend.agent.IPokerAgent;
import backend.agent.IPokerAgentListener;

public class StatsCollector implements IPokerAgentListener
{
    
    private final Player m_Player;
    private StatsRegister m_Register;
    
    public StatsCollector(Player p)
    {
        m_Player = p;
        Reset();
    }
    
    public StatsCollector(Player p, StatsRegister r)
    {
        m_Player = p;
        m_Register = r;
    }
    
    @Override
    public void addClosingListener(ClosingListener<IPokerAgent> pListener)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void betTurnEnded(ArrayList<Integer> pPotIndices, TypeGameState pGameStat)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void boardChanged(ArrayList<Integer> pBoardCardIndices)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void gameEnded()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void gameStarted(Player pOldDealer, Player pOldSmallBlind, Player pOldBigBlind)
    {
        // TODO Auto-generated method stub
        
    }
    
    public void IncCapacityGlobal(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsGlobal> setAction)
    {
        m_Register.IncCapacityGlobal(setStreet, setPos, setAction);
    }
    
    public void IncCapacityGlobal(TypeStatsStreet street, TypeStatsPokerPos pos, TypeStatsGlobal action)
    {
        m_Register.IncCapacityGlobal(EnumSet.of(street), EnumSet.of(pos), EnumSet.of(action));
    }
    
    public void IncCapacitySpecific(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsSpecifics> setDo)
    {
        m_Register.IncCapacitySpecific(setStreet, setPos, setDo, EnumSet.of(TypeStatsSpecifics.Unknown));
    }
    
    public void IncCapacitySpecific(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsSpecifics> setDo, EnumSet<TypeStatsSpecifics> setWhen)
    {
        m_Register.IncCapacitySpecific(setStreet, setPos, setDo, setWhen);
    }
    
    public void IncCapacitySpecific(TypeStatsStreet street, TypeStatsPokerPos pos, TypeStatsSpecifics action)
    {
        m_Register.IncCapacitySpecific(EnumSet.of(street), EnumSet.of(pos), EnumSet.of(action), EnumSet.of(TypeStatsSpecifics.Unknown));
    }
    
    public void IncCapacitySpecific(TypeStatsStreet street, TypeStatsPokerPos pos, TypeStatsSpecifics actionDo, TypeStatsSpecifics actionWhen)
    {
        m_Register.IncCapacitySpecific(EnumSet.of(street), EnumSet.of(pos), EnumSet.of(actionDo), EnumSet.of(actionWhen));
    }
    
    public void IncValueGlobal(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsGlobal> setAction)
    {
        m_Register.IncValueGlobal(setStreet, setPos, setAction);
    }
    
    public void IncValueGlobal(TypeStatsStreet street, TypeStatsPokerPos pos, TypeStatsGlobal action)
    {
        m_Register.IncValueGlobal(EnumSet.of(street), EnumSet.of(pos), EnumSet.of(action));
    }
    
    public void IncValueSpecific(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsSpecifics> setDo)
    {
        m_Register.IncValueSpecific(setStreet, setPos, setDo, EnumSet.of(TypeStatsSpecifics.Unknown));
    }
    
    public void IncValueSpecific(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsSpecifics> setDo, EnumSet<TypeStatsSpecifics> setWhen)
    {
        m_Register.IncValueSpecific(setStreet, setPos, setDo, setWhen);
    }
    
    public void IncValueSpecific(TypeStatsStreet street, TypeStatsPokerPos pos, TypeStatsSpecifics action)
    {
        m_Register.IncValueSpecific(EnumSet.of(street), EnumSet.of(pos), EnumSet.of(action), EnumSet.of(TypeStatsSpecifics.Unknown));
    }
    
    public void IncValueSpecific(TypeStatsStreet street, TypeStatsPokerPos pos, TypeStatsSpecifics actionDo, TypeStatsSpecifics actionWhen)
    {
        m_Register.IncValueSpecific(EnumSet.of(street), EnumSet.of(pos), EnumSet.of(actionDo), EnumSet.of(actionWhen));
    }
    
    @Override
    public void playerCardChanged(Player pPlayer)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void playerJoined(Player pPlayer)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void playerLeft(Player pPlayer)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void playerMoneyChanged(Player pPlayer, int pOldMoneyAmount)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void playerTurnBegan(Player pOldCurrentPlayer)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void playerTurnEnded(Player pPlayer, TypePlayerAction pAction, int pActionAmount)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void potWon(Player pPlayer, int pPotAmountWon, int pPotIndex)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void removeClosingListener(ClosingListener<IPokerAgent> pListener)
    {
        // TODO Auto-generated method stub
        
    }
    
    public void Reset()
    {
        m_Register = new StatsRegister();
    }
    
    @Override
    public void run()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void setTable(Table pTable)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void start()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void stop()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void tableClosed()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void tableInfos()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void waitingForPlayers()
    {
        // TODO Auto-generated method stub
        
    }
}