using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EricUtility;
using EricUtility.Games;
using PokerProtocol.Commands.Lobby.Response;
using PokerWorld.Game;

namespace PokerProtocol.Commands.Game
{
    public class TableInfoCommand : AbstractCommand
    {
        protected override string CommandName
        {
            get { return TableInfoCommand.COMMAND_NAME; }
        }
        public static string COMMAND_NAME = "gameTABLE_INFO";

        private readonly int m_TotalPotAmount;
        private readonly int m_NbSeats;
        private readonly List<int> m_PotsAmount;
        private readonly List<int> m_BoardCardIDs;
        private readonly int m_NbPlayers;
        private readonly List<TuplePlayerInfo> m_Seats;
        private readonly TypeBet m_Limit;

        public int TotalPotAmount
        {
            get { return m_TotalPotAmount; }
        }
        public int NbSeats
        {
            get { return m_NbSeats; }
        }
        public List<int> PotsAmount
        {
            get { return m_PotsAmount; }
        }
        public List<int> BoardCardIDs
        {
            get { return m_BoardCardIDs; }
        }
        public int NbPlayers
        {
            get { return m_NbPlayers; }
        }
        public List<TuplePlayerInfo> Seats
        {
            get { return m_Seats; }
        }
        public TypeBet Limit
        {
            get { return m_Limit; }
        }

        public TableInfoCommand(StringTokenizer argsToken)
        {
            m_PotsAmount = new List<int>();
            m_BoardCardIDs = new List<int>();
            m_Seats = new List<TuplePlayerInfo>();

            m_TotalPotAmount = int.Parse(argsToken.NextToken());
            m_NbSeats = int.Parse(argsToken.NextToken());
            for (int i = 0; i < m_NbSeats; ++i)
            {
                m_PotsAmount.Add(int.Parse(argsToken.NextToken()));
            }
            for (int i = 0; i < 5; ++i)
            {
                m_BoardCardIDs.Add(int.Parse(argsToken.NextToken()));
            }
            m_NbPlayers = int.Parse(argsToken.NextToken());
            for (int i = 0; i < m_NbPlayers; ++i)
            {
                m_Seats.Add(new TuplePlayerInfo(argsToken));
            }
            m_Limit = (TypeBet)int.Parse(argsToken.NextToken());
        }

        public TableInfoCommand(int totalPotAmount, int nbSeats, List<int> potsAmount, List<int> boardCardIDs, int nbPlayers, List<TuplePlayerInfo> seats, TypeBet limit)
        {
            m_TotalPotAmount = totalPotAmount;
            m_NbSeats = nbSeats;
            m_PotsAmount = potsAmount;
            m_BoardCardIDs = boardCardIDs;
            m_NbPlayers = nbPlayers;
            m_Seats = seats;
            m_Limit = limit;
        }

        public TableInfoCommand(TableInfo info, PlayerInfo pPlayer)
    {
        m_PotsAmount = new List<int>();
        m_BoardCardIDs = new List<int>();
        m_Seats = new List<TuplePlayerInfo>();
        
        m_TotalPotAmount = info.TotalPotAmnt;
        m_NbSeats = info.NbMaxSeats;
        m_NbPlayers = info.NbMaxSeats;
        
        foreach ( MoneyPot pot in info.Pots)
        {
            m_PotsAmount.Add(pot.Amount);
        }
        
        for (int i = info.Pots.Length; i < m_NbSeats; i++)
        {
            m_PotsAmount.Add(0);
        }
         GameCard[] boardCards = info.Cards;
        for (int i = 0; i < 5; ++i)
        {
                m_BoardCardIDs.Add(boardCards[i].Id);
        }
        
        for (int i = 0; i < info.NbMaxSeats; ++i)
        {
            TuplePlayerInfo seat = new TuplePlayerInfo(i);
            m_Seats.Add(seat);
            PlayerInfo player = info.GetPlayer(i);
            seat.IsEmpty = (player == null);
            
            if (seat.IsEmpty)
            {
                continue;
            }
            
            seat.PlayerName = player.Name; // playerName
            seat.Money = player.MoneySafeAmnt; // playerMoney
            
            bool itsMe = (i == pPlayer.NoSeat);
            
            // Player cards
            GameCard[] holeCards = itsMe ? player.Cards : player.RelativeCards;
            for (int j = 0; j < 2; ++j)
            {
                seat.HoleCardIDs.Add(holeCards[j].Id);
            }
            
            seat.IsDealer = info.NoSeatDealer == i; // isDealer
            seat.IsSmallBlind = info.NoSeatSmallBlind == i; // isSmallBlind
            seat.IsBigBlind = info.NoSeatBigBlind == i; // isBigBlind
            seat.IsCurrentPlayer = info.NoSeatCurrPlayer == i; // isCurrentPlayer
            seat.TimeRemaining = 0; // timeRemaining
            seat.Bet = player.MoneyBetAmnt; // betAmount
            seat.IsPlaying = player.IsPlaying;
        }
        m_Limit = info.BetLimit;
    }

        public override void Encode(StringBuilder sb)
        {
            Append(sb, m_TotalPotAmount);
            Append(sb, m_NbSeats);
            for (int i = 0; i < m_PotsAmount.Count; ++i)
            {
                Append(sb, m_PotsAmount[i]);
            }
            for (int i = 0; i < m_BoardCardIDs.Count; ++i)
            {
                Append(sb, m_BoardCardIDs[i]);
            }
            Append(sb, m_NbPlayers);
            for (int i = 0; i < m_Seats.Count; ++i)
            {
                Append(sb, m_Seats[i].ToString(AbstractCommand.Delimitter));
            }
            Append(sb, (int)m_Limit);
        }
    }
}
