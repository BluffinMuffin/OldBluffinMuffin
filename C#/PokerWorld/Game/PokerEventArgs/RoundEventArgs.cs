using PokerWorld.Game.Enums;
using System;
using System.Collections.Generic;
using System.Text;

namespace PokerWorld.Game.PokerEventArgs
{
    public class RoundEventArgs : EventArgs
    {
        private readonly RoundEnum m_Round;
        public RoundEnum Round { get { return m_Round; } }

        public RoundEventArgs(RoundEnum r)
        {
            m_Round = r;
        }
    }
}
