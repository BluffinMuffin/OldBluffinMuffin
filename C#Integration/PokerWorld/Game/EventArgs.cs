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
    public class RoundEventArgs : EventArgs
    {
        private readonly TypeRound m_Round;
        public TypeRound Round { get { return m_Round; } }

        public RoundEventArgs(TypeRound r)
        {
            m_Round = r;
        }
    }
    public class PlayerActionEventArgs : EventArgs
    {
        private readonly PlayerInfo m_Player;
        private readonly TypeAction m_Action;
        private readonly int m_AmountPlayed;

        public PlayerInfo Player { get { return m_Player; } }
        public TypeAction Action { get { return m_Action; } }
        public int AmountPlayed { get { return m_AmountPlayed; } }

        public PlayerActionEventArgs(PlayerInfo p, TypeAction action, int amnt)
        {
            m_Player = p;
            m_Action = action;
            m_AmountPlayed = amnt;
        }
    }
    public class PotWonEventArgs : EventArgs
    {
        private readonly PlayerInfo m_Player;
        private readonly int m_Id;
        private readonly int m_AmountWon;

        public PlayerInfo Player { get { return m_Player; } }
        public int Id { get { return m_Id; } }
        public int AmountWon { get { return m_AmountWon; } }

        public PotWonEventArgs(PlayerInfo p, int id, int amntWon)
        {
            m_Player = p;
            m_Id = id;
            m_AmountWon = amntWon;
        }
    }
}
