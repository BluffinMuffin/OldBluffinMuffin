using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using Com.Ericmas001.Util.Options;

namespace Com.Ericmas001.Game.Poker.DataTypes.Parameters
{
    public abstract class LimitOptions : IOption<LimitTypeEnum>
    {
        public abstract LimitTypeEnum OptionType { get; }
    }
}
