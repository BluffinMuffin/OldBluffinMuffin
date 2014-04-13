using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Com.Ericmas001.Game.Poker.DataTypes.Rules;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.DataTypes.Parameters;

namespace Com.Ericmas001.Game.Poker.GUI.Lobby
{
    public partial class CreateTableTabControl : UserControl
    {
        LobbyTypeEnum m_LobbyType;
        GameTypeEnum m_GameType;
        IEnumerable<RuleInfo> m_Rules;
        String CurrentVariant { get { return lstVariant.SelectedItem.ToString(); } }
        RuleInfo CurrentRule { get { return m_Rules.First(r => r.Name == CurrentVariant); } }
        public CreateTableTabControl(string playerName, int minPlayers, LobbyTypeEnum lobby, GameTypeEnum gameType, IEnumerable<RuleInfo> rules)
        {
            m_LobbyType = lobby;
            m_GameType = gameType;
            m_Rules = rules;
            InitializeComponent();
            txtTableName.Text = playerName + " Table";
            InitVariants();
            grpTraining.Visible = lobby == LobbyTypeEnum.Training;
        }

        private void InitVariants()
        {
            string[] names = m_Rules.Select(r => r.Name).ToArray();
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
            RuleInfo rule = CurrentRule;

            nudNbPlayersMin.Minimum = rule.MinPlayers;
            nudNbPlayersMin.Maximum = rule.MaxPlayers;
            nudNbPlayersMin.Value = rule.MinPlayers;

            nudNbPlayersMax.Minimum = rule.MinPlayers;
            nudNbPlayersMax.Maximum = rule.MaxPlayers;
            nudNbPlayersMax.Value = rule.MaxPlayers;
        }

        private void SetBetLimits()
        {
            RuleInfo rule = CurrentRule;
            lstBetLimit.Items.Clear();
            lstBetLimit.Items.AddRange(rule.AvailableLimits.Select(l => LimitFactory.GetInfos(l)).ToArray());
            lstBetLimit.SelectedIndex = lstBetLimit.FindStringExact(LimitFactory.GetInfos(rule.DefaultLimit).ToString());
        }

        private void SetBlindTypes()
        {
            RuleInfo rule = CurrentRule;
            lstBlinds.Items.Clear();
            lstBlinds.Items.AddRange(rule.AvailableBlinds.Select(b => BlindFactory.GetInfos(b)).ToArray());
            lstBlinds.SelectedIndex = lstBlinds.FindStringExact(BlindFactory.GetInfos(rule.DefaultBlind).ToString());
            SetBlindRules();
        }

        private void SetBlindRules()
        {
            BlindInfo blind = lstBlinds.SelectedItem as BlindInfo;
            lblBlind.Visible = nudBlind.Visible = blind.HasConfigurableAmount;
            if( blind.HasConfigurableAmount )
            { 
                nudBlind.Value = blind.ConfigurableDefaultValue;
                lblBlind.Text = String.Format("({0})", blind.ConfigurableAmountName);
            }
            else
                nudBlind.Value = 0;
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
                BlindOptions blind = new BlindOptionsNone();
                switch (((BlindInfo)lstBlinds.SelectedItem).Type)
                {
                    case BlindTypeEnum.Blinds:
                        blind = new BlindOptionsBlinds()
                        {
                            BigBlindAmount = (int)nudBlind.Value,
                        };
                        break;

                    case BlindTypeEnum.Antes:
                        blind = new BlindOptionsAnte()
                        {
                            AnteAmount = (int)nudBlind.Value,
                        };
                        break;
                }
                LimitOptions limit = null;
                switch (((LimitInfo)lstBetLimit.SelectedItem).Type)
                {
                    case LimitTypeEnum.NoLimit:
                        limit = new LimitOptionsNoLimit()
                        {
                            MaximumBuyIn = false, //TODO
                            BuyInIncrement = blind.MinimumRaiseAmount,
                        };
                        break;

                    case LimitTypeEnum.FixedLimit:
                        limit = new LimitOptionsFixed()
                        {
                        };
                        break;

                    case LimitTypeEnum.PotLimit:
                        limit = new LimitOptionsPot()
                        {
                        };
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
                };
            }
        }
    }
}
