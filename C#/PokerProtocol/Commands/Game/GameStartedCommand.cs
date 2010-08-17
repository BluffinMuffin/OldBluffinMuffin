using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Game
{
    public class GameStartedCommand : AbstractCommand
    {
        protected override string CommandName
        {
            get { return COMMAND_NAME; }
        }
        public static string COMMAND_NAME = "gameSTARTED";

        private readonly int m_NoSeatD;
        private readonly int m_NoSeatSB;
        private readonly int m_NoSeatBB;

        public int NoSeatD
        {
            get { return m_NoSeatD; }
        }
        public int NoSeatSB
        {
            get { return m_NoSeatSB; }
        }
        public int NoSeatBB
        {
            get { return m_NoSeatBB; }
        } 

        public GameStartedCommand(StringTokenizer argsToken)
        {
            m_NoSeatD = int.Parse(argsToken.NextToken());
            m_NoSeatSB = int.Parse(argsToken.NextToken());
            m_NoSeatBB = int.Parse(argsToken.NextToken());
        }

        public GameStartedCommand(int noSeatD, int noSeatSB, int noSeatBB)
        {
            m_NoSeatD = noSeatD;
            m_NoSeatSB = noSeatSB;
            m_NoSeatBB = noSeatBB;
        }

        public override void Encode(StringBuilder sb)
        {
            Append(sb, m_NoSeatD);
            Append(sb, m_NoSeatSB);
            Append(sb, m_NoSeatBB);
        }
    }
}
