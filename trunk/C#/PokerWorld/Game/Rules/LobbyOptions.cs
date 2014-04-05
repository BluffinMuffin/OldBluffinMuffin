using PokerWorld.Game.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PokerWorld.Game.Rules
{
    public abstract class LobbyOptions
    {
        public abstract LobbyTypeEnum LobbyType { get; }
    }
}
