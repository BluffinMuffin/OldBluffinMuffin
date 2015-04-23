namespace BluffinMuffin.Protocol.Game
{
    public class PlayerJoinedCommand : AbstractGameCommand
    {
        public string PlayerName { get; set; }
    }
}
