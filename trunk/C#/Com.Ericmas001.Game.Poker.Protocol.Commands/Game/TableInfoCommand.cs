using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Entities;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System.Linq;
using EricUtility.Games.CardGame;
using Com.Ericmas001.Game.Poker.DataTypes;

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
        public List<TupleSeat> Seats { get; set; }

        public TableInfoCommand()
        {
        }

        public TableInfoCommand(TableInfo table, PlayerInfo playerSendingTo)
        {
            BoardCardIDs = table.Cards.Select(c => c.Id).ToList();
            Seats = new List<TupleSeat>();

            Rules = table.Rules;

            TotalPotAmount = table.TotalPotAmnt;
            NbPlayers = Rules.MaxPlayers;

            PotsAmount = table.Pots.Select(pot => pot.Amount).ToList();
            PotsAmount.AddRange(Enumerable.Repeat(0, Rules.MaxPlayers - table.Pots.Count));

            for (int i = 0; i < Rules.MaxPlayers; ++i)
            {
                TupleSeat si = new TupleSeat() { NoSeat = i };
                Seats.Add(si);

                PlayerInfo p = table.GetPlayer(i);

                si.IsEmpty = (p == null);
                if (si.IsEmpty)
                    continue;
                si.Player = p.Clone();

                //If we are not sending the info about the player who is receiving, don't show the cards unless you can
                if (i != playerSendingTo.NoSeat)
                    si.Player.HoleCards = p.RelativeCards.ToList();

                if (si.Player.HoleCards.Count != 2)
                    si.Player.HoleCards = new List<GameCard>() { GameCard.NO_CARD, GameCard.NO_CARD };

                si.IsDealer = table.NoSeatDealer == i;
                si.IsSmallBlind = table.NoSeatSmallBlind == i;
                si.IsBigBlind = table.NoSeatBigBlind == i;
                si.IsCurrentPlayer = table.NoSeatCurrPlayer == i;
                si.IsPlaying = p.IsPlaying;
            }
        }
    }
}
