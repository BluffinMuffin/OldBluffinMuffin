using System;
using System.Collections.Generic;
using System.IO;
using System.Threading;
using EricUtility;
using EricUtility.Collections;
using EricUtility.Networking;
using EricUtility.Networking.Commands;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby;
using PokerWorld.Game;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Training;
using Com.Ericmas001.Game.Poker.Persistance;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Career;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Entities;
using PokerWorld.Game.Enums;
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
