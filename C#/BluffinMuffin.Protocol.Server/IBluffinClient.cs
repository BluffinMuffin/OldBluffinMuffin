using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Protocol.Commands;

namespace BluffinMuffin.Protocol.Server
{
    public interface IBluffinClient
    {
        string PlayerName { get; set; }

        void SendCommand(AbstractBluffinCommand command);
    }
}
