using PokerWorld.Game;
using PokerWorld.Game.Enums;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace BluffinPokerGUI.Lobby
{
    public partial class CreateTableForm : Form
    {
        public CreateTableForm(string playerName, int minPlayers, bool isTraining, List<RuleInfo> rules)
        {
            InitializeComponent();
            string[] types = rules.Select(r => r.GameType.ToString()).Distinct().ToArray();
            Array.Sort(types);
            foreach (string t in types)
            {
                TabPage tp = new TabPage(t) { Name = "tab" + t, BackColor = Color.White };
                tp.Controls.Add(new CreateTableTabControl(playerName, minPlayers, isTraining, rules.Where(r => r.GameType.ToString() == t)) { Dock = DockStyle.Fill });
                tabControl1.TabPages.Add(tp);
            }
        }

        private void btnAddTable_Click(object sender, EventArgs e)
        {

        }
    }
}
