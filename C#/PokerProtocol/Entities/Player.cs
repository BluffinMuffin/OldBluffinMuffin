using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using EricUtility;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Entities
{
    public class Player
    {
        public int NoSeat { get; set; }
        public bool IsEmpty { get; set; }
        public string PlayerName { get; set; }
        public int Money { get; set; }
        public List<int> HoleCardIDs { get; set; }
        public bool IsDealer { get; set; }
        public bool IsSmallBlind { get; set; }
        public bool IsBigBlind { get; set; }
        public bool IsCurrentPlayer { get; set; }
        public int TimeRemaining { get; set; }
        public int Bet { get; set; }
        public bool IsPlaying { get; set; }

        public Player(int noSeat, bool isEmpty, string playerName, int money, List<int> hole, bool isDealer, bool isSmallBlind, bool isBigBlind, bool isCurrentPlayer, int timeRemaining, int bet, bool isPlaying)
        {
            NoSeat = noSeat;
            IsEmpty = isEmpty;
            PlayerName = playerName;
            Money = money;
            HoleCardIDs = hole;
            IsDealer = isDealer;
            IsSmallBlind = isSmallBlind;
            IsBigBlind = isBigBlind;
            IsCurrentPlayer = isCurrentPlayer;
            TimeRemaining = timeRemaining;
            Bet = bet;
            IsPlaying = isPlaying;
        }

        public Player(int noSeat)
        {
            HoleCardIDs = new List<int>();
            NoSeat = noSeat;
            IsEmpty = true;
        }

        public Player()
        {
        }
    }
}
