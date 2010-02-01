package statsEric;

import java.util.EnumSet;

import backend.Player;

public class StatsAnalyzer
{
    
    private final Player m_Player;
    private final StatsRegister m_Register;
    
    public StatsAnalyzer(Player p, StatsRegister r)
    {
        m_Player = p;
        m_Register = r;
    }
    
    public int GetCapacityGlobal(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsGlobal> setAction)
    {
        return m_Register.GetCapacityGlobal(setStreet, setPos, setAction);
    }
    
    public int GetCapacityGlobal(TypeStatsStreet street, TypeStatsPokerPos pos, TypeStatsGlobal action)
    {
        return m_Register.GetCapacityGlobal(EnumSet.of(street), EnumSet.of(pos), EnumSet.of(action));
    }
    
    public int GetCapacitySpecific(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsSpecifics> setDo)
    {
        return m_Register.GetCapacitySpecific(setStreet, setPos, setDo, EnumSet.of(TypeStatsSpecifics.Unknown));
    }
    
    public int GetCapacitySpecific(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsSpecifics> setDo, EnumSet<TypeStatsSpecifics> setWhen)
    {
        return m_Register.GetCapacitySpecific(setStreet, setPos, setDo, setWhen);
    }
    
    public int GetCapacitySpecific(TypeStatsStreet street, TypeStatsPokerPos pos, TypeStatsSpecifics action)
    {
        return m_Register.GetCapacitySpecific(EnumSet.of(street), EnumSet.of(pos), EnumSet.of(action), EnumSet.of(TypeStatsSpecifics.Unknown));
    }
    
    public int GetCapacitySpecific(TypeStatsStreet street, TypeStatsPokerPos pos, TypeStatsSpecifics actionDo, TypeStatsSpecifics actionWhen)
    {
        return m_Register.GetCapacitySpecific(EnumSet.of(street), EnumSet.of(pos), EnumSet.of(actionDo), EnumSet.of(actionWhen));
    }
    
    public double GetRatioGlobal(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsGlobal> setAction)
    {
        return m_Register.GetRatioGlobal(setStreet, setPos, setAction);
    }
    
    public double GetRatioGlobal(TypeStatsStreet street, TypeStatsPokerPos pos, TypeStatsGlobal action)
    {
        return m_Register.GetRatioGlobal(EnumSet.of(street), EnumSet.of(pos), EnumSet.of(action));
    }
    
    public double GetRatioSpecific(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsSpecifics> setDo)
    {
        return m_Register.GetRatioSpecific(setStreet, setPos, setDo, EnumSet.of(TypeStatsSpecifics.Unknown));
    }
    
    public double GetRatioSpecific(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsSpecifics> setDo, EnumSet<TypeStatsSpecifics> setWhen)
    {
        return m_Register.GetRatioSpecific(setStreet, setPos, setDo, setWhen);
    }
    
    public double GetRatioSpecific(TypeStatsStreet street, TypeStatsPokerPos pos, TypeStatsSpecifics action)
    {
        return m_Register.GetRatioSpecific(EnumSet.of(street), EnumSet.of(pos), EnumSet.of(action), EnumSet.of(TypeStatsSpecifics.Unknown));
    }
    
    public double GetRatioSpecific(TypeStatsStreet street, TypeStatsPokerPos pos, TypeStatsSpecifics actionDo, TypeStatsSpecifics actionWhen)
    {
        return m_Register.GetRatioSpecific(EnumSet.of(street), EnumSet.of(pos), EnumSet.of(actionDo), EnumSet.of(actionWhen));
    }
    
    public int GetValueGlobal(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsGlobal> setAction)
    {
        return m_Register.GetValueGlobal(setStreet, setPos, setAction);
    }
    
    public int GetValueGlobal(TypeStatsStreet street, TypeStatsPokerPos pos, TypeStatsGlobal action)
    {
        return m_Register.GetValueGlobal(EnumSet.of(street), EnumSet.of(pos), EnumSet.of(action));
    }
    
    public int GetValueSpecific(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsSpecifics> setDo)
    {
        return m_Register.GetValueSpecific(setStreet, setPos, setDo, EnumSet.of(TypeStatsSpecifics.Unknown));
    }
    
    public int GetValueSpecific(EnumSet<TypeStatsStreet> setStreet, EnumSet<TypeStatsPokerPos> setPos, EnumSet<TypeStatsSpecifics> setDo, EnumSet<TypeStatsSpecifics> setWhen)
    {
        return m_Register.GetValueSpecific(setStreet, setPos, setDo, setWhen);
    }
    
    public int GetValueSpecific(TypeStatsStreet street, TypeStatsPokerPos pos, TypeStatsSpecifics action)
    {
        return m_Register.GetValueSpecific(EnumSet.of(street), EnumSet.of(pos), EnumSet.of(action), EnumSet.of(TypeStatsSpecifics.Unknown));
    }
    
    public int GetValueSpecific(TypeStatsStreet street, TypeStatsPokerPos pos, TypeStatsSpecifics actionDo, TypeStatsSpecifics actionWhen)
    {
        return m_Register.GetValueSpecific(EnumSet.of(street), EnumSet.of(pos), EnumSet.of(actionDo), EnumSet.of(actionWhen));
    }
}
