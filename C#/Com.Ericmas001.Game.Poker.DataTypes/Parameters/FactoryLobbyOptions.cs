using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Com.Ericmas001.Game.Poker.DataTypes.Parameters
{
    public static class FactoryLobbyOptions
    {
        public static LobbyOptions GenerateOptions(LobbyTypeEnum type)
        {
            switch (type)
            {
                case LobbyTypeEnum.Career: return new LobbyOptionsCareer();
                case LobbyTypeEnum.Training: return new LobbyOptionsTraining();
            }
            return null;
        }
    }
}
