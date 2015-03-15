using System.Windows.Forms;
using BluffinMuffin.Poker.DataTypes;

namespace BluffinMuffin.Poker.Windows.Forms.Game
{
    public partial class AbstractTableForm : Form, IPokerViewer
    {
        private bool m_IsBeingKilled;
        protected IPokerGame m_Game;
        protected string m_PlayerName;
        protected int m_NoSeat = -1;
        public AbstractTableForm()
        {
            InitializeComponent();
        }

        public virtual void SetGame(IPokerGame c, string n)
        {
            m_Game = c;
            m_PlayerName = n;
        }

        public virtual void Start()
        {
            Show();
        }

        public bool DirectKill{ get{ return m_IsBeingKilled;}}

        public delegate void EmptyDelegate();
        public void ForceKill()
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EmptyDelegate(ForceKill), new object[] {});
                return;
            }
            m_IsBeingKilled = true;
            Close();
        }
    }
}
