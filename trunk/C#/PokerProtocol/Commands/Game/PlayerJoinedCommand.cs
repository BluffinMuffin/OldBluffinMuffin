using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Game
{
    public class PlayerJoinedCommand : AbstractTextCommand
    {
        public static string COMMAND_NAME = "gamePLAYER_JOINED";

        private readonly int m_PlayerPos;
        private readonly int m_PlayerMoney;
        private readonly string m_PlayerName;

        public int PlayerPos
        {
            get { return m_PlayerPos; }
        }
        public int PlayerMoney
        {
            get { return m_PlayerMoney; }
        }
        public string PlayerName
        {
            get { return m_PlayerName; }
        }

        public PlayerJoinedCommand(StringTokenizer argsToken)
        {
            m_PlayerPos = int.Parse(argsToken.NextToken());
            m_PlayerName = argsToken.NextToken();
            m_PlayerMoney = int.Parse(argsToken.NextToken());
        }

        public PlayerJoinedCommand(int pos, string name, int money)
        {
            m_PlayerPos = pos;
            m_PlayerName = name;
            m_PlayerMoney = money;
        }

        public override void Encode(StringBuilder sb)
        {
            Append(sb, m_PlayerPos);
            Append(sb, m_PlayerName);
            Append(sb, m_PlayerMoney);
        }
    }
}
