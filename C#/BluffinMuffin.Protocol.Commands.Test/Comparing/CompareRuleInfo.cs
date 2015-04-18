using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Poker.DataTypes;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BluffinMuffin.Protocol.Commands.Test.Comparing
{
    public static class CompareRuleInfo
    {
        public static void Compare(RuleInfo r, RuleInfo dr)
        {
            Assert.IsFalse(r.AvailableBlinds.Except(dr.AvailableBlinds).Any());
            Assert.IsFalse(r.AvailableLimits.Except(dr.AvailableLimits).Any());
            Assert.IsFalse(r.AvailableLobbys.Except(dr.AvailableLobbys).Any());
            Assert.AreEqual(r.AvailableBlinds.Count, dr.AvailableBlinds.Count);
            Assert.AreEqual(r.AvailableLimits.Count, dr.AvailableLimits.Count);
            Assert.AreEqual(r.AvailableLobbys.Count, dr.AvailableLobbys.Count);
            Assert.AreEqual(r.CanConfigWaitingTime, dr.CanConfigWaitingTime);
            Assert.AreEqual(r.DefaultBlind, dr.DefaultBlind);
            Assert.AreEqual(r.DefaultLimit, dr.DefaultLimit);
            Assert.AreEqual(r.GameType, dr.GameType);
            Assert.AreEqual(r.MaxPlayers, dr.MaxPlayers);
            Assert.AreEqual(r.MinPlayers, dr.MinPlayers);
            Assert.AreEqual(r.Name, dr.Name);
        }
    }
}
