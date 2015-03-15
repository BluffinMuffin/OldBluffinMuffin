using BluffinMuffin.Poker.DataTypes.Enums;
using Com.Ericmas001.Util.Options;

namespace BluffinMuffin.Poker.DataTypes.Parameters
{
    public abstract class LobbyOptions : IOption<LobbyTypeEnum>
    {
        public abstract LobbyTypeEnum OptionType { get; }
    }
}
