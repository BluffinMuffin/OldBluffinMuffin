using Com.Ericmas001.Game.Poker.DataTypes.EventHandling;
using System;
using System.Collections.Generic;

namespace Com.Ericmas001.Game.Poker.DataTypes
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
        event EventHandler<SeatEventArgs> SeatUpdated;
        event EventHandler<PlayerInfoEventArgs> PlayerLeaved;
        event EventHandler<HistoricPlayerInfoEventArgs> PlayerActionNeeded;
        event EventHandler<PlayerInfoEventArgs> PlayerMoneyChanged;
        event EventHandler<PlayerInfoEventArgs> PlayerHoleCardsChanged;

        event EventHandler<PlayerActionEventArgs> PlayerActionTaken;

        event EventHandler<PotWonEventArgs> PlayerWonPot;

        TableInfo Table { get; }

        bool PlayMoney(PlayerInfo p, int amnt);
        bool LeaveGame(PlayerInfo p);
        bool SitIn(PlayerInfo p, int noSeat = -1);

        string Encode { get; }
    }
}
