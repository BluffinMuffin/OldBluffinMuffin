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
    public static class CompareGameCard
    {
        public static void Compare(GameCard c, GameCard dc)
        {
            Assert.AreEqual(c.Kind, dc.Kind);
            Assert.AreEqual(c.Value, dc.Value);
        }
    }
}
