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

        public Player(JObject obj)
        {
            NoSeat = (int)obj["NoSeat"];
            IsEmpty = (bool)obj["IsEmpty"];
            PlayerName = (string)obj["PlayerName"];
            Money = (int)obj["Money"];
            HoleCardIDs = ((JArray)obj["HoleCardIDs"]).Select(x => (int)x).ToList();
            IsDealer = (bool)obj["IsDealer"];
            IsSmallBlind = (bool)obj["IsSmallBlind"];
            IsBigBlind = (bool)obj["IsBigBlind"];
            IsCurrentPlayer = (bool)obj["IsCurrentPlayer"];
            TimeRemaining = (int)obj["TimeRemaining"];
            Bet = (int)obj["Bet"];
            IsPlaying = (bool)obj["IsPlaying"];
        }

        public Player(StringTokenizer argsToken)
        {
            HoleCardIDs = new List<int>();
            NoSeat = int.Parse(argsToken.NextToken());
            IsEmpty = bool.Parse(argsToken.NextToken());
            if (!IsEmpty)
            {
                PlayerName = argsToken.NextToken();
                Money = int.Parse(argsToken.NextToken());
                HoleCardIDs.Add(int.Parse(argsToken.NextToken()));
                HoleCardIDs.Add(int.Parse(argsToken.NextToken()));
                IsDealer = bool.Parse(argsToken.NextToken());
                IsSmallBlind = bool.Parse(argsToken.NextToken());
                IsBigBlind = bool.Parse(argsToken.NextToken());
                IsCurrentPlayer = bool.Parse(argsToken.NextToken());
                TimeRemaining = int.Parse(argsToken.NextToken());
                Bet = int.Parse(argsToken.NextToken());
                IsPlaying = bool.Parse(argsToken.NextToken());
            }
        }
        public string ToString(char d)
        {
            StringBuilder sb = new StringBuilder();
            sb.Append(NoSeat);
            sb.Append(d);
            sb.Append(IsEmpty);
            sb.Append(d);
            if (!IsEmpty)
            {
                sb.Append(PlayerName);
                sb.Append(d);
                sb.Append(Money);
                sb.Append(d);
                sb.Append(HoleCardIDs[0]);
                sb.Append(d);
                sb.Append(HoleCardIDs[1]);
                sb.Append(d);
                sb.Append(IsDealer);
                sb.Append(d);
                sb.Append(IsSmallBlind);
                sb.Append(d);
                sb.Append(IsBigBlind);
                sb.Append(d);
                sb.Append(IsCurrentPlayer);
                sb.Append(d);
                sb.Append(TimeRemaining);
                sb.Append(d);
                sb.Append(Bet);
                sb.Append(d);
                sb.Append(IsPlaying);
                sb.Append(d);
            }


            return sb.ToString();
        }
    }
}
