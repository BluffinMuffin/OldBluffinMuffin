using PokerWorld.Game.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PokerWorld.Game.Rules
{
    public class LobbyOptionsTraining : LobbyOptions
    {
        public override LobbyTypeEnum LobbyType
        {
            get { return LobbyTypeEnum.Training; }
        }

        public int StartingAmount { get; set; }

        public LobbyOptionsTraining()
        {
            StartingAmount = 1500;
        }
    }
}
