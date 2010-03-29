using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PokerWorld.Game
{
    public interface IPokerGame
    {
        event EventHandler EverythingEnded;
        event EventHandler GameBlindNeeded;
        event EventHandler GameEnded;
        event EventHandler GameGenerallyUpdated;

        event EventHandler<RoundEventArgs> GameBettingRoundStarted;
        event EventHandler<RoundEventArgs> GameBettingRoundEnded;

        event EventHandler<PlayerInfoEventArgs> PlayerJoined;
        event EventHandler<PlayerInfoEventArgs> PlayerLeaved;
        event EventHandler<PlayerInfoEventArgs> PlayerActionNeeded;
        event EventHandler<PlayerInfoEventArgs> PlayerMoneyChanged;
        event EventHandler<PlayerInfoEventArgs> PlayerHoleCardsChanged;

        event EventHandler<PlayerActionEventArgs> PlayerActionTaken;

        event EventHandler<PotWonEventArgs> PlayerWonPot;

        TableInfo Table { get; }

        bool PlayMoney(PlayerInfo p, int amnt);
        bool LeaveGame(PlayerInfo p);
    }
}
