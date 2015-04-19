using BluffinMuffin.Poker.DataTypes.Annotations;
using Com.Ericmas001.Net.Protocol;
using Newtonsoft.Json;
using BluffinMuffin.Poker.DataTypes.Enums;

namespace BluffinMuffin.Poker.DataTypes.Parameters
{
    public class TableParams
    {        
        public string TableName { get; set; }
        public GameTypeEnum GameType { [UsedImplicitly] get; set; }
        public string Variant { [UsedImplicitly] get; set; }
        public int MinPlayersToStart { get; set; }
        public int MaxPlayers { get; set; }
        public ConfigurableWaitingTimes WaitingTimes { get; set; }
        public int MoneyUnit { get; set; }


        [JsonConverter(typeof(OptionJsonConverter<LobbyOptions, LobbyTypeEnum>))]
        public LobbyOptions Lobby { get; set; }


        [JsonConverter(typeof(OptionJsonConverter<BlindOptions, BlindTypeEnum>))]
        public BlindOptions Blind { [UsedImplicitly] get; set; }


        [JsonConverter(typeof(OptionJsonConverter<LimitOptions, LimitTypeEnum>))]
        public LimitOptions Limit { [UsedImplicitly] get; set; }

        public TableParams()
        {
            TableName = "Anonymous Table";
            GameType = GameTypeEnum.Holdem;
            Variant = "Texas Hold'Em";
            MinPlayersToStart = 2;
            MaxPlayers = 10;
            WaitingTimes = new ConfigurableWaitingTimes();
            MoneyUnit = 10;
            Lobby = new LobbyOptionsTraining();
            Blind = new BlindOptionsNone() { MoneyUnit = MoneyUnit };
            Limit = new LimitOptionsPot();
        }
    }
}
