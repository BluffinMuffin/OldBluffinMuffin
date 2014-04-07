using System;
using System.Collections.Generic;
using System.IO;
using System.Threading;
using Com.Ericmas001;
using Com.Ericmas001.Collections;
using Com.Ericmas001.Net;
using Com.Ericmas001.Net.Protocol.JSON;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Training;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Career;
using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using Newtonsoft.Json.Linq;

namespace Com.Ericmas001.Game.Poker.Protocol.Client
{
    public class LobbyTCPClientTraining : LobbyTCPClient
    {
        public LobbyTCPClientTraining(string serverAddress, int serverPort)
            : base(serverAddress, serverPort)
        {
        }

        public bool Identify(string name)
        {
            m_PlayerName = name;

            Send(new IdentifyCommand() { Name = m_PlayerName });

            return WaitAndReceive<IdentifyResponse>().OK;
        }
    }
}
