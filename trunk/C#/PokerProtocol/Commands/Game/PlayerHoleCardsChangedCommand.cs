using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerProtocol.Commands.Lobby.Response;
using PokerWorld.Game;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Game
{
    public class PlayerHoleCardsChangedCommand : AbstractCommand
    {
        protected override string CommandName
        {
            get { return COMMAND_NAME; }
        }
        public static string COMMAND_NAME = "gameHOLE_CARDS_CHANGED";

        private readonly int m_PlayerPos;
        private readonly bool m_IsPlaying;
        private readonly List<int> m_CardsID;

        public int PlayerPos
        {
            get { return m_PlayerPos; }
        }
        public bool IsPlaying
        {
            get { return m_IsPlaying; }
        }

        public List<int> CardsID
        {
            get { return m_CardsID; }
        } 

        public PlayerHoleCardsChangedCommand(StringTokenizer argsToken)
        {
            m_PlayerPos = int.Parse(argsToken.NextToken());
            int count = 2;// int.Parse(argsToken.NextToken());
            m_CardsID = new List<int>();
            for (int i = 0; i < count; ++i)
            {
                m_CardsID.Add(int.Parse(argsToken.NextToken()));
            }
            m_IsPlaying = bool.Parse(argsToken.NextToken());
        }

        public PlayerHoleCardsChangedCommand(int pos, bool playing, params int[] cardsID)
        {
            m_CardsID = new List<int>(cardsID);
            m_PlayerPos = pos;
            m_IsPlaying = playing;
        }

        public PlayerHoleCardsChangedCommand(int pos, bool playing, List<int> cardsID)
        {
            m_CardsID = cardsID;
            m_PlayerPos = pos;
            m_IsPlaying = playing;
        }

        public override void Encode(StringBuilder sb)
        {
            Append(sb, m_PlayerPos);
            //Append(sb, m_CardsID.Count);
            foreach (int ci in m_CardsID)
                Append(sb, ci);
            Append(sb, m_IsPlaying);
        }
    }
}
