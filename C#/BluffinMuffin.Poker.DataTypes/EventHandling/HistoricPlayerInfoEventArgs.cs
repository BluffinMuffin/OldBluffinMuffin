namespace BluffinMuffin.Poker.DataTypes.EventHandling
{
    public class HistoricPlayerInfoEventArgs : PlayerInfoEventArgs
    {
        private readonly PlayerInfo m_Last;
        public PlayerInfo Last { get { return m_Last; } }

        public HistoricPlayerInfoEventArgs(PlayerInfo p, PlayerInfo l)
            : base(p)
        {
            m_Last = l;
        }
    }
}
