using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PokerProtocol.Commands
{
    public abstract class AbstractCommand
    {
        public static char Delimitter { get { return ';'; } }
        protected abstract string CommandName { get; }

        public virtual void Encode(StringBuilder sb) { }


        protected void Append(StringBuilder sb, string s)
        {
            sb.Append(s);
            sb.Append(AbstractCommand.Delimitter);
        }
        protected void Append(StringBuilder sb, bool b)
        {
            sb.Append(b);
            sb.Append(AbstractCommand.Delimitter);
        }
        protected void Append(StringBuilder sb, int i)
        {
            sb.Append(i);
            sb.Append(AbstractCommand.Delimitter);
        }

        public string Encode()
        {
            StringBuilder sb = new StringBuilder();
            Append(sb, CommandName);
            Encode(sb);
            return sb.ToString();
        }
    }
}
