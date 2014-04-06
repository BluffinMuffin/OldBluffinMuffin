using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Windows.Forms;

namespace Com.Ericmas001.Game.BluffinMuffin.GUI
{
    public partial class LogConsole : UserControl
    {
        public event EventHandler<IntEventArgs> RelativeSizeChanged = delegate { };

        private bool locked = false;
        private bool collapsed = false;
        public LogConsole()
        {
            InitializeComponent();
        }

        private void btnLock_Click(object sender, EventArgs e)
        {
            if (locked)
            {
                //Unlock
                locked = false;
                btnLock.Text = "Lock";
            }
            else
            {
                if (!collapsed)
                {
                    //Lock
                    locked = true;
                    btnLock.Text = "Unlock";
                }
            }
        }

        private void btnCollapse_Click(object sender, EventArgs e)
        {
            int old = Height;
            if (collapsed)
            {
                //UnCollapse
                collapsed = false;
                btnCollapse.Text = "^";
                txtLog.Visible = true;
                Height += txtLog.Height;
                txtLog.SelectionStart = txtLog.TextLength;
                txtLog.Focus();
                txtLog.ScrollToCaret();
            }
            else
            {
                //Collapse
                collapsed = true;
                btnCollapse.Text = "v";
                txtLog.Visible = false;
                Height -= txtLog.Height;
                locked = false;
                btnLock.Text = "Lock";
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
            if (this.InvokeRequired)
            {
                this.Invoke(new DelegateWrite(Write), new object[] { msg });
                return;
            }
            int old = txtLog.SelectionStart;
            txtLog.Text += msg;
            if (!locked)
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
