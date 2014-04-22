using Com.Ericmas001.Net.Protocol.JSON;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby
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
