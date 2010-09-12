using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using PokerProtocol;
using BluffinPokerGUI.Lobby;
using BluffinPokerGui;
using PokerWorld.Game;
using BluffinPokerGui.Game;

namespace BluffinPokerClient
{
    public partial class LobbyTrainingForm : Form
    {
        private LobbyTCPClient m_Server;
        public LobbyTrainingForm(LobbyTCPClient server)
        {
            m_Server = server;
            InitializeComponent();
            tableList.setServer(m_Server);
            lblStatus.Text = "[Training] Connected as " + server.PlayerName;
            Text = server.PlayerName + " ~ " + lblTitle.Text;
            tableList.RefreshList();
            if (tableList.NbTables == 0)
                tableList.AddTable(true);
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
            tableList.AddTable(true);
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
            Close();
        }

        private void LobbyForm_FormClosed(object sender, FormClosedEventArgs e)
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

        private void tableList_OnSelectionChanged(object sender, EventArgs e)
        {
            AllowJoinOrLeave();
        }
    }
}
