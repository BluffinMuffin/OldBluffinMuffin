using BluffinMuffin.Protocol.Commands;

namespace BluffinMuffin.Protocol.Server
{
    public class CommandEntry
    {
        public AbstractBluffinCommand Command { get; set; }
        public IBluffinClient Client { get; set; }
    }
}
