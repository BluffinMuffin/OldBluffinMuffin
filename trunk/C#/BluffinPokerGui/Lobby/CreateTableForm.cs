using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using Com.Ericmas001.Game.Poker.DataTypes.Rules;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Com.Ericmas001.Game.Poker.DataTypes;

namespace BluffinPokerGUI.Lobby
{
    public partial class CreateTableForm : Form
    {
        public GameRule GameRules { get; private set; }
        public CreateTableForm(string playerName, int minPlayers, LobbyTypeEnum lobby, List<RuleInfo> rules)
        {
            GameRules = null;
            InitializeComponent();
            IEnumerable<RuleInfo> availablesRules = rules.Where(r => r.AvailableLobbys.Contains(lobby));

            foreach (GameTypeEnum type in availablesRules.Select(r => r.GameType).Distinct())
            {
                string t = type.ToString();
                TabPage tp = new TabPage(t) { Name = "tab" + t, BackColor = Color.White };
                tp.Controls.Add(new CreateTableTabControl(playerName, minPlayers, lobby, type, availablesRules.Where(r => r.GameType.ToString() == t)) { Dock = DockStyle.Fill });
                tabControl1.TabPages.Add(tp);
            }
        }

        private void btnAddTable_Click(object sender, EventArgs e)
        {
            CreateTableTabControl cttc = tabControl1.SelectedTab.Controls.OfType<CreateTableTabControl>().First();
            GameRules = tabControl1.SelectedTab.Controls.OfType<CreateTableTabControl>().First().GameRules;
            Close();
        }
    }
}
