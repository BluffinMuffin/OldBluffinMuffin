using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Poker.DataTypes;
using Com.Ericmas001.Games;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BluffinMuffin.Protocol.Commands.Test.Comparing
{
    public static class CompareSeatInfo
    {
        public static void Compare(SeatInfo s, SeatInfo ds)
        {
            Assert.IsFalse(s.SerializableAttributes.Except(ds.SerializableAttributes).Any());
            Assert.AreEqual(s.SerializableAttributes.Length, ds.SerializableAttributes.Length);
            Assert.AreEqual(s.NoSeat, ds.NoSeat);
            ComparePlayerInfo.Compare(s.Player, ds.Player);
        }
    }
}
