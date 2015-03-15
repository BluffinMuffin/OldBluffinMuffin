using BluffinMuffin.Poker.DataTypes.Annotations;
using Newtonsoft.Json;
using BluffinMuffin.Poker.DataTypes.Enums;
using Com.Ericmas001.Net.JSON;

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
        public bool LimitMaximumBuyIn { get; set; }
        public int MoneyUnit { get; set; }


        [JsonConverter(typeof(OptionJsonConverter<LobbyOptions, LobbyTypeEnum>))]
        public LobbyOptions Lobby { get; set; }


        [JsonConverter(typeof(OptionJsonConverter<BlindOptions, BlindTypeEnum>))]
        public BlindOptions Blind { [UsedImplicitly] get; set; }


        [JsonConverter(typeof(OptionJsonConverter<LimitOptions, LimitTypeEnum>))]
        public LimitOptions Limit { [UsedImplicitly] get; set; }


        public int LimitedMinimumBuyIn { get { return 20 * MoneyUnit; } }
        public int LimitedMaximumBuyIn { get { return 100 * MoneyUnit; } }

        public TableParams()
        {
            TableName = "Anonymous Table";
            GameType = GameTypeEnum.Holdem;
            Variant = "Texas Hold'Em";
            MinPlayersToStart = 2;
            MaxPlayers = 10;
            WaitingTimes = new ConfigurableWaitingTimes();
            LimitMaximumBuyIn = false;
            MoneyUnit = 10;
            Lobby = new LobbyOptionsTraining();
            Blind = new BlindOptionsNone() { MoneyUnit = MoneyUnit };
            Limit = new LimitOptionsPot();
        }


        //public class LobbyJsonConverter : AbstractCustomJsonConverter<LobbyOptions>
        //{
        //    public override LobbyOptions ObtainCustomObject(JObject jObject)
        //    {
        //        return FactoryOption<LobbyOptions, LobbyTypeEnum>.GenerateOption((LobbyTypeEnum)((int)jObject.GetValue("OptionType")));
        //    }
        //}
        //public class BlindJsonConverter : AbstractCustomJsonConverter<BlindOptions>
        //{
        //    public override BlindOptions ObtainCustomObject(JObject jObject)
        //    {
        //        return FactoryOption<BlindOptions, BlindTypeEnum>.GenerateOption((BlindTypeEnum)((int)jObject.GetValue("OptionType")));
        //    }
        //}
        //public class LimitJsonConverter : AbstractCustomJsonConverter<LimitOptions>
        //{
        //    public override LimitOptions ObtainCustomObject(JObject jObject)
        //    {
        //        return FactoryOption<LimitOptions, LimitTypeEnum>.GenerateOption((LimitTypeEnum)((int)jObject.GetValue("OptionType")));
        //    }
        //}
    }
}
