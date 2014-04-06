using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System;
using System.Collections.Generic;
using System.Text;

namespace Com.Ericmas001.Game.Poker.DataTypes.EventHandling
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
