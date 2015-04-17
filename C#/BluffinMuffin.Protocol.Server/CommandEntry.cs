using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Protocol.Commands;

namespace BluffinMuffin.Protocol.Server
{
    public class CommandEntry<T>
        where T : AbstractBluffinCommand
    {
        public T Command { get; set; }
        public IBluffinClient Client { get; set; }
    }
}
