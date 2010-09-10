using System;
using System.Collections.Generic;
using System.IO;
using System.Threading;
using EricUtility;
using EricUtility.Collections;
using EricUtility.Networking;
using EricUtility.Networking.Commands;
using PokerProtocol.Commands.Lobby;
using PokerWorld.Game;
using PokerProtocol.Commands.Lobby.Training;
using PokerWorld.Data;
using PokerProtocol.Commands.Lobby.Career;

namespace PokerProtocol
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
            Send(new IdentifyCommand(m_PlayerName));
            StringTokenizer token = ReceiveCommand(IdentifyResponse.COMMAND_NAME);
            if (!token.HasMoreTokens())
                return false;
            IdentifyResponse response = new IdentifyResponse(token);
            return response.OK;
        }

        
    }
}
