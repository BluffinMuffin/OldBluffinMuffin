using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;
using Newtonsoft.Json.Linq;
using System.Web;
using Newtonsoft.Json;

namespace PokerProtocol.Commands
{
    public class DisconnectCommand : DisconnectJsonCommand
    {
        public DisconnectCommand(JObject obj): base(obj)
        {
        }

        public DisconnectCommand(): base()
        {
        }
    }
}
