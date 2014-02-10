using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;
using PokerWorld.Game.Enums;

namespace PokerProtocol.Commands.Game
{
    public class BetTurnStartedCommand : AbstractCommand
    {
        public static string COMMAND_NAME = "gameBET_TURN_STARTED";

        private readonly RoundEnum m_Round;
        private readonly List<int> m_CardsID;

        public RoundEnum Round
        {
            get { return m_Round; }
        }

        public List<int> CardsID
        {
            get { return m_CardsID; }
        } 

        public BetTurnStartedCommand(StringTokenizer argsToken)
        {
            int count = 5;// int.Parse(argsToken.NextToken());
            m_CardsID = new List<int>();
            for (int i = 0; i < count; ++i)
            {
                m_CardsID.Add(int.Parse(argsToken.NextToken()));
            }
            m_Round = (RoundEnum)int.Parse(argsToken.NextToken());
        }

        public BetTurnStartedCommand(RoundEnum round,params int[] cardsID)
        {
            m_CardsID = new List<int>(cardsID);
            m_Round = round;
        }

        public BetTurnStartedCommand(RoundEnum round, List<int> cardsID)
        {
            m_CardsID = cardsID;
            m_Round = round;
        }

        public override void Encode(StringBuilder sb)
        {
            //Append(sb, m_CardsID.Count);
            foreach (int ci in m_CardsID)
                Append(sb, ci);
            Append(sb, (int)m_Round);
        }
    }
}
