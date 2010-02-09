package clientAILobbyGUI;

import clientAI.TypeAgent;

public class TupleAISummary
{
    public String m_AIName;
    public TypeAgent m_AIType;
    public boolean m_viewer;
    public boolean m_viewer2;
    
    public TupleAISummary(String AIName, TypeAgent AIType, boolean viewer, boolean viewer2)
    {
        m_AIName = AIName;
        m_AIType = AIType;
        m_viewer = viewer;
        m_viewer2 = viewer2;
    }
    
    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        
        if (m_viewer)
        {
            
            if (m_viewer2)
            {
                sb.append("(V2) ");
            }
            else
            {
                sb.append("(V) ");
            }
        }
        
        sb.append(m_AIName);
        sb.append(" [");
        sb.append(m_AIType.name());
        sb.append(" ]");
        
        return sb.toString();
    }
}
