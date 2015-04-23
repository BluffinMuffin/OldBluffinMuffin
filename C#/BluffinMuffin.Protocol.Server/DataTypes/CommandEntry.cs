namespace BluffinMuffin.Protocol.Server.DataTypes
{
    public class CommandEntry
    {
        public AbstractBluffinCommand Command { get; set; }
        public IBluffinClient Client { get; set; }
    }
}
