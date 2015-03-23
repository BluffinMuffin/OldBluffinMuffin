using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.Logic;
using BluffinMuffin.Protocol.Commands.Game;
using Com.Ericmas001.Net.Protocol;

namespace BluffinMuffin.Protocol.Server
{
    public class GameTcpServerCareer : GameTcpServer
    {
        private readonly UserInfo m_UserInfo;

        public GameTcpServerCareer(int id, PokerGame game, UserInfo userInfo)
            : base(id,game,userInfo.DisplayName)
        {
            m_UserInfo = userInfo;
        }

        protected override int GetStartingMoney(CommandEventArgs<PlayerSitInCommand> e)
        {
            var parms = Game.Table.Params;
            int money = e.Command.MoneyAmount;
            if (m_UserInfo == null || m_UserInfo.TotalMoney < money)
                return -1;
            m_UserInfo.TotalMoney -= money;
            return money;
        }

        protected override void WhatToDoWithExtraMoneyWhenLeaving(int money)
        {
            m_UserInfo.TotalMoney += money; 
        }
    }
}
