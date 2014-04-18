using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Com.Ericmas001.Game.Poker.DataTypes.Parameters
{
    public class LobbyOptionsCareer : LobbyOptions
    {
        public override LobbyTypeEnum OptionType
        {
            get { return LobbyTypeEnum.Career; }
        }
    }
}
