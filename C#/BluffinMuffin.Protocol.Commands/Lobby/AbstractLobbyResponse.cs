using Com.Ericmas001.Net.Protocol.JSON;

namespace BluffinMuffin.Protocol.Commands.Lobby
{
    public abstract class AbstractLobbyResponse<T>: AbstractJsonCommandResponse<T>
        where T : AbstractLobbyCommand
    {
        public AbstractLobbyResponse()
        {
        }
        public AbstractLobbyResponse(T command)
            : base(command)
        {
        }
    }
}
