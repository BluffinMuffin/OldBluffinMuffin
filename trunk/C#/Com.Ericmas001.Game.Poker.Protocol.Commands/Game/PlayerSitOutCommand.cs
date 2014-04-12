using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class PlayerSitOutCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gameSIT_OUT";

        public string EncodeResponse(bool success)
        {
            return new PlayerSitOutResponse(this) { Success = success }.Encode();
        }
    }
}
