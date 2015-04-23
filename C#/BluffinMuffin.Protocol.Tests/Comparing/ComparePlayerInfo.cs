using BluffinMuffin.Protocol.DataTypes;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BluffinMuffin.Protocol.Tests.Comparing
{
    public static class ComparePlayerInfo
    {
        public static void Compare(PlayerInfo p, PlayerInfo dp)
        {
            Assert.AreEqual(p.HoleCards.Length, dp.HoleCards.Length);
            for(int i = 0; i < p.HoleCards.Length; ++i)
                CompareGameCard.Compare(p.HoleCards[i],dp.HoleCards[i]);

            Assert.AreEqual(p.State, dp.State);
            Assert.AreEqual(p.NoSeat, dp.NoSeat);
            Assert.AreEqual(p.Name, dp.Name);
            Assert.AreEqual(p.IsShowingCards, dp.IsShowingCards);
            Assert.AreEqual(p.MoneyBetAmnt, dp.MoneyBetAmnt);
            Assert.AreEqual(p.MoneySafeAmnt, dp.MoneySafeAmnt);
        }
    }
}
