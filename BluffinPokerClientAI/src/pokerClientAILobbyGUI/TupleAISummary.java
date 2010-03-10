package pokerClientAILobbyGUI;

import newPokerClientAI.TypePokerAI;

public class TupleAISummary
{
    public String m_AIName;
    public TypePokerAI m_AIType;
    public boolean m_viewer;
    
    public TupleAISummary(String AIName, TypePokerAI AIType, boolean viewer)
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
