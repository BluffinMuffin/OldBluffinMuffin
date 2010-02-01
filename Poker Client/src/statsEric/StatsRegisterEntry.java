package statsEric;

public class StatsRegisterEntry
{
    private int m_Capacity;
    private int m_Value;
    
    public StatsRegisterEntry()
    {
        m_Capacity = 0;
        m_Value = 0;
    }
    
    public int GetCapacity()
    {
        return m_Capacity;
    }
    
    public double GetRatio()
    {
        if (m_Capacity == 0)
        {
            return 0;
        }
        
        return m_Value / (double) m_Capacity;
    }
    
    public int GetValue()
    {
        return m_Value;
    }
    
    public void IncCapacity()
    {
        m_Capacity++;
    }
    
    public void IncValue()
    {
        m_Value++;
    }
}
