using PokerWorld.Game.Enums;
using System;
using System.Collections.Generic;
using System.Text;

namespace PokerWorld.Game.PokerEventArgs
{
    public class PlayerInfoEventArgs : EventArgs
    {
        private readonly PokerPlayer m_Player;
        public PokerPlayer Player { get { return m_Player; } }

        public PlayerInfoEventArgs(PokerPlayer p)
        {
            m_Player = p;
        }
    }
}
