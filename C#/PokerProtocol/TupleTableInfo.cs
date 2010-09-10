using System;
using System.Collections.Generic;
using System.Text;
using PokerWorld.Game;
using EricUtility;

namespace PokerProtocol
{
    public class TupleTableInfo : IComparable<TupleTableInfo>
    {
        private int m_NoPort;
        private string m_TableName;
        private int m_BigBlind;
        private int m_NbPlayers;
        private int m_NbSeats;
        private TypeBet m_Limit;

        public int NoPort
        {
            get { return m_NoPort; }
            set { m_NoPort = value; }
        }

        public string TableName
        {
            get { return m_TableName; }
            set { m_TableName = value; }
        }

        public int BigBlind
        {
            get { return m_BigBlind; }
            set { m_BigBlind = value; }
        }

        public int NbPlayers
        {
            get { return m_NbPlayers; }
            set { m_NbPlayers = value; }
        }

        public int NbSeats
        {
            get { return m_NbSeats; }
            set { m_NbSeats = value; }
        }

        public TypeBet Limit
        {
            get { return m_Limit; }
            set { m_Limit = value; }
        }

        public TupleTableInfo(int p_noPort, String p_tableName, int p_bigBlind, int p_nbPlayers, int p_nbSeats, TypeBet limit)
        {
            m_NoPort = p_noPort;
            m_TableName = p_tableName;
            m_BigBlind = p_bigBlind;
            m_NbPlayers = p_nbPlayers;
            m_NbSeats = p_nbSeats;
            m_Limit = limit;
        }

        public TupleTableInfo(StringTokenizer argsToken)
        {
            m_NoPort = int.Parse(argsToken.NextToken());
            m_TableName = argsToken.NextToken();
            m_BigBlind = int.Parse(argsToken.NextToken());
            m_NbPlayers = int.Parse(argsToken.NextToken());
            m_NbSeats = int.Parse(argsToken.NextToken());
            m_Limit = (TypeBet)int.Parse(argsToken.NextToken());
        }
        public int CompareTo(TupleTableInfo other)
        {
            return m_NoPort.CompareTo(other.m_NoPort);
        }

        public string ToString(char p_delimiter)
        {
            StringBuilder sb = new StringBuilder();
            sb.Append(m_NoPort);
            sb.Append(p_delimiter);
            sb.Append(m_TableName);
            sb.Append(p_delimiter);
            sb.Append(m_BigBlind);
            sb.Append(p_delimiter);
            sb.Append(m_NbPlayers);
            sb.Append(p_delimiter);
            sb.Append(m_NbSeats);
            sb.Append(p_delimiter);
            sb.Append((int)m_Limit);

            return sb.ToString();
        }
    }
}
