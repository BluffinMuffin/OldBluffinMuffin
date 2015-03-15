using BluffinMuffin.Poker.DataTypes.Enums;

namespace BluffinMuffin.Poker.DataTypes.Parameters
{
    public class LimitOptionsPot : LimitOptions
    {
        public override LimitTypeEnum OptionType { get { return LimitTypeEnum.PotLimit; } }
    }
}
