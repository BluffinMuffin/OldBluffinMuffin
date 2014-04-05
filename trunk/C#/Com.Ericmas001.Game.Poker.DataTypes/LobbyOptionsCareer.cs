using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Com.Ericmas001.Game.Poker.DataTypes
{
    public class LobbyOptionsCareer : LobbyOptions
    {
        public override LobbyTypeEnum LobbyType
        {
            get { return LobbyTypeEnum.Career; }
        }
    }
}
