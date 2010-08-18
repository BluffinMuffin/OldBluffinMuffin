using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PokerWorld.Game
{
    public class PlayerInfoEventArgs : EventArgs
    {
        private readonly PlayerInfo m_Player;
        public PlayerInfo Player { get { return m_Player; } }

        public PlayerInfoEventArgs(PlayerInfo p)
        {
            m_Player = p;
        }
    }
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
    public class RoundEventArgs : EventArgs
    {
        private readonly TypeRound m_Round;
        public TypeRound Round { get { return m_Round; } }

        public RoundEventArgs(TypeRound r)
        {
            m_Round = r;
        }
    }
    public class PlayerActionEventArgs : PlayerInfoEventArgs
    {
        private readonly TypeAction m_Action;
        private readonly int m_AmountPlayed;

        public TypeAction Action { get { return m_Action; } }
        public int AmountPlayed { get { return m_AmountPlayed; } }

        public PlayerActionEventArgs(PlayerInfo p, TypeAction action, int amnt)
            : base(p)
        {
            m_Action = action;
            m_AmountPlayed = amnt;
        }
    }
    public class PotWonEventArgs : PlayerInfoEventArgs
    {
        private readonly int m_Id;
        private readonly int m_AmountWon;

        public int Id { get { return m_Id; } }
        public int AmountWon { get { return m_AmountWon; } }

        public PotWonEventArgs(PlayerInfo p, int id, int amntWon)
            : base(p)
        {
            m_Id = id;
            m_AmountWon = amntWon;
        }
    }
}
