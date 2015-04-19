using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.Logic;

namespace BluffinMuffin.Protocol.Server
{
    public class RemotePlayer
    {
        public PokerGame Game { get; private set; }
        public PlayerInfo Player { get; private set; }
        public RemoteTcpClient Client { get; private set; }
        public int TableId { get; private set; }

        public RemotePlayer(PokerGame game, PlayerInfo player, RemoteTcpClient client, int tableId)
        {
            Game = game;
            Player = player;
            Client = client;
            TableId = tableId;
        }
    }
}
