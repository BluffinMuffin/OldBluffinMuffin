namespace BluffinMuffin.Protocol
{
    public class DisconnectCommand : AbstractBluffinCommand
    {
        public override BluffinCommandEnum CommandType
        {
            get { return BluffinCommandEnum.General; }
        }
    }
}
