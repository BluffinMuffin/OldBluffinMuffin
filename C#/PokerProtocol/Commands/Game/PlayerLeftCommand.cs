using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Game
{
    public class PlayerLeftCommand : AbstractTextCommand
    {
        public static string COMMAND_NAME = "gamePLAYER_LEFT";

        private readonly int m_PlayerPos;

        public int PlayerPos
        {
            get { return m_PlayerPos; }
        }

        public PlayerLeftCommand(StringTokenizer argsToken)
        {
            m_PlayerPos = int.Parse(argsToken.NextToken());
        }

        public PlayerLeftCommand(int pos)
        {
            m_PlayerPos = pos;
        }

        public override void Encode(StringBuilder sb)
        {
            Append(sb, m_PlayerPos);
        }
    }
}
