using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Drawing;

namespace BluffinPokerClient
{
    public enum StatePictureBoxStates
    {
        None,
        Waiting,
        Bad,
        Ok
    }
    public class StatePictureBox : PictureBox
    {
        private delegate void EmptyHandler();
        private Timer waitingTimer;
        private int waitingCounter = 0;
        private StatePictureBoxStates m_Etat = StatePictureBoxStates.None;
        public StatePictureBoxStates Etat
        {
            get
            {
                return m_Etat;
            }
            set
            {
                if (m_Etat != value)
                {
                    m_Etat = value;
                    UpdateBackgroundImage();
                }
            }
        }
        public StatePictureBox()
            : base()
        {
            this.BackgroundImageLayout = ImageLayout.Stretch;
            this.Size = new Size(20, 23);
        }
        private void UpdateBackgroundImage()
        {
            if (this.InvokeRequired)
            {
                this.Invoke(new EmptyHandler(UpdateBackgroundImage), new object[] { });
                return;
            }
            switch (m_Etat)
            {
                case StatePictureBoxStates.None:
                    BackgroundImage = null;
                    break;
                case StatePictureBoxStates.Waiting:
                    waitingTimer = new Timer();
                    waitingTimer.Interval = 100;
                    waitingTimer.Tick += new EventHandler(waitingTimer_Tick);
                    waitingTimer.Start();
                    BackgroundImage = Properties.Resources.waiting0;
                    break;
                case StatePictureBoxStates.Bad:
                    BackgroundImage = Properties.Resources.bad;
                    break;
                case StatePictureBoxStates.Ok:
                    BackgroundImage = Properties.Resources.OK;
                    break;
            }
            Invalidate();
        }
        private void waitingTimer_Tick(object sender, EventArgs e)
        {
            if (m_Etat == StatePictureBoxStates.Waiting)
            {
                if (this.InvokeRequired)
                {
                    this.Invoke(new EventHandler(waitingTimer_Tick), new object[] { sender, e });
                    return;
                }
                waitingCounter++;
                waitingCounter %= 8;
                switch (waitingCounter)
                {
                    case 0:
                        BackgroundImage = Properties.Resources.waiting0;
                        break;
                    case 1:
                        BackgroundImage = Properties.Resources.waiting1;
                        break;
                    case 2:
                        BackgroundImage = Properties.Resources.waiting2;
                        break;
                    case 3:
                        BackgroundImage = Properties.Resources.waiting3;
                        break;
                    case 4:
                        BackgroundImage = Properties.Resources.waiting4;
                        break;
                    case 5:
                        BackgroundImage = Properties.Resources.waiting5;
                        break;
                    case 6:
                        BackgroundImage = Properties.Resources.waiting6;
                        break;
                    case 7:
                        BackgroundImage = Properties.Resources.waiting7;
                        break;
                }
            }
            else
                waitingTimer.Stop();
        }
    }
}
