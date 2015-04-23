using Com.Ericmas001.Net.Protocol;
using Newtonsoft.Json;
using BluffinMuffin.Protocol.DataTypes.Enums;

namespace BluffinMuffin.Protocol.DataTypes
{
    public class TableParams
    {        
        public string TableName { get; set; }
        public GameTypeEnum GameType { get; set; }
        public string Variant { get; set; }
        public int MinPlayersToStart { get; set; }
        public int MaxPlayers { get; set; }
        public ConfigurableWaitingTimes WaitingTimes { get; set; }
        public int MoneyUnit { get; set; }


        [JsonConverter(typeof(OptionJsonConverter<LobbyOptions, LobbyTypeEnum>))]
        public LobbyOptions Lobby { get; set; }


        [JsonConverter(typeof(OptionJsonConverter<BlindOptions, BlindTypeEnum>))]
        public BlindOptions Blind { get; set; }


        [JsonConverter(typeof(OptionJsonConverter<LimitOptions, LimitTypeEnum>))]
        public LimitOptions Limit { get; set; }

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
