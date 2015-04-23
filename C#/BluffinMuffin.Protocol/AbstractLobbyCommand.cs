namespace BluffinMuffin.Protocol
{
    public abstract class AbstractLobbyCommand : AbstractBluffinCommand
    {
        public override BluffinCommandEnum CommandType
        {
            get { return BluffinCommandEnum.Lobby; }
        }
    }
}
