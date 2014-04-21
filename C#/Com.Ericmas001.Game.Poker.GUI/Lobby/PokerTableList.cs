using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Windows.Forms;
using Com.Ericmas001.Game.Poker.Protocol.Client;
using Com.Ericmas001.Game.Poker.GUI.Game;
using Com.Ericmas001.Util;
using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using Com.Ericmas001.Game.Poker.DataTypes.Parameters;

namespace Com.Ericmas001.Game.Poker.GUI.Lobby
{
    public partial class PokerTableList : UserControl
    {
        private LobbyTCPClient m_Server;

        public bool ShowTraining { get; set; }
        public bool ShowCareer { get; set; }
        public LobbyTypeEnum LobbyType { get; set; }
        public ITableFormFactory TableFormFactory { get; set; }

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
            List<TupleTable> lst = new List<TupleTable>();

            if (ShowTraining)
                lst.AddRange(m_Server.ListTables(LobbyTypeEnum.Training).ToArray());
            if (ShowCareer)
                lst.AddRange(m_Server.ListTables(LobbyTypeEnum.Career).ToArray());

            lst.Sort();
            for (int i = 0; i < lst.Count; ++i)
            {
                TupleTable info = lst[i];
                string type = info.Params.Lobby.OptionType == LobbyTypeEnum.Training ? "Training" : "Real";
                datTables.Rows.Add();
                datTables.Rows[i].Cells[0].Value = info.IdTable;
                datTables.Rows[i].Cells[1].Value = info.Params.TableName;
                datTables.Rows[i].Cells[2].Value = type + " - " + EnumFactory<LimitTypeEnum>.ToString(info.Params.Limit.OptionType);
                datTables.Rows[i].Cells[3].Value = info.BigBlind;
                datTables.Rows[i].Cells[4].Value = info.NbPlayers + "/" + info.Params.MaxPlayers;
            }
            if (datTables.RowCount > 0 && datTables.SelectedRows.Count > 0)
            {
                datTables.Rows[0].Selected = false;
                datTables.Rows[0].Selected = true;
            }
            OnListRefreshed(this, new EventArgs());
        }
        public void AddTable()
        {
            CreateTableForm ctf = new CreateTableForm(m_Server.PlayerName, 1, LobbyType, m_Server.GetSupportedRules());
            ctf.ShowDialog();
            TableParams parms = ctf.Params;
            if(parms != null)
            {
                int id = m_Server.CreateTable(parms);
                if (id != -1)
                {
                    JoinTable(id, parms.TableName);
                    RefreshList();
                }
                else
                {
                    LogManager.Log(LogLevel.Error, "PokerTableList.AddTable", "Cannot create table: '{0}'", parms.TableName);
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
                if (!JoinTable(noPort, tableName))
                    LogManager.Log(LogLevel.Error, "PokerTableList.JoinSelected", "Table '{0}' does not exist anymore.", tableName);
                RefreshList();

            }
        }
        private bool JoinTable(int p_noPort, String p_tableName)
        {
            AbstractTableForm gui = TableFormFactory.ObtainGUI();
            GameTCPClient tcpGame = m_Server.JoinTable(p_noPort, p_tableName, gui);
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

        public GameTCPClient FindClient()
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
