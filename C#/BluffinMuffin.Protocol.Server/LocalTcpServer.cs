﻿using System.Net.Sockets;
using BluffinMuffin.Protocol.Server.DataTypes;
using Com.Ericmas001.Net.Protocol;

namespace BluffinMuffin.Protocol.Server
{
    public class LocalTcpServer : SimpleTcpServer
    {
        private readonly IBluffinServer m_BluffinServer;
        public LocalTcpServer(int port, IBluffinServer bluffinServer)
            : base(port)
        {
            m_BluffinServer = bluffinServer;
        }

        protected override RemoteTcpEntity CreateClient(TcpClient tcpClient)
        {
            return new RemoteTcpClient(tcpClient, m_BluffinServer);
        }

        protected override void OnClientConnected(RemoteTcpEntity client)
        {
        }

        protected override void OnClientDisconnected(RemoteTcpEntity client)
        {
            ((RemoteTcpClient)client).OnConnectionLost();
        }
    }
}
