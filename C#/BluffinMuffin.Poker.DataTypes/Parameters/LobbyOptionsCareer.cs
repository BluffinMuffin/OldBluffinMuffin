using BluffinMuffin.Poker.DataTypes.Enums;

namespace BluffinMuffin.Poker.DataTypes.Parameters
{
    public class LobbyOptionsCareer : LobbyOptions
    {
        public override LobbyTypeEnum OptionType
        {
            get { return LobbyTypeEnum.Career; }
        }
    }
}
