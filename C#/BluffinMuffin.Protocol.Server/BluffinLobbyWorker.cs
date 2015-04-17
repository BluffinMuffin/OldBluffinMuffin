using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Poker.Persistance;
using BluffinMuffin.Protocol.Commands;
using BluffinMuffin.Protocol.Commands.Lobby.Training;
using Com.Ericmas001.Util;

namespace BluffinMuffin.Protocol.Server
{
    public class BluffinLobbyWorker
    {
        private KeyValuePair<Type, Action<AbstractBluffinCommand, IBluffinClient>>[] Methods;

        public IBluffinServer Server { get; private set; }
        public IBluffinLobby Lobby { get; private set; }
        public BluffinLobbyWorker(IBluffinServer server, IBluffinLobby lobby)
        {
            Server = server;
            Lobby = lobby;
            Methods = new[]
            {
                new KeyValuePair<Type, Action<AbstractBluffinCommand, IBluffinClient>>(typeof(AbstractBluffinCommand), OnCommandReceived), 
                new KeyValuePair<Type, Action<AbstractBluffinCommand, IBluffinClient>>(typeof(IdentifyCommand), OnIdentifyCommandReceived), 
            };
        }

        public void Start()
        {
            foreach (CommandEntry<AbstractBluffinCommand> entry in Server.LobbyCommands.GetConsumingEnumerable())
                Methods.Where(x => entry.Command.GetType().IsSubclassOf(x.Key) || x.Key == entry.Command.GetType()).ToList().ForEach(x => x.Value(entry.Command, entry.Client));
        }

        private void OnCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            LogManager.Log(LogLevel.MessageVeryLow, "ServerClientLobby.m_CommandObserver_CommandReceived", "Server RECV from {0} [{1}]", client.PlayerName, command.Encode());
        }

        void OnIdentifyCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            var c = (IdentifyCommand)command;
            client.PlayerName = c.Name;
            var ok = !Lobby.IsNameUsed(c.Name) && !DataManager.Persistance.IsDisplayNameExist(c.Name);
            LogManager.Log(LogLevel.Message, "ServerClientLobby.m_CommandObserver_IdentifyCommandReceived", "> Client indentifying training server as : {0}. Success={1}", c.Name, ok);
            client.SendCommand(c.Response(ok));
            if (ok)
                Lobby.AddName(c.Name);
        }
    }
}
