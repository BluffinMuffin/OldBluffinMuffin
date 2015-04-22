using System.Windows.Forms;
using BluffinMuffin.Poker.DataTypes;
using Com.Ericmas001.Util;

namespace BluffinMuffin.Poker.Windows.Forms.Game
{
    public partial class AbstractTableForm : Form, IPokerViewer
    {
        private bool m_IsBeingKilled;
        protected IPokerGame m_Game;
        protected int m_NoSeat = -1;

        protected AbstractTableForm()
        {
            InitializeComponent();
        }

        public virtual void SetGame(IPokerGame c)
        {
            m_Game = c;
        }

        public virtual void Start()
        {
            Show();
        }

        public bool DirectKill{ get{ return m_IsBeingKilled;}}

        public void ForceKill()
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EmptyHandler(ForceKill), new object[] {});
                return;
            }
            m_IsBeingKilled = true;
            Close();
        }
    }
}
