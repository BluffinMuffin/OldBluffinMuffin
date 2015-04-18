using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Poker.DataTypes;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BluffinMuffin.Protocol.Commands.Test.Comparing
{
    public static class CompareTupleTable
    {
        public static void Compare(TupleTable t, TupleTable dt)
        {
            Assert.AreEqual(t.BigBlind, dt.BigBlind);
            Assert.AreEqual(t.IdTable, dt.IdTable);
            Assert.AreEqual(t.NbPlayers, dt.NbPlayers);
            Assert.AreEqual(t.PossibleAction, dt.PossibleAction);
            CompareTableParams.Compare(t.Params,dt.Params);
        }
    }
}
