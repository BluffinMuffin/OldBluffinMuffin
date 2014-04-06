﻿using System.Collections.Generic;
using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class BetTurnEndedCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gameBET_TURN_ENDED";

        public RoundTypeEnum Round { get; set; }
        public List<int> PotsAmounts { get; set; }
    }
}
