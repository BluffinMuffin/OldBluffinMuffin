using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System;
using System.Collections.Generic;
using System.Text;

namespace PokerWorld.Game.PokerEventArgs
{
    public class RoundEventArgs : EventArgs
    {
        private readonly RoundTypeEnum m_Round;
        public RoundTypeEnum Round { get { return m_Round; } }

        public RoundEventArgs(RoundTypeEnum r)
        {
            m_Round = r;
        }
    }
}
