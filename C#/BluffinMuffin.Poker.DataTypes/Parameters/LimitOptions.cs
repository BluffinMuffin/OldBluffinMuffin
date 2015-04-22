using BluffinMuffin.Poker.DataTypes.Enums;
using Com.Ericmas001.Net.Protocol.Options;

namespace BluffinMuffin.Poker.DataTypes.Parameters
{
    public abstract class LimitOptions : IOption<LimitTypeEnum>
    {
        public abstract LimitTypeEnum OptionType { get; }
    }
}
