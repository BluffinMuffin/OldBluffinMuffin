using System;
using System.Windows.Forms;

namespace BluffinMuffin.Poker.Windows.Forms.Lobby
{
    public partial class NameUsedForm : Form
    {
        private string m_PlayerName;
        private bool m_Ok;

        public bool OK
        {
            get { return m_Ok; }
        }
        public string PlayerName
        {
            get { return m_PlayerName; }
        } 

        public NameUsedForm(string playerName)
        {
            m_PlayerName = playerName;
            InitializeComponent();
            txtPlayerName.Text = m_PlayerName;
        }

        private void btnModify_Click(object sender, EventArgs e)
        {
            m_PlayerName = txtPlayerName.Text;
            m_Ok = true;
            Close();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Close();
        }
    }
}
