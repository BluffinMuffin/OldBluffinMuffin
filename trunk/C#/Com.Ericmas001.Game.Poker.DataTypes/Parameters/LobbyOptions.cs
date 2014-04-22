using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using Com.Ericmas001.Util.Options;

namespace Com.Ericmas001.Game.Poker.DataTypes.Parameters
{
    public abstract class LobbyOptions : IOption<LobbyTypeEnum>
    {
        public abstract LobbyTypeEnum OptionType { get; }
    }
}
