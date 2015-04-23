using BluffinMuffin.Protocol.DataTypes;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BluffinMuffin.Protocol.Tests.Comparing
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
