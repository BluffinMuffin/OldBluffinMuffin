using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using PokerWorld.Game;
using PokerWorld.Game.Enums;

namespace PokerProtocol.Commands.Game
{
    public class PlayerTurnEndedCommand : AbstractTextCommand
    {
        public static string COMMAND_NAME = "gamePLAYER_TURN_ENDED";

        private readonly int m_PlayerPos;
        private readonly int m_PlayerBet;
        private readonly int m_PlayerMoney;
        private readonly int m_TotalPot;
        private readonly GameActionEnum m_ActionType;
        private readonly int m_ActionAmount;
        private readonly bool m_IsPlaying;

        public int PlayerPos
        {
            get { return m_PlayerPos; }
        }
        public int PlayerBet
        {
            get { return m_PlayerBet; }
        }
        public int PlayerMoney
        {
            get { return m_PlayerMoney; }
        }
        public int TotalPot
        {
            get { return m_TotalPot; }
        }
        public GameActionEnum ActionType
        {
            get { return m_ActionType; }
        }
        public int ActionAmount
        {
            get { return m_ActionAmount; }
        }
        public bool IsPlaying
        {
            get { return m_IsPlaying; }
        }

        public PlayerTurnEndedCommand(StringTokenizer argsToken)
        {
            m_PlayerPos = int.Parse(argsToken.NextToken());
            m_PlayerBet = int.Parse(argsToken.NextToken());
            m_PlayerMoney = int.Parse(argsToken.NextToken());
            m_TotalPot = int.Parse(argsToken.NextToken());
            m_ActionType = (GameActionEnum)int.Parse(argsToken.NextToken());
            m_ActionAmount = int.Parse(argsToken.NextToken());
            m_IsPlaying = bool.Parse(argsToken.NextToken());
        }

        public PlayerTurnEndedCommand(int pos, int bet, int money, int totalPot, GameActionEnum actionType, int actionAmount, bool isPlaying)
        {
            m_PlayerPos = pos;
            m_PlayerBet = bet;
            m_PlayerMoney = money;
            m_TotalPot = totalPot;
            m_ActionType = actionType;
            m_ActionAmount = actionAmount;
            m_IsPlaying = isPlaying;
        }

        public override void Encode(StringBuilder sb)
        {
            Append(sb, m_PlayerPos);
            Append(sb, m_PlayerBet);
            Append(sb, m_PlayerMoney);
            Append(sb, m_TotalPot);
            Append(sb, (int)m_ActionType);
            Append(sb, m_ActionAmount);
            Append(sb, m_IsPlaying);
        }
    }
}
