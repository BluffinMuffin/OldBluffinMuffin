using Com.Ericmas001.Game.Poker.DataTypes.Parameters;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby
{
    public class CreateTableCommand : AbstractLobbyCommand
    {
        public TableParams Params { get; set; }

        public string EncodeResponse(int id)
        {
            return new CreateTableResponse(this) { IdTable = id }.Encode();
        }
    }
}
