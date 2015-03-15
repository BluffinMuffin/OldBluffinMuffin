using System;
using System.Windows.Forms;
using BluffinMuffin.Poker.Windows.Forms.Properties;

namespace BluffinMuffin.Poker.Windows.Forms
{
    public partial class LogConsole : UserControl
    {
        public event EventHandler<IntEventArgs> RelativeSizeChanged = delegate { };

        private bool m_Locked;
        private bool m_Collapsed;
        public LogConsole()
        {
            InitializeComponent();
        }

        private void btnLock_Click(object sender, EventArgs e)
        {
            if (m_Locked)
            {
                //Unlock
                m_Locked = false;
                btnLock.Text = Resources.LogConsole_btnLock_Click_Lock;
            }
            else
            {
                if (!m_Collapsed)
                {
                    //Lock
                    m_Locked = true;
                    btnLock.Text = Resources.LogConsole_btnLock_Click_Unlock;
                }
            }
        }

        private void btnCollapse_Click(object sender, EventArgs e)
        {
            var old = Height;
            if (m_Collapsed)
            {
                //UnCollapse
                m_Collapsed = false;
                btnCollapse.Text = Resources.LogConsole_btnCollapse_Click_Up;
                txtLog.Visible = true;
                Height += txtLog.Height;
                txtLog.SelectionStart = txtLog.TextLength;
                txtLog.Focus();
                txtLog.ScrollToCaret();
            }
            else
            {
                //Collapse
                m_Collapsed = true;
                btnCollapse.Text = Resources.LogConsole_btnCollapse_Click_Down;
                txtLog.Visible = false;
                Height -= txtLog.Height;
                m_Locked = false;
                btnLock.Text = Resources.LogConsole_btnLock_Click_Lock;
            }
            RelativeSizeChanged(this, new IntEventArgs(Height - old));
        }

        public void Clear()
        {
            txtLog.Text = "";
        }

        public void WriteLine(string line)
        {
            Write(line + Environment.NewLine);
        }

        private delegate void DelegateWrite(string s);
        public void Write(string msg)
        {
            if (InvokeRequired)
            {
                Invoke(new DelegateWrite(Write), new object[] { msg });
                return;
            }
            var old = txtLog.SelectionStart;
            txtLog.Text += msg;
            if (!m_Locked)
            {
                txtLog.SelectionStart = txtLog.TextLength;
            }
            else
            {
                txtLog.SelectionStart = old;
            }
            txtLog.Focus();
            txtLog.ScrollToCaret();
        }
    }
}
