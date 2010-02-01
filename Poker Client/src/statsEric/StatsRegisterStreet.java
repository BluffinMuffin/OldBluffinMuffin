package statsEric;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

public class StatsRegisterStreet
{
    private final Map<TypeStatsPokerPos, StatsRegisterPosition> m_PosActions = new EnumMap<TypeStatsPokerPos, StatsRegisterPosition>(TypeStatsPokerPos.class);
    
    public StatsRegisterStreet()
    {
        for (final TypeStatsPokerPos street : TypeStatsPokerPos.values())
        {
            m_PosActions.put(street, new StatsRegisterPosition());
        }
    }
    
    public int GetCapacityGlobal(EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsGlobal> setAction)
    {
        int total = 0;
        for (final TypeStatsPokerPos action : setPos)
        {
            total += m_PosActions.get(action).GetCapacityGlobal(setAction);
        }
        return total;
    }
    
    public int GetCapacitySpecific(EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsSpecifics> setDo, EnumSet<TypeStatsSpecifics> setWhen)
    {
        int total = 0;
        for (final TypeStatsPokerPos action : setPos)
        {
            total += m_PosActions.get(action).GetCapacitySpecific(setDo, setWhen);
        }
        return total;
    }
    
    public double GetRatioGlobal(EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsGlobal> setAction)
    {
        double total = 0;
        for (final TypeStatsPokerPos action : setPos)
        {
            total += m_PosActions.get(action).GetRatioGlobal(setAction);
        }
        return total / setPos.size();
    }
    
    public double GetRatioSpecific(EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsSpecifics> setDo, EnumSet<TypeStatsSpecifics> setWhen)
    {
        double total = 0;
        for (final TypeStatsPokerPos action : setPos)
        {
            total += m_PosActions.get(action).GetRatioSpecific(setDo, setWhen);
        }
        return total / setPos.size();
    }
    
    public int GetValueGlobal(EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsGlobal> setAction)
    {
        int total = 0;
        for (final TypeStatsPokerPos action : setPos)
        {
            total += m_PosActions.get(action).GetValueGlobal(setAction);
        }
        return total;
    }
    
    public int GetValueSpecific(EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsSpecifics> setDo, EnumSet<TypeStatsSpecifics> setWhen)
    {
        int total = 0;
        for (final TypeStatsPokerPos action : setPos)
        {
            total += m_PosActions.get(action).GetValueSpecific(setDo, setWhen);
        }
        return total;
    }
    
    public void IncCapacityGlobal(EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsGlobal> setAction)
    {
        for (final TypeStatsPokerPos action : setPos)
        {
            m_PosActions.get(action).IncCapacityGlobal(setAction);
        }
    }
    
    public void IncCapacitySpecific(EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsSpecifics> setDo, EnumSet<TypeStatsSpecifics> setWhen)
    {
        for (final TypeStatsPokerPos action : setPos)
        {
            m_PosActions.get(action).IncCapacitySpecific(setDo, setWhen);
        }
    }
    
    public void IncValueGlobal(EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsGlobal> setAction)
    {
        for (final TypeStatsPokerPos action : setPos)
        {
            m_PosActions.get(action).IncValueGlobal(setAction);
        }
    }
    
    public void IncValueSpecific(EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsSpecifics> setDo, EnumSet<TypeStatsSpecifics> setWhen)
    {
        for (final TypeStatsPokerPos action : setPos)
        {
            m_PosActions.get(action).IncValueSpecific(setDo, setWhen);
        }
    }
}
