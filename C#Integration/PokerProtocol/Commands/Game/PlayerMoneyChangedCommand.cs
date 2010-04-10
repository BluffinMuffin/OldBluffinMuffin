using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EricUtility;
using PokerProtocol.Commands.Lobby.Response;
using PokerWorld.Game;

namespace PokerProtocol.Commands.Game
{
    public class PlayerMoneyChangedCommand : AbstractCommand
    {
        protected override string CommandName
        {
            get { return PlayerMoneyChangedCommand.COMMAND_NAME; }
        }
        public static string COMMAND_NAME = "gamePLAYER_MONEY_CHANGED";

        private readonly int m_PlayerPos;
        private readonly int m_PlayerMoney;

        public int PlayerPos
        {
            get { return m_PlayerPos; }
        }
        public int PlayerMoney
        {
            get { return m_PlayerMoney; }
        }

        public PlayerMoneyChangedCommand(StringTokenizer argsToken)
        {
            m_PlayerPos = int.Parse(argsToken.NextToken());
            m_PlayerMoney = int.Parse(argsToken.NextToken());
        }

        public PlayerMoneyChangedCommand(int pos, int money)
        {
            m_PlayerPos = pos;
            m_PlayerMoney = money;
        }

        public override void Encode(StringBuilder sb)
        {
            Append(sb, m_PlayerPos);
            Append(sb, m_PlayerMoney);
        }
    }
}
