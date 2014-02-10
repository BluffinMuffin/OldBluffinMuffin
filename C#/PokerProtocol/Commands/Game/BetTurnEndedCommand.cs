using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using PokerWorld.Game;
using PokerWorld.Game.Enums;

namespace PokerProtocol.Commands.Game
{
    public class BetTurnEndedCommand : AbstractCommand
    {
        public static string COMMAND_NAME = "gameBET_TURN_ENDED";

        private readonly RoundEnum m_Round;
        private readonly List<int> m_PotsAmounts;

        public RoundEnum Round
        {
            get { return m_Round; }
        }

        public List<int> PotsAmounts
        {
            get { return m_PotsAmounts; }
        } 

        public BetTurnEndedCommand(StringTokenizer argsToken)
        {
            int count = int.Parse(argsToken.NextToken());
            m_PotsAmounts = new List<int>();
            for (int i = 0; i < count; ++i)
            {
                m_PotsAmounts.Add(int.Parse(argsToken.NextToken()));
            }
            m_Round = (RoundEnum)int.Parse(argsToken.NextToken());
        }

        public BetTurnEndedCommand(List<int> potsAmounts, RoundEnum round)
        {
            m_PotsAmounts = potsAmounts;
            m_Round = round;
        }

        public override void Encode(StringBuilder sb)
        {
            Append(sb, m_PotsAmounts.Count);
            foreach( int pa in m_PotsAmounts )
                Append(sb, pa);
            Append(sb, (int)m_Round);
        }
    }
}
