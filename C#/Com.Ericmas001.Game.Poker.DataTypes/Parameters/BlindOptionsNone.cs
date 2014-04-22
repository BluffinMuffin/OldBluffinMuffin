using Com.Ericmas001.Game.Poker.DataTypes.Enums;

namespace Com.Ericmas001.Game.Poker.DataTypes.Parameters
{
    public class BlindOptionsNone : BlindOptions
    {
        public override BlindTypeEnum OptionType { get { return BlindTypeEnum.None; } }
    }
}