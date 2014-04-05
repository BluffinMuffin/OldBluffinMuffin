using PokerWorld.Game.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PokerWorld.Game.Rules
{
    public class LobbyOptionsCareer : LobbyOptions
    {
        public override LobbyTypeEnum LobbyType
        {
            get { return LobbyTypeEnum.Career; }
        }
    }
}
