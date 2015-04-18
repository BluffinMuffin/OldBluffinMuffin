using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.DataTypes.Parameters;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BluffinMuffin.Protocol.Commands.Test.Comparing
{
    public static class CompareLobbyOptions
    {
        public static void Compare(LobbyOptions l, LobbyOptions dl)
        {
            Assert.AreEqual(l.GetType(), dl.GetType());
            Assert.AreEqual(l.MaximumAmountForBuyIn, dl.MaximumAmountForBuyIn);
            Assert.AreEqual(l.MinimumAmountForBuyIn, dl.MinimumAmountForBuyIn);

            if (l.GetType() == typeof (LobbyOptionsCareer))
            {
                var lc = (LobbyOptionsCareer)l;
                var dlc = (LobbyOptionsCareer)dl;
                Assert.AreEqual(lc.IsMaximumBuyInLimited, dlc.IsMaximumBuyInLimited);
                Assert.AreEqual(lc.MoneyUnit, dlc.MoneyUnit);
            }
            else if (l.GetType() == typeof(LobbyOptionsTraining))
            {
                var lt = (LobbyOptionsCareer)l;
                var dlt = (LobbyOptionsCareer)dl;
                Assert.AreEqual(lt.IsMaximumBuyInLimited, dlt.IsMaximumBuyInLimited);
                Assert.AreEqual(lt.MoneyUnit, dlt.MoneyUnit);
            }
        }
    }
}
