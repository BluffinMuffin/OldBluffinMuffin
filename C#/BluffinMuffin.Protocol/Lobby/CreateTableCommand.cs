using BluffinMuffin.Protocol.DataTypes;

namespace BluffinMuffin.Protocol.Lobby
{
    public class CreateTableCommand : AbstractLobbyCommand
    {
        public TableParams Params { get; set; }

        public CreateTableResponse Response(int id)
        {
            return new CreateTableResponse(this) { IdTable = id };
        }
    }
}
