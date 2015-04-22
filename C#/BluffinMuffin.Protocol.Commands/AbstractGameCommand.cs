namespace BluffinMuffin.Protocol.Commands
{
    public abstract class AbstractGameCommand : AbstractBluffinCommand, IGameCommand
    {
        public override BluffinCommandEnum CommandType
        {
            get { return BluffinCommandEnum.Game; }
        }

        public int TableId { get; set; }
    }
}
