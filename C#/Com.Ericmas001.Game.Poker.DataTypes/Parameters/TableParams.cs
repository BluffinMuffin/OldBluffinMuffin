using Newtonsoft.Json;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Com.Ericmas001.Game.Poker.DataTypes.Parameters
{
    public class TableParams
    {
        //Important: leave it blank as default value to have a good JSON.Net serialization
        private LobbyOptions m_Lobby;
        private BlindOptions m_Blind;
        private LimitOptions m_Limit;

        /// <summary>
        /// Only used by JSON.Net to Serialize / Deserialize
        /// </summary>
        public LobbyOptions SerializableLobby { get { return m_Lobby; } set { m_Lobby = value; } }
        /// <summary>
        /// Only used by JSON.Net to Serialize / Deserialize
        /// </summary>
        public BlindOptions SerializableBlind { get { return m_Blind; } set { m_Blind = value; } }
        /// <summary>
        /// Only used by JSON.Net to Serialize / Deserialize
        /// </summary>
        public LimitOptions SerializableLimit { get { return m_Limit; } set { m_Limit = value; } }

        
        public string TableName { get; set; }
        public GameTypeEnum GameType { get; set; }
        public string Variant { get; set; }
        public int MinPlayersToStart { get; set; }
        public int MaxPlayers { get; set; }
        public ConfigurableWaitingTimes WaitingTimes { get; set; }

        [JsonIgnore]
        public LobbyOptions Lobby { get { return m_Lobby == null ? new LobbyOptionsTraining() : m_Lobby; } set { m_Lobby = value; } }
        [JsonIgnore]
        public BlindOptions Blind { get { return m_Blind == null ? new BlindOptionsNone() : m_Blind; } set { m_Blind = value; } }
        [JsonIgnore]
        public LimitOptions Limit { get { return m_Limit == null ? new LimitOptionsPot() : m_Limit; } set { m_Limit = value; } }

        public TableParams()
        {
            TableName = "Anonymous Table";
            GameType = GameTypeEnum.Holdem;
            Variant = "Texas Hold'Em";
            MinPlayersToStart = 2;
            MaxPlayers = 10;
            WaitingTimes = new ConfigurableWaitingTimes();
        }

    }
}
