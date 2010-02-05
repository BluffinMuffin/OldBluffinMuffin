package clientGame;

import pokerLogic.TypePlayerMajorPosition;
import pokerLogic.TypePlayerMinorPosition;

public class PlayerCustom extends ClientPokerPlayerInfo
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
