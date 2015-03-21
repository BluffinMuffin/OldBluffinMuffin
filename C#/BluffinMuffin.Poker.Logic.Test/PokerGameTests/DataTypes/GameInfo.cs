using System.Linq;
using BluffinMuffin.Poker.DataTypes;

namespace BluffinMuffin.Poker.Logic.Test.PokerGameTests.DataTypes
{
    public class GameInfo
    {
        public PokerGame Game { get; set; }
        public PlayerInfo P1 { get; set; }
        public PlayerInfo P2 { get; set; }
        public PlayerInfo P3 { get; set; }

        public PlayerInfo CurrentPlayer { get { return Game.Table.CurrentPlayer; } }
        public PlayerInfo PoorestPlayer { get { return Game.Table.Players.OrderBy(x => x.MoneySafeAmnt).First(); } }


        public bool CurrentPlayerPlays(int amount)
        {
            return Game.PlayMoney(CurrentPlayer, amount);
        }
        public bool CurrentPlayerChecks()
        {
            return CurrentPlayerPlays(0);
        }
        public bool CurrentPlayerFolds()
        {
            return CurrentPlayerPlays(-1);
        }
        public bool CurrentPlayerCalls()
        {
            return CurrentPlayerPlays(Game.Table.CallAmnt(CurrentPlayer));
        }
        public bool CurrentPlayerRaisesMinimum()
        {
            return CurrentPlayerPlays(Game.Table.MinRaiseAmnt(CurrentPlayer));
        }
        public PlayerInfo SitInGame(PlayerInfo p)
        {
            Game.JoinGame(p);
            Game.GameTable.AskToSitIn(p, -1);
            Game.SitIn(p);
            return p;
        }

        public int BlindNeeded(PlayerInfo p)
        {
            return Game.GameTable.GetBlindNeeded(p);
        }
        public void PutBlinds(PlayerInfo p)
        {
            Game.PlayMoney(p, BlindNeeded(p));
        }
    }
}
