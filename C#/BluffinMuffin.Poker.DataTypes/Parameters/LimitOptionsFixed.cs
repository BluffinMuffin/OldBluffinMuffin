using BluffinMuffin.Poker.DataTypes.Enums;

namespace BluffinMuffin.Poker.DataTypes.Parameters
{
    public class LimitOptionsFixed : LimitOptions
    {
        public override LimitTypeEnum OptionType { get { return LimitTypeEnum.FixedLimit; } }
    }
}
