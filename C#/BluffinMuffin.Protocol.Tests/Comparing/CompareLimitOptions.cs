using BluffinMuffin.Protocol.DataTypes;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BluffinMuffin.Protocol.Tests.Comparing
{
    public static class CompareLimitOptions
    {
        public static void Compare(LimitOptions l, LimitOptions dl)
        {
            Assert.AreEqual(l.GetType(), dl.GetType());
        }
    }
}
