using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby
{
    public class GameCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyGAME_COMMAND";

        private readonly int m_TableID;
        private readonly string m_Command;

        public int TableID
        {
            get { return m_TableID; }
        }

        public string Command
        {
            get { return m_Command; }
        } 

        public GameCommand(StringTokenizer argsToken)
        {
            m_TableID = int.Parse(argsToken.NextToken());
            m_Command = argsToken.NextToken();
        }

        public GameCommand(int p_tableID, string p_Command)
        {
            m_TableID = p_tableID;
            m_Command = p_Command;
        }

        public override void Encode(StringBuilder sb)
        {
            Append(sb, m_TableID);
            Append(sb, m_Command);
        }
    }
}
