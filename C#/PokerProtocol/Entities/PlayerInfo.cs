using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using EricUtility;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Entities
{
    public class PlayerInfo
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

        public PlayerInfo()
        {
            HoleCardIDs = new List<int>();
            IsEmpty = true;
        }
    }
}
