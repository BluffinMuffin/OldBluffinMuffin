using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;
using EricUtility;
using PokerWorld.Game.Dealer;
using PokerWorld.Game.Enums;
using PokerWorld.Game.PokerEventArgs;

namespace PokerWorld.Game.Enums
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
