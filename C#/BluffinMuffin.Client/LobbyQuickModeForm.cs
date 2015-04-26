using System;
using System.Windows.Forms;
using BluffinMuffin.Client.Properties;
using BluffinMuffin.Protocol.Client;
using BluffinMuffin.Poker.Windows.Forms.Game;
using BluffinMuffin.Client.Game;
using Com.Ericmas001.Util;

namespace BluffinMuffin.Client
{
    public partial class LobbyQuickModeForm : Form, ITableFormFactory
    {
        private LobbyTcpClient m_Server;
        public LobbyQuickModeForm(LobbyTcpClient server)
        {
            m_Server = server;
            m_Server.ServerLost += m_Server_ServerLost;
            InitializeComponent();
            tableList.TableFormFactory = this;
            tableList.SetServer(m_Server);
            Text = server.PlayerName + Resources.LobbyRegisteredModeForm_LobbyRegisteredModeForm_Tild + lblTitle.Text;
            lblPlayerName.Text = server.PlayerName;
            tableList.RefreshList();
        }

        void m_Server_ServerLost()
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EmptyHandler(m_Server_ServerLost), new object[] { });
                return;
            }
            m_Server = null;
            Close();
        }

        private void AllowJoinOrLeave()
        {
            var selected = tableList.SomethingSelected;
            var client = tableList.FindClient();
            btnJoinTable.Enabled = selected && (client == null);
            btnLeaveTable.Enabled = selected && (client != null);
        }
        private void btnRefresh_Click(object sender, EventArgs e)
        {
            tableList.RefreshList();
            AllowJoinOrLeave();
        }

        private void btnAddTable_Click(object sender, EventArgs e)
        {
            tableList.AddTable();
        }

        private void btnJoinTable_Click(object sender, EventArgs e)
        {
            AllowJoinOrLeave();
            tableList.JoinSelected();
        }

        private void btnLeaveTable_Click(object sender, EventArgs e)
        {
            tableList.LeaveSelected();
        }

        private void btnDisconnect_Click(object sender, EventArgs e)
        {
            tableList.LeaveAll();
            Close();
        }

        private void LobbyForm_FormClosed(object sender, FormClosedEventArgs e)
        {
            if (m_Server != null)
                m_Server.Disconnect();
            //Program.WForm.Show();
        }

        private void tableList_OnChoiceMade(object sender, EventArgs e)
        {
            AllowJoinOrLeave();
            tableList.JoinSelected();
        }

        private void tableList_OnSelectionChanged(object sender, EventArgs e)
        {
            AllowJoinOrLeave();
        }

        private void LobbyQuickModeForm_Load(object sender, EventArgs e)
        {
            if (tableList.NbTables == 0)
                tableList.AddTable();
        }

        public AbstractTableForm ObtainGui()
        {
            return new QuickModeTableForm();
        }
    }
}
