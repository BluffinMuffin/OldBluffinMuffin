using System;
using System.Collections.Generic;
using System.Text;

namespace Com.Ericmas001.Game.BluffinMuffin.GUI
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