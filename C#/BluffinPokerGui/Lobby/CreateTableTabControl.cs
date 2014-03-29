using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using PokerWorld.Game;

namespace BluffinPokerGUI.Lobby
{
    public partial class CreateTableTabControl : UserControl
    {

        public CreateTableTabControl(string playerName, int minPlayers, bool isTraining, IEnumerable<RuleInfo> rules)
        {
            InitializeComponent();
            string[] names = rules.Select(r => r.Name).ToArray();
            Array.Sort(names);
            lstVariant.Items.AddRange(names);
            lstVariant.Text = names[0];
            txtTableName.Text = playerName + " Table";
        }
    }
}
