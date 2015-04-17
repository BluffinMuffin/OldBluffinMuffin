namespace BluffinMuffin.Protocol.Commands
{
    public abstract class AbstractBluffinReponse<T> : AbstractBluffinCommand
        where T : AbstractBluffinCommand
    {
        private T Command { get; set; }

        public override BluffinCommandEnum CommandType
        {
            get { return Command.CommandType; }
        }

        public AbstractBluffinReponse()
        {
        }

        public AbstractBluffinReponse(T command)
        {
            Command = command;
        }
    }
}
