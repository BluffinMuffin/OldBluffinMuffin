using BluffinMuffin.Poker.DataTypes.Enums;

namespace BluffinMuffin.Poker.DataTypes.Parameters
{
    public class LimitOptionsNoLimit : LimitOptions
    {
        public override LimitTypeEnum OptionType { get { return LimitTypeEnum.NoLimit; } }
    }
}
