using System.ComponentModel;

namespace BluffinMuffin.Poker.DataTypes.Enums
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
