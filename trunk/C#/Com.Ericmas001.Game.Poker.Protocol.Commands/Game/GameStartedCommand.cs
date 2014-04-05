using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class GameStartedCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gameSTARTED";

        public int NoSeatD { get; set; }
        public int NoSeatSB { get; set; }
        public int NoSeatBB { get; set; }
    }
}
