using Com.Ericmas001.Game.Poker.DataTypes.Enums;

namespace Com.Ericmas001.Game.Poker.DataTypes.EventHandling
{
    public class PlayerActionEventArgs : PlayerInfoEventArgs
    {
        private readonly GameActionEnum m_Action;
        private readonly int m_AmountPlayed;

        public GameActionEnum Action { get { return m_Action; } }
        public int AmountPlayed { get { return m_AmountPlayed; } }

        public PlayerActionEventArgs(PlayerInfo p, GameActionEnum action, int amnt)
            : base(p)
        {
            m_Action = action;
            m_AmountPlayed = amnt;
        }
    }
}
