using PokerWorld.Game.Enums;
using System;
using System.Collections.Generic;
using System.Text;

namespace PokerWorld.Game.PokerEventArgs
{
    public class HistoricPlayerInfoEventArgs : PlayerInfoEventArgs
    {
        private readonly PlayerInfo m_Last;
        public PlayerInfo Last { get { return m_Last; } }

        public HistoricPlayerInfoEventArgs(PlayerInfo p, PlayerInfo l)
            : base(p)
        {
            m_Last = l;
        }
    }
}
