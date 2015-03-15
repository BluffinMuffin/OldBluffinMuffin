using System.Web;
using Newtonsoft.Json;

namespace BluffinMuffin.Protocol.Commands.Lobby
{
    public class GameCommand : AbstractLobbyCommand
    {
        public int TableId { get; set; }

        public string EncodedCommand { get; set; }

        [JsonIgnore]
        public string DecodedCommand 
        {
            get
            {
                return HttpUtility.UrlDecode(EncodedCommand);
            }
        }
    }
}
