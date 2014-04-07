using System;
using System.Collections.Generic;
using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;
using Newtonsoft.Json.Linq;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class PlayerSitInResponse : AbstractJsonCommandResponse<PlayerSitInCommand>
    {
        public static string COMMAND_NAME = "gameSIT_IN_RESPONSE";

        public int NoSeat { get; set; }

        public PlayerSitInResponse()
            : base()
        {
        }

        public PlayerSitInResponse(PlayerSitInCommand command)
            : base(command)
        {
        }
    }
}
