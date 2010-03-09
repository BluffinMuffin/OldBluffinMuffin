package clientAILobbyGUI;

import clientAI.TypeAgent;

public class OldTupleAISummary
{
    public String m_AIName;
    public TypeAgent m_AIType;
    public boolean m_viewer;
    
    public OldTupleAISummary(String AIName, TypeAgent AIType, boolean viewer)
    {
        m_AIName = AIName;
        m_AIType = AIType;
        m_viewer = viewer;
    }
    
    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        
        if (m_viewer)
        {
            sb.append("(V) ");
            
        }
        
        sb.append(m_AIName);
        sb.append(" [");
        sb.append(m_AIType.name());
        sb.append(" ]");
        
        return sb.toString();
    }
}
