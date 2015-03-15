using BluffinMuffin.Poker.DataTypes.Enums;
using System;

namespace BluffinMuffin.Poker.DataTypes.EventHandling
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
