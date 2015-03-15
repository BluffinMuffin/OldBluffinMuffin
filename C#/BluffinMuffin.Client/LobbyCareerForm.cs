using System;
using System.Windows.Forms;
using BluffinMuffin.Client.Properties;
using BluffinMuffin.Protocol.Client;
using BluffinMuffin.Poker.Windows.Forms.Game;
using BluffinMuffin.Client.Game;

namespace BluffinMuffin.Client
{
    public partial class LobbyCareerForm : Form, ITableFormFactory
    {
        private LobbyTcpClientCareer m_Server;
        public LobbyCareerForm(LobbyTcpClientCareer server)
        {
            m_Server = server;
            m_Server.ServerLost += m_Server_ServerLost;
            InitializeComponent();
            tableList.TableFormFactory = this;
            tableList.SetServer(m_Server);
            Text = server.User.DisplayName + Resources.LobbyCareerForm_LobbyCareerForm_Tild + lblTitle.Text;
            lblServer.Text = String.Format("{0} on port {1}", m_Server.ServerAddress, m_Server.ServerPort);
        }



         public delegate void EmptyDelegate();
         void m_Server_ServerLost()
         {
             if (InvokeRequired)
             {
                 // We're not in the UI thread, so we need to call BeginInvoke
                 BeginInvoke(new EmptyDelegate(m_Server_ServerLost), new object[] { });
                 return;
             }
             m_Server = null;
             Close();
         }

        private void RefreshInfo()
        {
            var u = m_Server.User;
            m_Server.RefreshUserInfo(u.Username);
            lblAccount.Text = String.Format("{0} ( {1}, {2} )", u.DisplayName, u.Username, u.Email);
            lblMoney.Text = String.Format("{0}", (int)u.TotalMoney);
        }

        public void AllowJoinOrLeave()
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

        private void btnLogOut_Click(object sender, EventArgs e)
        {
            tableList.LeaveAll();
            Close();
        }

        private void CareerLobbyForm_FormClosed(object sender, FormClosedEventArgs e)
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

        private void tableList_OnListRefreshed(object sender, EventArgs e)
        {
            RefreshInfo();
        }

        private void tableList_OnSelectionChanged(object sender, EventArgs e)
        {
            AllowJoinOrLeave();
        }

        private void LobbyCareerForm_Activated(object sender, EventArgs e)
        {
            tableList.RefreshList();
        }

        private void LobbyCareerForm_Load(object sender, EventArgs e)
        {
            if (tableList.NbTables == 0)
                tableList.AddTable();
        }

        public AbstractTableForm ObtainGui()
        {
            return new CareerTableForm(m_Server.User);
        }
    }
}
