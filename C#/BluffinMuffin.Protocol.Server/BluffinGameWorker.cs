using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.DataTypes.Enums;
using BluffinMuffin.Poker.DataTypes.Parameters;
using BluffinMuffin.Poker.Logic;
using BluffinMuffin.Poker.Persistance;
using BluffinMuffin.Protocol.Commands;
using BluffinMuffin.Protocol.Commands.Game;
using BluffinMuffin.Protocol.Commands.Lobby;
using BluffinMuffin.Protocol.Commands.Lobby.Career;
using BluffinMuffin.Protocol.Commands.Lobby.Training;
using Com.Ericmas001.Util;

namespace BluffinMuffin.Protocol.Server
{
    public class BluffinGameWorker
    {
        private KeyValuePair<Type, Action<AbstractBluffinCommand, IBluffinClient, RemotePlayer>>[] Methods;

        public IBluffinServer Server { get; private set; }
        public BluffinGameWorker(IBluffinServer server)
        {
            Server = server;
            Methods = new[]
            {
                //General
                new KeyValuePair<Type, Action<AbstractBluffinCommand, IBluffinClient, RemotePlayer>>(typeof(AbstractBluffinCommand), OnCommandReceived), 
                new KeyValuePair<Type, Action<AbstractBluffinCommand, IBluffinClient, RemotePlayer>>(typeof(DisconnectCommand), OnDisconnectCommandReceived), 
                
                //Game
                new KeyValuePair<Type, Action<AbstractBluffinCommand, IBluffinClient, RemotePlayer>>(typeof(PlayerPlayMoneyCommand), OnPlayerPlayMoneyCommandReceived), 
                new KeyValuePair<Type, Action<AbstractBluffinCommand, IBluffinClient, RemotePlayer>>(typeof(PlayerSitOutCommand), OnPlayerSitOutCommandReceived), 
                new KeyValuePair<Type, Action<AbstractBluffinCommand, IBluffinClient, RemotePlayer>>(typeof(PlayerSitInCommand), OnPlayerSitInCommandReceived), 
                
            };
        }

        public void Start()
        {
            foreach (GameCommandEntry entry in Server.GameCommands.GetConsumingEnumerable())
                Methods.Where(x => entry.Command.GetType().IsSubclassOf(x.Key) || x.Key == entry.Command.GetType()).ToList().ForEach(x => x.Value(entry.Command, entry.Client, entry.Player));
        }

        private void OnCommandReceived(AbstractBluffinCommand command, IBluffinClient client, RemotePlayer p)
        {
            LogManager.Log(LogLevel.MessageVeryLow, "BluffinGameWorker.OnCommandReceived", "GameWorker RECV from {0} [{1}]", client.PlayerName, command.Encode());
            LogManager.Log(LogLevel.MessageVeryLow, "BluffinGameWorker.OnCommandReceived", "-------------------------------------------");
        }

        void OnDisconnectCommandReceived(AbstractBluffinCommand command, IBluffinClient client, RemotePlayer p)
        {
            var c = (DisconnectCommand)command;
            if (p.Game.Params.Lobby.OptionType == LobbyTypeEnum.Career)
                DataManager.Persistance.Get(p.Client.PlayerName).TotalMoney += p.Player.MoneySafeAmnt;

            client.RemovePlayer(p);

            p.Player.State = PlayerStateEnum.Zombie;

            var t = p.Game.Table;
            LogManager.Log(LogLevel.Message, "BluffinGameWorker.OnDisconnectCommandReceived", "> Client '{0}' left table: {2}:{1}", p.Player.Name, t.Params.TableName, p.TableId);

            p.Game.LeaveGame(p.Player);
            
        }

        private void OnPlayerSitInCommandReceived(AbstractBluffinCommand command, IBluffinClient client, RemotePlayer p)
        {
            UserInfo userInfo = null;
            var c = (PlayerSitInCommand)command;
            if (p.Game.Params.Lobby.OptionType == LobbyTypeEnum.Training)
                p.Player.MoneySafeAmnt = ((LobbyOptionsTraining) p.Game.Params.Lobby).StartingAmount;
            else
            {
                int money = c.MoneyAmount;
                userInfo = DataManager.Persistance.Get(p.Client.PlayerName);
                if (userInfo == null || userInfo.TotalMoney < money)
                    p.Player.MoneySafeAmnt = -1;
                else
                {
                    userInfo.TotalMoney -= money;
                    p.Player.MoneySafeAmnt = money;
                }
            }
            var seat = p.Game.GameTable.SitIn(p.Player, c.NoSeat);
            if (seat == null)
            {
                client.SendCommand(c.Response(-1));
                if (userInfo != null)
                    userInfo.TotalMoney += p.Player.MoneySafeAmnt; 
            }
            else
            {
                client.SendCommand(c.Response(seat.NoSeat));
                p.Game.AfterPlayerSat(p.Player);
            }
        }

        private void OnPlayerSitOutCommandReceived(AbstractBluffinCommand command, IBluffinClient client, RemotePlayer p)
        {
            var c = (PlayerSitOutCommand)command;
            client.SendCommand(c.Response(true));
            p.Game.SitOut(p.Player);
        }

        private void OnPlayerPlayMoneyCommandReceived(AbstractBluffinCommand command, IBluffinClient client, RemotePlayer p)
        {
            var c = (PlayerPlayMoneyCommand)command;
            p.Game.PlayMoney(p.Player, c.Played);
        }
    }
}
