using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using Com.Ericmas001.Net.Protocol;

namespace BluffinMuffin.Protocol.Client
{
    public class BluffinTcpClient : SimpleTcpClient
    {
        public BluffinTcpClient(string ip, int port) : base(ip, port)
        {
        }

        protected override RemoteTcpEntity CreateServer(TcpClient tcpClient)
        {
            return new RemoteTcpClient(tcpClient);
        }

        protected override void OnServerConnected(RemoteTcpEntity client)
        {
            
        }

        protected override void OnServerDisconnected(RemoteTcpEntity client)
        {
            
        }
    }
}
