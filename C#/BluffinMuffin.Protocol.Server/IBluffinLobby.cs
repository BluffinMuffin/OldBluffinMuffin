using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Protocol.Commands;

namespace BluffinMuffin.Protocol.Server
{
    public interface IBluffinLobby
    {
        bool IsNameUsed(string name);
        void AddName(string name);
        void RemoveName(string name);
    }
}
