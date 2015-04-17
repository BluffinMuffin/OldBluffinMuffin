using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Protocol.Commands;
using Com.Ericmas001.Net.Protocol;
using Com.Ericmas001.Util;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace BluffinMuffin.Protocol.Client
{
    public class RemoteTcpClient : RemoteTcpEntity
    {
        public RemoteTcpClient(TcpClient remoteEntity)
            : base(remoteEntity)
        {
        }

        protected override void OnDataReceived(string data)
        {
            Console.WriteLine("[Received] {0}", data);
        }

        protected override void OnDataSent(string data)
        {
            Console.WriteLine("[Sent] {0}", data);
        }
    }
}
