using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EricUtility;

namespace PokerProtocol.Commands
{
    public abstract class AbstractCommandResponse<T>: AbstractCommand
        where T : AbstractCommand
    {
        private readonly T m_Command;

        public AbstractCommandResponse(T command)
        {
            m_Command = command;
        }
        protected T Command
        {
            get { return m_Command; }
        } 


        public override void Encode(StringBuilder sb) 
        {
            m_Command.Encode(sb);
        }

    }
}
