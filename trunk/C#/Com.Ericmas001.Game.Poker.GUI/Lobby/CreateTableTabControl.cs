using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Forms;
using Com.Ericmas001.Game.Poker.GUI.Properties;
using Com.Ericmas001.Util;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.DataTypes.Parameters;

namespace Com.Ericmas001.Game.Poker.GUI.Lobby
{
    public partial class CreateTableTabControl : UserControl
    {
        readonly LobbyTypeEnum m_LobbyType;
        readonly GameTypeEnum m_GameType;
        readonly IEnumerable<RuleInfo> m_Rules;
        String CurrentVariant { get { return lstVariant.SelectedItem.ToString(); } }
        RuleInfo CurrentRule { get { return m_Rules.First(r => r.Name == CurrentVariant); } }
        public CreateTableTabControl(string playerName, LobbyTypeEnum lobby, GameTypeEnum gameType, IEnumerable<RuleInfo> rules)
        {
            m_LobbyType = lobby;
            m_GameType = gameType;
            m_Rules = rules;
            InitializeComponent();
            txtTableName.Text = playerName + Resources.CreateTableTabControl_CreateTableTabControl__Table;
            InitVariants();
            RefreshNumbers();
            grpTraining.Visible = lobby == LobbyTypeEnum.Training;
        }

        private void InitVariants()
        {
            object[] names = m_Rules.Select(r => r.Name).ToArray();
            lstVariant.Items.AddRange(names);
            lstVariant.SelectedItem = names[0];
            VariantChoosen();
        }

        private void lstVariant_SelectedIndexChanged(object sender, EventArgs e)
        {
            VariantChoosen();
        }

        private void VariantChoosen()
        {
            SetMinMax();
            SetBetLimits();
            SetBlindTypes();
            SetWaitingTimes();
        }

        private void SetMinMax()
        {
            var rule = CurrentRule;

            nudNbPlayersMin.Minimum = rule.MinPlayers;
            nudNbPlayersMin.Maximum = rule.MaxPlayers;
            nudNbPlayersMin.Value = rule.MinPlayers;

            nudNbPlayersMax.Minimum = rule.MinPlayers;
            nudNbPlayersMax.Maximum = rule.MaxPlayers;
            nudNbPlayersMax.Value = rule.MaxPlayers;
        }

        private void SetBetLimits()
        {
            var rule = CurrentRule;
            lstBetLimit.Items.Clear();
            lstBetLimit.Items.AddRange(rule.AvailableLimits.Select(l => EnumFactory<LimitTypeEnum>.ToString(l)).ToArray());
            lstBetLimit.SelectedIndex = lstBetLimit.FindStringExact(EnumFactory<LimitTypeEnum>.ToString(rule.DefaultLimit));
        }

        private void SetBlindTypes()
        {
            var rule = CurrentRule;
            lstBlinds.Items.Clear();
            lstBlinds.Items.AddRange(rule.AvailableBlinds.Select(b => EnumFactory<BlindTypeEnum>.ToString(b)).ToArray());
            lstBlinds.SelectedIndex = lstBlinds.FindStringExact(EnumFactory<BlindTypeEnum>.ToString(rule.DefaultBlind));
            SetBlindRules();
        }

        private void SetBlindRules()
        {
            var blind = EnumFactory<BlindTypeEnum>.Parse((string)lstBlinds.SelectedItem);
            ucAnte.Visible = blind == BlindTypeEnum.Antes;
            ucBlinds.Visible = blind == BlindTypeEnum.Blinds;
        }

        private void SetWaitingTimes()
        {
            nudWTAPlayerAction.Value = 500;
            nudWTABoardDealed.Value = 500;
            nudWTAPotWon.Value = 2500;
            grpTimes.Enabled = CurrentRule.CanConfigWaitingTime;
        }

        private void nudNbPlayersMin_ValueChanged(object sender, EventArgs e)
        {
            nudNbPlayersMax.Minimum = nudNbPlayersMin.Value;
        }

        private void nudNbPlayersMax_ValueChanged(object sender, EventArgs e)
        {
            nudNbPlayersMin.Maximum = nudNbPlayersMax.Value;
        }

        private void lstBlinds_SelectedIndexChanged(object sender, EventArgs e)
        {
            SetBlindRules();
        }

        public TableParams Params
        {
            get
            {
                LobbyOptions lobby = null;
                switch (m_LobbyType)
                {
                    case LobbyTypeEnum.Training:
                        lobby = new LobbyOptionsTraining()
                        {
                            StartingAmount = (int)nudStartingAmount.Value,
                        };
                        break;

                    case LobbyTypeEnum.Career:
                        lobby = new LobbyOptionsCareer();
                        break;
                }
                var moneyUnit = (int)nudMoneyUnit.Value;
                BlindOptions blind = new BlindOptionsNone() { MoneyUnit = moneyUnit };
                switch (EnumFactory<BlindTypeEnum>.Parse((string)lstBlinds.SelectedItem))
                {
                    case BlindTypeEnum.Blinds:
                        blind = new BlindOptionsBlinds() { MoneyUnit = moneyUnit };
                        break;

                    case BlindTypeEnum.Antes:
                        blind = new BlindOptionsAnte() { MoneyUnit = moneyUnit };
                        break;
                }
                LimitOptions limit = null;
                switch (EnumFactory<LimitTypeEnum>.Parse((string)lstBetLimit.SelectedItem))
                {
                    case LimitTypeEnum.NoLimit:
                        limit = new LimitOptionsNoLimit();
                        break;

                    case LimitTypeEnum.FixedLimit:
                        limit = new LimitOptionsFixed();
                        break;

                    case LimitTypeEnum.PotLimit:
                        limit = new LimitOptionsPot();
                        break;
                }
                return new TableParams()
                {
                    TableName = txtTableName.Text,
                    GameType = m_GameType,
                    Variant = lstVariant.SelectedItem.ToString(),
                    MinPlayersToStart = (int)nudNbPlayersMin.Value,
                    MaxPlayers = (int)nudNbPlayersMax.Value,
                    WaitingTimes = new ConfigurableWaitingTimes()
                    {
                        AfterPlayerAction = (int)nudWTAPlayerAction.Value,
                        AfterBoardDealed = (int)nudWTABoardDealed.Value,
                        AfterPotWon = (int)nudWTAPotWon.Value,
                    },
                    Lobby = lobby,
                    Blind = blind,
                    Limit = limit,
                    MoneyUnit = moneyUnit,
                    LimitMaximumBuyIn = rdBuyInLimited.Checked,
                };
            }
        }

        private void NeedToRefreshNumbers(object sender, EventArgs e)
        {
            RefreshNumbers();
        }
        private void RefreshNumbers()
        {
            var moneyUnit = (int)nudMoneyUnit.Value;
            var minBuyIn = moneyUnit * 20;
            var maxBuyIn = moneyUnit * 100;
            lblGameSize.Text = String.Format("${0} / ${1}", moneyUnit, moneyUnit * 2);
            lblMinimumBuyIn.Text = String.Format("${0}", minBuyIn);
            lblMaximumBuyIn.Text = String.Format("(${0})", maxBuyIn);
            ucAnte.SetAnte(moneyUnit);
            ucBlinds.SetBlinds(moneyUnit);
            nudStartingAmount.Minimum = minBuyIn;
            nudStartingAmount.Maximum = rdBuyInLimited.Checked ? maxBuyIn : int.MaxValue;
            nudStartingAmount.Increment = moneyUnit;
        }
    }
}
