using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Game
{
    public class PlayerTurnBeganCommand : AbstractCommand
    {
        public static string COMMAND_NAME = "gamePLAYER_TURN_BEGAN";

        private readonly int m_PlayerPos;
        private readonly int m_LastPlayerNoSeat;
        private readonly int m_MinimumRaise;

        public int PlayerPos
        {
            get { return m_PlayerPos; }
        }
        public int LastPlayerNoSeat
        {
            get { return m_LastPlayerNoSeat; }
        }
        public int MinimumRaise
        {
            get { return m_MinimumRaise; }
        }

        public PlayerTurnBeganCommand(StringTokenizer argsToken)
        {
            m_PlayerPos = int.Parse(argsToken.NextToken());
            m_LastPlayerNoSeat = int.Parse(argsToken.NextToken());
            m_MinimumRaise = int.Parse(argsToken.NextToken());
        }

        public PlayerTurnBeganCommand(int pos, int last, int minimumRaise)
        {
            m_PlayerPos = pos;
            m_LastPlayerNoSeat = last;
            m_MinimumRaise = minimumRaise;
        }

        public override void Encode(StringBuilder sb)
        {
            Append(sb, m_PlayerPos);
            Append(sb, m_LastPlayerNoSeat);
            Append(sb, m_MinimumRaise);
        }
    }
}
