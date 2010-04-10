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
    public class PlayerPlayMoneyCommand : AbstractCommand
    {
        protected override string CommandName
        {
            get { return PlayerPlayMoneyCommand.COMMAND_NAME; }
        }
        public static string COMMAND_NAME = "gamePLAY_MONEY";

        private readonly int m_Played;

        public int Played
        {
            get { return m_Played; }
        }

        public PlayerPlayMoneyCommand(StringTokenizer argsToken)
        {
            m_Played = int.Parse(argsToken.NextToken());
        }

        public PlayerPlayMoneyCommand(int played)
        {
            m_Played = played;
        }

        public override void Encode(StringBuilder sb)
        {
            Append(sb, m_Played);
        }
    }
}
