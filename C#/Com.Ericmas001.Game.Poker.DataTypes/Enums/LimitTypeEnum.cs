using System.ComponentModel;

namespace Com.Ericmas001.Game.Poker.DataTypes.Enums
{
    public enum LimitTypeEnum
    {
        [Description("No Limit")]
        NoLimit,

        [Description("Fixed Limit")]
        FixedLimit,

        [Description("Pot Limit")]
        PotLimit
    }
}
