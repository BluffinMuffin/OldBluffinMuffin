using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Windows.Forms;
using PokerProtocol;
using BluffinPokerGui.Game;
using EricUtility;
using PokerProtocol.Entities;

namespace BluffinPokerGUI.Lobby
{
    public partial class PokerTableList : UserControl
    {
        private LobbyTCPClient m_Server;
        private bool m_ShowTraining = true;
        private bool m_ShowCareer = false;

        public bool ShowTraining
        {
            get { return m_ShowTraining; }
            set { m_ShowTraining = value; }
        }

        public bool ShowCareer
        {
            get { return m_ShowCareer; }
            set { m_ShowCareer = value; }
        }

        public PokerTableList()
        {
            InitializeComponent();
        }

        public void setServer(LobbyTCPClient server)
        {
            m_Server = server;
            m_Server.ServerLost += new DisconnectDelegate(m_Server_ServerLost);
        }

        void m_Server_ServerLost()
        {
            foreach (AbstractTableForm f in guis.Values)
                f.ForceKill();
        }

        public int NbTables { get { return datTables.RowCount; } }
        public bool SomethingSelected { get { return datTables.RowCount > 0 && datTables.SelectedRows.Count > 0; } }
        public event EventHandler OnListRefreshed = delegate { };
        public event EventHandler OnSelectionChanged = delegate { };
        public event EventHandler OnChoiceMade = delegate { };
        private Dictionary<int, AbstractTableForm> guis = new Dictionary<int, AbstractTableForm>();
        
        public void RefreshList()
        {
            datTables.Rows.Clear();
            List<Table> lst = new List<Table>();
            if (ShowTraining)
            {
                List<TableTraining> lstT = ((LobbyTCPClientTraining)m_Server).ListTables();
                lst.AddRange(lstT.ToArray());
            }
            if (ShowCareer)
            {
                List<TableCareer> lstT = ((LobbyTCPClientCareer)m_Server).ListTables();
                lst.AddRange(lstT.ToArray());
            }
            lst.Sort();
            for (int i = 0; i < lst.Count; ++i)
            {
                Table info = lst[i];
                string type = info is TableTraining ? "Training" : "Real";
                datTables.Rows.Add();
                datTables.Rows[i].Cells[0].Value = info.NoPort;
                datTables.Rows[i].Cells[1].Value = info.TableName;
                datTables.Rows[i].Cells[2].Value = type + " - " + info.Limit.ToString();
                datTables.Rows[i].Cells[3].Value = info.BigBlind;
                datTables.Rows[i].Cells[4].Value = info.NbPlayers + "/" + info.NbSeats;
            }
            if (datTables.RowCount > 0 && datTables.SelectedRows.Count > 0)
            {
                datTables.Rows[0].Selected = false;
                datTables.Rows[0].Selected = true;
            }
            OnListRefreshed(this, new EventArgs());
        }
        public void AddTable(bool trainingOnly)
        {
            AddTableForm form = new AddTableForm(m_Server.PlayerName, 1, trainingOnly, m_Server.GetSupportedRules());
            form.ShowDialog();
            if (form.OK)
            {
                int noPort = -1;
                if (form.Training)
                    noPort = ((LobbyTCPClientTraining)m_Server).CreateTable(form.TableName, form.BigBlind, form.NbPlayer, form.WaitingTimeAfterPlayerAction, form.WaitingTimeAfterBoardDealed, form.WaitingTimeAfterPotWon, form.Limit, form.NbPlayerMin, form.TrainingStartingAmount);
                else
                    noPort = ((LobbyTCPClientCareer)m_Server).CreateTable(form.TableName, form.BigBlind, form.NbPlayer, form.WaitingTimeAfterPlayerAction, form.WaitingTimeAfterBoardDealed, form.WaitingTimeAfterPotWon, form.Limit, form.NbPlayerMin);
                
                if (noPort != -1)
                {
                    JoinTable(noPort, form.TableName, form.BigBlind);
                    RefreshList();
                }
                else
                {
                    LogManager.Log(LogLevel.Error, "PokerTableList.AddTable", "Cannot create table: '{0}'", form.TableName); 
                }
            }
        }
        public void LeaveAll()
        {
            int[] keys = new int[guis.Keys.Count];
            guis.Keys.CopyTo(keys,0);
            foreach (int i in keys)
                LeaveTable(i);
        }
        private void LeaveTable(int idGame)
        {
            if (guis.ContainsKey(idGame))
            {
                AbstractTableForm gui = guis[idGame];
                guis.Remove(idGame);
                gui.Close();
            }
            else
            {
                m_Server.LeaveTable(idGame);
            }
            RefreshList();
        }
        public void LeaveSelected()
        {
            LeaveTable(FindClientId());
        }
        public void JoinSelected()
        {

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
            if (FindClient() != null)
                LogManager.Log(LogLevel.Error, "PokerTableList.JoinSelected", "You are already sitting on the table: '{0}'", tableName);
            else
            {
                object o3 = datTables.SelectedRows[0].Cells[3].Value;
                if (o3 == null)
                    return;
                int bigBlind = (int)o3;
                if (!JoinTable(noPort, tableName, bigBlind))
                    LogManager.Log(LogLevel.Error, "PokerTableList.JoinSelected", "Table '{0}' does not exist anymore.", tableName);
                RefreshList();

            }
        }
        private bool JoinTable(int p_noPort, String p_tableName, int p_bigBlindAmount)
        {
            AbstractTableForm gui = new TableForm();
            GameClient tcpGame = m_Server.JoinTable(p_noPort, p_tableName, gui);
            if (guis.ContainsKey(p_noPort))
                guis[p_noPort] = gui;
            else
                guis.Add(p_noPort, gui);
            gui.FormClosed += delegate
            {
                if( !gui.DirectKill )
                    LeaveTable(p_noPort);
            };
            return true;
        }

        public GameClient FindClient()
        {
            return m_Server.FindClient(FindClientId());
        }

        public int FindClientId()
        {
            if (datTables.RowCount == 0 || datTables.SelectedRows.Count == 0)
            {
                return -1;
            }
            object o = datTables.SelectedRows[0].Cells[0].Value;
            if (o == null)
                return -1;
            return (int)o;
        }

        private void datTables_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            OnSelectionChanged(this, new EventArgs());
        }

        private void datTables_CellDoubleClick(object sender, DataGridViewCellEventArgs e)
        {
            OnChoiceMade(this, new EventArgs());
        }

        private void datTables_SelectionChanged(object sender, EventArgs e)
        {
            OnSelectionChanged(this, new EventArgs());
        }
    }
}
