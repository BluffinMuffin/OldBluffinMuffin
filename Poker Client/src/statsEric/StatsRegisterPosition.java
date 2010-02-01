package statsEric;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

public class StatsRegisterPosition
{
    private final Map<TypeStatsGlobal, StatsRegisterEntry> m_GlobalActions = new EnumMap<TypeStatsGlobal, StatsRegisterEntry>(TypeStatsGlobal.class);
    private final Map<TypeStatsSpecifics, StatsRegisterAction> m_SpecificActions = new EnumMap<TypeStatsSpecifics, StatsRegisterAction>(TypeStatsSpecifics.class);
    
    public StatsRegisterPosition()
    {
        for (final TypeStatsGlobal gAction : TypeStatsGlobal.values())
        {
            m_GlobalActions.put(gAction, new StatsRegisterEntry());
        }
        for (final TypeStatsSpecifics sAction : TypeStatsSpecifics.values())
        {
            m_SpecificActions.put(sAction, new StatsRegisterAction());
        }
    }
    
    public int GetCapacityGlobal(EnumSet<TypeStatsGlobal> set)
    {
        int total = 0;
        for (final TypeStatsGlobal action : set)
        {
            total += m_GlobalActions.get(action).GetCapacity();
        }
        return total;
    }
    
    public int GetCapacitySpecific(EnumSet<TypeStatsSpecifics> setDo, EnumSet<TypeStatsSpecifics> setWhen)
    {
        int total = 0;
        for (final TypeStatsSpecifics action : setDo)
        {
            total += m_SpecificActions.get(action).GetCapacity(setWhen);
        }
        return total;
    }
    
    public double GetRatioGlobal(EnumSet<TypeStatsGlobal> set)
    {
        double total = 0;
        for (final TypeStatsGlobal action : set)
        {
            total += m_GlobalActions.get(action).GetRatio();
        }
        return total / set.size();
    }
    
    public double GetRatioSpecific(EnumSet<TypeStatsSpecifics> setDo, EnumSet<TypeStatsSpecifics> setWhen)
    {
        double total = 0;
        for (final TypeStatsSpecifics action : setDo)
        {
            total += m_SpecificActions.get(action).GetRatio(setWhen);
        }
        return total / setDo.size();
    }
    
    public int GetValueGlobal(EnumSet<TypeStatsGlobal> set)
    {
        int total = 0;
        for (final TypeStatsGlobal action : set)
        {
            total += m_GlobalActions.get(action).GetValue();
        }
        return total;
    }
    
    public int GetValueSpecific(EnumSet<TypeStatsSpecifics> setDo, EnumSet<TypeStatsSpecifics> setWhen)
    {
        int total = 0;
        for (final TypeStatsSpecifics action : setDo)
        {
            total += m_SpecificActions.get(action).GetValue(setWhen);
        }
        return total;
    }
    
    public void IncCapacityGlobal(EnumSet<TypeStatsGlobal> set)
    {
        for (final TypeStatsGlobal action : set)
        {
            m_GlobalActions.get(action).IncCapacity();
        }
    }
    
    public void IncCapacitySpecific(EnumSet<TypeStatsSpecifics> setDo, EnumSet<TypeStatsSpecifics> setWhen)
    {
        for (final TypeStatsSpecifics action : setDo)
        {
            m_SpecificActions.get(action).IncCapacity(setWhen);
        }
    }
    
    public void IncValueGlobal(EnumSet<TypeStatsGlobal> set)
    {
        for (final TypeStatsGlobal action : set)
        {
            m_GlobalActions.get(action).IncValue();
        }
    }
    
    public void IncValueSpecific(EnumSet<TypeStatsSpecifics> setDo, EnumSet<TypeStatsSpecifics> setWhen)
    {
        for (final TypeStatsSpecifics action : setDo)
        {
            m_SpecificActions.get(action).IncValue(setWhen);
        }
    }
}
