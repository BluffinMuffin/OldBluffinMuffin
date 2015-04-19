using Com.Ericmas001.Net.Protocol.JSON;

namespace BluffinMuffin.Protocol.Commands.Game
{
    public class PlayerSitInResponse : AbstractBluffinReponse<PlayerSitInCommand>, IGameCommand
    {
        public int NoSeat { get; set; }

        public PlayerSitInResponse()
        {
        }

        public PlayerSitInResponse(PlayerSitInCommand command)
            : base(command)
        {
        }

        public int TableId 
        {
            get { return Command.TableId; }
            set { }
        }
    }
}
