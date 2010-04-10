using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EricUtility;
using PokerProtocol.Commands.Lobby.Response;
using PokerWorld.Game;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Game
{
    public class PlayerTurnBeganCommand : AbstractCommand
    {
        protected override string CommandName
        {
            get { return PlayerTurnBeganCommand.COMMAND_NAME; }
        }
        public static string COMMAND_NAME = "gamePLAYER_TURN_BEGAN";

        private readonly int m_PlayerPos;
        private readonly int m_LastPlayerNoSeat;

        public int PlayerPos
        {
            get { return m_PlayerPos; }
        }
        public int LastPlayerNoSeat
        {
            get { return m_LastPlayerNoSeat; }
        }

        public PlayerTurnBeganCommand(StringTokenizer argsToken)
        {
            m_PlayerPos = int.Parse(argsToken.NextToken());
            m_LastPlayerNoSeat = int.Parse(argsToken.NextToken());
        }

        public PlayerTurnBeganCommand(int pos, int last)
        {
            m_PlayerPos = pos;
            m_LastPlayerNoSeat = last;
        }

        public override void Encode(StringBuilder sb)
        {
            Append(sb, m_PlayerPos);
            Append(sb, m_LastPlayerNoSeat);
        }
    }
}
