using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BluffinMuffin.Protocol.Commands
{
    public interface IGameCommand
    {
        int TableId { get; set; }
    }
}
