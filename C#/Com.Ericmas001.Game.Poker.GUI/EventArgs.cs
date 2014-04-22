using System;

namespace Com.Ericmas001.Game.Poker.GUI
{
    public class IntEventArgs : EventArgs
    {
        private readonly int m_Value;
        public int Value { get { return m_Value; } }

        public IntEventArgs(int i)
        {
            m_Value = i;
        }
    }
}