using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Games.CardGame;
using EricUtility.Networking.Commands;
using PokerWorld.Game;
using PokerProtocol.Entities;
using PokerWorld.Game.Enums;

namespace PokerProtocol.Commands.Game
{
    public class TableInfoCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gameTABLE_INFO";

        public int TotalPotAmount { get; set; }
        public int NbSeats { get; set; }
        public int NbMinPlayersToStart { get; set; }
        public List<int> PotsAmount { get; set; }
        public List<int> BoardCardIDs { get; set; }
        public int NbPlayers { get; set; }
        public List<Player> Seats { get; set; }
        public BetEnum Limit { get; set; }
        public int BigBlindAmount { get; set; }

        public TableInfoCommand()
        {
        }

        public TableInfoCommand(int totalPotAmount, int nbSeats, List<int> potsAmount, List<int> boardCardIDs, int nbPlayers, List<Player> seats, BetEnum limit, int bigBlindAmount, int nbMinPlayersToStart)
        {
            TotalPotAmount = totalPotAmount;
            NbSeats = nbSeats;
            PotsAmount = potsAmount;
            BoardCardIDs = boardCardIDs;
            NbPlayers = nbPlayers;
            Seats = seats;
            Limit = limit;
            BigBlindAmount = bigBlindAmount;
            NbMinPlayersToStart = nbMinPlayersToStart;
        }

        public TableInfoCommand(TableInfo info, PlayerInfo pPlayer)
        {
            PotsAmount = new List<int>();
            BoardCardIDs = new List<int>();
            Seats = new List<Player>();

            TotalPotAmount = info.TotalPotAmnt;
            NbSeats = info.NbMaxSeats;
            NbPlayers = info.NbMaxSeats;

            foreach (MoneyPot pot in info.Pots)
            {
                PotsAmount.Add(pot.Amount);
            }

            for (int i = info.Pots.Count; i < NbSeats; i++)
            {
                PotsAmount.Add(0);
            }
            GameCard[] boardCards = info.Cards;
            for (int i = 0; i < 5; ++i)
            {
                BoardCardIDs.Add(boardCards[i].Id);
            }

            for (int i = 0; i < info.NbMaxSeats; ++i)
            {
                Player seat = new Player(i);
                Seats.Add(seat);
                PlayerInfo player = info.GetPlayer(i);
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

                seat.IsDealer = info.NoSeatDealer == i; // isDealer
                seat.IsSmallBlind = info.NoSeatSmallBlind == i; // isSmallBlind
                seat.IsBigBlind = info.NoSeatBigBlind == i; // isBigBlind
                seat.IsCurrentPlayer = info.NoSeatCurrPlayer == i; // isCurrentPlayer
                seat.TimeRemaining = 0; // timeRemaining
                seat.Bet = player.MoneyBetAmnt; // betAmount
                seat.IsPlaying = player.IsPlaying;
            }
            Limit = info.BetLimit;
            BigBlindAmount = info.BigBlindAmnt;
            NbMinPlayersToStart = info.NbMinPlayersToStart;
        }
    }
}
