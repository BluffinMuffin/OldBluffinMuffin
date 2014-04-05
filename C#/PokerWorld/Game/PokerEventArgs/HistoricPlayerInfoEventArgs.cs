using PokerWorld.Game.Enums;
using System;
using System.Collections.Generic;
using System.Text;

namespace PokerWorld.Game.PokerEventArgs
{
    public class HistoricPlayerInfoEventArgs : PlayerInfoEventArgs
    {
        private readonly PokerPlayer m_Last;
        public PokerPlayer Last { get { return m_Last; } }

        public HistoricPlayerInfoEventArgs(PokerPlayer p, PokerPlayer l)
            : base(p)
        {
            m_Last = l;
        }
    }
}
