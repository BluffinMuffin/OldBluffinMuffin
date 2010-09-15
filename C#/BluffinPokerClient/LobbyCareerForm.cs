using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using PokerProtocol;
using BluffinPokerGUI.Lobby;
using BluffinPokerGui.Game;
using PokerWorld.Data;

namespace BluffinPokerClient
{
    public partial class LobbyCareerForm : Form
    {
        private LobbyTCPClientCareer m_Server;
        public LobbyCareerForm(LobbyTCPClientCareer server)
        {
            m_Server = server;
            InitializeComponent();
            tableList.setServer(m_Server);
            Text = server.User.DisplayName + " ~ " + lblTitle.Text;
            lblServer.Text = String.Format("{0} on port {1}", m_Server.ServerAddress, m_Server.ServerPort);
            tableList.RefreshList();
            if (tableList.NbTables == 0)
                tableList.AddTable(false);
        }

        private void RefreshInfo()
        {
            UserInfo u = m_Server.User;
            m_Server.RefreshUserInfo(u.Username);
            lblAccount.Text = String.Format("{0} ( {1}, {2} )", u.DisplayName, u.Username, u.Email);
            lblMoney.Text = String.Format("{0}", (int)u.TotalMoney);
        }

        public void AllowJoinOrLeave()
        {
            bool selected = tableList.SomethingSelected;
            GameClient client = tableList.FindClient();
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
            tableList.AddTable(false);
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

        private void btnLogOut_Click(object sender, EventArgs e)
        {
            Close();
        }

        private void CareerLobbyForm_FormClosed(object sender, FormClosedEventArgs e)
        {
            if (m_Server != null)
                m_Server.Disconnect();
            Program.WForm.Show();
        }

        private void tableList_OnChoiceMade(object sender, EventArgs e)
        {
            AllowJoinOrLeave();
            tableList.JoinSelected();
        }

        private void tableList_OnListRefreshed(object sender, EventArgs e)
        {
            RefreshInfo();
        }

        private void tableList_OnSelectionChanged(object sender, EventArgs e)
        {
            AllowJoinOrLeave();
        }
    }
}
