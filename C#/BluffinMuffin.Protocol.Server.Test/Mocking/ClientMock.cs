﻿using System;
using BluffinMuffin.Protocol.Server.DataTypes;

namespace BluffinMuffin.Protocol.Server.Test.Mocking
{
    public class ClientMock : IBluffinClient
    {
        private ServerMock m_Server;
        public ClientMock(ServerMock server)
        {
            m_Server = server;
            PlayerName = "SpongeBob";
        }

        public string PlayerName { get; set; }
        public void SendCommand(AbstractBluffinCommand command)
        {
            m_Server.ServerSendedCommands.Add(new CommandEntry() { Client = this, Command = command });
        }

        public void AddPlayer(RemotePlayer p)
        {
            throw new NotImplementedException();
        }

        public void RemovePlayer(RemotePlayer p)
        {
            throw new NotImplementedException();
        }
    }
}
