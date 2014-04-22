using Com.Ericmas001.Game.Poker.DataTypes.Enums;

namespace Com.Ericmas001.Game.Poker.DataTypes.Parameters
{
    public class BlindOptionsAnte : BlindOptions
    {
        public override BlindTypeEnum OptionType { get { return BlindTypeEnum.Antes; } }
        public int AnteAmount { get { return MoneyUnit; } }
    }
}
