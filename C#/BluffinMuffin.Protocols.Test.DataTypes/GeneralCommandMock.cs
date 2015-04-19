using BluffinMuffin.Protocol.Commands;

namespace BluffinMuffin.Protocols.Test.DataTypes
{
    public static class GeneralCommandMock
    {
        public static DisconnectCommand DisconnectCommand()
        {
            return new DisconnectCommand();
        }
    }
}
