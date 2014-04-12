using System;
using System.Collections.Generic;
using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;
using Newtonsoft.Json.Linq;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class PlayerSitOutResponse : AbstractJsonCommandResponse<PlayerSitOutCommand>
    {
        public static string COMMAND_NAME = "gameSIT_OUT_RESPONSE";

        public bool Success { get; set; }

        public PlayerSitOutResponse()
            : base()
        {
        }

        public PlayerSitOutResponse(PlayerSitOutCommand command)
            : base(command)
        {
        }
    }
}
