using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using PokerProtocol;
using BluffinPokerGUI.Lobby;

namespace BluffinPokerClient
{
    public partial class MainForm : Form
    {
        private LobbyTCPClient m_Server;
        public MainForm()
        {
            InitializeComponent();
        }

        private void btnConnect_Click(object sender, EventArgs e)
        {
            if (m_Server == null)
            {
                ConnectForm form = new ConnectForm();
                form.ShowDialog();
                if (form.OK)
                {
                    m_Server = new LobbyTCPClient(form.ServerAddress, form.ServerPort);
                    if (m_Server.Connect())
                    {
                        string name = form.PlayerName;
                        bool isOk = m_Server.Identify(name);
                        while (!isOk)
                        {
                            NameUsedForm form2 = new NameUsedForm(name);
                            form2.ShowDialog();
                            name = form2.PlayerName;
                            isOk = m_Server.Identify(name);
                        }
                        lblStatus.Text = "Connected as " + name;
                        Text =  name + " ~ " + lblTitle.Text;
                        btnConnect.Text = "Disconnect";
                        RefreshTables();
                        if (datTables.RowCount == 0)
                            AddTable();
                    }
                    else
                    {
                        lblStatus.Text = "Connection Failed";
                    }
                }
            }
            else
            {
                m_Server.Disconnect();
                m_Server = null;
                btnConnect.Text = "Connect";
                lblStatus.Text = "Not Connected";
                Text = lblTitle.Text;
            }
        }
        private void RefreshTables()
        {
            datTables.Rows.Clear();
            List<TupleTableInfo> lst = m_Server.getListTables();
            for( int i = 0; i < lst.Count; ++i)
            {
                TupleTableInfo info = lst[i];
                datTables.Rows.Add();
                datTables.Rows[i].Cells[0].Value = info.NoPort;
                datTables.Rows[i].Cells[1].Value = info.TableName;
                datTables.Rows[i].Cells[2].Value = info.Limit.ToString();
                datTables.Rows[i].Cells[3].Value = info.BigBlind;
                datTables.Rows[i].Cells[4].Value = info.NbPlayers + "/" + info.NbSeats;
            }
        }
        private void AddTable()
        {
        }
        private void btnRefresh_Click(object sender, EventArgs e)
        {
            RefreshTables();
        }

        private void btnAddTable_Click(object sender, EventArgs e)
        {
            AddTable();
        }

        private void btnJoinTable_Click(object sender, EventArgs e)
        {

        }

        private void btnLeaveTable_Click(object sender, EventArgs e)
        {

        }
    }
}
