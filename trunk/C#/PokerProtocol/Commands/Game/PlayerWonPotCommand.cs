using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Game
{
    public class PlayerWonPotCommand : AbstractCommand
    {
        protected override string CommandName
        {
            get { return COMMAND_NAME; }
        }
        public static string COMMAND_NAME = "gamePLAYER_WON_POT";

        private readonly int m_PlayerPos;
        private readonly int m_PotID;
        private readonly int m_Shared;
        private readonly int m_PlayerMoney;

        public int PlayerPos
        {
            get { return m_PlayerPos; }
        }
        public int PotID
        {
            get { return m_PotID; }
        }
        public int Shared
        {
            get { return m_Shared; }
        }
        public int PlayerMoney
        {
            get { return m_PlayerMoney; }
        }

        public PlayerWonPotCommand(StringTokenizer argsToken)
        {
            m_PlayerPos = int.Parse(argsToken.NextToken());
            m_PotID = int.Parse(argsToken.NextToken());
            m_Shared = int.Parse(argsToken.NextToken());
            m_PlayerMoney = int.Parse(argsToken.NextToken());
        }

        public PlayerWonPotCommand(int pos, int potID, int shared, int money)
        {
            m_PlayerPos = pos;
            m_PotID = potID;
            m_Shared = shared;
            m_PlayerMoney = money;
        }

        public override void Encode(StringBuilder sb)
        {
            Append(sb, m_PlayerPos);
            Append(sb, m_PotID);
            Append(sb, m_Shared);
            Append(sb, m_PlayerMoney);
        }
    }
}
