using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Protocol.Commands;

namespace BluffinMuffin.Protocol.Server
{
    public class GameCommandEntry : CommandEntry
    {
        public RemotePlayer Player { get; set; }
    }
}
