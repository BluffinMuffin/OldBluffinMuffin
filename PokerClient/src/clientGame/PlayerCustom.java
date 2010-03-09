package clientGame;

import pokerLogic.OldTypePlayerMajorPosition;
import pokerLogic.OldTypePlayerMinorPosition;

public class PlayerCustom extends ClientPokerPlayerInfo
{
    public OldTypePlayerMinorPosition m_minorPosition = null;
    public OldTypePlayerMajorPosition m_majorPosition = null;
    
    public PlayerCustom(int pNoSeat)
    {
        super(pNoSeat);
    }
    
    public PlayerCustom(int pNoSeat, String pName, int pMoney)
    {
        super(pNoSeat, pName, pMoney);
    }
}
