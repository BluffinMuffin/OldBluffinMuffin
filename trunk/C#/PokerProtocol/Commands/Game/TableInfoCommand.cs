using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Games.CardGame;
using EricUtility.Networking.Commands;
using PokerWorld.Game;
using PokerProtocol.Entities;
using PokerWorld.Game.Enums;
using PokerWorld.Game.Rules;

namespace PokerProtocol.Commands.Game
{
    public class TableInfoCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gameTABLE_INFO";

        public GameRule Rules { get; set; }
        public int TotalPotAmount { get; set; }
        public List<int> PotsAmount { get; set; }
        public List<int> BoardCardIDs { get; set; }
        public int NbPlayers { get; set; }
        public List<PlayerInfo> Seats { get; set; }

        public TableInfoCommand()
        {
        }

        public TableInfoCommand(PokerTable info, PokerPlayer pPlayer)
        {
            PotsAmount = new List<int>();
            BoardCardIDs = new List<int>();
            Seats = new List<PlayerInfo>();

            Rules = info.Rules;

            TotalPotAmount = info.TotalPotAmnt;
            NbPlayers = Rules.MaxPlayers;

            foreach (MoneyPot pot in info.Pots)
            {
                PotsAmount.Add(pot.Amount);
            }

            for (int i = info.Pots.Count; i < Rules.MaxPlayers; i++)
            {
                PotsAmount.Add(0);
            }
            GameCard[] boardCards = info.Cards;
            for (int i = 0; i < 5; ++i)
            {
                BoardCardIDs.Add(boardCards[i].Id);
            }

            for (int i = 0; i < Rules.MaxPlayers; ++i)
            {
                PlayerInfo seat = new PlayerInfo(i);
                Seats.Add(seat);
                PokerPlayer player = info.GetPlayer(i);
                seat.IsEmpty = (player == null);

                if (seat.IsEmpty)
                {
                    continue;
                }

                seat.PlayerName = player.Name; // playerName
                seat.Money = player.MoneySafeAmnt; // playerMoney

                bool itsMe = (i == pPlayer.NoSeat);

                // Player cards
                GameCard[] holeCards = itsMe ? player.Cards : player.RelativeCards;
                for (int j = 0; j < 2; ++j)
                {
                    seat.HoleCardIDs.Add(holeCards[j].Id);
                }

                seat.IsDealer = info.NoSeatDealer == i;
                seat.IsSmallBlind = info.NoSeatSmallBlind == i;
                seat.IsBigBlind = info.NoSeatBigBlind == i;
                seat.IsCurrentPlayer = info.NoSeatCurrPlayer == i;
                seat.TimeRemaining = 0;
                seat.Bet = player.MoneyBetAmnt;
                seat.IsPlaying = player.IsPlaying;
            }
        }
    }
}
