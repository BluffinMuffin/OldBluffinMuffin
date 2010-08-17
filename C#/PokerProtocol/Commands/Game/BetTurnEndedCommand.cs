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
    public class BetTurnEndedCommand : AbstractCommand
    {
        protected override string CommandName
        {
            get { return COMMAND_NAME; }
        }
        public static string COMMAND_NAME = "gameBET_TURN_ENDED";

        private readonly TypeRound m_Round;
        private readonly List<int> m_PotsAmounts;

        public TypeRound Round
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
            m_Round = (TypeRound)int.Parse(argsToken.NextToken());
        }

        public BetTurnEndedCommand(List<int> potsAmounts, TypeRound round)
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
