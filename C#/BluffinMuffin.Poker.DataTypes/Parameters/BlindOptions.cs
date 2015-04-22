using BluffinMuffin.Poker.DataTypes.Enums;
using Com.Ericmas001.Net.Protocol.Options;

namespace BluffinMuffin.Poker.DataTypes.Parameters
{
    public abstract class BlindOptions : IOption<BlindTypeEnum>
    {
        public int MoneyUnit { get; set; }
        public abstract BlindTypeEnum OptionType { get; }
    }
}
