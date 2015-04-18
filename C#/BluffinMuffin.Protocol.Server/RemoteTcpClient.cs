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

namespace BluffinMuffin.Protocol.Server
{
    public class RemoteTcpClient : RemoteTcpEntity, IBluffinClient
    {
        private readonly IBluffinServer m_BluffinServer;

        public string PlayerName { get; set; }

        public RemoteTcpClient(TcpClient remoteEntity, IBluffinServer bluffinServer)
            : base(remoteEntity)
        {
            m_BluffinServer = bluffinServer;
        }

        protected override void OnDataReceived(string data)
        {
            if(!String.IsNullOrEmpty(data))
                m_BluffinServer.OnCommandReceived(AbstractBluffinCommand.DeserializeCommand(data), this);
        }

        protected override void OnDataSent(string data)
        {
            Console.WriteLine("[Sent] {0}", data);
        }

        public void OnConnectionLost()
        {
            m_BluffinServer.OnCommandReceived(new DisconnectCommand(), this);
        }

        public void SendCommand(AbstractBluffinCommand command)
        {
            string line = command.Encode();
            LogManager.Log(LogLevel.MessageVeryLow, "ServerClientLobby.Send", "Server SEND to {0} [{1}]", PlayerName, line);
            Send(line);
        }
    }
}
