using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using Com.Ericmas001.Net.Protocol;

namespace BluffinMuffin.Protocol.Server
{
    public class BluffinTcpServer : SimpleTcpServer
    {
        private readonly IBluffinServer m_BluffinServer;
        public BluffinTcpServer(int port, IBluffinServer bluffinServer)
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
