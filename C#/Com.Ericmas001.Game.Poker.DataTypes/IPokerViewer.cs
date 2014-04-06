using System;
using System.Collections.Generic;
using System.Text;

namespace Com.Ericmas001.Game.Poker.DataTypes
{
    public interface IPokerViewer
    {
        void SetGame(IPokerGame c, int s);
        void Start();
    }
}
