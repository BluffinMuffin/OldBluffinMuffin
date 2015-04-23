using Com.Ericmas001.Games;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BluffinMuffin.Protocol.Tests.Comparing
{
    public static class CompareGameCard
    {
        public static void Compare(GameCard c, GameCard dc)
        {
            Assert.AreEqual(c.Kind, dc.Kind);
            Assert.AreEqual(c.Value, dc.Value);
        }
    }
}
