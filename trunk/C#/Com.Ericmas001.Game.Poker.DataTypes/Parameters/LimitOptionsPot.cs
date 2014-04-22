using Com.Ericmas001.Game.Poker.DataTypes.Enums;

namespace Com.Ericmas001.Game.Poker.DataTypes.Parameters
{
    public class LimitOptionsPot : LimitOptions
    {
        public override LimitTypeEnum OptionType { get { return LimitTypeEnum.PotLimit; } }
    }
}
