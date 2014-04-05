﻿using PokerWorld.Game.PokerEventArgs;
using System;
using System.Collections.Generic;

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
        event EventHandler<HistoricPlayerInfoEventArgs> PlayerActionNeeded;
        event EventHandler<PlayerInfoEventArgs> PlayerMoneyChanged;
        event EventHandler<PlayerInfoEventArgs> PlayerHoleCardsChanged;

        event EventHandler<PlayerActionEventArgs> PlayerActionTaken;

        event EventHandler<PotWonEventArgs> PlayerWonPot;

        PokerTable Table { get; }

        bool PlayMoney(PokerPlayer p, int amnt);
        bool LeaveGame(PokerPlayer p);

        string Encode { get; }
    }
}
