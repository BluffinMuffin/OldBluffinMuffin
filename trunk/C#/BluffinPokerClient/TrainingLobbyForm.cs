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
    public partial class TrainingLobbyForm : Form
    {
        private LobbyTCPClient m_Server;
        public TrainingLobbyForm(LobbyTCPClient server)
        {
            m_Server = server;
            InitializeComponent();
            lblStatus.Text = "[Training] Connected as " + server.PlayerName;
            Text = server.PlayerName + " ~ " + lblTitle.Text;
            RefreshTables();
            if (datTables.RowCount == 0)
                AddTable();
        }

        private void RefreshTables()
        {
            datTables.Rows.Clear();
            List<TupleTableInfo> lst = m_Server.getListTables();
            for (int i = 0; i < lst.Count; ++i)
            {
                TupleTableInfo info = lst[i];
                datTables.Rows.Add();
                datTables.Rows[i].Cells[0].Value = info.NoPort;
                datTables.Rows[i].Cells[1].Value = info.TableName;
                datTables.Rows[i].Cells[2].Value = info.Limit.ToString();
                datTables.Rows[i].Cells[3].Value = info.BigBlind;
                datTables.Rows[i].Cells[4].Value = info.NbPlayers + "/" + info.NbSeats;
            }
            if (datTables.RowCount > 0 && datTables.SelectedRows.Count > 0)
            {
                datTables.Rows[0].Selected = false;
                datTables.Rows[0].Selected = true;
            }
        }
        private void AddTable()
        {
            AddTableForm form = new AddTableForm(m_Server.PlayerName, 1);
            form.ShowDialog();
            if (form.OK)
            {
                int noPort = m_Server.CreateTable(form.TableName, form.BigBlind, form.NbPlayer, form.WaitingTimeAfterPlayerAction, form.WaitingTimeAfterBoardDealed, form.WaitingTimeAfterPotWon, form.Limit);

                if (noPort != -1)
                {
                    JoinTable(noPort, form.TableName, form.BigBlind);
                    RefreshTables();
                }
                else
                {
                    Console.WriteLine("Cannot create table: '" + form.TableName + "'");
                }
            }
        }

        private void LeaveTable(GameClient client)
        {
            if (client != null)
            {
                client.Disconnect();
                RefreshTables();
            }
        }
        public bool JoinTable(int p_noPort, String p_tableName, int p_bigBlindAmount)
        {
            AbstractTableForm gui = new TableForm();
            GameClient tcpGame = m_Server.JoinTable(p_noPort, p_tableName, gui);
            gui.FormClosed += delegate
            {
                LeaveTable(tcpGame);
            };
            return true;
        }

        public void AllowJoinOrLeave()
        {
            bool selected = datTables.RowCount > 0 && datTables.SelectedRows.Count > 0;
            GameClient client = findClient();
            btnJoinTable.Enabled = selected && (client == null);
            btnLeaveTable.Enabled = selected && (client != null);
        }
        private void btnRefresh_Click(object sender, EventArgs e)
        {
            RefreshTables();
            AllowJoinOrLeave();
        }

        private void btnAddTable_Click(object sender, EventArgs e)
        {
            AddTable();
            RefreshTables();
        }

        private void btnJoinTable_Click(object sender, EventArgs e)
        {
            AllowJoinOrLeave();
            if (datTables.RowCount == 0 || datTables.SelectedRows.Count == 0)
            {
                return;
            }
            object o = datTables.SelectedRows[0].Cells[0].Value;
            if (o == null) 
                return;
            int noPort = (int)o;
            object o2 = datTables.SelectedRows[0].Cells[1].Value;
            if (o2 == null) 
                return;
            string tableName = (string)o2;
            if (findClient() != null)
                Console.WriteLine("You are already sitting on the table: " + tableName);
            else
            {
                object o3 = datTables.SelectedRows[0].Cells[3].Value;
                if (o3 == null) 
                    return;
                int bigBlind = (int)o3;
                if (!JoinTable(noPort, tableName, bigBlind))
                    Console.WriteLine("Table '" + tableName + "' does not exist anymore.");
                RefreshTables();

            }
        }

        private void btnLeaveTable_Click(object sender, EventArgs e)
        {
            LeaveTable(findClient());
            RefreshTables();
        }

        private GameClient findClient()
        {
            if (datTables.RowCount == 0 || datTables.SelectedRows.Count == 0)
            {
                return null;
            }
            object o = datTables.SelectedRows[0].Cells[0].Value;
            if (o == null) 
                return null;
            int noPort = (int)o;
            return m_Server.FindClient(noPort);
        }

        private void datTables_CellDoubleClick(object sender, DataGridViewCellEventArgs e)
        {
            btnJoinTable_Click(datTables, new EventArgs());
        }

        private void datTables_SelectionChanged(object sender, EventArgs e)
        {
            AllowJoinOrLeave();
        }

        private void datTables_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            AllowJoinOrLeave();
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
    }
}
