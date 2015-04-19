namespace BluffinMuffin.Protocol.Commands.Game
{
    public class GameStartedCommand : AbstractGameCommand
    {
        public int NeededBlind { get; set; }
    }
}
