using BluffinMuffin.Protocol.DataTypes.Enums;
using Com.Ericmas001.Net.Protocol.Options;

namespace BluffinMuffin.Protocol.DataTypes
{
    public abstract class BlindOptions : IOption<BlindTypeEnum>
    {
        public int MoneyUnit { get; set; }
        public abstract BlindTypeEnum OptionType { get; }
    }
}
