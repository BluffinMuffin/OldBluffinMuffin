using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PokerProtocol.Commands;

namespace PokerProtocol
{
    public class StringEventArgs : EventArgs
    {
        private readonly string m_Str;
        public string Str { get { return m_Str; } }

        public StringEventArgs(string s)
        {
            m_Str = s;
        }
    }
    public class CommandEventArgs<T> : EventArgs
        where T : AbstractCommand
    {
        private readonly T m_Command;
        public T Command { get { return m_Command; } }

        public CommandEventArgs(T c)
        {
            m_Command = c;
        }
    }
}
