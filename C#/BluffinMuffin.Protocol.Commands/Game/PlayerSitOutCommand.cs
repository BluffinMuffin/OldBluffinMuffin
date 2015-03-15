using Com.Ericmas001.Net.Protocol.JSON;

namespace BluffinMuffin.Protocol.Commands.Game
{
    public class PlayerSitOutCommand : AbstractJsonCommand
    {
        public string EncodeResponse(bool success)
        {
            return new PlayerSitOutResponse(this) { Success = success }.Encode();
        }
    }
}
