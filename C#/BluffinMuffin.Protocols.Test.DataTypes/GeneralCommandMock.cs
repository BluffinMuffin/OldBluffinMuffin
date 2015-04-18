using System.Linq;
using BluffinMuffin.Protocol.Commands;
using BluffinMuffin.Protocol.Commands.Lobby;
using BluffinMuffin.Protocol.Commands.Lobby.Career;
using BluffinMuffin.Protocol.Commands.Lobby.Training;
using BluffinMuffin.Poker.DataTypes.Enums;

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
