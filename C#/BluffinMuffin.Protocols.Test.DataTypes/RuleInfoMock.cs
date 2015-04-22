using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.Logic;

namespace BluffinMuffin.Protocols.Test.DataTypes
{
    public static class RuleInfoMock
    {
        public static RuleInfo[] GetAllRules()
        {
            return RuleFactory.SupportedRules;
        }
    }
}
