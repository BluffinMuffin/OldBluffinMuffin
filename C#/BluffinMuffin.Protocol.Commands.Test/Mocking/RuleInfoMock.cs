using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.Logic;

namespace BluffinMuffin.Protocol.Commands.Test.Mocking
{
    public static class RuleInfoMock
    {
        public static RuleInfo[] GetAllRules()
        {
            return RuleFactory.SupportedRules;
        }
    }
}
