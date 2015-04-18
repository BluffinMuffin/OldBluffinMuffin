using BluffinMuffin.Poker.DataTypes.Parameters;

namespace BluffinMuffin.Protocol.Commands.Lobby
{
    public class CreateTableCommand : AbstractLobbyCommand
    {
        public TableParams Params { get; set; }

        public string EncodeResponse(int id)
        {
            return Response(id).Encode();
        }
        public CreateTableResponse Response(int id)
        {
            return new CreateTableResponse(this) { IdTable = id };
        }
    }
}
