using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;

namespace Com.Ericmas001.Game.Poker.DataTypes.Enums
{
    public enum GameStateEnum
    {
        Init,
        WaitForPlayers,
        WaitForBlinds,
        Playing,
        Showdown,
        DecideWinners,
        DistributeMoney,
        End
    }
}
