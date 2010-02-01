package statsEric;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

import backend.Player;

public class StatsRegister
{
    
    private Player m_Player;
    
    private final Map<TypeStatsStreet, StatsRegisterStreet> m_StreetActions = new EnumMap<TypeStatsStreet, StatsRegisterStreet>(TypeStatsStreet.class);
    
    public StatsRegister()
    {
        for (final TypeStatsStreet street : TypeStatsStreet.values())
        {
            m_StreetActions.put(street, new StatsRegisterStreet());
        }
    }
    
    public StatsRegister(Player p)
    {
        m_Player = p;
    }
    
    public int GetCapacityGlobal(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsGlobal> setAction)
    {
        int total = 0;
        for (final TypeStatsStreet action : setStreet)
        {
            total += m_StreetActions.get(action).GetCapacityGlobal(setPos, setAction);
        }
        return total;
    }
    
    public int GetCapacitySpecific(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsSpecifics> setDo, EnumSet<TypeStatsSpecifics> setWhen)
    {
        int total = 0;
        for (final TypeStatsStreet action : setStreet)
        {
            total += m_StreetActions.get(action).GetCapacitySpecific(setPos, setDo, setWhen);
        }
        return total;
    }
    
    public Player getPlayer()
    {
        return m_Player;
    }
    
    public double GetRatioGlobal(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsGlobal> setAction)
    {
        double total = 0;
        for (final TypeStatsStreet action : setStreet)
        {
            total += m_StreetActions.get(action).GetRatioGlobal(setPos, setAction);
        }
        return total / setStreet.size();
    }
    
    public double GetRatioSpecific(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsSpecifics> setDo, EnumSet<TypeStatsSpecifics> setWhen)
    {
        double total = 0;
        for (final TypeStatsStreet action : setStreet)
        {
            total += m_StreetActions.get(action).GetRatioSpecific(setPos, setDo, setWhen);
        }
        return total / setStreet.size();
    }
    
    public int GetValueGlobal(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsGlobal> setAction)
    {
        int total = 0;
        for (final TypeStatsStreet action : setStreet)
        {
            total += m_StreetActions.get(action).GetValueGlobal(setPos, setAction);
        }
        return total;
    }
    
    public int GetValueSpecific(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsSpecifics> setDo, EnumSet<TypeStatsSpecifics> setWhen)
    {
        int total = 0;
        for (final TypeStatsStreet action : setStreet)
        {
            total += m_StreetActions.get(action).GetValueSpecific(setPos, setDo, setWhen);
        }
        return total;
    }
    
    public void IncCapacityGlobal(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsGlobal> setAction)
    {
        for (final TypeStatsStreet action : setStreet)
        {
            m_StreetActions.get(action).IncCapacityGlobal(setPos, setAction);
        }
    }
    
    public void IncCapacitySpecific(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsSpecifics> setDo, EnumSet<TypeStatsSpecifics> setWhen)
    {
        for (final TypeStatsStreet action : setStreet)
        {
            m_StreetActions.get(action).IncCapacitySpecific(setPos, setDo, setWhen);
        }
    }
    
    public void IncValueGlobal(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsGlobal> setAction)
    {
        for (final TypeStatsStreet action : setStreet)
        {
            m_StreetActions.get(action).IncValueGlobal(setPos, setAction);
        }
    }
    
    public void IncValueSpecific(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsSpecifics> setDo, EnumSet<TypeStatsSpecifics> setWhen)
    {
        for (final TypeStatsStreet action : setStreet)
        {
            m_StreetActions.get(action).IncValueSpecific(setPos, setDo, setWhen);
        }
    }
}
