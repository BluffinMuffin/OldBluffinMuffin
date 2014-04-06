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
        //Important: have to be set to have a defaut value;
        private LobbyOptions m_Lobby;// = new LobbyOptionsTraining();

        public string TableName { get; set; }
        public GameTypeEnum GameType { get; set; }
        public string Variant { get; set; }
        public LimitTypeEnum LimitType { get; set; }
        public BlindTypeEnum BlindType { get; set; }
        public int BlindAmount { get; set; }
        public int MinPlayersToStart { get; set; }
        public int MaxPlayers { get; set; }
        public ConfigurableWaitingTimes WaitingTimes { get; set; }

        /// <summary>
        /// IMPORTANT: Do not use to Get the value, only use CurrentLobby
        /// </summary>
        public LobbyOptions Lobby { get { return m_Lobby; } set { m_Lobby = value; } }

        [JsonIgnore]
        public LobbyOptions CurrentLobby { get { return m_Lobby == null ? new LobbyOptionsTraining() : m_Lobby; } }

        public TableParams()
        {
            TableName = "Anonymous Table";
            GameType = GameTypeEnum.Holdem;
            Variant = "Texas Hold'Em";
            LimitType = LimitTypeEnum.NoLimit;
            BlindType = BlindTypeEnum.Blinds;
            BlindAmount = 10;
            MinPlayersToStart = 2;
            MaxPlayers = 10;
            WaitingTimes = new ConfigurableWaitingTimes();
        }

    }
}
