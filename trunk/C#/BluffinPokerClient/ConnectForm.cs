using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace BluffinPokerClient
{
    public partial class ConnectForm : Form
    {
        private string m_PlayerName;
        private string m_ServerAddress;
        private int m_ServerPort;
        private bool m_OK;

        public string PlayerName
        {
            get { return m_PlayerName; }
        }

        public string ServerAddress
        {
            get { return m_ServerAddress; }
        }

        public int ServerPort
        {
            get { return m_ServerPort; }
        }

        public bool OK
        {
            get { return m_OK; }
        }

        public ConnectForm()
        {
            m_OK = false;
            InitializeComponent();
        }

        private void btnConnect_Click(object sender, EventArgs e)
        {
            m_PlayerName = txtPlayerName.Text;
            m_ServerAddress = clstServerName.Text;
            m_ServerPort = (int)nudServerPort.Value;
            m_OK = true;
            Close();
        }
    }
}
