using System;

namespace Com.Ericmas001.Game.Poker.DataTypes.EventHandling
{
    public class PlayerInfoEventArgs : EventArgs
    {
        private readonly PlayerInfo m_Player;
        public PlayerInfo Player { get { return m_Player; } }

        public PlayerInfoEventArgs(PlayerInfo p)
        {
            m_Player = p;
        }
    }
}
