namespace BluffinMuffin.Protocol
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
