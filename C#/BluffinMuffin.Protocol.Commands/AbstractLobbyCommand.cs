using Com.Ericmas001.Net.Protocol.JSON;

namespace BluffinMuffin.Protocol.Commands
{
    public abstract class AbstractLobbyCommand : AbstractBluffinCommand
    {
        public override BluffinCommandEnum CommandType
        {
            get { return BluffinCommandEnum.Lobby; }
        }
    }
}
