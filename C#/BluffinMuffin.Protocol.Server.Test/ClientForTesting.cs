using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using Com.Ericmas001.Net.Protocol;

namespace BluffinMuffin.Protocol.Server.Test
{
    public class ClientForTesting : SimpleTcpClient
    {
        private BlockingCollection<RemoteTcpServer> m_Servers = new BlockingCollection<RemoteTcpServer>();
        public RemoteTcpServer ObtainTcpEntity()
        {
            return m_Servers.GetConsumingEnumerable().First();
        }
        public ClientForTesting() : base("127.0.0.1", 42084)
        {
        }

        protected override RemoteTcpEntity CreateServer(TcpClient tcpClient)
        {
            var server = new RemoteTcpServer(tcpClient);
            m_Servers.Add(server);
            m_Servers.CompleteAdding();
            return server;
        }

        protected override void OnServerConnected(RemoteTcpEntity client)
        {
        }

        protected override void OnServerDisconnected(RemoteTcpEntity client)
        {
        }
    }
}
