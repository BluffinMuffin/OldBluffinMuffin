using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using PokerWorld.Game;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Entities;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using PokerWorld.Game.Rules;
using System.Linq;
using EricUtility.Games.CardGame;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
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

        public TableInfoCommand(PokerTable table, PokerPlayer player)
        {
            BoardCardIDs = table.Cards.Select(c => c.Id).ToList();
            Seats = new List<PlayerInfo>();

            Rules = table.Rules;

            TotalPotAmount = table.TotalPotAmnt;
            NbPlayers = Rules.MaxPlayers;

            PotsAmount = table.Pots.Select(pot => pot.Amount).ToList();
            PotsAmount.AddRange(Enumerable.Repeat(0, Rules.MaxPlayers - table.Pots.Count));

            for (int i = 0; i < Rules.MaxPlayers; ++i)
            {
                PlayerInfo pi = new PlayerInfo() { NoSeat = i };
                Seats.Add(pi);

                PokerPlayer p = table.GetPlayer(i);

                pi.IsEmpty = (p == null);
                if (pi.IsEmpty)
                    continue;

                pi.PlayerName = p.Name;
                pi.Money = p.MoneySafeAmnt; 

                bool itsHim = (i == player.NoSeat);

                // Player cards
                GameCard[] holeCards = itsHim ? p.Cards : p.RelativeCards;
                for (int j = 0; j < 2; ++j)
                    pi.HoleCardIDs.Add(holeCards[j].Id);

                pi.IsDealer = table.NoSeatDealer == i;
                pi.IsSmallBlind = table.NoSeatSmallBlind == i;
                pi.IsBigBlind = table.NoSeatBigBlind == i;
                pi.IsCurrentPlayer = table.NoSeatCurrPlayer == i;
                pi.TimeRemaining = 0;
                pi.Bet = p.MoneyBetAmnt;
                pi.IsPlaying = p.IsPlaying;
            }
        }
    }
}
