﻿using System;
using System.Collections.Generic;
using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class GameEndedCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gameENDED";
    }
}
