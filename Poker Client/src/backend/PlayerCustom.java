package backend;

import basePoker.TypePlayerMajorPosition;
import basePoker.TypePlayerMinorPosition;

public class PlayerCustom extends ClientPokerPlayer
{
    public TypePlayerMinorPosition m_minorPosition = null;
    public TypePlayerMajorPosition m_majorPosition = null;
    
    public PlayerCustom(int pNoSeat)
    {
        super(pNoSeat);
    }
    
    public PlayerCustom(int pNoSeat, String pName, int pMoney)
    {
        super(pNoSeat, pName, pMoney);
    }
}
