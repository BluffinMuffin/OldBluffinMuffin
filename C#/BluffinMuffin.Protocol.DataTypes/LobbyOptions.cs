using BluffinMuffin.Protocol.DataTypes.Enums;
using Com.Ericmas001.Net.Protocol.Options;

namespace BluffinMuffin.Protocol.DataTypes
{
    public abstract class LobbyOptions : IOption<LobbyTypeEnum>
    {
        public abstract LobbyTypeEnum OptionType { get; }

        public abstract int MinimumAmountForBuyIn { get; }
        public abstract int MaximumAmountForBuyIn { get; }
    }
}
