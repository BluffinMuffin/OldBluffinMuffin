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
    public static class CompareLimitOptions
    {
        public static void Compare(LimitOptions l, LimitOptions dl)
        {
            Assert.AreEqual(l.GetType(), dl.GetType());
        }
    }
}
