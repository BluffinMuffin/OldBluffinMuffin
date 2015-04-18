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
    public static class CompareBlindOptions
    {
        public static void Compare(BlindOptions b, BlindOptions db)
        {
            Assert.AreEqual(b.GetType(), db.GetType());
            Assert.AreEqual(b.MoneyUnit, db.MoneyUnit);

            if (b.GetType() == typeof (BlindOptionsAnte))
            {
                var ba = (BlindOptionsAnte)b;
                var dba = (BlindOptionsAnte)db;
                Assert.AreEqual(ba.AnteAmount, dba.AnteAmount);
            }
            else if (b.GetType() == typeof (BlindOptionsBlinds))
            {
                var bb = (BlindOptionsBlinds)b;
                var dbb = (BlindOptionsBlinds)db;
                Assert.AreEqual(bb.SmallBlindAmount, dbb.SmallBlindAmount);
                Assert.AreEqual(bb.BigBlindAmount, dbb.BigBlindAmount);
            }
        }
    }
}
