using BluffinMuffin.Protocol.DataTypes.Enums;
using Com.Ericmas001.Net.Protocol.Options;

namespace BluffinMuffin.Protocol.DataTypes
{
    public abstract class LimitOptions : IOption<LimitTypeEnum>
    {
        public abstract LimitTypeEnum OptionType { get; }
    }
}
