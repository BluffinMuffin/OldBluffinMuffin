using Com.Ericmas001.Net.Protocol.JSON;

namespace BluffinMuffin.Protocol.Commands.Game
{
    public class PlayerSitOutResponse : AbstractBluffinReponse<PlayerSitOutCommand>,IGameCommand
    {
        public bool Success { get; set; }

        public PlayerSitOutResponse()
        {
        }

        public PlayerSitOutResponse(PlayerSitOutCommand command)
            : base(command)
        {
        }

        public int TableId
        {
            get { return Command.TableId; }
            set {  }
        }
    }
}
