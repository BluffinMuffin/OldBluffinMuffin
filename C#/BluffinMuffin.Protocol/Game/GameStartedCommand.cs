namespace BluffinMuffin.Protocol.Game
{
    public class GameStartedCommand : AbstractGameCommand
    {
        public int NeededBlind { get; set; }
    }
}
