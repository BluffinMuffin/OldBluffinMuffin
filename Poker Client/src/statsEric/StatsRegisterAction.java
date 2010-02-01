package statsEric;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

public class StatsRegisterAction
{
    private final Map<TypeStatsSpecifics, StatsRegisterEntry> m_OnWhat = new EnumMap<TypeStatsSpecifics, StatsRegisterEntry>(TypeStatsSpecifics.class);
    
    public StatsRegisterAction()
    {
        for (final TypeStatsSpecifics action : TypeStatsSpecifics.values())
        {
            m_OnWhat.put(action, new StatsRegisterEntry());
        }
    }
    
    public int GetCapacity(EnumSet<TypeStatsSpecifics> set)
    {
        int total = 0;
        for (final TypeStatsSpecifics action : set)
        {
            total += m_OnWhat.get(action).GetCapacity();
        }
        return total;
    }
    
    public double GetRatio(EnumSet<TypeStatsSpecifics> set)
    {
        double total = 0;
        for (final TypeStatsSpecifics action : set)
        {
            total += m_OnWhat.get(action).GetRatio();
        }
        return total / set.size();
    }
    
    public int GetValue(EnumSet<TypeStatsSpecifics> set)
    {
        int total = 0;
        for (final TypeStatsSpecifics action : set)
        {
            total += m_OnWhat.get(action).GetValue();
        }
        return total;
    }
    
    public void IncCapacity(EnumSet<TypeStatsSpecifics> set)
    {
        for (final TypeStatsSpecifics action : set)
        {
            m_OnWhat.get(action).IncCapacity();
        }
    }
    
    public void IncValue(EnumSet<TypeStatsSpecifics> set)
    {
        for (final TypeStatsSpecifics action : set)
        {
            m_OnWhat.get(action).IncValue();
        }
    }
}
